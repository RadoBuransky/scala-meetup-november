package services

import play.api.libs.json.JsValue
import play.api.libs.ws.WS
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

object CruzService {
  val annualReportsUrl = "http://www.registeruz.sk/cruz-public/api/vyrocne-spravy?zmenene-od=2000-01-01&pokracovat-za-id=1&max-zaznamov=10"

  def getAnnualReports(): Future[JsValue] = {
    WS.url(annualReportsUrl).get().map { response =>
      response.json
    }
  }
}
