package com.knoldus

import play.api.libs.json.{Format, Json}

case class Person(id: String, name: String, city: String)

object Person {
  implicit val format: Format[Person] = Json.format[Person]
}
