package com.akkahttp.marshallers

import com.akkahttp.marshallers.JsonApiFormat._
import com.akkahttp.marshallers.JsonSupport._
import com.akkahttp.models.{Book, Cat, CreateCat}

object JsonApiSupport{
  implicit val CatJW = jsonApiFormat[Cat]
  implicit val SeqCatJW = jsonApiFormat[Seq[Cat]]
  implicit val BookJW = jsonApiFormat[Book]
  implicit val SeqBookJW = jsonApiFormat[Seq[Book]]
  implicit val CreateCatJF = jsonApiFormat[CreateCat]
}
