case class Adress(str: String, b: Int)

case class Person(name: String, adress: Any)

val list = List("asdasd", Person("123", Adress("asd", 12)))

for (el <- list)
  el match {
    case Person(name adress@Adress(street, building)) =>
      println (s"$name : street, $building $adress")
    case _ =>
  }
