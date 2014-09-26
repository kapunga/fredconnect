package org.kapunga.fredconnect

import org.kapunga.fredconnect.Gender.Gender
import play.api.libs.json.{JsArray, JsValue}
import org.kapunga.fredconnect.Weapon._

object FredDataParsers {
  def parseFencer(jsValue: JsValue): Fencer = {
    val id = parseInt(jsValue \ "id", -1)

    val firstName = parseString(jsValue \ "first_name", "")

    val lastName = parseString(jsValue \ "last_name", "")

    val gender = parseGender(jsValue \ "gender")

    val birthYear = parseInt(jsValue \ "birth_year", 1970)

    val authIds = parseAuthorityId(jsValue \ "usfa_id", jsValue \ "cff_id", jsValue \ "fie_id")

    val division = parseDivision(jsValue \ "division")

    val clubs = parseClubs(jsValue \ "clubs", jsValue \ "primary_club_id")

    val ratings = parseRatings(jsValue \ "usfa_ratings")

    new Fencer(id, authIds, firstName, lastName, gender, birthYear, division, clubs, ratings)
  }

  def parseAuthorityId(usfa: JsValue, cff: JsValue, fie: JsValue): AuthorityId = {
    val usfaId = parseString(usfa, null)
    val cffId = parseString(cff, null)
    val fieId = parseString(fie, null)

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
          val id = parseInt(value \ "id", -1)
          val name = parseString(value \ "name", "")
          val initials = parseString(value \ "initials", "")

          if (id == primaryId) {
            list = new Club(id, name, initials) :: list
          } else {
            list = list :+ new Club(id, name, initials)
          }
        }

        list

      case _ => List[Club]()
    }
  }

  def parseRatings(ratings: JsValue): Map[Weapon, Rating] = {
    Map(Foil -> parseRating(ratings \ "foil"),
        Epee -> parseRating(ratings \ "epee"),
        Saber -> parseRating(ratings \ "saber"))
  }

  def parseRating(rating: JsValue): Rating = {
    val id = parseInt(rating \ "id", -1)

    val weapon = {
      parseString(rating \ "weapon", "").toLowerCase match {
        case "foil" => Foil
        case "saber" => Saber
        case _ => Epee
      }
    }

    val letter = {
      parseString(rating \ "letter", "U").toLowerCase match {
        case "a" => RatingLetter.A
        case "b" => RatingLetter.B
        case "c" => RatingLetter.C
        case "d" => RatingLetter.D
        case "e" => RatingLetter.E
        case _ => RatingLetter.U
      }
    }

    val year = parseString(rating \ "year", "XXXX")
    val authority = parseString(rating \ "authority", "USFA")

    new Rating(id, weapon, letter, year, authority)
  }

  def parseGender(jsValue: JsValue): Gender = {
    parseString(jsValue, "M").toLowerCase match {
      case "f" => Gender.Female
      case _ => Gender.Male
    }
  }

  def parseString(jsValue: JsValue, defValue: String): String = {
    jsValue.asOpt[String] match {
      case Some(item) => item
      case None => defValue
    }
  }

  def parseInt(jsValue: JsValue, defValue: Int): Int = {
    jsValue.asOpt[Int] match {
      case Some(item) => item
      case None => defValue
    }
  }
}
