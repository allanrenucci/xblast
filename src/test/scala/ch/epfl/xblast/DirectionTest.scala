package ch.epfl.xblast

import org.scalatest.FunSuite

class DirectionTest extends FunSuite {
  import Direction._

  test("opposite of opposite is identity") {
    Direction.values.foreach { d =>
      assert(d == d.opposite.opposite)
    }
  }

 test("opposite works for all 4 directions") {
    assert(S == N.opposite)
    assert(W == E.opposite)
    assert(N == S.opposite)
    assert(E == W.opposite)
  }

  test("IsHorizontal is correct") {
    assert(!Direction.N.isHorizontal)
    assert(Direction.E.isHorizontal)
    assert(!Direction.S.isHorizontal)
    assert(Direction.W.isHorizontal)
  }

  test("IsParallel is true only for opposite and self") {
    for (d1 <- Direction.values; d2 <- Direction.values) {
      if (d1 == d2 || d1 == d2.opposite)
        assert(d1.isParallelTo(d2), s"$d1 should be parallel to $d2")
      else
        assert(!d1.isParallelTo(d2), s"$d1 should not be parallel to $d2")
    }
  }

}
