package ch.epfl.xblast


sealed trait Direction {
  import Direction._

  def opposite: Direction = this match {
    case N => S
    case S => N
    case E => W
    case W => E
  }

  def isHorizontal: Boolean = this match {
    case E | W => true
    case _     => false
  }

  def isParallelTo(that: Direction): Boolean =
    this == that || this == that.opposite
}

object Direction {
  case object N extends Direction
  case object S extends Direction
  case object E extends Direction
  case object W extends Direction

  def values: Set[Direction] = Set(N, S, E, W)
}
