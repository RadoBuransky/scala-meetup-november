package model

import java.util.UUID

import org.joda.time.DateTime

case class Coffee(_id: UUID,
                  strength: CoffeeStrength,
                  timestamp: DateTime)

case class CoffeeStrength(value: Int) extends AnyVal

object JsonFormats {
  import play.api.libs.json.Json

  implicit val coffeeStrengthFormat = Json.format[CoffeeStrength]
  implicit val coffeeFormat = Json.format[Coffee]
}