package ro.igstan.core

import ro.igstan.macros.StringEnum.Enum

object Main {

  def factory(name: String) = s"$name Ramones"

  // The Enum macro should generate the equivalent of this:
  //
  // object Ramones {
  //   val Joey   = factory("Joey")
  //   val Johnny = factory("Johnny")
  //   val Tommy  = factory("Tommy")
  // }
  object Ramones extends Enum(factory, "Joey", "Johnny", "Tommy")

  def main(args: Array[String]): Unit = {
    println(Ramones.Joey)
    println(Ramones.Johnny)
    println(Ramones.Tommy)
  }
}
