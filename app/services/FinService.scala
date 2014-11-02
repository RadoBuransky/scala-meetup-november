package services

import play.api.libs.json.JsValue
import services.impl.CruzFinService

import scala.concurrent.Future

trait FinService {
  def getSubjectDetails(id: Int): Future[JsValue]
}

object FinService {
  def apply(): FinService = new CruzFinService(CruzService())
}