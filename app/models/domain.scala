package models

import java.sql.{Date => SqlDate}
import java.util.Date

import slick.driver.PostgresDriver.api._
import slick.lifted.Tag

case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

// Employee model
case class Employee(id: Option[Long],
                    name: String,
                    address: String,
                    city: String,
                    county: String,
                    country: String,
                    postcode: String,
                    phone: Long,
                    email: String,
                    status: String)

class Employees(tag: Tag) extends Table[Employee](tag, "EMPLOYEE") {

  implicit val dateColumnType = MappedColumnType.base[Date, Long](d => d.getTime, d => new Date(d))

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def address = column[String]("address")
  def city = column[String]("city")
  def county = column[String]("county")
  def country = column[String]("country")
  def postcode = column[String]("postcode")
  def phone = column[Long]("phone")
  def email = column[String]("email")

  def status = column[String]("status")

  def * = (id.?, name, address, city, county, country, postcode, phone, email, status) <> (Employee.tupled, Employee.unapply _)

}
