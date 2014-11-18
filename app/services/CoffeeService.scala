package services

import java.util.UUID

import common.CoffeeSystem
import model.{Coffee, CoffeeStrength}
import repositories.CoffeeRepository
import services.impl.CoffeeServiceImpl

import scala.util.Try

trait CoffeeService {
  def make(strength: CoffeeStrength): Try[Seq[Coffee]]
  def get(id: UUID): Try[Option[Coffee]]
  def all(): Try[Seq[Coffee]]
  def top(): Try[Seq[Coffee]]
}

object CoffeeService {
  def apply(coffeeSystem: CoffeeSystem, coffeeRepository: CoffeeRepository): CoffeeService =
    new CoffeeServiceImpl(coffeeSystem, coffeeRepository)
}
