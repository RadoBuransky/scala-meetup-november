package services

import common.CoffeeSystem
import repositories.CoffeeRepository
import services.impl.CoffeeServiceImpl

import scala.util.Try

class CoffeeStrength private (val value: Int) extends AnyVal

object CoffeeStrength {
  def apply(value: Int) = {
    if (value <= 0)
      throw new IllegalArgumentException(s"strength must be a positive number! [$value]")

    new CoffeeStrength(value)
  }
}

class DailyStats(val avgCoffeeStrength: Double)

trait CoffeeService {
  def makeCoffee(coffeeStrength: CoffeeStrength): Try[DailyStats]
}

object CoffeeService {
  def apply(coffeeSystem: CoffeeSystem, coffeeRepository: CoffeeRepository): CoffeeService =
    new CoffeeServiceImpl(coffeeSystem, coffeeRepository)
}
