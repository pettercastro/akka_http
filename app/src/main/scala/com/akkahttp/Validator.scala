package com.akkahttp

import com.akkahttp.models.{UpdateBook, CreateBook}

trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object CreateBookValidator extends Validator[CreateBook] {

  def validate(createBook: CreateBook): Option[ApiError] =
    if (createBook.title.length > 10)
      Some(ApiError(404, "Title too long"))
    else
      None
}

object UpdateBookValidator extends Validator[UpdateBook] {

  def validate(updateBook: UpdateBook): Option[ApiError] =
    if (updateBook.title.exists(_.isEmpty))
      Some(ApiError(404, "It is empty"))
    else
      None
}
