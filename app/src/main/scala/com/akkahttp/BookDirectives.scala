package com.akkahttp

import scala.concurrent.Future
import scala.util.{Failure, Success}

import akka.http.scaladsl.server.{Directive1, Directives}

trait BookDirectives extends Directives {
  val errorCode = 400

  def handle[T]
  (f: Future[T])
  (e: Throwable => ApiError): Directive1[T] = onComplete(f) flatMap {
    case Success(t) =>
      provide(t)
    case Failure(error) =>
      val apiError = e(error)
      complete(apiError.statusCode, apiError.message)
  }

  def handleWithGeneric[T](f: Future[T]): Directive1[T] =
    handle[T](f)(_ => ApiError(errorCode, "Something went wrong"))

}
