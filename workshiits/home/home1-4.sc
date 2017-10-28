def fixByName(step: (=> (BigInt, Long) => Long) => ((BigInt, Long) => Long)): (BigInt, Long) => Long =
  step(fixByName(step))

def countOnes(num: BigInt, count: Long): Long =
  if (num == 0) count
  else countOnes(num / 2, if (num % 2 == 0) count else count + 1)

countOnes(2221, 0)

fixByName(recur => (x, steps) =>
  if (x == 0) steps
  else recur(x / 2, if (x % 2 == 0) steps
                    else steps + 1))(2221, 0)
