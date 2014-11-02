package services.impl

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsArray
import services.{CruzService, FinService}
import play.api.libs.json._

import scala.concurrent.Future

private[services] class CruzFinService(cruzService: CruzService) extends FinService {
  override def getSubjectDetails(id: Int) = {
    cruzService.getSubject(id).flatMap { subject =>
      val accountingStatementFutures = for {
        accountingStatementId <- (subject \ "idUctovnychZavierok").as[JsArray].value
      } yield cruzService.getAccountingStatement(accountingStatementId.as[Int])

      // Seq[Future[_]] -> Future[Seq[_]]
      Future.sequence(accountingStatementFutures).map { accountingStatements =>
        // Update JSON
        subject.transform((__ \ "idUctovnychZavierok").json.update(
          __.read[JsArray].map(_ => JsArray(accountingStatements.filter(_ != JsNull)))
        )).get
      }
    }
  }
}
