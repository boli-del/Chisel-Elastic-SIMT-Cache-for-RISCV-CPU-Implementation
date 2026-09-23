import chisel3._

// Placeholder module to verify the Chisel toolchain; replace with the SIMT cache design.
class Hello extends Module {
  val io = IO(new Bundle {
    val a   = Input(UInt(8.W))
    val b   = Input(UInt(8.W))
    val out = Output(UInt(8.W))
  })
  io.out := RegNext(io.a + io.b, 0.U)
}
