package ch.epfl.xblast

object Utils {
  implicit class SeqOps[T](val l: Seq[T]) extends AnyVal {
    def mirrored: Seq[T] = l ++ l.view.reverse.tail
  }
}
