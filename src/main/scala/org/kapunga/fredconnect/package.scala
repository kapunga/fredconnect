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

    val Foil = Value("Foil")
    val Epee = Value("Epee")
    val Saber = Value("Saber")
  }

  object RatingLetter extends Enumeration {
    type RatingLetter = Value

    val A = Value("A")
    val B = Value("B")
    val C = Value("C")
    val D = Value("D")
    val E = Value("E")
    val U = Value("U")
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

    val Male = Value("M")
    val Female = Value("F")
  }

  object EventGender extends Enumeration {
    type EventGender = Value

    val Men, Women, Mixed = Value
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
