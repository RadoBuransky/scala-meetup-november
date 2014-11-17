package services.impl

import common.CoffeeSystem
import repositories.CoffeeRepository
import services.{DailyStats, CoffeeStrength, CoffeeService}

import scala.util.{Success, Try}

private[services] class CoffeeServiceImpl(coffeeSystem: CoffeeSystem,
                                          coffeeRepository: CoffeeRepository) extends CoffeeService {
  override def makeCoffee(coffeeStrength: CoffeeStrength): Try[DailyStats] = {
    Try {
      // Get current time
      val timeIsNow = coffeeSystem.now

      // Save coffee to DB
      val coffeeId = coffeeRepository.saveCoffee(coffeeStrength.value, timeIsNow)

      // Create result
      new DailyStats(1.0)
    }
  }
}
