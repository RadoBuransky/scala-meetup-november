package services.impl

import java.util.UUID

import common.CoffeeSystem
import model.{Coffee, CoffeeStrength}
import repositories.CoffeeRepository
import services.CoffeeService

private[services] class CoffeeServiceImpl(coffeeSystem: CoffeeSystem,
                                          repository: CoffeeRepository) extends CoffeeService {
  override def make(strength: CoffeeStrength) = {
    val timeIsNow = coffeeSystem.now

    if (timeIsNow.isAfter(timeIsNow.withTime(22, 0, 0, 0)))
      throw new IllegalStateException("You cannot drink coffee after 10 PM!")

    val coffee = Coffee(UUID.randomUUID(), strength, timeIsNow)
    repository.save(coffee).flatMap(_ => top())
  }

  override def get(id: UUID) = repository.get(id)
  override def all() = repository.all()
  override def top() = all().map(_.sortBy(-_.strength.value).take(3))
}
