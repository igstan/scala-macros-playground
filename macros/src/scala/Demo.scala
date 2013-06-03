package ro.igstan.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

class Demo(val y: String, val args: String*)

object Demo {
  def impl(c: Context)(x: c.Expr[Int], args: c.Expr[String]*) = {
    import c.universe._

    // For:
    //
    //   class Demo(val y: String, val args: List[String])
    //
    // you'd need this quasiquote:
    //
    //   q"new Demo($x.toString, List(..$args))"
    //

    q"new Demo($x.toString, ..$args)"
  }

  def apply(x: Int, args: String*) = macro impl
}
