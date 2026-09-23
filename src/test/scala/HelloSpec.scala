import chisel3._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.flatspec.AnyFlatSpec

class HelloSpec extends AnyFlatSpec {
  it should "add correctly" in {
    simulate(new Hello) { dut =>
      dut.io.a.poke(3.U)
      dut.io.b.poke(4.U)
      dut.clock.step(1)
      dut.io.out.expect(7.U)
    }
  }
}
