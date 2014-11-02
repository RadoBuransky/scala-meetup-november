package services.impl

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WS
import services.CruzService

private[services] object WsCruzService extends CruzService {
  override def getSubject(id: Int) = getJsonByUrl(subjectUrl(id))
  override def getFinancialStatement(id: Int) = getJsonByUrl(financialStatementUrl(id))
  override def getAccountingStatement(id: Int) = getJsonByUrl(accountingStatementUrl(id))

  private def getJsonByUrl(url: String) = WS.url(url).get().map(_.json)
  private def subjectUrl(id: Int) = s"http://www.registeruz.sk/cruz-public/api/uctovna-jednotka?id=$id"
  private def financialStatementUrl(id: Int) = s"http://www.registeruz.sk/cruz-public/api/uctovna-zavierka?id=$id"
  private def accountingStatementUrl(id: Int) = s"http://www.registeruz.sk/cruz-public/api/uctovny-vykaz?id=$id"
}
