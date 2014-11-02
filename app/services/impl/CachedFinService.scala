package services.impl

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import repositories.FinRepository
import services.FinService

import scala.concurrent.Future

private[services] class CachedFinService(finService: FinService, finRepository: FinRepository) extends FinService {
  override def getSubjectDetails(id: Int): Future[JsValue] = {
    finService.getSubjectDetails(id).map { sd =>
      finRepository.saveSubjectDetails(sd)
      sd
    }
  }
}
