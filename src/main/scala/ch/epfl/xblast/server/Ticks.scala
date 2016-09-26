package ch.epfl.xblast.server

import ch.epfl.xblast.Time._

object Ticks {
  val PLAYER_DYING_TICKS        = 8
  val PLAYER_INVULNERABLE_TICKS = 64
  val BOMB_FUSE_TICKS           = 100
  val EXPLOSION_TICKS           = 30
  val WALL_CRUMBLING_TICKS      = EXPLOSION_TICKS
  val BONUS_DISAPPEARING_TICKS  = EXPLOSION_TICKS
  val TICKS_PER_SECOND          = 20
  val TICK_NANOSECOND_DURATION  = NS_PER_S / TICKS_PER_SECOND
  val TOTAL_TICKS               = 2 * S_PER_MIN * TICKS_PER_SECOND
}
