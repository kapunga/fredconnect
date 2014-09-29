package org.kapunga.fredconnect.parsers

import org.kapunga.fredconnect.Weapon.Weapon
import org.kapunga.fredconnect._
import org.kapunga.fredconnect.Weapon._
import org.kapunga.fredconnect.parsers.SimpleParsers._
import play.api.libs.json.{JsArray, JsValue}

/**
 * Created by kapunga on 9/28/14.
 */
object SupportDataParsers {
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

  def parseBout(jsValue: JsValue): Bout = {
    val id = parseInt(jsValue \ "id")

    val fencers = parseArray(jsValue \ "fencers")

    fencers.length match {
      case 2 => new Bout(id, parseBoutFencer(fencers(0)), parseBoutFencer(fencers(1)))
      case _ => new Bout(id, null, null)
    }
  }

  def parseBoutFencer(jsValue: JsValue): BoutFencer = {
    val id = parseInt(jsValue \ "id")

    val usfaId = parseString(jsValue \ "usfa_id")

    val firstName = parseString(jsValue \ "first_name")

    val lastName = parseString(jsValue \ "last_name")

    val gender = parseGender(jsValue \ "gender")

    val birthYear = parseInt(jsValue \ "birthyear")

    val score = parseInt(jsValue \ "score")

    val status = parseBoutResult(jsValue \ "status")

    val seed = parseInt(jsValue \ "seed")

    val primaryClubId = parseInt(jsValue \ "primary_club_id")

    new BoutFencer(id, usfaId, firstName, lastName, gender, birthYear, score, status, seed, primaryClubId)
  }

  def getPoolBouts(jsValue: JsValue): List[Bout] = {
    parseArray(jsValue).map(item => parseArray(item \ "bouts")).flatten.map(parseBout).filter(bout => bout.hasFencers)
  }

  def getDeBouts(jsValue: JsValue): List[Bout] = {
    parseArray(jsValue).map(item => parseArray(item \ "bouts")).flatten.map(parseBout).filter(bout => bout.hasFencers)
  }
}
