package ro.igstan.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

object StringEnum {
  def impl(c: Context)(vals: c.Expr[String]*) = {
    import c.universe._

    // A `Template` is whatever comes after the `extends` keyword when you
    // declare a Scala class or object. Here we're getting the Template of
    //
    //   object Ramones extends Enum("Joey", "Johnny", "Tommy")
    //
    // A Template consists of:
    //
    //  - a list of parents of that class/object
    //  - a representation of the self type (not sure what this is yet)
    //  - the body (constructor + definitions) of the class/object
    //
    val Template(parents, self, ctor :: defs) = c.enclosingTemplate


    // Now, we're just mapping over the list of names we've passed to `Enum`
    // in order to transform them into `val` definitions.
    val values = vals.map { valName =>
      // Each name is the AST representation of a String, so we have to
      // extract the actual string name from it. In this case we're using
      // pattern matching in assignment.
      val Expr(Literal(Constant(name: String))) = valName

      // Finally, we're building the AST for the `val` definition
      ValDef(
        mods = Modifiers(),
        name = TermName(name),
        tpt  = TypeTree(),
        rhs  = Literal(Constant(name))
      )
    }

    // Having a list of `val` ASTs, we can now build a new Template to
    // replace the one given to us by the compiler.
    Template(Nil, emptyValDef, ctor :: defs ++ values)
  }

  type Enum(vals: String*) = macro impl
}
