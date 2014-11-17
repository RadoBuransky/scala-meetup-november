package controllers

import common.CoffeeSystem
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import repositories.CoffeeRepository
import services.{CoffeeStrength, CoffeeService}

import scala.util.{Failure, Success}

object Application extends Controller {
  private val coffeeSystem = CoffeeSystem
  private val coffeeRepository = CoffeeRepository()
  private val coffeeService = CoffeeService(coffeeSystem, coffeeRepository)

  private val makeCoffeeForm = Form { single("strength" -> number) }

  def index = Action { Redirect(routes.Application.getMakeCoffee()) }
  def getMakeCoffee() = Action { Ok(views.html.make()) }

  def postMakeCoffee() = Action { implicit request =>
    // Get strength value from HTTP form
    makeCoffeeForm.bindFromRequest.value match {
      // Is there some strength and is it a positive number?
      case Some(strength) if strength > 0 => {
        // Call service
        coffeeService.makeCoffee(CoffeeStrength(strength)) match {
          // Service worked
          case Success(dailyStats) => Ok(Json.obj("avgCoffeeStrength" -> strength))

          // Service failed
          case Failure(t) => InternalServerError(t.toString + t.getStackTrace.mkString("\n","\n",""))
        }
      }

      // Strength is invalid
      case Some(strength) => BadRequest("strength must be greater than zero!")

      // Strength is not there at all
      case _ => BadRequest("strength required!")
    }
  }

  def coffee(id: String) = Action {
    Ok
  }
}