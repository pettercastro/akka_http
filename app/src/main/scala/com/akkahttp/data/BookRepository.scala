package com.akkahttp.data

import scala.concurrent.Future

import com.akkahttp.models.{Book, CreateBook, UpdateBook}

trait BookRepository extends BaseRepository[Book, CreateBook, UpdateBook] {
  def sold(): Future[Seq[Book]]
  def pending(): Future[Seq[Book]]
}
