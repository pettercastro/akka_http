package com.origami.api.models

case class ErrorInfo(code: Int = 0,
                     message: String = "OK",
                     // [Shachar] swagger OpenApi 2.0 doesn't support array of anyOf
                     // and akka http has not support for OpenApi 3.0 which does support it.
                     validationErrors: Seq[IErrorInfoItem] =
                       Seq.empty[ErrorInfoItem])

object ErrorInfo {
  def withValidationErrors(validationErrors: Seq[IErrorInfoItem]) =
    ErrorInfo(ErrorCodes.Validation.code,
              "Validation errors found",
              validationErrors)

  def empty() = ErrorInfo()
}
