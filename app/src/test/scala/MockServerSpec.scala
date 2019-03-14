import com.intuit.karate.netty._
import java.io.File

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{WordSpec, BeforeAndAfterAll, Matchers}
import com.typesafe.config.{Config, ConfigFactory}

import com.akkahttp.Router
import com.akkahttp.data.CatRepository
import com.akkahttp.data.inmemory.InMemoryBookRepository
import com.akkahttp.models.Book

class MockServerSpec extends WordSpec with Matchers with BeforeAndAfterAll with ScalatestRouteTest{

  private var server: FeatureServer = _
  private var appConfig: Config = ConfigFactory.load().getConfig("akka_http")

  override def beforeAll(): Unit = {
    val file = new File(getClass.getResource("cats_server.feature").getPath)
    server = FeatureServer.start(file, 0, false, null)

    val configString = s"""|servers.cats.port: ${server.getPort}""".stripMargin
    appConfig = ConfigFactory.parseString(configString).withFallback(appConfig)
  }

  override def afterAll(): Unit = {
    server.stop()
  }

  "Cats empty" should {
    "inside here" in {
      val bookRepository = new InMemoryBookRepository(Seq(
        Book("1", "Buy eggs", "Ran out of eggs, buy a dozen", sold=true),
        Book("2", "Buy milk", "The cat is thirsty!")
      ))
      val catRepository = new CatRepository(appConfig)
      val controllers = new Router(bookRepository, catRepository)
      Get("/cats") ~> controllers.routes ~> check {
        val resp = responseAs[String]
        resp shouldBe """[{"data":[]}]"""
      }

//      Get("/cats") ~> Router(bookRepository, catRepository).rou ~> check {
//                status shouldBe StatusCodes.InternalServerError
//                val resp = responseAs[String]
//                resp shouldBe ApiError.generic.message
//              }

//      val request = HttpRequest(uri = host + "/cats", method = HttpMethods.GET)
//      val response = Http().singleRequest(request)
//      response map { response =>
//        val resFuture = Unmarshal(response.entity).to[String]
//        val res = Await.result(resFuture, 1.second)
//        res shouldBe "[]"
//      }
    }
  }
}
