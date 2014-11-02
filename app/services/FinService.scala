package services

import play.api.libs.json.JsValue
import repositories.FinRepository
import services.impl.{CachedFinService, CruzFinService}

import scala.concurrent.Future

trait FinService {
  def getSubjectDetails(id: Int): Future[JsValue]
}

object FinService {
  def apply(): FinService = cachedFinService

  private lazy val finService = new CruzFinService(CruzService())
  private lazy val cachedFinService = new CachedFinService(finService, FinRepository())
}