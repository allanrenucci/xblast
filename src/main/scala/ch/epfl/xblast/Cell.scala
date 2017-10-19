package ch.epfl.xblast

import scala.collection.mutable

final class Cell private (val x: Int, val y: Int) {
  import Cell._
  import Direction._

  def rowMajorIndex: Int =
    x + COLUMNS * y

  def neighbor(dir: Direction): Cell = dir match {
    case N => Cell(x, y - 1)
    case S => Cell(x, y + 1)
    case E => Cell(x + 1, y)
    case W => Cell(x - 1, y)
  }

  override def equals(that: Any): Boolean = that match {
    case Cell(`x`, `y`) => true
    case _              => false
  }

  override def hashCode: Int =
    rowMajorIndex

  override def toString: String = s"($x, $y)"
}

object Cell {
  val COLUMNS = 15
  val ROWS    = 13
  val COUNT   = COLUMNS * ROWS

  val ROW_MAJOR_ORDER = rowMajorOrder
  val SPIRAL_ORDER    = spiralOrder

  private def rowMajorOrder: Seq[Cell] =
    (0 until COUNT) map (i => Cell(i % COLUMNS, i / COLUMNS))

  private def spiralOrder: Seq[Cell] = {
    var ix = mutable.Queue(0 until COLUMNS: _*)
    var iy = mutable.Queue(0 until ROWS: _*)
    var horizontal = true
    val spiral = mutable.Buffer.empty[Cell]

    while (ix.nonEmpty && iy.nonEmpty) {
      if (horizontal) {
        val c2 = iy.dequeue()
        ix foreach (c1 => spiral += Cell(c1, c2))
        ix = ix.reverse
      } else {
        val c2 = ix.dequeue()
        iy foreach(c1 => spiral += Cell(c2, c1))
        iy = iy.reverse
      }

      horizontal = !horizontal
    }

    spiral.toVector
  }


  def apply(_x: Int, _y: Int): Cell = {
    val x = Math.floorMod(_x, COLUMNS)
    val y = Math.floorMod(_y, ROWS)
    new Cell(x, y)
  }

  def unapply(arg: Cell): Option[(Int, Int)] =
    Some((arg.x, arg.y))
}
