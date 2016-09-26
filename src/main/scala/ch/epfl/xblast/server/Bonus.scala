package ch.epfl.xblast.server

trait Bonus {
  def applyTo(player: Player): Player = ???
}

object Bonus {
  case object INC_BOMB  extends Bonus
  case object INC_RANGE extends Bonus
}
