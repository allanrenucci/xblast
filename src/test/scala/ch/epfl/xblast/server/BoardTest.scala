package ch.epfl.xblast.server

import ch.epfl.xblast.Cell
import ch.epfl.xblast.Utils.SeqOps
import org.scalatest.FunSuite

class BoardTest extends FunSuite {
  import Block._

  test("Empty sequence of block sequences raises exception") {
    intercept[IllegalArgumentException] {
      new Board(Seq.empty[Stream[Block]])
    }
  }

  test("Invalid number of block sequences raises exception") {
    val freeBlockSequence = Stream.continually(FREE)
    val wholeBoardList = Seq.fill(Cell.COUNT - 1)(freeBlockSequence)

    intercept[IllegalArgumentException] {
      new Board(wholeBoardList)
    }
  }

  test("Invalid number of rows raises exception") {
    val freeRow = Seq.fill(Cell.COLUMNS)(FREE)
    val matrix = Seq.fill(Cell.ROWS + 1)(freeRow)

    intercept[IllegalArgumentException] {
      Board.ofRows(matrix)
    }
  }

  test("Invalid column raises exception") {
    val validRow = Seq.fill(Cell.COLUMNS)(FREE)
    val incompleteRow = validRow.tail
    val matrix = Seq.fill(Cell.ROWS - 1)(validRow)

    intercept[IllegalArgumentException] {
      Board.ofRows(matrix :+ incompleteRow)
    }
  }

  test("blockAt is correct") {
    val blockTypes = Block.values.toVector
    val row = generateRow(Cell.COLUMNS, blockTypes)
    val matrix = Seq.fill(Cell.ROWS)(row)

    val board = Board.ofRows(matrix)

    for (y <- 0 until Cell.ROWS; x <- 0 until Cell.COLUMNS) {
      val index = x % blockTypes.size
      val c = Cell(x, y)
      assert(blockTypes(index) == board.blockAt(c))
    }
  }


  test("blockAts is correct") {
    val blockTypes = Block.values.toVector
    val block = Stream.continually(blockTypes).flatten
    val blocks = (0 until Cell.COUNT) map block.drop

    val board = new Board(blocks)

    for {
      y <- 0 until Cell.ROWS
      x <- 0 until Cell.COLUMNS
    } {
      val index = x + y * Cell.COLUMNS
      val c = Cell(x, y)
      val blockStream = board.blocksAt(c)

      def blockType(index: Int) = blockTypes(index % blockTypes.size)

      (0 to 10) foreach { i =>
        assert(blockStream(i) == blockType(index + i))
      }
    }
  }

  test("ofQuadrantNW invalid columns raises exception") {
    val someRow = Seq.fill(Cell.COLUMNS / 2 + 1)(FREE)
    val matrix = Seq.fill(Cell.ROWS / 2)(someRow)

    intercept[IllegalArgumentException] {
      Board.ofQuadrantNWBlocksWalled(matrix)
    }
  }

  test("ofQuadrantNW invalid rows raises exception") {
    val someRow = Seq.fill(Cell.COLUMNS / 2 )(FREE)
    val matrix = Seq.fill(Cell.ROWS / 2 - 1)(someRow)

    intercept[IllegalArgumentException] {
      Board.ofQuadrantNWBlocksWalled(matrix)
    }
  }

  test("ofQuadrantNWBlocksWalled is correct") {
    val row = generateRow(Cell.COLUMNS / 2, Block.values.toSeq)
    val matrix = Seq.fill(Cell.ROWS / 2)(row)
    val board = Board.ofQuadrantNWBlocksWalled(matrix)

    boardIsWalled(board)

    for {
      y <- Cell.ROWS / 2 to 1
      mirroredRow = matrix(y - 1).mirrored
      x <- 1 until Cell.COLUMNS - 1
    } {
      val b1 = board.blockAt(Cell(x, y))
      val b2 = board.blockAt(Cell(x, Cell.ROWS - 1 - y))

      val row = mirroredRow(x - 1)
      assert(row == b1)
      assert(row == b2)
    }
  }

  test("ofInnerBlocks invalid argument raises exception") {
    val blocks = Seq.fill(Cell.ROWS - 2)(Seq.empty[Block])

    intercept[IllegalArgumentException] {
      Board.ofInnerBlocksWalled(blocks)
    }
  }

  test("ofInnerBlocksWalled is correct") {
    val aRow = Seq.fill(Cell.COLUMNS - 2)(CRUMBLING_WALL)
    val matrix = Seq.fill(Cell.ROWS - 2)(aRow)
    val board = Board.ofInnerBlocksWalled(matrix)

    boardIsWalled(board)

    for {
      x <- 1 until Cell.COLUMNS - 1
      y <- 1 until Cell.ROWS - 1
      b = board.blockAt(Cell(x, y))
    } assert(CRUMBLING_WALL == b)
  }


  private def boardIsWalled(board: Board): Unit = {
    (0 until Cell.COLUMNS) foreach { x =>
      // top row
      assert(INDESTRUCTIBLE_WALL == board.blockAt(Cell(x, 0)))

      // bottom row
      assert(INDESTRUCTIBLE_WALL == board.blockAt(Cell(x, Cell.ROWS - 1)))
    }

    (0 until Cell.COLUMNS) foreach { y =>
      // left column
      assert(INDESTRUCTIBLE_WALL == board.blockAt(Cell(0, y)))

      // right column
      assert(INDESTRUCTIBLE_WALL == board.blockAt(Cell(Cell.COLUMNS - 1, y)))
    }
  }

  private def generateRow(requiredSize: Int, blockTypes: Seq[Block]): Seq[Block] =
    (0 until requiredSize) map (i => blockTypes(i % blockTypes.length))
}
