val list = List(1, 2, 3, 4, 5, 6, 7, 8, 100500)
def printList(list: List[Int]): Unit = list match {
  case List(head) => println(head)
  case List(head, t /*чтото было8/) :: tail =>
    printList(tail)
    println(head)
}
printList(list)
