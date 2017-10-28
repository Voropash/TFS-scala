List('1', '2').head
"123".head
//"".head
"".headOption
"123das".last
List(1, 2, 3).collectFirst {
  case c if c > 2 => c.toString
}

List(1, 2, 3).find(_ >= 2)

List.range(1, 10).tail.tail.tail.init.init

List.range(1, 10).slice(1, 5)

List.range(1, 10).take(5)

List.range(1, 10).drop(3)

List.range(1, 10).takeWhile(_ < 4)
// dropWhile

List.range(1, 10).filter(_ > 4)

List(1, 2, 3, 4, 5, 4, 3, 2, 1).splitAt(3)

List(1, 2, 3, 4, 5, 4, 3, 2, 1).span(_ < 4)


