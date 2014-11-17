package repositories.mongo

import common.Logging
import org.joda.time.DateTime
import reactivemongo.bson.{BSONDateTime, BSONObjectID, BSONDocument}
import repositories.CoffeeRepository
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Await
import scala.util.Try
import scala.concurrent.duration._

private[repositories] class MongoCoffeeRepository extends BaseMongoRepository(MongoCoffeeRepository.dbName)
  with CoffeeRepository with Logging {
  override def saveCoffee(strength: Int, timestamp: DateTime): Try[String] =
    withCollection(MongoCoffeeRepository.coffeeColl) { coll =>
      val id = BSONObjectID.generate

      val futureInsert = coll.insert(BSONDocument("_id" -> id,
        "strength" -> strength,
        "timestamp" -> BSONDateTime(timestamp.getMillis)))

      futureInsert.onFailure {
        case t => log.error(s"save coffee failed! [$id, $strength, $timestamp]", t)
      }

      Await.result(futureInsert, 10 seconds)

      id.stringify
    }

  //  db.coffee.aggregate({ $group: { _id: { $dayOfYear: "$timestamp" }, avgStrength: { $avg: "$strength" } } })
}

private object MongoCoffeeRepository {
  val dbName = "coffeeDb"
  val coffeeColl = "coffee"
}