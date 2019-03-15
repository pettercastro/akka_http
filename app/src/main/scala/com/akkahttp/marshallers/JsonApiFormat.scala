package com.akkahttp.marshallers

import org.zalando.jsonapi.JsonapiRootObjectFormat
import org.zalando.jsonapi.json.sprayjson.SprayJsonJsonapiProtocol._
import org.zalando.jsonapi.model.JsonApiObject.{BooleanValue, NumberValue, StringValue}
import org.zalando.jsonapi.model.{Attribute, RootObject}
import org.zalando.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import spray.json._

object JsonApiFormat {
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

  /**
    * Serialize and deserialize a case class into a [RootObject], which is used to follow the json api specifications
    * for requests and responses
    * @param format: The JsonWriter and JsonReader used to serialize and deserialize the case class into a list
    *              of attributes
    * @tparam X
    * @return
    */
  def jsonApiFormat[X](implicit format: JsonFormat[X]): JsonapiRootObjectFormat[X]= {
    new JsonapiRootObjectFormat[X] {
      override def toJsonapi(x: X): RootObject = {
        x match {
          case elements:Seq[X] =>
            val resources = elements map { element => convertTo[X](element) }
            RootObject(data = Some(ResourceObjects(resources.toList)), links=None)
          case element:X =>
            RootObject(data = Some(convertTo[X](element)))
        }
      }
      override def fromJsonapi(rootObject: RootObject): X = {
        rootObject.data.map {
          case x:ResourceObject => x.toJson.asJsObject.getFields("attributes") match {
            case Seq(JsObject(obj)) => JsObject(obj).convertTo[X]
          }
        }.get
      }
    }
  }
}
