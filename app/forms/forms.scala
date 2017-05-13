package forms

import models.Employee
import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by Jason on 21/12/2016.
  */
object forms {

  /**
    * Describe the employee form (used in both edit and create screens).
    */
  val employeeForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> text.verifying("Name Required", _.nonEmpty),
      "address" -> text.verifying("Address Required", _.nonEmpty),
      "city" -> text.verifying("City Required", _.nonEmpty),
      "county" -> text.verifying("County Required", _.nonEmpty),
      "country" -> text.verifying("Country Required", _.nonEmpty),
      "postcode" -> text.verifying("Post Code Required", _.nonEmpty),
      "phone" -> longNumber,
      "email" -> text.verifying("E-Mail Required", _.nonEmpty),
      "status" -> text.verifying("Status Required", _.nonEmpty )
    )
    (Employee.apply)(Employee.unapply)
    verifying("Status must equal Active OR Archived", employee => employee.status.matches("Active") || employee.status.matches("Archived"))
  )
}
