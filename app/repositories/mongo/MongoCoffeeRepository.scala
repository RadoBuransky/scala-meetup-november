package repositories.mongo

import java.util.UUID

import common.Logging
import model.Coffee
import model.JsonFormats._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import repositories.CoffeeRepository

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

private[repositories] class MongoCoffeeRepository extends BaseMongoRepository(MongoCoffeeRepository.dbName)
  with CoffeeRepository with Logging {

  override def save(coffee: Coffee): Try[Unit] =
    withCollection(MongoCoffeeRepository.coffeeColl) { coll =>
      val futureInsert = coll.insert(coffee)
      log.error("xxx")

      futureInsert.onFailure {
        case t => log.error(s"save coffee failed! [$coffee]", t)
      }

      Await.result(futureInsert, 10 seconds)
    }

  override def all(): Try[Seq[Coffee]] = withCollection(MongoCoffeeRepository.coffeeColl) { coll =>
    Await.result(coll.find(Json.obj()).cursor[Coffee].collect[Seq](), 10 seconds)
  }

  override def get(id: UUID): Try[Option[Coffee]] = withCollection(MongoCoffeeRepository.coffeeColl) { coll =>
    Await.result(coll.find(Json.obj()).cursor[Coffee].headOption, 10 seconds)
  }
}

private object MongoCoffeeRepository {
  val dbName = "coffeeDb"
  val coffeeColl = "coffee"
}