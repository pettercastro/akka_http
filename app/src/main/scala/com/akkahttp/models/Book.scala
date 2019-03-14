package com.akkahttp.models

case class Book(id: String, title: String, description: String, sold: Boolean = false, isDeleted: Boolean = false)

case class CreateBook(title: String, description: String, sold: Option[Boolean])
case class UpdateBook(title: Option[String], description: Option[String], sold: Option[Boolean])
