package com.akkahttp

import akka.http.scaladsl.server._
import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import models.Book
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import com.akkahttp.data.inmemory.InMemoryBookRepository
import com.akkahttp.data.CatRepository

trait SwaggerSite extends Directives {
  val swaggerSiteRoute: Route = path("swagger") {
    getFromResource("swagger-ui/index.html")
  } ~
    getFromResourceDirectory("swagger-ui")
}

class SwaggerDocService(override val apiClasses: Set[Class[_]])
  extends SwaggerHttpService {
  override val host = "localhost:9000" //the url of your api, not swagger's json endpoint
  override val basePath = "/" //the basePath for the API you are exposing
  override val apiDocsPath = "_server/swagger" //where you want the swagger-json endpoint exposed
  override val info = Info() //provides license and other description details
}


object Main extends App with SwaggerSite {
  // configs
  val appConfig = ConfigFactory.load().getConfig("akka_http")
  val host = appConfig.getString("host.name")
  val port = appConfig.getInt("host.port")

  // implicits
  implicit val system: ActorSystem = ActorSystem(name = "custom_actor_system")
  import system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // repositories
  val bookRepository = new InMemoryBookRepository(Seq(
    Book("1", "Buy eggs", "Ran out of eggs, buy a dozen", sold=true),
    Book("2", "Buy milk", "The cat is thirsty!")
  ))
  val catRepository = new CatRepository(appConfig)

  val swaggerDocService = new SwaggerDocService(Set(classOf[Router]))
  val controllers = new Router(bookRepository, catRepository)

  val allRoutes = controllers.routes ~ swaggerDocService.routes ~ swaggerSiteRoute

  Http().bindAndHandle(allRoutes, host, port)
}
