package com.akkahttp.data.inmemory

import java.util.UUID

import com.akkahttp.models.{Book, CreateBook, UpdateBook}
import com.akkahttp.data.BookRepository
import scala.concurrent.{ExecutionContext, Future}

case class BookNotFound(id: String) extends Throwable(s"Book not found $id")

class InMemoryBookRepository(initialBooks: Seq[Book] = Seq.empty)(implicit ec: ExecutionContext) extends BookRepository {

  private var books: Vector[Book] = initialBooks.toVector


  override def find(id: String): Future[Option[Book]] = Future.successful(books.find(_.id == id))

  override def all(): Future[Seq[Book]] = Future.successful(books)

  override def sold(): Future[Seq[Book]] = Future.successful(books.filter(_.sold))

  override def pending(): Future[Seq[Book]] = Future.successful(books.filterNot(_.sold))

  override def save(createBook: CreateBook): Future[Book] = Future.successful {
    val book = Book(
      UUID.randomUUID().toString,
      createBook.title,
      createBook.description,
      createBook.sold.getOrElse(false)
    )
    books = books :+ book
    book
  }

  override def update(id: String, updateBook: UpdateBook): Future[Book] = {
    this.find(id).flatMap {
      case Some(foundBook) =>
        val newBook = updateHelper(foundBook, updateBook)
        books = books.map(t => if (t.id == id) newBook else t)
        Future.successful(newBook)
      case None =>
        Future.failed(BookNotFound(id))
    }
  }

  private def updateHelper(oldBook: Book, updatedBook: UpdateBook): Book = {
    val title = updatedBook.title.getOrElse(oldBook.title)
    val description = updatedBook.description.getOrElse(oldBook.description)
    val sold = updatedBook.sold.getOrElse(oldBook.sold)
    oldBook.copy(title=title, description=description, sold=sold)
  }

  override def delete(id: String): Future[Boolean] = {
     this.find(id).flatMap {
      case Some(_) =>
        books = books.filter(_.id != id)
        Future.successful(true)
      case None =>
        Future.failed(BookNotFound(id))
    }
  }

}