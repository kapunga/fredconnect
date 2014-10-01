package org.kapunga.fredconnect.parsers

import org.kapunga.fredconnect.parsers.SimpleParsers._
import org.kapunga.fredconnect.parsers.SupportDataParsers._
import org.kapunga.fredconnect._
import play.api.libs.json.{JsArray, JsValue}

object FredDataParsers {
  def parseFencer(jsValue: JsValue): Fencer = {
    val id = parseInt(jsValue \ "id")

    val person = parsePerson(jsValue)

    val authIds = parseAuthorityId(jsValue)

    val division = parseDivision(jsValue \ "division")

    val clubs = parseClubs(jsValue \ "clubs", jsValue \ "primary_club_id")

    val ratings = parseRatings(jsValue \ "usfa_ratings")

    new Fencer(id, authIds, person, division, clubs, ratings)
  }

  def parseTournament(jsValue: JsValue): Tournament = {
    val id = parseInt(jsValue \ "id")

    val name = parseString(jsValue \ "name")

    val venue = parseVenue(jsValue \ "venue")

    val division = parseDivision(jsValue \ "division")

    val startDate = parseDate(jsValue \ "start_date")

    val endDate = parseDate(jsValue \ "end_date", startDate)

    val comments = parseString(jsValue \ "comments")

    val preregOpen = parseDate(jsValue \ "prereg_open")

    val preregClose = parseDate(jsValue \ "prereg_close")

    val authority = parseString(jsValue \ "authority")

    val feeRequired = parseBoolean(jsValue \ "is_fee_required")

    val roc = parseBoolean(jsValue \ "is_roc")

    val bayCup = parseBoolean(jsValue \ "is_baycup")

    val visible = parseBoolean(jsValue \ "is_visible")

    val cancelled = parseBoolean(jsValue \ "is_cancelled")

    val acceptFeesOnline = parseBoolean(jsValue \ "is_accepting_fees_online")

    val fee = parseDouble(jsValue \ "fee")

    val currency = parseString(jsValue \ "fee_currency")

    val moreInfo = parseString(jsValue \ "more_info")

    val events = (jsValue \ "events") match {
      case JsArray(seq) => seq.map(jsValue => parseEvent(jsValue)).toList
      case _ => List[Event]()
    }

    new Tournament(id, name, venue, division, startDate, endDate, comments, preregOpen, preregClose, authority,
                   feeRequired, roc, bayCup, visible, cancelled, acceptFeesOnline, fee, currency, moreInfo, events)
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

    val weapon = parseWeapon(jsValue \ "weapon")

    val eventLimits = parseEventLimits(jsValue)

    val eventRating = parseEventRating(jsValue \ "event_rating")

    val eventDesc = parseString(jsValue \ "event_desc")

    val eventDate = parseDate(jsValue \ "event_date")

    val eventTime = parseString(jsValue \ "event_time")

    val authority = parseString(jsValue \ "authority")

    val tournamentDivId = parseInt(jsValue \ "tournament_division_id")

    val competitorDivId = parseInt(jsValue \ "competitor_division_id")

    val club = parseResultClub(jsValue)

    val ratingBeforeLetter = parseRatingLetter(jsValue \ "rating_before_letter")

    val ratingBeforeYear = parseInt(jsValue \ "rating_before_year", 1970)

    val beforeRating = new Rating(-1, weapon, ratingBeforeLetter, ratingBeforeYear.toString)

    val eventFinish = parseEventFinish(jsValue)

    new Result(id, eventId, tournamentId, competitorId, firstName, lastName, tournamentName, tournamentStart,
               tournamentEnd, venue, weapon, eventLimits, eventRating, eventDesc, eventDate, eventTime,
               authority, tournamentDivId, competitorDivId, club, beforeRating, eventFinish)
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
