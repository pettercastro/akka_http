package com.akkahttp

import scala.concurrent.ExecutionContextExecutor

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.akkahttp.data.CatRepository
import com.akkahttp.data.inmemory.InMemoryBookRepository
import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import com.typesafe.config.ConfigFactory
import models.Book

trait SwaggerSite extends Directives {
  val swaggerSiteRoute: Route = path("swagger") {
    getFromResource("swagger-ui/index.html")
  } ~
    getFromResourceDirectory("swagger-ui")
}

class SwaggerDocService(override val apiClasses: Set[Class[_]])
  extends SwaggerHttpService {
  override val host = "localhost:9001" //the url of your api, not swagger's json endpoint
  override val basePath = "/" //the basePath for the API you are exposing
  override val apiDocsPath = "_server/swagger" //where you want the swagger-json endpoint exposed
  override val info = Info() //provides license and other description details
}


object Main extends App with SwaggerSite {
  // configs
  val AppConfig = ConfigFactory.load().getConfig("akka_http")
  val Host = AppConfig.getString("host.name")
  val Port = AppConfig.getInt("host.port")

  // implicits
  implicit val System: ActorSystem = ActorSystem(name = "custom_actor_system")
  implicit val ExecutionContext: ExecutionContextExecutor = System.dispatcher
  implicit val Materializer: ActorMaterializer = ActorMaterializer()

  // repositories
  val BookRepository = new InMemoryBookRepository(Seq(
    Book("1", "Buy eggs", "Ran out of eggs, buy a dozen", sold=true),
    Book("2", "Buy milk", "The cat is thirsty!")
  ))
  val CatRepository = new CatRepository(AppConfig)

  val SwaggerDocService = new SwaggerDocService(Set(classOf[Router]))
  val Controllers = new Router(BookRepository, CatRepository)

  val AllRoutes = Controllers.routes ~ SwaggerDocService.routes ~ swaggerSiteRoute

  Http().bindAndHandle(AllRoutes, Host, Port)
}
