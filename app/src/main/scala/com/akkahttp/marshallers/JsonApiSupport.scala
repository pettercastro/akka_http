package com.akkahttp.marshallers

import org.zalando.jsonapi.{JsonapiRootObjectReader, JsonapiRootObjectWriter}
import org.zalando.jsonapi.model.JsonApiObject.{BooleanValue, StringValue, NumberValue}
import org.zalando.jsonapi.model.{Attribute, RootObject}
import org.zalando.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import com.akkahttp.models.{Book, Cat, CreateCat}
import spray.json.DefaultJsonProtocol
import org.zalando.jsonapi.json.sprayjson.SprayJsonJsonapiProtocol._
import spray.json._

object JsonApiConverter extends DefaultJsonProtocol {
  def convertTo[X](x: X): ResourceObject = {
    val fields = (Map[String, Any]() /: x.getClass.getDeclaredFields) { (a, f) =>
      f.setAccessible(true)
      a + (f.getName -> f.get(x))}

    val attrs = fields map {
      case(field, value) => value match {
        case v:Double => Attribute(field, NumberValue(v))
        case v:Int => Attribute(field, NumberValue(v))
        case v:String => Attribute(field, StringValue(v))
        case v:Boolean => Attribute(field, BooleanValue(v))
        //        case v:_ => Attribute(field, JsObjectValue(v))
      }
    }
    ResourceObject(`type` = x.getClass.getSimpleName.toLowerCase + "s", attributes = Some(attrs.toList))
  }
}


object JsonApiSupport{
  import JsonApiConverter._

  implicit val catJF: JsonapiRootObjectWriter[Cat] = new JsonapiRootObjectWriter[Cat] {
    override def toJsonapi(cat: Cat) = {
      RootObject(data = Some(convertTo[Cat](cat)))
    }
  }

  implicit val seqCatJF: JsonapiRootObjectWriter[Seq[Cat]] = new JsonapiRootObjectWriter[Seq[Cat]] {
    override def toJsonapi(cats: Seq[Cat]) = {
      val resources = cats map { cat => convertTo[Cat](cat) }
      RootObject(data = Some(ResourceObjects(resources.toList)), links=None)
    }
  }

  implicit val bookJF: JsonapiRootObjectWriter[Book] = new JsonapiRootObjectWriter[Book] {
    override def toJsonapi(book: Book) = {
      RootObject(data = Some(convertTo[Book](book)))
    }
  }

  implicit val seqBookJF: JsonapiRootObjectWriter[Seq[Book]] = new JsonapiRootObjectWriter[Seq[Book]] {
    override def toJsonapi(books: Seq[Book]) = {
      val resources = books map { book => convertTo[Book](book)}
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
