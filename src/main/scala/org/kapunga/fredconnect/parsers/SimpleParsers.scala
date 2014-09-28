package org.kapunga.fredconnect.parsers

import java.util.Date

import org.kapunga.fredconnect.AgeLimit.AgeLimit
import org.kapunga.fredconnect.EventGender.EventGender
import org.kapunga.fredconnect.EventRating.EventRating
import org.kapunga.fredconnect.Gender
import org.kapunga.fredconnect.Gender._
import org.kapunga.fredconnect.RatingLetter
import org.kapunga.fredconnect.RatingLimit.RatingLimit
import org.kapunga.fredconnect.Weapon.Weapon
import org.kapunga.fredconnect._
import org.kapunga.fredconnect.RatingLetter._
import org.kapunga.fredconnect.Weapon._
import play.api.libs.json.JsValue

/**
 * Created by kapunga on 9/27/14.
 */
object SimpleParsers {
  def parseWeapon(jsValue: JsValue): Weapon = {
    parseString(jsValue).toLowerCase match {
      case "foil" => Foil
      case "saber" => Saber
      case _ => Epee
    }
  }

  def parseRatingLetter(jsValue: JsValue): RatingLetter = {
    parseString(jsValue).toLowerCase match {
      case "a" => RatingLetter.A
      case "b" => RatingLetter.B
      case "c" => RatingLetter.C
      case "d" => RatingLetter.D
      case "e" => RatingLetter.E
      case _ => RatingLetter.U
    }
  }

  def parseRatingLimit(jsValue: JsValue): RatingLimit = {
    parseString(jsValue, "unrated").toLowerCase match {
      case "open" => RatingLimit.Open
      case "eunder" => RatingLimit.Eunder
      case "div3" => RatingLimit.Div3
      case "div2" => RatingLimit.Div2
      case "div1a" => RatingLimit.Div1a
      case "dabove" => RatingLimit.Dabove
      case "div1" => RatingLimit.Div1
      case "babove" => RatingLimit.Babove
      case "aonly" => RatingLimit.Aonly
      case _ => RatingLimit.Unrated
    }
  }

  def parseAgeLimit(jsValue: JsValue): AgeLimit = {
    parseString(jsValue, "none").toLowerCase match {
      case "y8" => AgeLimit.Y8
      case "y10" => AgeLimit.Y10
      case "y12" => AgeLimit.Y12
      case "y14" => AgeLimit.Y14
      case "u16" => AgeLimit.U16
      case "u19" => AgeLimit.U19
      case "vet40" => AgeLimit.Vet40
      case "vet50" => AgeLimit.Vet50
      case "vet60" => AgeLimit.Vet60
      case "vet70" => AgeLimit.Vet70
      case "vetcombined" => AgeLimit.VetCombined
      case _ => AgeLimit.None
    }
  }

  def parseEventRating(jsValue: JsValue): EventRating = {
    parseString(jsValue, "unrated").toLowerCase match {
      case "a4" => EventRating.A4
      case "a3" => EventRating.A3
      case "a2" => EventRating.A2
      case "a1" => EventRating.A1
      case "b3" => EventRating.B3
      case "b2" => EventRating.B2
      case "b1" => EventRating.B1
      case "c3" => EventRating.C3
      case "c2" => EventRating.C2
      case "c1" => EventRating.C1
      case "d1" => EventRating.D1
      case "e1" => EventRating.E1
      case _ => EventRating.Unrated
    }
  }

  def parseGender(jsValue: JsValue): Gender = {
    parseString(jsValue, "M").toLowerCase match {
      case "f" => Gender.Female
      case _ => Gender.Male
    }
  }

  def parseEventGender(jsValue: JsValue): EventGender = {
    parseString(jsValue, "mixed").toLowerCase match {
      case "men" => EventGender.Men
      case "women" => EventGender.Women
      case _ => EventGender.Mixed
    }
  }

  def parseString(jsValue: JsValue, defValue: String = ""): String = {
    jsValue.asOpt[String] match {
      case Some(item) => item
      case None => defValue
    }
  }

  def parseInt(jsValue: JsValue, defValue: Int = -1): Int = {
    jsValue.asOpt[Int] match {
      case Some(item) => item
      case None => defValue
    }
  }

  def parseDouble(jsValue: JsValue, defValue: Double = 0.0): Double = {
    jsValue.asOpt[Double] match {
      case Some(item) => item
      case None => defValue
    }
  }

  def parseBoolean(jsValue: JsValue, defValue: Boolean = false): Boolean = {
    jsValue.asOpt[Boolean] match {
      case Some(item) => item
      case None => defValue
    }
  }

  def parseDate(jsValue: JsValue, defValue: Date = new Date()): Date = {
    jsValue.asOpt[Date] match {
      case Some(item) => item
      case None => defValue
    }
  }
}
