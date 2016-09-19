package ch.epfl.xblast.server

import ch.epfl.xblast.server.Player.LifeState.State
import ch.epfl.xblast.server.Player._
import ch.epfl.xblast.server.Ticks._
import ch.epfl.xblast.{Cell, Direction, PlayerID, SubCell}

final class Player(val id: PlayerID.Value,
                   val lifeStates: Stream[LifeState],
                   val directedPos: Stream[DirectedPosition],
                   val maxBombs: Int,
                   val bombRange: Int) {


  require(maxBombs >= 0)
  require(bombRange >= 0)

  def lifeState: LifeState =
    lifeStates.head


  def statesForNextLife: Stream[LifeState] =
    Stream.fill(PLAYER_DYING_TICKS)(LifeState(lives, State.DYING)) #:::
      Player.lifeStates(lives - 1)

  def lives: Int =
    lifeState.lives

  def isAlive: Boolean =
    lives > 0

  def position: SubCell =
    directedPos.head.position

  def direction: Direction =
    directedPos.head.direction

  def newBomb: Bomb =
    Bomb(id, position.containingCell, Ticks.BOMB_FUSE_TICKS, bombRange)
}

object Player {

  def apply(id: PlayerID.Value,
            lifeStates: Stream[LifeState],
            directedPos: Stream[DirectedPosition],
            maxBombs: Int,
            bombRange: Int): Player =
    new Player(id, lifeStates, directedPos, maxBombs, bombRange)

  def apply(id: PlayerID.Value,
            lives: Int,
            position: Cell,
            maxBombs: Int,
            bombRange: Int): Player = {
    require(lives >= 0)

    val lifeStates = Player.lifeStates(lives)
    val subCell = SubCell.centralSubCellOf(position)
    val directedPos = Stream.continually(DirectedPosition(subCell, Direction.S))

    new Player(id, lifeStates, directedPos, maxBombs, bombRange)
  }

  private def lifeStates(lives: Int): Stream[LifeState] =
    if (lives > 0) {
      Stream.fill(PLAYER_INVULNERABLE_TICKS)(LifeState(lives, State.INVULNERABLE)) #:::
        Stream.continually(LifeState(lives, State.VULNERABLE))
    } else {
      Stream.continually(LifeState(0, State.DEAD))
    }

  import LifeState._

  final case class LifeState(lives: Int, state: State.Value) {
    import State._

    require(lives >= 0)

    def canMove = state match {
      case INVULNERABLE | VULNERABLE => true
      case _                         => false
    }
  }

  object LifeState {
    object State extends Enumeration {
      val INVULNERABLE, VULNERABLE, DYING, DEAD = Value
    }
  }

  final case class DirectedPosition(position: SubCell, direction: Direction)

  object DirectedPosition {

    def stopped(p: DirectedPosition): Stream[DirectedPosition] =
      Stream.continually(p)

    def moving(p: DirectedPosition): Stream[DirectedPosition] =
      Stream.iterate(p) {
        case DirectedPosition(pos, dir) => DirectedPosition(pos.neighbor(dir), dir)
      }
  }
}
