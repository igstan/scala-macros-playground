package ro.igstan.core

import ro.igstan.macros.Demo

object Main {
  def main(args: Array[String]): Unit = {
    val demo = Demo(42, "foo", "bar", "baz")
    println(s"y=${demo.y}; args=${demo.args}")
  }
}
