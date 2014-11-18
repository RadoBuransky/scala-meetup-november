package services

import common.CoffeeSystem
import model.{Coffee, CoffeeStrength}
import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar
import org.specs2.runner.JUnitRunner
import repositories.CoffeeRepository
import services.impl.CoffeeServiceImpl
import org.mockito.Mockito._
import org.mockito.Matchers._

import scala.Unit
import scala.util.Success

@RunWith(classOf[JUnitRunner])
class CoffeeServiceSpec extends FlatSpec with MockitoSugar {
  behavior of "make"

  it should "fail if time is after 10 pm" in {
    val coffeeSystem = mock[CoffeeSystem]
    val repository = mock[CoffeeRepository]
    val service = new CoffeeServiceImpl(coffeeSystem, repository)

    // Setup
    val now = DateTime.now
    when(coffeeSystem.now).thenReturn(now.withTime(23, 0, 0, 0))

    // Execute
    intercept[IllegalStateException] {
      service.make(CoffeeStrength(10))
    }

    // Verify
    verify(coffeeSystem).now
    verifyZeroInteractions(repository)
  }

  it should "save to repository" in {
    val coffeeSystem = mock[CoffeeSystem]
    val repository = mock[CoffeeRepository]
    val service = new CoffeeServiceImpl(coffeeSystem, repository)

    // Setup
    val now = DateTime.now
    when(coffeeSystem.now).thenReturn(now.withTime(18, 0, 0, 0))
    when(repository.save(any[Coffee])).thenReturn(Success(()))
    when(repository.all()).thenReturn(Success(Nil))

    // Execute
    assert(service.make(CoffeeStrength(10)).isSuccess)

    // Verify
    verify(coffeeSystem).now
    verify(repository).save(any[Coffee])
    verify(repository).all()
  }
}
