package ro.igstan.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

object StringEnum {
  def impl[A](c: Context)(factory: c.Expr[String => A], vals: c.Expr[String]*) = {
    import c.universe._

    val Template(parents, self, ctor :: defs) = c.enclosingTemplate

    val values = vals.map { value =>
      val Expr(Literal(Constant(name: String))) = value
      val fn = c.resetAllAttrs(factory.tree)

      // Manual AST construction mixed with quasiquotes, which works fine.
      //
      // ValDef(
      //   mods = Modifiers(),
      //   name = TermName(name),
      //   tpt  = TypeTree(),
      //   rhs  = q"$fn($name)"
      // )

      // Building the AST for the `val` definition using quasiquotation.

      // Not working
      // q"val $name = $fn($name)"

      // Working
      q"val ${TermName(name)} = $fn($name)"
    }

    Template(Nil, emptyValDef, ctor :: defs ++ values)
  }

  type Enum[A](factory: String => A, vals: String*) = macro impl[A]
}
