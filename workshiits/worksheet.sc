val func = (str: String) => print(str)


def func2 = print("hello world")

def func3[T](str:T) = print(str)

()


def printSomething() = " - это 2 плюс 3"
def calculateSomething() = 1 + 1
def result = calculateSomething + 3 + printSomething

result



val c = 'd'
val c2: Char = 'd'
val cA: Any = 'd'
// cI - иметь значение - 100
val cI: Int = 'd'
// cI2 - будет иметь значение 'd'
val cI2: Char = 100
val cD: Float = 'd'
// Won't compile
//val cD2: Char = 100.0
// Won't compile
//val cN: Nothing = 'd'
// SET FLOAT
val set = Set(1, 'c', 20f)
// SET AnyVal
val set2 = Set(1, 'c', 20f, true)
// Set Any
val set3 = Set(1, 'c', 20f, true, "foo")
//Wont't compile
//val set4: AnyVal = Set(1, 'c', 20f, true, "foo")

implicitly[Char => Int].apply('d')

set2.count{
  case u : Int => true
  case _ => false
}

set2.collect{
  case u : Int => true
  case _ => false
}

{x: Int => x}.apply(3)

def f(i: Int) = i + 3

def ff(prm1: Int, prm2: String): Option[Int] = ???

ff(1, "21")
