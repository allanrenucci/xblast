package ch.epfl.xblast.server

import org.scalatest.FunSuite

class BlockTest extends FunSuite {
  import Block._

  test("isFree behaviour is correct") {
    assert(FREE.isFree)
    assert(!INDESTRUCTIBLE_WALL.isFree)
    assert(!DESTRUCTIBLE_WALL.isFree)
    assert(!CRUMBLING_WALL.isFree)
//    assert(!BONUS_BOMB.isFree)
//    assert(BONUS_RANGE.isFree
  }

  test("canHostPlayer behavior is correct") {
    assert(FREE.canHostPlayer)
//    assert(BONUS_BOMB.canHostPlayer)
//    assert(BONUS_RANGE.canHostPlayer)
    assert(!INDESTRUCTIBLE_WALL.canHostPlayer)
    assert(!DESTRUCTIBLE_WALL.canHostPlayer)
    assert(!CRUMBLING_WALL.canHostPlayer)
  }

  test("castsShadow behaviour is correct") {
    assert(INDESTRUCTIBLE_WALL.castsShadow)
    assert(DESTRUCTIBLE_WALL.castsShadow)
    assert(CRUMBLING_WALL.castsShadow)
    assert(!FREE.castsShadow)
//    assert(!BONUS_BOMB.castsShadow)
//    assert(!BONUS_RANGE.castsShadow)
  }

//  test("retrievingBonus behavior is correct") {
//    intercept[NoSuchElementException] {
//      FREE.associatedBonus
//    }
//
//    intercept[NoSuchElementException] {
//      INDESTRUCTIBLE_WALL.associatedBonus
//    }
//
//    intercept[NoSuchElementException] {
//      DESTRUCTIBLE_WALL.associatedBonus
//    }
//
//    intercept[NoSuchElementException] {
//      CRUMBLING_WALL.associatedBonus
//    }
//
//    assert(INC_BOMB == BONUS_BOMB.associatedBonus)
//
//    assert(INC_RANGE == BONUS_RANGE.associatedBonus)
//  }

//  test("isBonus behaviour is correct") {
//    assert(BONUS_RANGE.isBonus)
//    assert(BONUS_BOMB.isBonus)
//    assert(!FREE.isBonus)
//    assert(!INDESTRUCTIBLE_WALL.isBonus)
//    assert(!DESTRUCTIBLE_WALL.isBonus)
//    assert(!CRUMBLING_WALL.isBonus)
//  }

}
