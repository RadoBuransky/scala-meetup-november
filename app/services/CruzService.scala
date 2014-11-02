package services

import play.api.libs.json.JsValue
import services.impl.WsCruzService

import scala.concurrent.Future

trait CruzService {
  def getSubject(id: Int): Future[JsValue]
  def getFinancialStatement(id: Int): Future[JsValue]
  def getAccountingStatement(id: Int): Future[JsValue]
}

object CruzService {
  def apply(): CruzService = WsCruzService
}