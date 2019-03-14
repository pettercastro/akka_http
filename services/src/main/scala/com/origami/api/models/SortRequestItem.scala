package com.origami.api.models

case class RequestSortItem(field: String, order: String)

object RequestSortItem {
  val ASC = "asc"
  val DESC = "desc"
  val VALID_VALUES = Seq(ASC, DESC)
}
