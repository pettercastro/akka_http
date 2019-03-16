package com.akkahttp.data

import scala.concurrent.Future

trait BaseRepository[T, C, U] {

  def all(): Future[Seq[T]]

  def save(newElem: C): Future[T]

  def update(id: String, updatedElem: U): Future[T]

  def find(id: String): Future[Option[T]]

  def delete(id: String): Future[Boolean]
}
