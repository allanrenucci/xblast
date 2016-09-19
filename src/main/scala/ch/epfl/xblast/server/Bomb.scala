package ch.epfl.xblast.server

import ch.epfl.xblast.server.Ticks._
import ch.epfl.xblast.{Cell, Direction, PlayerID}

final class Bomb private (val ownerId: PlayerID.Value,
                  val position: Cell,
                  val fuseLengths: Stream[Int],
                  val range: Int) {
  require(fuseLengths.nonEmpty)
  require(range > 0)

  def fuseLength: Int = fuseLengths.head

  def explosion: Seq[Stream[Stream[Cell]]] = {
    def explosionTowards(dir: Direction): Stream[Stream[Cell]] = {
      val particle = Stream.iterate(position)(_.neighbor(dir)).take(range)
      Stream.fill(EXPLOSION_TICKS)(particle)
    }

    Direction.values.toSeq.map(explosionTowards)
  }
}

object Bomb {
  def apply(ownerId: PlayerID.Value, position: Cell, fuseLengths: Stream[Int], range: Int): Bomb =
    new Bomb(ownerId, position, fuseLengths, range)

  def apply(ownerId: PlayerID.Value, position: Cell, fuseLength: Int, range: Int): Bomb = {
    val fuseLengths = Stream.iterate(fuseLength)(_ - 1).take(fuseLength)
    new Bomb(ownerId, position, fuseLengths, range)
  }
}
