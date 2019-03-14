package com.akkahttp.data

import com.akkahttp.models.{Book, CreateBook, UpdateBook}

import scala.concurrent.Future

trait BookRepository extends BaseRepository[Book, CreateBook, UpdateBook] {
  def sold(): Future[Seq[Book]]
  def pending(): Future[Seq[Book]]
}
