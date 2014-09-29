package org.kapunga.fredconnect.parsers

import org.kapunga.fredconnect.parsers.SimpleParsers._
import org.kapunga.fredconnect.parsers.SupportDataParsers._
import org.kapunga.fredconnect._
import play.api.libs.json.{JsArray, JsValue}

object FredDataParsers {
  def parseFencer(jsValue: JsValue): Fencer = {
    val id = parseInt(jsValue \ "id")

    val firstName = parseString(jsValue \ "first_name")

    val lastName = parseString(jsValue \ "last_name")

    val gender = parseGender(jsValue \ "gender")

    val birthYear = parseInt(jsValue \ "birthyear", 1970)

    val authIds = parseAuthorityId(jsValue)

    val division = parseDivision(jsValue \ "division")

    val clubs = parseClubs(jsValue \ "clubs", jsValue \ "primary_club_id")

    val ratings = parseRatings(jsValue \ "usfa_ratings")

    new Fencer(id, authIds, firstName, lastName, gender, birthYear, division, clubs, ratings)
  }

  def parseTournament(jsValue: JsValue): Tournament = {
    val id = parseInt(jsValue \ "id")

    val name = parseString(jsValue \ "name")

    val venue = parseVenue(jsValue \ "venue")

    val division = parseDivision(jsValue \ "division")

    val events = (jsValue \ "events") match {
      case JsArray(seq) => seq.map(jsValue => parseEvent(jsValue)).toList
      case _ => List[Event]()
    }

    new Tournament(id, name, venue, division, events)
  }

  def parseEvent(jsValue: JsValue): Event = {
    val id = parseInt(jsValue \ "id")

    val tournamentId = parseInt(jsValue \ "tournament_id")

    val name = parseString(jsValue \ "full_name")

    val weapon = parseWeapon(jsValue \ "weapon")

    val gender = parseEventGender(jsValue \ "gender")

    val ageLimit = parseAgeLimit(jsValue \ "age_limit")

    val ratingLimit = parseRatingLimit(jsValue \ "rating_limit")

    val entries = parseInt(jsValue \ "entries")

    val preRegs = parseInt(jsValue \ "prereg_count")

    val isTeamEvent = parseBoolean(jsValue \ "is_team")

    val rating = parseEventRating(jsValue \ "rating")

    val ratingPrediction = parseEventRating(jsValue \ "rating_prediction")

    val preregs = (jsValue \ "preregs") match {
      case JsArray(seq) => seq.map(prereg => parseCompetitor(prereg, weapon)).toList
      case _ => List[Competitor]()
    }

    new Event(id, tournamentId, name, weapon, gender, ageLimit, ratingLimit, entries, preRegs, isTeamEvent, rating,
              ratingPrediction, preregs)
  }

  def parseResult(jsValue: JsValue): Result = {
    val id = parseInt(jsValue \ "id")

    val eventId = parseInt(jsValue \ "event_id")

    val tournamentId = parseInt(jsValue \ "tournament_id")

    val competitorId = parseInt(jsValue \ "competitor_id")

    val firstName = parseString(jsValue \ "first_name")

    val lastName = parseString(jsValue \ "last_name")

    val venue = parseResultVenue(jsValue)

    val tournamentName = parseString(jsValue \ "tournament_name")

    val tournamentStart = parseDate(jsValue \ "tournament_start_date")

    val tournamentEnd = parseDate(jsValue \ "tournament_end_date")

    val weapon = parseWeapon(jsValue \ "")

    val gender = parseEventGender(jsValue \ "")

    val club = parseResultClub(jsValue)

    val ratingBeforeLetter = parseRatingLetter(jsValue \ "rating_before_letter")

    val ratingBeforeYear = parseInt(jsValue \ "rating_before_year", 1970)

    val ratingEarnedLetter = parseRatingLetter(jsValue \ "rating_Earned_letter")

    val ratingEarnedYear = parseInt(jsValue \ "rating_Earned_year", 1970)

    val beforeRating = new Rating(-1, weapon, ratingBeforeLetter, ratingBeforeYear.toString)

    val earnedRating = new Rating(-1, weapon, ratingEarnedLetter, ratingEarnedYear.toString)

    val excluded = parseBoolean(jsValue \ "is_excluded")

    val withdrawn = parseBoolean(jsValue \ "is_withdraw")

    new Result(id, eventId, tournamentId, competitorId, firstName, lastName, tournamentName, tournamentStart,
               tournamentEnd, venue, weapon, gender, club, beforeRating, earnedRating, excluded, withdrawn)
  }

  def parseRoundResult(jsValue: JsValue): RoundResult = {
    val id = parseInt(jsValue \ "id")

    val tournamentId = parseInt(jsValue \ "tournament_id")

    val eventId = parseInt(jsValue \ "event_id")

    val roundType = parseRoundType(jsValue \ "round_type")

    val roundDesc = parseString(jsValue \ "round_desc")

    val roundSeq = parseInt(jsValue \ "seq")

    val bouts = roundType match {
      case RoundType.POOLS => getPoolBouts(jsValue \ "pools")
      case RoundType.DE_TABLES => getDeBouts(jsValue \ "de_tables")
    }

    new RoundResult(id, tournamentId, eventId, roundType, roundDesc, roundSeq, bouts)
  }

}
