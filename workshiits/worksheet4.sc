def READpOWbIT(base: BigInt, p: Int)(bit: Int) = base.pow(p).testBit(bit)

READpOWbIT(3, 1000)(10)
READpOWbIT(3, 1000)(20)
READpOWbIT(3, 1000)(30)

def read3pow1000bb(base: BigInt, p: Int): Int => Boolean = {
  val num = base.pow(p)
  i => num.testBit(i)
}

val bit3100 = read3pow1000bb(3, 1000)

bit3100(10)
bit3100(20)

val cF = read3pow1000bb.curried
