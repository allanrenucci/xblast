package ch.epfl.xblast

final class SubCell private (val x: Int, val y: Int) {
  import Direction._
  import SubCell._

  def distanceToCentral: Int =
    Math.abs(x % DIVISIONS - DIVISIONS / 2 ) + Math.abs(y % DIVISIONS - DIVISIONS / 2 )

  def isCentral: Boolean =
    distanceToCentral == 0

  def neighbor(dir: Direction): SubCell = dir match {
    case N => SubCell(x, y - 1)
    case S => SubCell(x, y + 1)
    case E => SubCell(x + 1, y)
    case W => SubCell(x - 1, y)
  }

  def containingCell: Cell =
    Cell(x / DIVISIONS, y / DIVISIONS)

  override def equals(that: Any): Boolean = that match {
    case SubCell(`x`, `y`) => true
    case _                 => false
  }

  override def hashCode: Int =
    x + Cell.COLUMNS * DIVISIONS * y

  override def toString: String = s"($x, $y)"
}

object SubCell {
  private val DIVISIONS = 16

  def centralSubCellOf(cell: Cell): SubCell =
    SubCell(cell.x * DIVISIONS + DIVISIONS / 2, cell.y * DIVISIONS + DIVISIONS / 2)

  def apply(_x: Int, _y: Int): SubCell = {
    val x = Math.floorMod(_x, Cell.COLUMNS * DIVISIONS)
    val y = Math.floorMod(_y, Cell.ROWS * DIVISIONS)
    new SubCell(x, y)
  }

  def unapply(arg: SubCell): Option[(Int, Int)] =
    Some((arg.x, arg.y))
}
