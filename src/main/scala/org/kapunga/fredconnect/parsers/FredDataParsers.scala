package org.kapunga.fredconnect.parsers

import org.kapunga.fredconnect.parsers.SimpleParsers._
import org.kapunga.fredconnect.Weapon._
import org.kapunga.fredconnect._
import play.api.libs.json.{JsArray, JsValue}

object FredDataParsers {
  def parseFencer(jsValue: JsValue): Fencer = {
    val id = parseInt(jsValue \ "id", -1)

    val firstName = parseString(jsValue \ "first_name", "")

    val lastName = parseString(jsValue \ "last_name", "")

    val gender = parseGender(jsValue \ "gender")

    val birthYear = parseInt(jsValue \ "birth_year", 1970)

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

    new Event(id, tournamentId, name, weapon, gender, ageLimit, ratingLimit, entries, preRegs, isTeamEvent)
  }

  def parseAuthorityId(jsValue: JsValue): AuthorityId = {
    val usfaId = parseString(jsValue \ "usfa_id", null)
    val cffId = parseString(jsValue \ "cff_id", null)
    val fieId = parseString(jsValue \ "fie_id", null)

    new AuthorityId(usfaId, cffId, fieId)
  }

  def parseDivision(jsValue: JsValue): Division = {
    val id = parseInt(jsValue \ "id", -1)
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
}
