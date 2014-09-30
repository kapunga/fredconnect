package org.kapunga.fredconnect

import org.kapunga.fredconnect.Gender.Gender
import org.kapunga.fredconnect.QueryEval.QueryEval

/**
 * This class is essentially a Builder for a map of params to be used in an arbitrary query for a list
 * of Fencers.  The 'set' methods are used to set certain search parameters and then "getQueryMap" is
 * used to get the map.  There is, however an implicit conversion of the superclass to extract the
 * parameter map, so you can just pass an instance instead of a map to the AskFred client.
 *
 * There are currently two parameters askFred takes for fencers which we do not support:
 *
 * user_id - integer<br>
 * user_ids - comma separated list of integers<br>
 *
 * This appears to be an internal fred userId which is at the moment irrelevant.
 *
 * @author Paul J Thordarson
 */
class FencerQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  /**
   *
   * @param ids A list of fencer ids to look up.
   * @throws IllegalArgumentException If ids is longer than the maximum allowed number, @see QueryParams#MAX_IDS
   */
  def setFencerIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("fencer_ids")

  /**
   *
   * @param id
   */
  def setUsfaId(id: String): Unit = parameterMap = parameterMap + ("usfa_id" -> id)

  /**
   *
   * @param name
   * @param exact
   */
  def setFirstName(name: String, exact: Boolean = true): Unit = {
    parameterMap = exact match {
      case true => parameterMap + ("first_name" -> name)
      case false => parameterMap + ("first_name_contains" -> name)
    }
  }

  /**
   *
   * @param name
   * @param exact
   */
  def setLastName(name: String, exact: Boolean = true): Unit = {
    parameterMap = exact match {
      case true => parameterMap + ("last_name" -> name)
      case false => parameterMap + ("last_name_contains" -> name)
    }
  }

  /**
   *
   * @param gender
   */
  def setGender(gender: Gender): Unit = {
    parameterMap = gender match {
      case Gender.Female => parameterMap + ("gender" -> "F")
      case Gender.Male => parameterMap + ("gender" -> "M")
    }
  }

  /**
   *
   * @param id
   */
  def setClub(id: Int): Unit = parameterMap = parameterMap + ("club_id" -> id.toString)

  /**
   *
   * @param ids
   */
  def setClubs(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("club_ids")

  /**
   *
   * @param id
   */
  def setDivision(id: Int): Unit = parameterMap = parameterMap + ("division_id" -> id.toString)

  /**
   *
   * @param ids
   */
  def setDivisions(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("division_ids")

  /**
   *
   * @param year
   * @param queryEval
   */
  def setBirthYear(year: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    if (year > 9999 || year < 1000)
      throw new IllegalArgumentException(s"Year must be 4 digits, ${year} is an invalid birth year.")

    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("birthyear" -> year.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("birthyear_gte" -> year.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("birthyear_lte" -> year.toString)
    }
  }
}
