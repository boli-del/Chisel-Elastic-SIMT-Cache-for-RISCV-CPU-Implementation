import chisel3._
import chisel3.utils._

//access level is read per thread from shared thread instruction pool can be either 0, 1, 2, 3:
//0 is base cache, 1 is l1, 2 is l2, 3 is l3 cache(if selected)

class thread(l2_selected: bool, l3_selected: bool, l2_cache_size: Int, l3_cache_size: Int, l2_num_ways: Int, l3_num_ways: Int, l2_offset: Int, l3_offset) extends Module{
    io = IO(new Bundle{
        ca
        cache_index = Input(log2ceil(l2_cache_size).W)
        cache_set_index = Input(log2ceil(l2_num_ways).W)
        access_level = Input(UInt(2.W))
        l2_cache_reg = Input(Vec(log2ceil(l2_num_ways), Vec(log2ceil(l2_cache_size), Vec(log2ceil(l2_offset/8), UInt(l2_offset.W)))))
        l3_cache_reg = Input(Vec(log2ceil(l3_num_ways), Vec(log2ceil(l3_cache_size), Vec(log2ceil(l3_offset/8), UInt(l3_offset.W)))))
        task_access_start = Input(UInt(32.W))
        task_done = Output(UInt(1.W))
        task_found = Output(UInt(1.W))
    })
    switch(access_level){
        case (2.U){
            when()
        }
        case (1.U){
            
        }
    }
}