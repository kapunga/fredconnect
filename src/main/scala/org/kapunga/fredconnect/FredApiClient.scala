package org.kapunga.fredconnect

import com.stackmob.newman.ApacheHttpClient
import com.stackmob.newman.dsl._
import com.stackmob.newman.response.HttpResponseCode.Ok
import scala.concurrent.duration._
import play.api.libs.json._

import java.net.URL

import scala.concurrent.Await

class FredApiClient(apiKey: String) {
  implicit val httpClient = new ApacheHttpClient

  def idQuery(query: FredQuery.QueryType, id: Int): Option[JsValue] = {
    executeRequest(FredApiClient.getBaseUrlString(query) + s"/${id}") match {
      case Some(item) => Some(item \ query.query)
      case None => None
    }
  }

  def listQuery(query: FredQuery.QueryType, params: Map[String, String]): Seq[JsValue] = {
    val queryUrl: String = FredApiClient.getBaseUrlString(query) + "?" + getQueryString(params)

    var total = 0
    var page = 0
    var perPage = 10

    var list: Seq[JsValue] = Seq.empty[JsValue]

    do {
      page += 1

      executeRequest(queryUrl + s"_page=${page}&_per_page${perPage}") match {
        case Some(json) =>
          total = (json \ "total_matched") match {
            case JsNumber(num) => num.intValue
            case _ => total
          }

          list = list ++ {
            (json \ query.listMap) match {
              case JsArray(elements) => elements
              case _ => Seq.empty[JsValue]
            }
          }
        case None => Unit
      }
    } while (total > page * perPage)

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

    val response = Await.result(GET(url).addHeaders(FredApiClient.apiHeader -> apiKey).apply,
                                FredApiClient.timeout.second)

    response.code match {
      case Ok => Some(Json.parse(response.bodyString))
      case _ => None
    }
  }
}

object FredApiClient {
  val apiHeader = "X-Fred-Api-Key"
  val baseApiUrl = "api.askfred.net/v1/"

  val timeout = 5

  def getBaseUrlString(query: FredQuery.QueryType): String = {
    s"https://${baseApiUrl}${query.query}"
  }
}


