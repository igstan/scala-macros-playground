package ro.igstan.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

object Macros {
  def describe[P](property: P): Seq[(String, String)] = macro describeMacro[P]

  def describeMacro[P : c.WeakTypeTag](c: Context)(property: c.Expr[P]): c.Expr[Seq[(String, String)]] = {
    import c.universe._

    val fields = weakTypeOf[P].declarations.collect {
      case m: MethodSymbol if m.isCaseAccessor =>
        val name = c.literal(m.name.decoded.toUpperCase)
        val value = c.Expr(Select(property.tree, m.name))
        val pair = reify(name.splice -> value.splice).tree
        Apply(Ident(newTermName("BSONDocument")), List(pair))
    }

    val plused = fields.reduceLeft { (acc, a) =>
      Apply(Select(acc, newTermName("$plus$plus")), List(a))
    }

    c.Expr[Seq[(String,String)]](plused)
  }
}
