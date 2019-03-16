package com.akkahttp

import com.akkahttp.models.{CreateBook, UpdateBook}

trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object CreateBookValidator extends Validator[CreateBook] {
  val ErrorCode = 404

  def validate(createBook: CreateBook): Option[ApiError] =
    if (createBook.title.length > 10) {
      Some(ApiError(ErrorCode, "Title too long"))
    } else {
      None
    }
}

object UpdateBookValidator extends Validator[UpdateBook] {
  val ErrorCode = 404

  def validate(updateBook: UpdateBook): Option[ApiError] =
    if (updateBook.title.exists(_.isEmpty)) {
      Some(ApiError(ErrorCode, "It is empty"))
    } else {
      None
    }
}
