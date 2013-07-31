package ro.igstan.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

object Macros {
  def id[A](a: A): A = macro idMacro[A]
  def idMacro[A](c: Context)(a: c.Expr[A]): c.Expr[A] = a
}
