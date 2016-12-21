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
      "name" -> nonEmptyText,
      "address" -> nonEmptyText,
      "dob" -> optional(date("dd/MM/yyyy")),
      "joiningDate" -> default(date("dd/MM/yyyy"), new java.util.Date),
      "designation" -> optional(text))(Employee.apply)(Employee.unapply))

}
