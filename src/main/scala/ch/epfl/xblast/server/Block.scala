package ch.epfl.xblast.server

import ch.epfl.xblast.server.Bonus.{INC_BOMB, INC_RANGE}

sealed trait Block {
  import Block._

  def isFree: Boolean =
    this == FREE

  def isBonus: Boolean = this match {
    case BONUS_BOMB | BONUS_RANGE => true
    case _                        => false
  }

  def associatedBonus: Bonus = this match {
    case BONUS_BOMB  => INC_BOMB
    case BONUS_RANGE => INC_RANGE
    case _           => throw new NoSuchElementException(s"$this does not have an associated bonus")
  }

  def canHostPlayer: Boolean = this match {
    case FREE | BONUS_BOMB | BONUS_RANGE => true
    case _                               => false
  }
    this == FREE

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
  case object BONUS_BOMB          extends Block
  case object BONUS_RANGE         extends Block

  def values: Set[Block] = Set(
    FREE,
    INDESTRUCTIBLE_WALL,
    DESTRUCTIBLE_WALL,
    CRUMBLING_WALL,
    BONUS_BOMB,
    BONUS_RANGE
  )
}
