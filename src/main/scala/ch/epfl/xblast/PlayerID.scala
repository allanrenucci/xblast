package ch.epfl.xblast

//sealed trait PlayerID
//
//object PlayerID {
//  case object PLAYER_1 extends PlayerID
//  case object PLAYER_2 extends PlayerID
//  case object PLAYER_3 extends PlayerID
//  case object PLAYER_4 extends PlayerID
//}

object PlayerID extends Enumeration {
  val PLAYER_1, PLAYER_2, PLAYER_3, PLAYER_4 = Value
}
