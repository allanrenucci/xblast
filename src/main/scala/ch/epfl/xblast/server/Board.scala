package ch.epfl.xblast.server

import ch.epfl.xblast.Cell
import ch.epfl.xblast.Util.SeqOps

/**
  * @param blocks Sequences of blocks stored in row major order
  */
final class Board(private val blocks: Seq[Stream[Block]]) {
  require(blocks.size == Cell.COUNT)

  def blocksAt(cell: Cell): Stream[Block] =
    blocks(cell.rowMajorIndex)

  def blockAt(cell: Cell): Block =
    blocksAt(cell).head
}

object Board {

  def ofRows(rows: Seq[Seq[Block]]): Board = {
    checkBlockMatrix(rows, Cell.ROWS, Cell.COLUMNS)

    val blocks = Cell.ROW_MAJOR_ORDER map (c => rows(c.y)(c.x))
    new Board(blocks map (b => Stream.continually(b)))
  }

  def ofInnerBlocksWalled(innerBlocks: Seq[Seq[Block]]): Board = {
    checkBlockMatrix(innerBlocks, Cell.ROWS - 2, Cell.COLUMNS - 2)

    val rows = IndexedSeq.tabulate(Cell.ROWS, Cell.COLUMNS) { (r, c) =>
      if (r == 0 || r == Cell.ROWS - 1 || c == 0 || c == Cell.COLUMNS - 1) Block.INDESTRUCTIBLE_WALL
      else innerBlocks(r - 1)(c - 1)
    }

    ofRows(rows)
  }

  def ofQuadrantNWBlocksWalled(quadrantNWBlocks: Seq[Seq[Block]]): Board = {
    checkBlockMatrix(quadrantNWBlocks, Cell.ROWS / 2, Cell.COLUMNS / 2)

    val innerBlocks = quadrantNWBlocks.map(_.mirrored).mirrored

    ofInnerBlocksWalled(innerBlocks)
  }

  private def checkBlockMatrix(matrix: Seq[Seq[Block]], rows: Int, columns: Int) = {
    require(matrix.size == rows, s"Found ${matrix.size} rows, $rows expected")

    matrix foreach { row =>
      require(row.size == columns, s"Found ${row.size} columns, $columns expected")
    }
  }
}
