package ch.epfl.xblast.server

import ch.epfl.xblast.{Cell, Direction, PlayerID}

final class GameState private (val ticks: Int,
                               val board: Board,
                               val players: Seq[Player],
                               bombs: Seq[Bomb],
                               explosions: Seq[Stream[Stream[Cell]]],
                               blasts: Seq[Stream[Cell]]) {

  require(ticks >= 0)
  require(players.size == PlayerID.values.size)

  def isGameOver: Boolean =
    ticks > Ticks.TOTAL_TICKS || players.count(_.isAlive) <= 1

  def remainingTime: Int =
    (Ticks.TOTAL_TICKS - ticks) / Ticks.TICKS_PER_SECOND

  def winner: Option[Player] = alivePlayers match {
    case Seq(winner) => Some(winner)
    case _           => None
  }

  def alivePlayers: Seq[Player] =
    players.filter(_.isAlive)

  def bombedCells: Map[Cell, Bomb] =
    bombs.map(b => (b.position, b)).toMap

  def blastedCells: Set[Cell] =
    blasts.flatMap(_.headOption).toSet

  def next(speedChangeEvents: Map[PlayerID.Value, Direction], bombDropEvents: Set[PlayerID.Value]): GameState = {
    val board1 = ???
    val bombs1 = ???
    val explosions1 = ???
    val blasts1 = nextBlasts(blasts, board, explosions)
    GameState(ticks - 1, board1, players, bombs1, explosions1, blasts1)
  }

  private def nextBlasts(blasts0: Seq[Stream[Cell]],
                         board0: Board,
                         explosions0: Seq[Stream[Stream[Cell]]]): Seq[Stream[Cell]] = {
    val existings = blasts0.collect {
      case x #:: xs if board0.blockAt(x).isFree => xs
    }

    val news = explosions0.map(_.head)

    existings ++ news
  }
}

object GameState {
  def apply(ticks: Int,
            board: Board,
            players: Seq[Player],
            bombs: Seq[Bomb],
            explosions: Seq[Stream[Stream[Cell]]],
            blasts: Seq[Stream[Cell]]): GameState =
    new GameState(ticks, board, players, bombs, explosions, blasts)

  def apply(board: Board, players: Seq[Player]): GameState = {
    val ticks = 0
    val bombs = Seq.empty
    val explosions = Seq.empty
    val blasts = Seq.empty

    new GameState(ticks, board, players, bombs, explosions, blasts)
  }
}
