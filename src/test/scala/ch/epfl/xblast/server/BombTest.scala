package ch.epfl.xblast.server

import ch.epfl.xblast.{Cell, Direction, PlayerID}
import org.scalatest.FunSuite

class BombTest extends FunSuite {

  test("apply(PlayerID, Cell, Int, Int) is correct") {
    val fuseLengths = Bomb(PlayerID.PLAYER_1, Cell(0, 0), 3, 6).fuseLengths

    assert(Stream(3, 2, 1) == fuseLengths)
  }

  private  def armDirection(arm: Stream[Stream[Cell]]): Direction = {
    Direction.values.find { dir =>
      val particle = arm.head
      particle.head.neighbor(dir) == particle(1)
    }.get
  }

  test("explosion contains all direction") {
    val explosion = Bomb(PlayerID.PLAYER_1, Cell(0, 0), 1, 3).explosion

    explosion.map(armDirection).toSet == Direction.values
  }

  test("explosion contains right number of particles") {
    val explosion = Bomb(PlayerID.PLAYER_1, Cell(0, 0), 1, 3).explosion

    assert(
      explosion.forall { arm =>
        arm.size == Ticks.EXPLOSION_TICKS
      }
    )
  }

  test("explosion arms have correct duration and range") {
    val range = 2
    val bomb = Bomb(PlayerID.PLAYER_1, Cell(0, 0), 1, range)
    val explosion = bomb.explosion

    for {
      arm <- explosion
      dir = armDirection(arm)
      particle <- arm
    } {
      val expected = Stream.iterate(bomb.position)(_.neighbor(dir)).take(range)
      assert(particle == expected)
    }
  }

}
