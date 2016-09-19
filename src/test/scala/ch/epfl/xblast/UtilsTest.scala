package ch.epfl.xblast

import ch.epfl.xblast.Utils.SeqOps
import org.scalatest.FunSuite

class UtilsTest extends FunSuite {

  test("mirrored throws exception on empty sequence") {
    intercept[UnsupportedOperationException] {
      Seq.empty[Int].mirrored
    }
  }

  test("mirrored behaviour is correct") {
    assert("aibohphobia".toSeq == "aibohp".toSeq.mirrored)
    assert("detartrated".toSeq == "detart".toSeq.mirrored)
    assert("deleveled".toSeq   == "delev".toSeq.mirrored)
    assert("racecar".toSeq     == "race".toSeq.mirrored)
  }

  test("Singleton sequence mirrored remains unchanged") {
    val a = Seq("a")
    assert(a == a.mirrored)
  }
}
