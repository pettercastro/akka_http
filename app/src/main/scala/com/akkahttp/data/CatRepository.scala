package com.akkahttp.data

import scala.concurrent.{ExecutionContext, Future}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.akkahttp.marshallers.JsonSupport._
import com.akkahttp.models._
import com.typesafe.config.Config

class CatRepository(appConfig: Config)(implicit system: ActorSystem,
                                    executionContext: ExecutionContext,
                                    mat: ActorMaterializer)
  extends BaseRepository[Cat, CreateCat, UpdateCat]{

  private val host: String = appConfig.getString("servers.cats.host") + ":" + appConfig.getString("servers.cats.port")

  override def all(): Future[Seq[Cat]] = {
    val request = HttpRequest(HttpMethods.GET, uri=host + "/cats")
    Http().singleRequest(request).flatMap { response =>
      Unmarshal(response.entity).to[Seq[Cat]]
    }
  }

  override def save(data: CreateCat): Future[Cat] = {
    Marshal(data).to[RequestEntity] flatMap  { value =>
      val request = HttpRequest(HttpMethods.POST, uri=host + "/cats", entity=value)
      Http().singleRequest(request).flatMap { response =>
        Unmarshal(response.entity).to[Cat]
      }
    }
  }

  override def update(id: String, updatedElem: UpdateCat): Future[Cat] = Future.successful(Cat("aaa", "fff", "gg"))

  override def find(id: String): Future[Option[Cat]] = Future.successful(Some(Cat("1","2","3")))

  override def delete(id: String): Future[Boolean] = Future.successful(true)
}
