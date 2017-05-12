package controllers

import java.util.concurrent.TimeoutException

import forms._
import models.{DAO, DAOComponent}
import play.api._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import views._

import scala.concurrent.Future

/**
  * Manage a database of employees
  */
class Application(dao: DAOComponent) extends Controller {

  /**
    * This result directly redirect to the application home.
    */
  val Home = Redirect(routes.Application.list(0, 2, ""))

  /**
    * Handle default path requests, redirect to employees list
    */
  def index = Action {
    Home
  }

  /**
    * Display the paginated list of employees.
    */
  def list(page: Int, orderBy: Int, filter: String): Action[AnyContent] = Action.async { implicit request =>
    dao.list(page, 10, orderBy, "%" + filter + "%").map { pageEmp =>
      Ok(html.list(pageEmp, orderBy, filter))
    }.recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in employee list process")
        InternalServerError(ex.getMessage)
    }
  }

  /**
    * Display the paginated list of ACTIVE employees.
    */
  def listActive(page: Int, orderBy: Int, filter: String): Action[AnyContent] = Action.async { implicit request =>
    dao.listActive(page, 10, orderBy, "%" + filter + "%").map { pageEmp =>
      Ok(html.list(pageEmp, orderBy, filter))
    }.recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in employee list process")
        InternalServerError(ex.getMessage)
    }
  }

  /**
    * Display the paginated list of ARCHIVED employees.
    */
  def listArchived(page: Int, orderBy: Int, filter: String): Action[AnyContent] = Action.async { implicit request =>
    dao.listArchived(page, 10, orderBy, "%" + filter + "%").map { pageEmp =>
      Ok(html.list(pageEmp, orderBy, filter))
    }.recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in employee list process")
        InternalServerError(ex.getMessage)
    }
  }

  /**
    * Display the 'edit form' of a existing employee.
    */
  def edit(id: Long): Action[AnyContent] = Action.async { implicit request =>
    dao.findById(id).map(employee => Ok(html.editForm(id, forms.employeeForm.fill(employee)))).recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in employee edit process")
        InternalServerError(ex.getMessage)
    }
  }

  /**
    * Handle the 'edit form' submission
    */
  def update(id: Long): Action[AnyContent] = Action.async { implicit request =>
    forms.employeeForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.editForm(id, formWithErrors))),
      employee => {
        val futureEmpUpdate = dao.update(id, employee.copy(id = Some(id)))
        futureEmpUpdate.map { result =>
          Home.flashing("success" -> "Employee %s has been updated".format(employee.name))
        }.recover {
          case ex: TimeoutException =>
            Logger.error("Problem found in employee update process")
            InternalServerError(ex.getMessage)
        }
      })
  }

  /**
    * Display the 'new employee form'.
    */
  def create: Action[AnyContent] = Action { implicit request =>
    Ok(html.createForm(forms.employeeForm))
  }

  /**
    * Handle the 'new employee form' submission.
    */
  def save: Action[AnyContent] = Action.async { implicit request =>
    forms.employeeForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.createForm(formWithErrors))),
      employee => {
        val futureEmpInsert = dao.insert(employee)
        futureEmpInsert.map { result => Home.flashing("success" -> "Employee %s has been created".format(employee.name)) }.recover {
          case ex: TimeoutException =>
            Logger.error("Problem found in employee save process")
            InternalServerError(ex.getMessage)
        }
      })
  }

  /**
    * Handle employee deletion
    */
  def delete(id: Long): Action[AnyContent] = Action.async { implicit request =>
    val futureEmpDel = dao.delete(id)
    futureEmpDel.map { result => Home.flashing("success" -> "Employee has been deleted") }.recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in employee delete process")
        InternalServerError(ex.getMessage)
    }
  }

}

object Application extends Application(DAO)
