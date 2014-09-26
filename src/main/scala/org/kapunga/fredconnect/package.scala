package org.kapunga

/**
 * Created by kapunga on 9/24/14.
 */
package object fredconnect {
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

  object Gender extends Enumeration {
    type Gender = Value

    val Male = Value("M")
    val Female = Value("F")
  }

  object FredQuery extends Enumeration {
    val tournament = QueryType("tournament", "tournaments")
    val event = QueryType("event", "events")
    val roundresult = QueryType("roundresult", "roundresults")
    val fencer = QueryType("fencer", "fencers")
    val result = QueryType("result", "results")

    final case class QueryType(query: String, listMap: String) extends Val
  }
}
