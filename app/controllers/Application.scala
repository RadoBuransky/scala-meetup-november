package controllers

import play.api.mvc._
import services.CruzService
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends Controller {
  private val IbmId = 174558

  def index = Action.async {
    CruzService().getSubject(IbmId).map(Ok(_))
  }

}