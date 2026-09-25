import chisel3._
import chisel3.utils._

class threadCounter extends Module(
    io = IO(new Bundle{
        thread_mask = Input(UInt(8.W))
        threaded_task = Output(Vec(8, UInt(32.W)))
        threaded_task_bound = Output(Vec(8, UInt(32.W)))
        threaded_task_gen = Output(UInt(1.W))
    })
    io.threaded_task_gen := 1.U
    
)