List(1, 2, 3) ++ List(4, 5, 6)
List(1, 2, 3) ::: List(4, 5, 6)
Vector(1, 2, 3) ++ List(4, 5, 6)
List(1, 2, 3) ++ Vector(4, 5, 6)
Vector(1, 2, 3) ++ Vector(4, 5, 6)

List().isEmpty
List().nonEmpty

"fsa123123".map(_.toUpper)
"asd".flatMap(
  c => List(c, c)
)


List(true, false, true, true).map {
  x => if (x) 1 else 2
}

List(1, 2, 3, 4).collect {
  case x if x % 2 == 0 => (x + 1).toString
}

// Map нельзя для Partial function

List(1, 2, 3, 4).flatMap {
  x => List.fill(x)(x)
}

List(1, 2, 3, 4).map {
  x => List.fill(x)(x)
}.flatten

for {
  x <- List(1, 2, 3, 4)
  y <- List.fill(x)(x)
} yield x

List(4, 5, 4).toVector.toArray.toSet.toList
List(4, 5, 4).distinct

List((1, "1"), (2, "2")).toMap
List(1 -> "1", 2 -> "2").toMap
//List[Any](1,2).toMap // error



