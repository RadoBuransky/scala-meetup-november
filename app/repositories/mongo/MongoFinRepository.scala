package repositories.mongo

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import reactivemongo.api.MongoDriver
import repositories.FinRepository

import scala.concurrent.Future
import play.modules.reactivemongo.json.ImplicitBSONHandlers._

private[repositories] class MongoFinRepository extends FinRepository {
  override def saveSubjectDetails(subjectDetails: JsValue) = {
    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    val db = connection("test")

    val collection = db("subjectDetails")
    collection.insert(subjectDetails).map(_.toString)
  }

  override def getSubjectDetails(id: Int): Future[JsValue] = ???
}
