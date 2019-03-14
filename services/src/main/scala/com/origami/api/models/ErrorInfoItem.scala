package com.origami.api.models

sealed trait IErrorInfoItem {
  def name: String
  def message: String
  def `validationType`: String
  def `type`: String
}
case class ErrorInfoItem(name: String,
                         message: String,
                         validationType: String,
                         sqlQueryInfo: Option[SqlQueryInfo] = None,
                         `type`: String = "errorInfo")
    extends IErrorInfoItem

case class SqlQueryInfo(startPosition: Option[Int], line: Option[Int])
