import circt.stage.ChiselStage
import java.io.{File, PrintWriter}

object EmitVerilog extends App {
  new File("generated").mkdirs()
  val verilog = ChiselStage.emitSystemVerilog(new Hello)
  new PrintWriter("generated/Hello.sv") { write(verilog); close() }
}
