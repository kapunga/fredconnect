package org.kapunga.fredconnect

import com.stackmob.newman.ApacheHttpClient
import com.stackmob.newman.dsl._
import com.stackmob.newman.response.HttpResponse
import com.stackmob.newman.response.HttpResponseCode.Ok

import play.api.libs.json._

import scala.concurrent.Await
import scala.concurrent.duration._

import java.net.URL
import java.util.concurrent.TimeoutException

protected class FredApiClient(apiKey: String, batchSize: Int = 10) {
  implicit val httpClient = new ApacheHttpClient

  def idQuery(query: FredQuery.QueryType, id: Int): Option[JsValue] = {
    executeRequest(FredApiClient.getBaseUrlString(query) + s"/$id") match {
      case Some(item) => Some(item \ query.query)
      case None => None
    }
  }

  def listQuery(query: FredQuery.QueryType, params: Map[String, String]): Seq[JsValue] = {
    val queryUrl: String = FredApiClient.getBaseUrlString(query) + "?" + getQueryString(params)

    var total = 0
    var page = 0

    var list: Seq[JsValue] = Seq.empty[JsValue]

    do {
      page += 1

      executeRequest(queryUrl + s"_page=$page&_per_page$batchSize") match {
        case Some(json) =>
          total = json \ "total_matched" match {
            case JsNumber(num) => num.intValue()
            case _ => total
          }

          list = list ++ {
            json \ query.listMap match {
              case JsArray(elements) => elements
              case _ => Seq.empty[JsValue]
            }
          }
        case None => Unit
      }
    } while (total > page * batchSize)

    list
  }

  private def getQueryString(params: Map[String, String]): String = {
    var queryString: String = ""

    for ((key, value) <- params) {
      queryString = queryString + key + "=" + value + "&"
    }

    queryString
  }

  private def executeRequest(urlString: String): Option[JsValue] = {
    val url = new URL(urlString)

    val response = fetchResult(url, FredApiClient.retries) match {
      case Some(result) => result
      case None => throw new FredRequestFailedException(FredFailureReason.RequestTimedOut)
    }

    response.code match {
      case Ok => Some(Json.parse(response.bodyString))
      case _ => None
    }
  }

  private def fetchResult(url: URL, retries: Int): Option[HttpResponse] = {
    if (retries <= 0) None

    try {
      Some(Await.result(GET(url).addHeaders(FredApiClient.apiHeader -> apiKey).apply, FredApiClient.timeout.second))
    } catch {
      case e: TimeoutException => fetchResult(url, retries - 1)
    }
  }
}

object FredApiClient {
  val apiHeader = "X-Fred-Api-Key"
  val baseApiUrl = "api.askfred.net/v1/"

  val timeout = 5
  val retries = 3

  def getBaseUrlString(query: FredQuery.QueryType): String = {
    s"https://${baseApiUrl}${query.query}"
  }
}


