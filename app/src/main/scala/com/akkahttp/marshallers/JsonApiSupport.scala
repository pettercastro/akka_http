package com.akkahttp.marshallers

import org.zalando.jsonapi.{JsonapiRootObjectReader, JsonapiRootObjectWriter}
import org.zalando.jsonapi.model.JsonApiObject.{BooleanValue, NumberValue, StringValue}
import org.zalando.jsonapi.model.{Attribute, RootObject}
import org.zalando.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import com.akkahttp.models.{Book, Cat, CreateCat}
import spray.json._

object JsonApiConverter {
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

  def jsonApiWriter[X](implicit writer: JsonWriter[X])= {
    new JsonapiRootObjectWriter[X] {
      override def toJsonapi(x: X) = {
        x match {
          case elements:Seq[X] =>
            val resources = elements map { element => convertTo[X](element) }
            RootObject(data = Some(ResourceObjects(resources.toList)), links=None)
          case element:X =>
            RootObject(data = Some(convertTo[X](element)))
        }
      }
    }
  }

  def jsonApiReader[X](implicit reader: JsonReader[X])= {
    new JsonapiRootObjectReader[X] {
      override def fromJsonapi(rootObject: RootObject): X = {
        import org.zalando.jsonapi.json.sprayjson.SprayJsonJsonapiProtocol._
        val resourceObject = rootObject.data.map { case x:ResourceObject => x }.get

        val attrs = resourceObject.toJson.asJsObject.getFields("attributes")
        attrs match {
          case Seq(JsObject(x)) => JsObject(x).convertTo[X]
        }
      }
    }
  }
}


object JsonApiSupport{
  import JsonApiConverter._

  import com.akkahttp.marshallers.JsonSupport._
  implicit val catJW = jsonApiWriter[Cat]
  implicit val seqCatJW = jsonApiWriter[Seq[Cat]]
  implicit val bookJW = jsonApiWriter[Book]
  implicit val seqBookJW = jsonApiWriter[Seq[Book]]

  implicit val createCatJF = jsonApiReader[CreateCat]
}
