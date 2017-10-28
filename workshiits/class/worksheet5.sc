def sum(n: Int, acc: Int): Int =
  if (n == 0) acc
  else sum(n - 1, acc + n)

sum(5, 0)

def fix[A](f: (=> A) => A): A = f(fix(f))

//def tailRec[A,B](f: A => Either)

val sumF = fix[(Int, Int) => Int](recur => (n, acc) =>
  if (n == 0) acc
  else recur(n - 1, acc + n)
)

sumF(10, 0)
sumF(1000, 0)
//sumF(10000,0)

sum(10000, 0)


val myArray = Array(
  Array("пельмени", "очень", "вредная", "еда"),
  Array("бетон ", "крепче дерева"),
  Array("scala", "вообще", "не", "еда"),
  Array("скорее", "бы", "в", "отпуск")
)
for (anArray: Array[String] <- myArray;
     aString: String <- anArray;
     aStringUC = aString.toUpperCase()
     if aStringUC.indexOf("ЕДА") != -1
) {
  println(aString)
}

val foodArray: Array[String] =
  for (anArray: Array[String] <- myArray;
       aString: String <- anArray;
       aStringUC = aString.toUpperCase()
       if aStringUC.indexOf("ЕДА") != -1
  ) yield {
    aString
  }
