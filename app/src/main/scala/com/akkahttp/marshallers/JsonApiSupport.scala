package com.akkahttp.marshallers

import com.akkahttp.models.{Book, Cat, CreateCat}
import com.akkahttp.marshallers.JsonApiFormat._
import com.akkahttp.marshallers.JsonSupport._

object JsonApiSupport{
  implicit val catJW = jsonApiFormat[Cat]
  implicit val seqCatJW = jsonApiFormat[Seq[Cat]]
  implicit val bookJW = jsonApiFormat[Book]
  implicit val seqBookJW = jsonApiFormat[Seq[Book]]
  implicit val createCatJF = jsonApiFormat[CreateCat]
}
