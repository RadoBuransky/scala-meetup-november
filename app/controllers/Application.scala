package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.FinService

object Application extends Controller {
  private val IbmId = 174558

  def index = Action.async {
    FinService().getSubjectDetails(IbmId).map(Ok(_))
  }
}