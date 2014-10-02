package org.kapunga.fredconnect

import java.util.Date

import org.kapunga.fredconnect.AgeLimit.AgeLimit
import org.kapunga.fredconnect.BoutResult.BoutResult
import org.kapunga.fredconnect.EventGender.EventGender
import org.kapunga.fredconnect.EventRating.EventRating
import org.kapunga.fredconnect.RatingLimit.RatingLimit
import org.kapunga.fredconnect.RoundType.RoundType
import org.kapunga.fredconnect.Weapon.Weapon
import org.kapunga.fredconnect.RatingLetter.RatingLetter
import org.kapunga.fredconnect.Gender.Gender

/**
 * This superclass is the parent to any askFred data type that gets sent with an id, so presumably it can be looked up.
 * This can be anything from the objects we query for like [[Fencer]] and [[Tournament]], to things we can't look up
 * directly, but can be used to query other objects, like [[Division]] and [[Club]], even a couple classes that we get
 * ids for but don't actually have any visible way to access or filter by them in the askFred interface, like [[Bout]].
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 */
abstract class FredData {
  val id: Int
}

/**
 * This case class represents a fencer in the askFred database.  AskFred offers no guarantee that fencers won't go away,
 * this happens mainly when there are duplicate fencers for the same person and the records are merged.
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 * @param id The askFred id for this Fencer class.
 * @param authIds A collection of authoritiy ids, see [[AuthorityId]].
 * @param person Personal information like name and birth year, see [[Person]]
 * @param division Division information for this fencer, see [[Division]]
 * @param clubs A list of clubs this fencer is a member of.  The first club listed is their primary club, see [[Club]]
 * @param ratings A map of [[Weapon]] to [[Rating]] representing this fencers ratings in each of the three weapons.
 */
case class Fencer(id: Int, authIds: AuthorityId, person: Person, division: Division,
                  clubs: List[Club], ratings: Map[Weapon, Rating]) extends FredData

/**
 * A collection of id numbers for different fencing authorities.  This represents a fencers specific identification
 * number with organizations like the United States Fencing Association.  AskFred has three of these.
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 * @param usfa Identification number for the USFA (United States Fencing Association)
 * @param cff Identification number for the CFF (Canadian Fencing Federation)
 * @param fie Identification number for the FIE (Fédération Internationale d'Escrime)
 */
case class AuthorityId(usfa: String, cff: String, fie: String)

/**
 * A collection of personal data about a fencer including their name, gender, and birth year.
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 * @param firstName The fencer's first name.
 * @param lastName The fencer's last name.
 * @param gender The fencer's gender.
 * @param birthYear The fencer's birth year.
 */
case class Person(firstName: String, lastName: String, gender: Gender, birthYear: Int) {
  /**
   * Pretty prints a person's name for human reading.
   *
   * @since 0.1
   *
   * @return Human readable name string.
   */
  def printName: String = s"${firstName} ${lastName}"
}

/**
 * Represents a division in the USFA.  Division id is used as a parameter in a number of searches, for example
 * [[Tournament]].
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 * @param id The askFred Id for this Division.
 * @param name The full name of this Division.
 * @param abbrev The abbreviation of this division.
 */
case class Division(id: Int, name: String, abbrev: String) extends FredData

/**
 * Represents a club in the USFA.  Club id is used as a parameter in a number of searches, for example [[Fencer]].
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 * @param id The askFred Id for this fencing club.
 * @param name The full name of this club.
 * @param initials The initals for this club.
 */
case class Club(id: Int, name: String, initials: String) extends FredData

/**
 * A USFA rating issued to a fencer after placing high enough in a tournament.
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 * @param id The askFred Id for this rating.  Currently there is no use for this id in the askFred REST API
 * @param weapon The weapon this rating is issued for.
 * @param letter The letter of this rating.
 * @param year The year this rating was earned.
 * @param authority The authority issuing this rating.
 */
case class Rating(id: Int, weapon: Weapon, letter: RatingLetter,
                  year: String, authority: String = "USFA") extends FredData {
  /**
   * @return The short string of this rating, such as "C14".
   */
  def shortValue: String = {
    letter match {
      case RatingLetter.U => "U"
      case _ => letter.toString + year.substring(2)
    }
  }
}

/**
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 * @param id
 * @param name
 * @param venue
 * @param division
 * @param startDate
 * @param endDate
 * @param comments
 * @param preregOpen
 * @param preregClose
 * @param authority
 * @param feeRequired
 * @param roc
 * @param bayCup
 * @param visible
 * @param cancelled
 * @param isAcceptingFeeOnline
 * @param fee
 * @param currency
 * @param moreInfo
 * @param events
 */
case class Tournament(id: Int, name: String, venue: Venue, division: Division, startDate: Date, endDate: Date,
                      comments: String, preregOpen: Date, preregClose: Date, authority: String, feeRequired: Boolean,
                      roc: Boolean, bayCup: Boolean, visible: Boolean, cancelled: Boolean, isAcceptingFeeOnline: Boolean,
                      fee: Double, currency: String, moreInfo: String, events: List[Event]) extends FredData

case class Venue(name: String, address: String, city: String, state: String, zipCode: String, country: String,
                 timezone: String, latitude: Double, longitude: Double, precision: String)

/**
 *
 * The following fields are returned by askFred but are currently unimplemented:
 * <ul>
 *   <li>"authority" : { }</li>
 *   <li>"description" : ""</li>
 *   <li>"close_of_reg" : "2014-09-26T18:45:00-04:00"</li>
 *   <li>"fee" : "20.00"</li>
 * </ul>
 *
 * @author Paul J Thordarson
 * @since 0.1
 *
 * @param id The askFred id for this event.
 * @param tournamentId The askFred id for the [[Tournament]] this event is a member of.
 * @param name The name of the tournament as specified by the organizers.
 * @param weapon
 * @param eventLimits
 * @param entries
 * @param preRegs
 * @param rating
 * @param ratingPrediction
 * @param preregs
 */
case class Event(id: Int, tournamentId: Int, name: String, weapon: Weapon, eventLimits: EventLimits, entries: Int,
                 preRegs: Int, rating: EventRating, ratingPrediction: EventRating, preregs: List[Competitor]) extends FredData

case class EventLimits(gender: EventGender, ageLimit: AgeLimit, ratingLimit: RatingLimit, isTeam: Boolean)

case class Competitor(id: Int, competitorId: Int, rating: Rating, club: Club, authorityId: AuthorityId,
                      person: Person) extends FredData

case class Result(id: Int, eventId: Int, tournamentId: Int, competitorId: Int, firstName: String, lastName: String,
                  tournamentName: String, tournamentStart: Date, tournamentEnd: Date, venue: Venue, weapon: Weapon,
                  eventLimits: EventLimits, eventRating: EventRating, eventDesc: String, eventDate: Date,
                  eventTime: String, authority: String, tourmamentDivId: Int, competitorDivId: Int, club: Club,
                  ratingBefore: Rating, eventFinish: EventFinish) extends FredData

case class EventFinish(entries: Int, place: Int, ratingEarned: Rating, excluded: Boolean, withdrawn: Boolean)

case class RoundResult(id: Int, tournamentId: Int, eventId: Int, roundType: RoundType, roundDesc: String,
                       roundSeq: Int, bouts: List[Bout]) extends FredData

case class Bout(id: Int, boutFencerOne: BoutFencer, boutFencerTwo: BoutFencer) extends FredData {
  def hasFencers(): Boolean = boutFencerOne != null && boutFencerTwo != null

  def hasFencer(id: Int): Boolean = boutFencerOne.id == id || boutFencerTwo.id == id

  def getVictor(): BoutFencer = boutFencerOne.result match {
    case BoutResult.V => boutFencerOne
    case BoutResult.D => boutFencerTwo
  }

  def printBout(): String = {
    s"${boutFencerOne.person.printName} (${boutFencerOne.result} ${boutFencerOne.score}-" +
    s"${boutFencerTwo.score} ${boutFencerTwo.result}) ${boutFencerTwo.person.printName}"
  }
}

case class BoutFencer(id: Int, usfaId: String, person: Person, score: Int, result: BoutResult,
                      seed: Int, primaryClubId: Int)
