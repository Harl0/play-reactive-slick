package test

import org.specs2.mutable._
import play.api.test._

class IntegrationSpec extends Specification {

  "Application" should {

    "work from within a browser" in new WithBrowser {

      browser.goTo("http://localhost:" + port)

      browser.pageSource must contain("JHSG")
    }
  }

}
