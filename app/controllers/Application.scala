package controllers

import java.util.UUID

import common.CoffeeSystem
import model.CoffeeStrength
import model.JsonFormats._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import repositories.CoffeeRepository
import services.CoffeeService

import scala.util.{Failure, Success, Try}

case class ErrorResult()

object Application extends Controller {
  private val coffeeSystem = CoffeeSystem
  private val coffeeRepository = CoffeeRepository()
  private val coffeeService = CoffeeService(coffeeSystem, coffeeRepository)

  private val makeCoffeeForm = Form { single("strength" -> number) }

  def index = Action { Ok(views.html.index()) }

  def postCoffee() = Action { implicit request =>
    makeCoffeeForm.bindFromRequest.value match {
      case Some(strength) if strength > 0 => {
        withSuccess(coffeeService.make(CoffeeStrength(strength))) { coffees =>
          Ok(Json.obj("coffees" -> coffees))
        }
      }

      case Some(strength) => BadRequest("strength must be greater than zero!")
      case _ => BadRequest("strength required!")
    }
  }

  def coffee(id: String) = Action {
    Try(UUID.fromString(id)) match {
      case Success(uuid) =>
        withSuccess(coffeeService.get(uuid)) {
          case Some(coffee) => Ok(Json.toJson(coffee))
          case None => NotFound
        }
      case Failure(t) => BadRequest(s"id is not an UUID $id")
    }
  }

  def all() = Action { okJson(coffeeService.all()) }
  def top() = Action { okJson(coffeeService.top()) }

  private def okJson[T](value: Try[T])(implicit writes: Writes[T]): Result = {
    withSuccess(value) { result =>
      Ok(Json.toJson(result))
    }
  }

  private def withSuccess[T](value: Try[T])(action: T => Result): Result = {
    value match {
      case Success(s) => action(s)
      case Failure(t) => internalServerError(t)
    }
  }

  private def internalServerError(t: Throwable) =
    InternalServerError(t.toString + t.getStackTrace.mkString("\n","\n",""))
}