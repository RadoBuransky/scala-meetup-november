package repositories

import play.api.libs.json.JsValue
import repositories.mongo.MongoFinRepository

import scala.concurrent.Future

trait FinRepository {
  def saveSubjectDetails(subjectDetails: JsValue): Future[String]
  def getSubjectDetails(id: Int): Future[JsValue]
}

object FinRepository {
  def apply(): FinRepository = finRepository

  private lazy val finRepository = new MongoFinRepository()
}