def f(x: Int)(y: Int, z: Int) = x + y + z

val foo = f(1) _

foo(2, 3)


val bar = (x: Int) => (y: String) => y * x

val bar2: Function2[ Int, String, String] = ???

