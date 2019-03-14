package com.akkahttp

import akka.http.scaladsl.server._
import io.swagger.annotations.{Api, ApiOperation}
import javax.ws.rs.Path
import scala.util.{Failure, Success}

import com.akkahttp.models.{Book, Cat, CreateBook, CreateCat, UpdateBook}
import com.akkahttp.data.{BookRepository, CatRepository}


final case class Test(name: String, lastName: String)

final case class MyCustomRejection(parameterName: String, errorMsg: String, cause: Option[Throwable] = None)
  extends Rejection

@Path("/books")
@Api(produces = "application/json")
class Router(bookRepository: BookRepository, catRepository: CatRepository) extends BookDirectives with ValidatorDirectives{

//  var rejectionHandler =
//    RejectionHandler
//      .newBuilder()
//      .handle {
//        case a: MyCustomRejection => complete("lalala")
//      }.handleAll[MyCustomRejection] { rejections =>
//        complete(404, rejections.map(_.errorMsg))
//      }.result()

  import com.akkahttp.marshallers.JsonApiSupport._
  import org.zalando.jsonapi.json.akka.http.AkkaHttpJsonapiSupport._

//  def temporalDirective(): Directive0 = {
//    reject(ValidationRejection(""), MyCustomRejection("seoncd error","bbb"))
//  }

  @ApiOperation(
    value = "Get books information",
    httpMethod = "GET",
    response = classOf[Book]
  )
  def getRoute: Route = {
    get {
      pathEndOrSingleSlash {
        complete(bookRepository.all())
      }
    }
  }

//  def postRoute: Route = {
//    post {
//      entity(as[CreateBook]) { book =>
//        validateWith(CreateBookValidator)(book) {
//          onComplete(bookRepository.save(book)) {
//            case Success(b) => complete(b)
//            case Failure(_) => complete("Error somewhere")
//          }
//        }
//      }
//    }
//  }
//
//  def putRoute: Route = {
//    path(Segment) { id: String =>
//      put {
//        entity(as[UpdateBook]) { updateBook =>
//          validateWith(UpdateBookValidator)(updateBook) {
//            onComplete(bookRepository.update(id, updateBook)) {
//              case Success(b) => complete(b)
//              case Failure(e) => complete(s"Error somewhere ${e.getMessage}")
//            }
//          }
//        }
//      }
//    }
//  }

  def getCats: Route = {
    get {
      pathEndOrSingleSlash {
        complete(catRepository.all())
      }
    }
  }

  def postCats: Route = {
    post {
      entity(as[CreateCat]) { cat =>
          onComplete(catRepository.save(cat)) {
            case Success(b) => complete(b)
            case Failure(e) => complete("Error somewhere" + e)
          }
      }
    }
  }

  def getTest: Route = {
    get {
      complete(Cat("123", "Peter", "Castro"))
    }
  }

  val routes: Route = pathPrefix("books") {
//    handleRejections(rejectionHandler) {
      getRoute // ~ postRoute ~ putRoute
//    }
  } ~  pathPrefix("cats") {
    getCats ~ postCats
  } ~ pathPrefix("test") {
    getTest
  }
}
