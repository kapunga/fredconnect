package org.kapunga

import java.text.SimpleDateFormat

import play.api.libs.json.{Reads, Writes}

import scala.language.implicitConversions

/**
 * Created by kapunga on 9/24/14.
 */
package object fredconnect {
  val defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd")
  implicit val dateTimeReads = Reads.dateReads("yyyy-MM-dd")

  abstract class QueryParams {
    val MAX_IDS: Int = 100

    def getQueryMap(): Map[String, String]
    
    protected def getIdsKv(tag: String)(implicit ids: List[Int]): (String, String) = {
      if (ids.length > MAX_IDS)
        throw new IllegalArgumentException(s"${ids.length} is greater than the maximum ids of ${MAX_IDS}.")
      
      (tag -> ids.mkString(","))
    }
  }

  implicit def queryParamsToMap(queryParams: QueryParams): Map[String, String] = queryParams.getQueryMap()

  /**
   * An enumeration representing the three weapons in fencing.
   *
   * @author Paul J Thordarson
   * @since 0.1
   */
  object Weapon extends Enumeration {
    type Weapon = Value

    val Foil, Epee, Saber = Value
  }

  /**
   * An enum eration representing the letter ratings used by the USFA.
   *
   * @author Paul J Thordarson
   * @since 0.1
   */
  object RatingLetter extends Enumeration {
    type RatingLetter = Value

    val A, B, C, D, E, U = Value

    def getRatingLetter(letter: String): RatingLetter = {
      letter match {
        case "A" => A
        case "B" => B
        case "C" => C
        case "D" => D
        case "E" => E
        case _ => U
      }
    }
  }

  object EventRating extends Enumeration {
    type EventRating = Value

    val A1, A2, A3, A4, B1, B2, B3, C1, C2, C3, D1, E1, Unrated = Value
  }

  object RatingLimit extends Enumeration {
    type RatingLimit = Value

    val Open, Unrated, Eunder, Div3, Div2, Div1a, Dabove, Div1, Babove, Aonly = Value
  }

  object AgeLimit extends Enumeration {
    type AgeLimit = Value

    val None, Y8, Y10, Y12, Y14, U16, U19, Vet40, Vet50, Vet60, Vet70, VetCombined = Value
  }

  object SearchAgeLimit extends Enumeration {
    type SearchAgeLimit = Value

    val None, Y8, Y10, Y12, Y14, U16, U19, Vet40, Vet50, Vet60, Vet70, Vetcombined, Youth,
        Cadet, Junior, Cadet_lte, Junior_lte, Senior, Open, Senior_Open, Veteran = Value
  }

  object Gender extends Enumeration {
    type Gender = Value

    val Male, Female = Value
  }

  object EventGender extends Enumeration {
    type EventGender = Value

    val Men, Women, Mixed = Value
  }

  object SearchGender extends Enumeration {
    type SearchGender = Value

    val Men, Men_Mixed, Women, Women_Mixed, Mixed = Value
  }

  object QueryEval extends Enumeration {
    type QueryEval = Value

    val LTE, GTE, EQ = Value
  }

  object RoundType extends Enumeration {
    type RoundType = Value

    val POOLS, DE_TABLES = Value
  }

  object BoutResult extends Enumeration {
    type BoutResult = Value

    val V, D = Value
  }

  object FredQuery extends Enumeration {
    val tournament = QueryType("tournament", "tournaments")
    val event = QueryType("event", "events")
    val roundresult = QueryType("roundresult", "rounds")
    val fencer = QueryType("fencer", "fencers")
    val result = QueryType("result", "results")

    final case class QueryType(query: String, listMap: String) extends Val
  }
}
