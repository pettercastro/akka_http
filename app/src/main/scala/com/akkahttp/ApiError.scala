package com.akkahttp

import akka.http.scaladsl.model.{StatusCode, StatusCodes}

final case class ApiError(statusCode: StatusCode, message: String)

//object ApiError {
//  def apply(statusCode: StatusCode, message: String): ApiError = new ApiError(statusCode, message)

//  val generic: ApiError = new ApiError(StatusCodes.InternalServerError, "Unknown error.")

//  def fieldTooLong(field: String): ApiError = new ApiError(StatusCodes.BadRequest, s"The $field field is too long.")
//
//  def bookNotFound(id: String): ApiError =
//    new ApiError(StatusCodes.NotFound, s"The book with id $id could not be found.")
//}
