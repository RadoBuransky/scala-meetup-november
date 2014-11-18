package repositories

import java.util.UUID

import model.Coffee
import repositories.mongo.MongoCoffeeRepository

import scala.util.Try

trait CoffeeRepository {
  def save(coffee: Coffee): Try[Unit]
  def get(id: UUID): Try[Option[Coffee]]
  def all(): Try[Seq[Coffee]]
}

object CoffeeRepository {
  def apply(): CoffeeRepository = new MongoCoffeeRepository()
}