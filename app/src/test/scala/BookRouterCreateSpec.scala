import java.util.UUID

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import com.akkahttp.data.inmemory.InMemoryBookRepository
//import com.akkahttp.marshallers.JsonSupport
import com.akkahttp.models.{Book, CreateBook}

class BookRouterCreateSpec extends WordSpec with Matchers with ScalatestRouteTest {

  val testCreateBook = CreateBook(
    "Test todo",
    "Test description",
    None
  )

//  "A BookRouter" should {
//
//    "create a todo with valid data" in {
//      val repository = new InMemoryBookRepository()
//      val router = new Router(repository)
//
//      Post("/books", testCreateBook) ~> router.route ~> check {
//        status shouldBe StatusCodes.OK
//        val resp = responseAs[Book]
//        resp.title shouldBe testCreateBook.title
//        resp.description shouldBe testCreateBook.description
//      }
//    }
//  }
}



















//"not create a todo with invalid data" in {
//  val repository = new FailingRepository
//  val router = new Router(repository)
//
//  Post("/books", testCreateBook.copy(title = "")) ~> router.route ~> check {
//    status shouldBe ApiError.emptyTitleField.statusCode
//    val resp = responseAs[String]
//    resp shouldBe ApiError.emptyTitleField.message
//  }
//}
//
//    "handle repository failure when creating books" in {
//      val repository = new FailingRepository
//      val router = new Router(repository)
//
//      Post("/books", testCreateBook) ~> router.route ~> check {
//        status shouldBe ApiError.generic.statusCode
//        val resp = responseAs[String]
//        resp shouldBe ApiError.generic.message
//      }
//    }
//  }

//}
