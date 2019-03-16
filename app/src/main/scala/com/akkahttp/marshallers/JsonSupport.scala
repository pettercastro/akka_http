package com.akkahttp.marshallers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.akkahttp.marshallers.JsonSupport.{jsonFormat2, jsonFormat3}
import com.akkahttp.models._
import spray.json._

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val JsCatJF = jsonFormat3(Cat)
  implicit val JsCreateCatJF = jsonFormat2(CreateCat)
  implicit val BookJF = jsonFormat5(Book)
//  implicit val createBookJF = jsonFormat3(CreateBook)
//  implicit val updateBookJF = jsonFormat3(UpdateBook)
//
//  implicit val updateCateJf = jsonFormat2(UpdateCat)
}
