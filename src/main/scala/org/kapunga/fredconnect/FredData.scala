package org.kapunga.fredconnect

import org.kapunga.fredconnect.Weapon.Weapon
import org.kapunga.fredconnect.RatingLetter.RatingLetter
import org.kapunga.fredconnect.Gender.Gender

sealed abstract class FredData {
  val id: Int
}

/**
 *
 * @param usfa
 * @param cff
 * @param fie
 * @param id
 */
case class AuthorityId(usfa: String, cff: String, fie: String, id: Int = -1) extends FredData

/**
 *
 * @param id
 * @param name
 * @param abbrev
 */
case class Division(id: Int, name: String, abbrev: String) extends FredData

/**
 *
 * @param id
 * @param name
 * @param initials
 */
case class Club(id: Int, name: String, initials: String) extends FredData

/**
 *
 * @param id
 * @param weapon
 * @param letter
 * @param year
 * @param authority
 */
case class Rating(id: Int, weapon: Weapon, letter: RatingLetter, year: String, authority: String) extends FredData {
  def shortValue(): String = {
    letter match {
      case RatingLetter.U => "U"
      case _ => letter.toString + year.substring(2)
    }
  }
}

/**
 *
 * @param id
 * @param authIds
 * @param firstName
 * @param lastName
 * @param gender
 * @param birthYear
 * @param division
 * @param clubs
 * @param ratings
 */
case class Fencer(id: Int, authIds: AuthorityId, firstName: String, lastName: String, gender: Gender, birthYear: Int,
                  division: Division, clubs: List[Club], ratings: Map[Weapon, Rating]) extends FredData

