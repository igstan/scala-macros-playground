package ro.igstan.core

import ro.igstan.macros.Macros.describe

object Main {

  case class Property(id: String, foo: String, bar: String)

  trait BSONDocumentWriter[P]

  implicit object propertyBSONWriter extends BSONDocumentWriter[Property] {
    def write(property: Property) = {
      Seq()                      ++
      Seq("ID"  -> property.id)  ++
      Seq("FOO" -> property.foo) ++
      Seq("BAR" -> property.bar)
    }
  }

  val BSONDocument = Seq

  implicit object propertyBSONWriter2 extends BSONDocumentWriter[Property] {
    def write(property: Property) = describe(property)
  }

  def main(args: Array[String]): Unit = {
    val p = Property("id", "123", "456")

    val w1 = propertyBSONWriter.write(p)
    println(w1)

    val w2 = propertyBSONWriter2.write(p)
    println(w2)
  }
}
