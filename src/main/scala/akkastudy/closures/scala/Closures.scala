package akkastudy.closures.scala

object Closures {
  def foo(f: Int => Int): Int = f(5)

  def main(args: Array[String]) {
    val x: Integer = 3
    val add3: Int => Int = z => x + z
    System.out.println(foo(add3))
  }
}
