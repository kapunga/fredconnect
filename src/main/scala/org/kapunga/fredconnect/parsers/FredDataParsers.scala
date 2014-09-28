package org.kapunga.fredconnect.parsers

import org.kapunga.fredconnect.parsers.SimpleParsers._
import org.kapunga.fredconnect.Weapon._
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

    val club = parseResultClub(jsValue)

    val venue = parseResultVenue(jsValue)

    new Result(id, eventId, tournamentId, competitorId, firstName, lastName, venue, club)
  }

  def parseRoundResult(jsValue: JsValue): RoundResult = {
    val id = parseInt(jsValue \ "id")

    new RoundResult(id)
  }

  def parseCompetitor(jsValue: JsValue, weapon: Weapon): Competitor = {
    val id = parseInt(jsValue \ "id")
    val competitorId = parseInt(jsValue \ "competitor_id")

    val rawRating = parseString(jsValue \ "rating")

    val rating = rawRating.length match {
      case 5 => new Rating(-1, weapon, RatingLetter.getRatingLetter(rawRating.charAt(0).toString), rawRating.substring(1))
      case _ => new Rating(-1, weapon, RatingLetter.U, "")
    }

    val club = parseClub(jsValue \ "club")

    val competitor = jsValue \ "competitor"

    val authorityId = parseAuthorityId(competitor)

    val firstName = parseString(competitor \ "first_name")

    val lastName = parseString(competitor \ "last_name")

    val gender = parseGender(competitor \ "gender")

    val birthYear = parseInt(competitor \ "birthyear")

    new Competitor(id, competitorId, rating, club, authorityId, firstName, lastName, gender, birthYear)
  }

  def parseAuthorityId(jsValue: JsValue): AuthorityId = {
    val usfaId = parseString(jsValue \ "usfa_id", null)
    val cffId = parseString(jsValue \ "cff_id", null)
    val fieId = parseString(jsValue \ "fie_id", null)

    new AuthorityId(usfaId, cffId, fieId)
  }

  def parseDivision(jsValue: JsValue): Division = {
    val id = parseInt(jsValue \ "id")
    val name = parseString(jsValue \ "name", "Unattached")
    val abbrev = parseString(jsValue \ "abbrev", "UNATT")

    new Division(id, name, abbrev)
  }

  def parseClubs(clubs: JsValue, primary: JsValue): List[Club] = {
    val primaryId = parseInt(primary, -1)

    clubs match {
      case JsArray(clubSeq) =>
        var list: List[Club] = List[Club]()

        for (value: JsValue <- clubSeq) {
          val club = parseClub(value)

          if (club.id == primaryId) {
            list = club :: list
          } else {
            list = list :+ club
          }
        }

        list

      case _ => List[Club]()
    }
  }

  def parseClub(jsValue: JsValue): Club = {
    val id = parseInt(jsValue \ "club_id_1")
    val name = parseString(jsValue \ "club_1_name")
    val initials = parseString(jsValue \ "club_1_initials")

    new Club(id, name, initials)
  }

  def parseResultClub(jsValue: JsValue): Club = {
    val id = parseInt(jsValue \ "id")
    val name = parseString(jsValue \ "name")
    val initials = parseString(jsValue \ "initials")

    new Club(id, name, initials)
  }

  def parseRatings(ratings: JsValue): Map[Weapon, Rating] = {
    Map(Foil -> parseRating(ratings \ "foil"),
        Epee -> parseRating(ratings \ "epee"),
        Saber -> parseRating(ratings \ "saber"))
  }

  def parseRating(rating: JsValue): Rating = {
    val id = parseInt(rating \ "id")

    val weapon = parseWeapon(rating \ "weapon")

    val letter = parseRatingLetter(rating \ "letter")

    val year = parseString(rating \ "year", "XXXX")
    val authority = parseString(rating \ "authority", "USFA")

    new Rating(id, weapon, letter, year, authority)
  }

  def parseVenue(jsValue: JsValue): Venue = {
    val name = parseString(jsValue \ "name")
    val address = parseString(jsValue \ "address")
    val city = parseString(jsValue \ "city")
    val state = parseString(jsValue \ "state")
    val zipCode = parseString(jsValue \ "zip")
    val country = parseString(jsValue \ "country")
    val timeZone = parseString(jsValue \ "timezone")
    val latitude = parseDouble(jsValue \ "latitude")
    val longitude = parseDouble(jsValue \ "longitude")
    val precision = parseString(jsValue \ "geocode_precision")

    new Venue(name, address, city, state, zipCode, country, timeZone, latitude, longitude, precision)
  }

  def parseResultVenue(jsValue: JsValue): Venue = {
    val name = parseString(jsValue \ "venue_name")
    val address = parseString(jsValue \ "venue_address")
    val city = parseString(jsValue \ "venue_city")
    val state = parseString(jsValue \ "venue_state")
    val zipCode = parseString(jsValue \ "venue_zip")
    val country = parseString(jsValue \ "venue_country")
    val timeZone = ""
    val latitude = parseDouble(jsValue \ "venue_latitude")
    val longitude = parseDouble(jsValue \ "venue_longitude")
    val precision = parseString(jsValue \ "venue_geo_precision")

    new Venue(name, address, city, state, zipCode, country, timeZone, latitude, longitude, precision)
  }
}
