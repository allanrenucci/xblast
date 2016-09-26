package ch.epfl.xblast.server.debug

import ch.epfl.xblast.Cell
import ch.epfl.xblast.Direction._
import ch.epfl.xblast.PlayerID._
import ch.epfl.xblast.server.Block._
import ch.epfl.xblast.server.{Block, GameState, Player}

object GameStatePrinter {

  def printGameState(gameState: GameState): Unit = {
    val players = gameState.alivePlayers
    val board = gameState.board

    for (y <- 0 until Cell.ROWS) {
      for (x <- 0 until Cell.COLUMNS) {
        val cell = Cell(x, y)
        players.find(p => p.position.containingCell == cell) match {
          case Some(p) =>
            print(stringForPlayer(p))
          case _       =>
            val block = board.blockAt(cell)
            print(stringForBlock(block))
        }
      }
      println()
    }
  }

  private def stringForPlayer(player: Player): String = {
    val id = player.id match {
      case PLAYER_1 => 1
      case PLAYER_2 => 2
      case PLAYER_3 => 3
      case PLAYER_4 => 4
    }

    val direction = player.direction match {
      case N => '^'
      case S => '>'
      case E => 'v'
      case W => '<'
    }

    s"$id$direction"
  }

  private def stringForBlock(block: Block): String = block match {
    case FREE                => "  "
    case INDESTRUCTIBLE_WALL => "##"
    case DESTRUCTIBLE_WALL   => "??"
    case CRUMBLING_WALL      => "¿¿"
    case BONUS_BOMB          => "+b"
    case BONUS_RANGE         => "+r"
  }
}
