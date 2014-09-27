package org.kapunga

import scala.language.implicitConversions

/**
 * Created by kapunga on 9/24/14.
 */
package object fredconnect {
  abstract class QueryParams {
    val MAX_IDS:Int = 100

    def getQueryMap(): Map[String, String]
  }

  implicit def queryParamsToMap(queryParams: QueryParams): Map[String, String] = queryParams.getQueryMap()

  object Weapon extends Enumeration {
    type Weapon = Value

    val Foil, Epee, Saber = Value
  }

  object RatingLetter extends Enumeration {
    type RatingLetter = Value

    val A, B, C, D, E, U = Value
  }

  object RatingLimit extends Enumeration {
    type RatingLimit = Value

    val Open, Unrated, Eunder, Div3, Div2, Div1a, Dabove, Div1, Babove, Aonly = Value
  }

  object AgeLimit extends Enumeration {
    type AgeLimit = Value

    val None, Y8, Y10, Y12, Y14, U16, U19, Vet40, Vet50, Vet60, Vet70, VetCombined = Value
  }

  object Gender extends Enumeration {
    type Gender = Value

    val Male, Female = Value
  }

  object EventGender extends Enumeration {
    type EventGender = Value

    val Men, Women, Mixed = Value
  }

  object QueryEval extends Enumeration {
    type QueryEval = Value

    val LTE, GTE, EQ = Value
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
