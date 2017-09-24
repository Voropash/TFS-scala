def fixByVal(step: ((BigInt, Long) => Long) => ((BigInt, Long) => Long)): (BigInt, Long) => Long = {
  println(1)
  step(fixByVal(step))
}
def fixByName(step: (=> (BigInt, Long) => Long) => ((BigInt, Long) => Long)): (BigInt, Long) => Long = {
  println(step)
  step(fixByName(step))
}



val function1: (=> (BigInt, Long) => Long) => (BigInt, Long) => Long =
  recur => (x, steps) => {
    println(x)
    if (x == 1) steps
    else recur(if (x % 2 == 0) x / 2 else 3 * x + 1, steps + 1)
  }

fixByName(function1)(27, 0)

val function2: ((BigInt, Long) => Long) => (BigInt, Long) => Long =
  recur => (x, steps) => {
    println(0)
    println(x)
    println(steps)
    println(recur)
    println("!")
    if (x == 1) steps
    else recur(if (x % 2 == 0) x / 2 else 3 * x + 1, steps + 1)
  }

fixByVal(function2)(27, 0)
