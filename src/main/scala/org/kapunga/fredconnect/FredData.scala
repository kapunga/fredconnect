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

case class Result(id: Int, eventId: Int, tournamentId: Int)

/*
  "event_id" : 104519,
  "tournament_id" : 26218,
  "competitor_id" : 43595,
  "first_name" : "Paul",
  "last_name" : "Thordarson",
  "tournament_name" : "2014 Massachusetts Bay State Games",
  "tournament_start_date" : "2014-07-11",
  "tournament_end_date" : "2014-07-13",
  "venue_name" : "Prise De Fer Fencing Club",
  "venue_address" : "71 Faulkner St",
  "venue_city" : "Billerica",
  "venue_state" : "MA",
  "venue_zip" : "01862",
  "venue_country" : "USA",
  "venue_latitude" : 42.592204,
  "venue_longitude" : -71.284101,
  "venue_geo_precision" : "street",
  "weapon" : "Epee",
  "gender" : "Men",
  "age_limit" : "Senior",
  "rating_limit" : "Open",
  "event_rating" : "B2",
  "entries" : 35,
  "is_team" : false,
  "event_desc" : "",
  "event_date" : "2014-07-13",
  "event_time" : "13:30:00",
  "authority" : "USFA",
  "place" : 23,
  "tournament_division_id" : 42,
  "competitor_division_id" : 42,
  "club_id_1" : 6859,
  "club_1_name" : "Olympia Fencing Center",
  "club_1_initials" : "OLYMPIAFC",
  "rating_before_letter" : "D",
  "rating_before_year" : 2014,
  "is_excluded" : false,
  "is_withdraw" : false
 */

