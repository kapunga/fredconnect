package org.kapunga.fredconnect

import org.kapunga.fredconnect.AgeLimit.AgeLimit
import org.kapunga.fredconnect.EventGender.EventGender
import org.kapunga.fredconnect.RatingLimit.RatingLimit
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
 * @param name
 * @param address
 * @param city
 * @param state
 * @param zipCode
 * @param country
 * @param timezone
 * @param latitude
 * @param longitude
 * @param precision
 */
case class Venue(name: String, address: String, city: String, state: String, zipCode: String, country: String,
                 timezone: String, latitude: Double, longitude: Double, precision: String)

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

case class Tournament(id: Int, name: String, venue: Venue, division: Division, events: List[Event]) extends FredData
/*
{
  "start_date" : "2014-09-26",
  "end_date" : "",
  "comments" : "Mixed open foil. Non-NEUSFA, fast paced, less wait. Entry fee is tax deductible fee as proceeds go to Moe Fencing Foundation: 501(c)(3) providing underserved K-12 Greater Boston youth access to a comprehensive fencing program. Great training tournament!",
  "prereg_open" : "2014-09-22",
  "prereg_close" : "2014-09-25",
  "authority" : "NONE",
  "is_fee_required" : true,
  "is_roc" : false,
  "is_baycup" : false,
  "is_visible" : true,
  "is_cancelled" : false,
  "is_accepting_fees_online" : true,
  "fee" : 0,
  "fee_currency" : "USD",
  "more_info" : "Mixed open foil. Non-NEUSFA, fast paced, less wait. Entry fee is tax deductible fee as proceeds go to Moe Fencing Foundation: 501(c)(3) providing underserved K-12 Greater Boston youth access to a comprehensive fencing program. Great training tournament!\r\n\r\nNO Y10 FENCERS WITHOUT PRIOR APPROVAL of MOE WEN COACH!\r\n\r\nIf you are not pre-paid before the close of registration, the event fee doubles (so pre-pay online in advance or pay $40 at the door.)\r\n\r\nEvents Scheduled:\r\nMixed Foil check in closes at 6:45pm.\r\n\r\nOnce pools are posted, we cannot accept new entries. We will not give refunds to fencers who are late.\r\n\r\nProof of USFA membership and full fencing uniform (including electric bib and long socks) required.\r\n\r\nActivity participation waiver required. May be completed on day of event at venue.\r\n\r\n*all paying participants are included in a raffle with prize of minimum $15 value if the tournament raises $100 or more for Moe Fencing Foundation Inc.\r\n\r\nMoe Wen Fencing Club is a private club and reserves the right to review and deny participation in events held at its venue on a case by case basis.\r\n\r\n\r\nPreregistration opens on 09/22/2014.\r\nPreregistration closes on 09/25/2014.\r\n\r\n",
}
*/

case class Event(id: Int, tournamentId: Int, name: String, weapon: Weapon, gender: EventGender, ageLimit: AgeLimit,
                 ratingLimit: RatingLimit, entries: Int, preRegs: Int, isTeamEvent: Boolean) extends FredData
/*
{
  "authority" : { },
  "rating" : "",
  "rating_prediction" : "",
  "description" : "",
  "close_of_reg" : "2014-09-26T18:45:00-04:00",
  "fee" : "20.00"
}
*/
