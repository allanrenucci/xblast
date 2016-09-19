package ch.epfl.xblast.server

import ch.epfl.xblast.server.Player.LifeState.State
import ch.epfl.xblast.server.Player.{DirectedPosition, LifeState}
import ch.epfl.xblast.server.Ticks._
import ch.epfl.xblast.{Cell, Direction, PlayerID, SubCell}
import org.scalatest.FunSuite

class PlayerTest extends FunSuite {

  val lives = 2
  val maxBombs = 2
  val newMaxBombs = 3
  val bombRange = 2
  val newBombRange = 3
  val id = PlayerID.PLAYER_1
  val direction = Direction.S
  val cellPosition = Cell(0, 0)
  val subCellPosition = SubCell(0, 0)
  val lifeStates = Stream.continually(LifeState(lives, LifeState.State.VULNERABLE))
  val directedPos = DirectedPosition.stopped(DirectedPosition(subCellPosition, direction))
  val player = Player(id, lifeStates, directedPos, maxBombs, bombRange)
  val player2 = Player(id, lives, cellPosition, maxBombs, bombRange)
  val deadPlayer = Player(id, 0, cellPosition, maxBombs, bombRange)


  test("LifeState") {
    intercept[IllegalArgumentException] {
      LifeState(-1, State.VULNERABLE)
    }

    State.values.foreach { s =>
      assert(s == LifeState(1, s).state)
    }

    (0 to 10).foreach { lives =>
      assert(lives == LifeState(lives, State.VULNERABLE).lives)
    }
  }

  test("canMove") {
    State.values.foreach { s =>
      val ls = LifeState(1, s)
      assert {
        ls.canMove == {
          s == State.INVULNERABLE ||
          s == State.VULNERABLE
        }
      }
    }
  }

  test("statesForNextLife should have a period of dying states") {
    val lifeStates = player.statesForNextLife

    assert {
      lifeStates.take(PLAYER_DYING_TICKS).forall(_.state == State.DYING)
    }

    assert(lifeStates(PLAYER_DYING_TICKS).state != State.DYING)
  }

  test("statesForNextLife should have a period of dying states have on less ") {
    val lives = 3
    val player = Player(id, lives, cellPosition, maxBombs, bombRange)
    val lifeStates = player.statesForNextLife


  }

  test("testNewBomb") {

  }

  test("testBombRange") {

  }

  test("testIsAlive") {

  }

  test("testLives") {

  }

  test("testStatesForNextLife") {

  }

  test("testDirection") {

  }

  test("testMaxBombs") {

  }

  test("testId") {

  }

  test("testLifeStates") {

  }

  test("testApply") {

  }

}
