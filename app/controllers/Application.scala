package controllers

import play.api.mvc._
import services.CruzService
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends Controller {

  def index = Action.async {
    CruzService.getAnnualReports().map(Ok(_))
  }

}