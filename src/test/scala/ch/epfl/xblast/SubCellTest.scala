package ch.epfl.xblast

import org.scalatest.FunSuite

class SubCellTest extends FunSuite {

  test("Central subcell of know cell is correct") {
    val c = SubCell.centralSubCellOf(Cell(2, 1))
    assert(SubCell(40, 24) == c)
    assert(40 == c.x)
    assert(24 == c.y)
  }

  test("Central subcell is central") {
    Cell.ROW_MAJOR_ORDER foreach { c =>
      assert(SubCell.centralSubCellOf(c).isCentral)
    }
  }

  test("Distance to central of central is zero") {
    Cell.ROW_MAJOR_ORDER foreach { c =>
      assert(0 == SubCell.centralSubCellOf(c).distanceToCentral)
    }
  }

  test("Constructor correctly normalizes coordinates") {
    (-2 to 2) foreach { i =>
      val c = SubCell(239 + 240 * i, 207 + 208 * i)
      assert(SubCell(239, 207) == c)
      assert(239 == c.x)
      assert(207 == c.y)
    }
  }

  test("Distance to central of origin is correct") {
    val s = SubCell(0, 0)
    assert(16 == s.distanceToCentral)
  }

  test("Neighbors of origin are correct") {
    val c = SubCell(0, 0)
    assert(SubCell(  0, 207) == c.neighbor(Direction.N))
    assert(SubCell(  1,   0) == c.neighbor(Direction.E))
    assert(SubCell(  0,   1) == c.neighbor(Direction.S))
    assert(SubCell(239,   0) == c.neighbor(Direction.W))
  }

  test("Containing cell of centrals neighbor is correct") {
    for {
      c <- Cell.ROW_MAJOR_ORDER
      s = SubCell.centralSubCellOf(c)
      d <- Direction.values
    } assert(c == s.neighbor(d).containingCell)
  }

  test("equals is correct") {
    val c1 = SubCell(0, 0)
    val c2 = SubCell(0, 1)
    val c4 = SubCell(1, 0)
    val c3 = SubCell(0, 0)
    assert(c1 != c2)
    assert(c1 != c4)
    assert(c4 != c2)
    assert(c1 == c3)
  }

  test("neighbor of corner is correct") {
    val NE = SubCell(239,   0)
    val NW = SubCell(  0,   0)
    val SE = SubCell(239, 207)
    val SW = SubCell(  0, 207)

    assert(SubCell(  0, 207) == NW.neighbor(Direction.N))
    assert(SubCell(  1,   0) == NW.neighbor(Direction.E))
    assert(SubCell(  0,   1) == NW.neighbor(Direction.S))
    assert(SubCell(239,   0) == NW.neighbor(Direction.W))

    assert(SE == NE.neighbor(Direction.N))
    assert(NW == NE.neighbor(Direction.E))
    assert(SubCell(239,  1) == NE.neighbor(Direction.S))
    assert(SubCell(238,  0) == NE.neighbor(Direction.W))

    assert(SubCell(0, 206) == SW.neighbor(Direction.N))
    assert(SubCell(1, 207) == SW.neighbor(Direction.E))
    assert(NW == SW.neighbor(Direction.S))
    assert(SE == SW.neighbor(Direction.W))

    assert(SubCell(239, 206) == SE.neighbor(Direction.N))
    assert(SW == SE.neighbor(Direction.E))
    assert(NE == SE.neighbor(Direction.S))
    assert(SubCell(238, 207) == SE.neighbor(Direction.W))
  }

  test("distanceToCentral of some subCell is correct") {
    assert(10 == SubCell( 0, 10).distanceToCentral)
    assert(5  == SubCell( 5, 10).distanceToCentral)
    assert(4  == SubCell(10, 10).distanceToCentral)
    assert(9  == SubCell(15, 10).distanceToCentral)
    assert(11 == SubCell( 0,  5).distanceToCentral)
    assert(6  == SubCell( 5,  5).distanceToCentral)
    assert(9  == SubCell(10, 15).distanceToCentral)
  }

  test("isCentral of first cell is correct") {
    for {
      i <- 0 until 16
      j <- 0 until 16
      isCentral = i == 8 && j == 8
    } assertResult(isCentral)(SubCell(i, j).isCentral)
  }

  test("Border case of containingCell") {
    assert(Cell(0,0) == SubCell(15, 15).containingCell)
    assert(Cell(1,1) == SubCell(16, 16).containingCell)
    assert(Cell(0,1) == SubCell(15, 16).containingCell)
    assert(Cell(1,0) == SubCell(16, 15).containingCell)
  }
}
