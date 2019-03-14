package com.akkahttp.marshallers

//import com.akkahttp.marshallers.JsonSupport.{cat, createCat, jsonFormat2}
import org.zalando.jsonapi.{JsonapiRootObjectReader, JsonapiRootObjectWriter}
import org.zalando.jsonapi.model.JsonApiObject.{BooleanValue, StringValue}
import org.zalando.jsonapi.model.{Attribute, RootObject}
import org.zalando.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import com.akkahttp.models.{Book, Cat, CreateCat, UpdateCat}
import spray.json.DefaultJsonProtocol

import org.zalando.jsonapi.json.sprayjson.SprayJsonJsonapiProtocol._
import spray.json._


object JsonApiSupport extends DefaultJsonProtocol {

  def catToRootObject(cat: Cat) = {
    ResourceObject(
      `type` = "cats",
      attributes = Some(List(
        Attribute("id", StringValue(cat.id)),
        Attribute("name", StringValue(cat.name)),
        Attribute("age", StringValue(cat.age))
      ))
    )
  }

  def bookToRootObject(book: Book) = {
    ResourceObject(
      `type` = "books",
      attributes = Some(List(
        Attribute("id", StringValue(book.id)),
        Attribute("title", StringValue(book.title)),
        Attribute("description", StringValue(book.description)),
        Attribute("sold", BooleanValue(book.sold))
      ))
    )
  }

  implicit val catJF: JsonapiRootObjectWriter[Cat] = new JsonapiRootObjectWriter[Cat] {
    override def toJsonapi(cat: Cat) = {
      RootObject(data = Some(catToRootObject(cat)))
    }
  }

  implicit val seqCatJF: JsonapiRootObjectWriter[Seq[Cat]] = new JsonapiRootObjectWriter[Seq[Cat]] {
    override def toJsonapi(cats: Seq[Cat]) = {
      val resources = cats map { cat => catToRootObject(cat) }
      RootObject(data = Some(ResourceObjects(resources.toList)), links=None)
    }
  }

  implicit val bookJF: JsonapiRootObjectWriter[Book] = new JsonapiRootObjectWriter[Book] {
    override def toJsonapi(book: Book) = {
      RootObject(data = Some(bookToRootObject(book)))
    }
  }

  implicit val seqBookJF: JsonapiRootObjectWriter[Seq[Book]] = new JsonapiRootObjectWriter[Seq[Book]] {
    override def toJsonapi(books: Seq[Book]) = {
      val resources = books map { book => bookToRootObject(book)}
      RootObject(data = Some(ResourceObjects(resources.toList)), links=None)
    }
  }

  implicit val createCatJF: JsonapiRootObjectReader[CreateCat] = new JsonapiRootObjectReader[CreateCat] {
    override def fromJsonapi(rootObject: RootObject): CreateCat  = {
      rootObject.toJson.asJsObject.getFields("data") match {
        case Seq(JsObject(data)) => JsObject(data).getFields("attributes") match {
          case Seq(JsObject(x)) => JsObject(x).getFields("name", "age") match {
            case Seq(JsString(name), JsString(age)) => CreateCat(name.toString, age.toString)
          }
        }
      }
    }
  }
}
