
trait Printable[A] {
  def format(value: A): String
}

object Printable {
  final case class Person(name: String, email: String)

  implicit val personPrintable: Printable[Person] = (value: Person) => value.name + " " + value.email
}

object PrintableInterface{
  def print[A](value : A)( implicit m: Printable[A]): String = m.format(value)
}

object PrintableSyntax {
  implicit class PrintAbleOps[A](value: A) {
    def format(implicit v: Printable[A]): String = {
      v.format(value)
    }
  }
}

object MainClass{
  def main(args: Array[String]): Unit = {
    import Printable._
    import PrintableSyntax._
    println(Person("test", "123").format)
  }
}
