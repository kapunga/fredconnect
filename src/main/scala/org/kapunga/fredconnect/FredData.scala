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

sealed abstract class FredData {
  val id: Int
}

case class Fencer(id: Int, authIds: AuthorityId, firstName: String, lastName: String, gender: Gender, birthYear: Int,
                  division: Division, clubs: List[Club], ratings: Map[Weapon, Rating]) extends FredData

case class AuthorityId(usfa: String, cff: String, fie: String)

case class Division(id: Int, name: String, abbrev: String) extends FredData

case class Club(id: Int, name: String, initials: String) extends FredData

case class Rating(id: Int, weapon: Weapon, letter: RatingLetter, year: String, authority: String = "USFA") extends FredData {
  def shortValue: String = {
    letter match {
      case RatingLetter.U => "U"
      case _ => letter.toString + year.substring(2)
    }
  }
}

case class Venue(name: String, address: String, city: String, state: String, zipCode: String, country: String,
                 timezone: String, latitude: Double, longitude: Double, precision: String)

case class Competitor(id: Int, competitorId: Int, rating: Rating, club: Club, authorityId: AuthorityId,
                      firstName: String, lastName: String, gender: Gender, birthYear: Int) extends FredData

case class Bout(id: Int, boutFencerOne: BoutFencer, boutFencerTwo: BoutFencer) extends FredData {
  def hasFencers(): Boolean = boutFencerOne != null && boutFencerTwo != null
  def printBout(): String = {
    s"${boutFencerOne.firstName} ${boutFencerOne.lastName} (${boutFencerOne.result} ${boutFencerOne.score}-" +
    s"${boutFencerTwo.score} ${boutFencerTwo.result}) ${boutFencerTwo.firstName} ${boutFencerTwo.lastName}"
  }
}

case class BoutFencer(id: Int, usfaId: String, firstName: String, lastName: String, gender: Gender, birthyear: Int,
                      score: Int, result: BoutResult, seed: Int, primaryClubId: Int)

case class Tournament(id: Int, name: String, venue: Venue, division: Division, startDate: Date, endDate: Date,
                      comments: String, preregOpen: Date, preregClose: Date, authority: String, feeRequired: Boolean,
                      roc: Boolean, bayCup: Boolean, visible: Boolean, cancelled: Boolean, isAcceptingFeeOnline: Boolean,
                      fee: Double, currency: String, moreInfo: String, events: List[Event]) extends FredData

case class Event(id: Int, tournamentId: Int, name: String, weapon: Weapon, gender: EventGender, ageLimit: AgeLimit,
                 ratingLimit: RatingLimit, entries: Int, preRegs: Int, isTeamEvent: Boolean, rating: EventRating,
                 ratingPrediction: EventRating, preregs: List[Competitor]) extends FredData
/*
{
  "authority" : { },
  "description" : "",
  "close_of_reg" : "2014-09-26T18:45:00-04:00",
  "fee" : "20.00"
}
*/

case class Result(id: Int, eventId: Int, tournamentId: Int, competitorId: Int, firstName: String, lastName: String,
                  tournamentName: String, tournamentStart: Date, tournamentEnd: Date, venue: Venue, weapon: Weapon,
                  gender: EventGender, ageLimit: AgeLimit, ratingLimit: RatingLimit, eventRating: EventRating,
                  entries: Int, isTeam: Boolean, eventDesc: String, eventDate: Date, eventTime: String, authority: String,
                  place: Int, tourmamentDivId: Int, competitorDivId: Int, club: Club, ratingBefore: Rating,
                  earnedRating: Rating, excluded: Boolean, withdrawn: Boolean) extends FredData

case class RoundResult(id: Int, tournamentId: Int, eventId: Int, roundType: RoundType, roundDesc: String,
                       roundSeq: Int, bouts: List[Bout]) extends FredData
