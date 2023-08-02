// Define a very simple JSON AST


//There are three important components to the type class pattern:
// the type class itself
// instance for particular types
// interface methods that we expose to users.

// type class is an interface or API that represent some functionality we want to implement.
// type class is represented by a trait with at least one type parameter.
sealed trait Json

final case class JsObject(get: Map[String, Json]) extends Json

final case class JsString(get: String) extends Json

final case class JsNumber(get: Double) extends Json

case object JsNull extends Json

// The "serialize to JSON" behaviour is encoded in this trait
trait JsonWriter[A] { // is our type class
  def write(value: A): Json
}

// instance for particular types
// the instance of a type class provide implementations for the types we care about.
// including types from the scala standard library and types form our domain model.
object JsonWriterInstances {
  final case class Person(name: String, email: String)

  implicit val stringWriter: JsonWriter[String] =
    (value: String) => JsString(value)

  implicit val personWriter: JsonWriter[Person] =
    (value: Person) => JsObject(Map(
      "name" -> JsString(value.name),
      "email" -> JsString(value.email)
    ))
}

// interface methods that we expose to users.

// A type class interface is any functionality we expose to users.
// Interfaces are generic methods that accept instances of the type class as implicit parameters.
// Two common ways to specifying an interface: Interface Object and Interface syntax.


// interface objects
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = w.write(value)


  def main(args: Array[String]): Unit = {
    import JsonWriterInstances._
    println(Json.toJson(Person("Dave", "dave@example.com")))
    // Completer spot that we've called toJson method without providing the implicit parameters.
    //  It try to fix this by searching for type class instances of relevant types and inserting them at the call site:
    Json.toJson(Person("Dave", "dave@example.com"))(personWriter)

  }
}

// Interface syntax
object JsonSyntax {
  // we use interface syntax by importing it along side the instances for the types we need:
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json = w.write(value)
  }

  def main(args: Array[String]): Unit = {
    import JsonSyntax._
    import JsonWriterInstances._
    println(Person("Dave", "dave@example.com").toJson)
    // equal
    println(JsonWriterOps(Person("Dave", "dave@example.com")).toJson(personWriter))
  }
}