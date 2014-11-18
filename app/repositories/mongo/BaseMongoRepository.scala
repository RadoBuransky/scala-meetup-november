package repositories.mongo

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.MongoDriver

import scala.util.Try

abstract class BaseMongoRepository(dbName: String) {
  def withCollection[T](collName: String)(action: JSONCollection => T): Try[T] = Try {
    val connection = BaseMongoRepository.driver.connection(BaseMongoRepository.nodes)
    try {
      val db = connection(dbName)
      val collection = db.collection[JSONCollection](collName)

      action(collection)
    }
    finally {
      connection.close()
    }
  }
}

private object BaseMongoRepository {
  val nodes = List("localhost")
  lazy val driver = new MongoDriver()
}