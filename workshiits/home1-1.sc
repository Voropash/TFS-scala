def fixByVal(step: ((BigInt, Long) => Long) => ((BigInt, Long) => Long)): (BigInt, Long) => Long =
  step(fixByVal(step))

def fixByName(step: (=> (BigInt, Long) => Long) => ((BigInt, Long) => Long)): (BigInt, Long) => Long =
  step(fixByName(step))



fixByName(recur => (x, steps) =>
  if (x == 1) steps
  else recur(if (x % 2 == 0) x / 2 else 3 * x + 1, steps + 1))(27, 0)

//fixByVal(recur => (x, steps) =>
//  if (x == 1) steps
//  else recur(if (x % 2 == 0) x / 2 else 3 * x + 1, steps + 1))(27, 0)
