package repositories

import org.joda.time.DateTime
import repositories.mongo.MongoCoffeeRepository

import scala.util.Try

trait CoffeeRepository {
  def saveCoffee(strength: Int, timestamp: DateTime): Try[String]
}

object CoffeeRepository {
  def apply(): CoffeeRepository = new MongoCoffeeRepository()
}