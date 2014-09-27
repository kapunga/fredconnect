package org.kapunga.fredconnect

import org.kapunga.fredconnect.Gender.Gender

/**
 *
 * There are currently two parameters askFred takes for fencers which we do not support:
 *
 * user_id	integer
 * user_ids	comma separated list of integers
 *
 * This appears to be an internal fred userId which is at the moment irrelevant.
 */
class FencerQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  def getQueryMap(): Map[String, String] = parameterMap

  def setFencerIds(ids: List[Int]): Unit = {
    if (ids.length > MAX_IDS)
      throw new IllegalArgumentException(s"${ids.length} is greater than the maximum ids of ${MAX_IDS}.")

    parameterMap = parameterMap + ("fencer_ids" -> ids.mkString(","))
  }

  def setUsfaId(id: String): Unit = parameterMap = parameterMap + ("usfa_id" -> id)

  def setFirstName(name: String, exact: Boolean = true): Unit = {
    parameterMap = exact match {
      case true => parameterMap + ("first_name" -> name)
      case false => parameterMap + ("first_name_contains" -> name)
    }
  }

  def setLastName(name: String, exact: Boolean = true): Unit = {
    parameterMap = exact match {
      case true => parameterMap + ("last_name" -> name)
      case false => parameterMap + ("last_name_contains" -> name)
    }
  }

  def setGender(gender: Gender): Unit = {
    parameterMap = gender match {
      case Gender.Female => parameterMap + ("gender" -> "F")
      case Gender.Male => parameterMap + ("gender" -> "M")
    }
  }

  def setClub(id: Int): Unit = parameterMap = parameterMap + ("club_id" -> id.toString)

  def setClubs(ids: List[Int]): Unit = {
    if (ids.length > MAX_IDS)
      throw new IllegalArgumentException(s"${ids.length} is greater than the maximum ids of ${MAX_IDS}.")

    parameterMap = parameterMap + ("club_ids" -> ids.mkString(","))
  }

  def setDivision(id: Int): Unit = parameterMap = parameterMap + ("division_id" -> id.toString)

  def setDivisions(ids: List[Int]): Unit = {
    if (ids.length > MAX_IDS)
      throw new IllegalArgumentException(s"${ids.length} is greater than the maximum ids of ${MAX_IDS}.")

    parameterMap = parameterMap + ("division_ids" -> ids.mkString(","))
  }

  def setBirthYearExact(year: Int): Unit = {
    validateBirthYear(year)

    parameterMap = parameterMap + ("birthyear" -> year.toString)
  }

  def setBirthYearBefore(year: Int): Unit = {
    validateBirthYear(year)

    parameterMap = parameterMap + ("birthyear_lte" -> year.toString)
  }

  def setBirthYearAfter(year: Int): Unit = {
    validateBirthYear(year)

    parameterMap = parameterMap + ("birthyear_gte" -> year.toString)
  }

  private def validateBirthYear(year: Int): Unit = {
    if (year > 9999 || year < 1000)
      throw new IllegalArgumentException(s"Year must be 4 digits, ${year} is an invalid birth year.")
  }
}

/*
2	user_id	integer
4	user_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
 */
