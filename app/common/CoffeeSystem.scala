package common

import org.joda.time.DateTime

trait CoffeeSystem {
  def now: DateTime
}

object CoffeeSystem extends CoffeeSystem {
  override def now: DateTime = DateTime.now()
}