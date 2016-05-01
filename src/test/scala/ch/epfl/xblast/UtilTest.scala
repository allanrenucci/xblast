package ch.epfl.xblast

import org.scalatest.FunSuite

class UtilTest extends FunSuite {
  import Util._


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
