package org.kapunga.fredconnect

/**
 *
 */
class RoundResultQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  /**
   *
   * @param id
   */
  def setTournamentId(id: Int): Unit = parameterMap = parameterMap + ("tournament_id" -> id.toString)

  /**
   *
   * @param id
   */
  def setEventId(id: Int): Unit = parameterMap = parameterMap + ("event_id" -> id.toString)

  /**
   *
   * @param ids
   */
  def setEventIds(ids: List[Int]): Unit = {
    if (ids.length > MAX_IDS)
      throw new IllegalArgumentException(s"${ids.length} is greater than the maximum ids of ${MAX_IDS}.")

    parameterMap = parameterMap + ("event_ids" -> ids.mkString(","))
  }
}
