package ro.igstan.core

import ro.igstan.macros.StringEnum.Enum

object Main {

  // The Enum macro will generate the equivalent of this:
  //
  // object Ramones {
  //   val Joey   = "Joey"
  //   val Johnny = "Johnny"
  //   val Tommy  = "Tommy"
  // }
  object Ramones extends Enum("Joey", "Johnny", "Tommy")

  def main(args: Array[String]): Unit = {
    println(Ramones.Joey)
    println(Ramones.Johnny)
    println(Ramones.Tommy)
  }
}
