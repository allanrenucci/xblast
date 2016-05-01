package ch.epfl.xblast

import org.scalatest.FunSuite

class CellTest extends FunSuite {

  test("Row major index corresponds to order") {
    Cell.ROW_MAJOR_ORDER.zipWithIndex foreach {
      case (c, i) =>
        assert(i == c.rowMajorIndex)
    }

    assert(Cell.COUNT == Cell.ROW_MAJOR_ORDER.size)
  }

  test("Spiral order contains all cells") {
    assert(Cell.COUNT == Cell.SPIRAL_ORDER.size)

    var alreadySeen = Set.empty[Cell]
    Cell.SPIRAL_ORDER foreach { c =>
      assert(!alreadySeen(c))
      alreadySeen += c
    }
  }

  test("Spiral order neighbors are spatial neighbors") {
    Cell.SPIRAL_ORDER zip Cell.SPIRAL_ORDER.tail foreach {
      case (pred, curr) =>
        val areNeighborsCount = (Direction.values foldLeft 0) {
          case (count, d) =>
            if (pred == curr.neighbor(d)) count + 1
            else count
        }

        assert(areNeighborsCount == 1)
    }
  }

  test("Constructor correctly normalizes coordinates") {
    (-2 to 2) foreach { i =>
      val c = Cell(14 + Cell.COLUMNS * i, 12 + Cell.ROWS * i)
      assert(c.x == 14)
      assert(c.y == 12)
      assert(c == Cell(14, 12))
    }
  }

  test("equals is correct") {
    val c1 = Cell(0, 0)
    val c2 = Cell(0, 1)
    val c4 = Cell(1, 0)
    val c3 = Cell(0, 0)
    assert(c1 !=c2)
    assert(c1 !=c4)
    assert(c4 !=c2)
    assert(c1 == c3)
  }

  test("neighborOfCornerCorrect") {
    val NE = Cell(14,  0)
    val NW = Cell( 0,  0)
    val SE = Cell(14, 12)
    val SW = Cell( 0, 12)

    assert(Cell( 0, 12) == NW.neighbor(Direction.N))
    assert(Cell( 1,  0) == NW.neighbor(Direction.E))
    assert(Cell( 0,  1) == NW.neighbor(Direction.S))
    assert(Cell(14,  0) == NW.neighbor(Direction.W))

    assert(SE == NE.neighbor(Direction.N))
    assert(NW == NE.neighbor(Direction.E))
    assert(Cell(14, 1) == NE.neighbor(Direction.S))
    assert(Cell(13, 0) == NE.neighbor(Direction.W))

    assert(Cell( 0, 11) == SW.neighbor(Direction.N))
    assert(Cell( 1, 12) == SW.neighbor(Direction.E))
    assert(NW == SW.neighbor(Direction.S))
    assert(SE == SW.neighbor(Direction.W))

    assert(Cell(14, 11) == SE.neighbor(Direction.N))
    assert(SW == SE.neighbor(Direction.E))
    assert(NE == SE.neighbor(Direction.S))
    assert(Cell(13, 12) == SE.neighbor(Direction.W))
  }

  test("Neighbors of origin are correct") {
    val c = Cell(0, 0)
    assert(Cell( 0, 12) == c.neighbor(Direction.N))
    assert(Cell( 1,  0) == c.neighbor(Direction.E))
    assert(Cell( 0,  1) == c.neighbor(Direction.S))
    assert(Cell(14,  0) == c.neighbor(Direction.W))
  }

  test("Opposite neighbor of neighbor is this") {
    for (c <- Cell.ROW_MAJOR_ORDER; d <- Direction.values) {
      assert(c == c.neighbor(d).neighbor(d.opposite))
    }
  }


}
