package org.kapunga.fredconnect

import java.util.Date

import org.kapunga.fredconnect.EventRating._
import org.kapunga.fredconnect.QueryEval._
import org.kapunga.fredconnect.SearchAgeLimit._
import org.kapunga.fredconnect.SearchGender._
import org.kapunga.fredconnect.Weapon._

/**
 *
 */
class TournamentQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  def setDivisionId(id: Int): Unit = parameterMap = parameterMap + ("division_id" -> id.toString)

  def setDivisionIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("division_ids")

  def setTournamentIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("tournament_ids")

  def setName(name: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("name" -> name)
      case false => parameterMap = parameterMap + ("name_contains" -> name)
    }
  }

  def setLocation(location: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("location" -> location)
      case false => parameterMap = parameterMap + ("location_contains" -> location)
    }
  }

  def setComments(comments: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("comments" -> comments)
      case false => parameterMap = parameterMap + ("comments_contains" -> comments)
    }
  }

  def setMoreInfo(moreInfo: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("more_info" -> moreInfo)
      case false => parameterMap = parameterMap + ("more_info_contains" -> moreInfo)
    }
  }

  def setStartDate(startDate: Date, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("start_date_eq" -> defaultDateFormat.format(startDate))
      case QueryEval.LTE => parameterMap = parameterMap + ("start_date_lte" -> defaultDateFormat.format(startDate))
      case QueryEval.GTE => parameterMap = parameterMap + ("start_date_gte" -> defaultDateFormat.format(startDate))
    }
  }

  def setEndDate(endDate: Date, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("end_date_eq" -> defaultDateFormat.format(endDate))
      case QueryEval.LTE => parameterMap = parameterMap + ("end_date_lte" -> defaultDateFormat.format(endDate))
      case QueryEval.GTE => parameterMap = parameterMap + ("end_date_gte" -> defaultDateFormat.format(endDate))
    }
  }

  def setPreregOpen(preregOpen: Date, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("prereg_open_eq" -> defaultDateFormat.format(preregOpen))
      case QueryEval.LTE => parameterMap = parameterMap + ("prereg_open_lte" -> defaultDateFormat.format(preregOpen))
      case QueryEval.GTE => parameterMap = parameterMap + ("prereg_open_gte" -> defaultDateFormat.format(preregOpen))
    }
  }
  
  def setPreregClose(preregClose: Date, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("prereg_close_eq" -> defaultDateFormat.format(preregClose))
      case QueryEval.LTE => parameterMap = parameterMap + ("prereg_close_lte" -> defaultDateFormat.format(preregClose))
      case QueryEval.GTE => parameterMap = parameterMap + ("prereg_close_gte" -> defaultDateFormat.format(preregClose))
    }
  }
  
  def setSectionId(id: Int): Unit = parameterMap = parameterMap + ("section_id" -> id.toString)
  
  def setSectionIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("section_ids")

  def setAddress(address: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("address" -> address)
      case false => parameterMap = parameterMap + ("address_contains" -> address)
    }
  }

  def setCity(city: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("city" -> city)
      case false => parameterMap = parameterMap + ("city_contains" -> city)
    }
  }

  def setState(state: String): Unit = {
    if (state.length != 2)
      throw new IllegalArgumentException(s"${state} is not a two letter state code.")

    parameterMap = parameterMap + ("state" -> state)
  }

  def setZipCode(zipCode: String): Unit = parameterMap = parameterMap + ("zip" -> zipCode)

  def setCountry(country: String): Unit = parameterMap = parameterMap + ("country" -> country)

  def setRadius(latitude: Double, longitude: Double, radius: Double, isMiles: Boolean): Unit = {
    parameterMap = parameterMap + ("lat" -> latitude.toString)
    parameterMap = parameterMap + ("long" -> longitude.toString)

    isMiles match {
      case true => parameterMap = parameterMap - "radius_km"
        parameterMap = parameterMap + ("radius_mi" -> radius.toString)
      case false => parameterMap = parameterMap - "radius_mi"
        parameterMap = parameterMap + ("radius_km" -> radius.toString)
    }
  }

  /**
   *
   * @param isRoc
   */
  def setIsRoc(isRoc: Boolean): Unit = {
    isRoc match {
      case true => parameterMap = parameterMap + ("is_roc" -> "1")
      case false => parameterMap = parameterMap + ("is_roc" -> "0")
    }
  }

  /**
   *
   * @param isBayCup
   */
  def setIsBayCup(isBayCup: Boolean): Unit = {
    isBayCup match {
      case true => parameterMap = parameterMap + ("is_baycup" -> "1")
      case false => parameterMap = parameterMap + ("is_baycup" -> "0")
    }
  }

  /**
   *
   * @param isCancelled
   */
  def setIsCancelled(isCancelled: Boolean): Unit = {
    isCancelled match {
      case true => parameterMap = parameterMap + ("is_cancelled" -> "1")
      case false => parameterMap = parameterMap + ("is_cancelled" -> "0")
    }
  }
  
  /**
   *
   * @param weapon
   */
  def setEventWeapon(weapon: Weapon): Unit = parameterMap = parameterMap + ("event_weapon_eq" -> weapon.toString.toLowerCase)

  /**
   *
   * @param searchGender
   */
  def setEventGender(searchGender: SearchGender): Unit = {
    searchGender match {
      case SearchGender.Men_Mixed => parameterMap = parameterMap + ("event_gender_eq" -> "men-mixed")
      case SearchGender.Women_Mixed => parameterMap = parameterMap + ("event_gender_eq" -> "women-mixed")
      case _ => parameterMap = parameterMap + ("event_gender_eq" -> searchGender.toString)
    }
  }

  /**
   *
   * @param ageLimit
   */
  def setEventAgeLimit(ageLimit: SearchAgeLimit): Unit = {
    ageLimit match {
      case SearchAgeLimit.Senior_Open => parameterMap = parameterMap + ("event_age_eq" -> "senior-open")
      case _ => parameterMap = parameterMap + ("event_age_eq" -> ageLimit.toString.toLowerCase)
    }
  }

  /**
   *
   * @param isTeam
   */
  def setEventIsTeam(isTeam: Boolean): Unit = {
    isTeam match {
      case true => parameterMap = parameterMap + ("event_is_team" -> "1")
      case false => parameterMap = parameterMap + ("event_is_team" -> "0")
    }
  }

  /**
   *
   * @param eventEntries
   * @param queryEval
   */
  def setEventEntries(eventEntries: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("event_entries_eq" -> eventEntries.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("event_entries_lte" -> eventEntries.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("event_entries_gte" -> eventEntries.toString)
    }
  }

  /**
   *
   * @param eventPreregs
   * @param queryEval
   */
  def setEventPreregs(eventPreregs: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("event_preregs_eq" -> eventPreregs.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("event_preregs_lte" -> eventPreregs.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("event_preregs_gte" -> eventPreregs.toString)
    }
  }

  /**
   *
   * @param eventRating
   * @param queryEval
   */
  def setEventRating(eventRating: EventRating, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("event_rating_eq" -> eventRating.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("event_rating_lte" -> eventRating.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("event_rating_gte" -> eventRating.toString)
    }
  }

  /**
   *
   * @param eventRating
   * @param queryEval
   */
  def setEventRatingPrediction(eventRating: EventRating, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("event_rating_prediction_eq" -> eventRating.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("event_rating_prediction_lte" -> eventRating.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("event_rating_prediction_gte" -> eventRating.toString)
    }
  }
}
