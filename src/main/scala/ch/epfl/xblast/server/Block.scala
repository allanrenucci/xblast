package ch.epfl.xblast.server

sealed trait Block {
  import Block._

  def isFree: Boolean = this == FREE

  def canHostPlayer: Boolean = this == FREE

  def castsShadow: Boolean = this match {
    case INDESTRUCTIBLE_WALL | DESTRUCTIBLE_WALL | CRUMBLING_WALL => true
    case _                                                        => false
  }
}

object Block {
  case object FREE                extends Block
  case object INDESTRUCTIBLE_WALL extends Block
  case object DESTRUCTIBLE_WALL   extends Block
  case object CRUMBLING_WALL      extends Block

  def values: Set[Block] = Set(FREE, INDESTRUCTIBLE_WALL, DESTRUCTIBLE_WALL, CRUMBLING_WALL)
}
