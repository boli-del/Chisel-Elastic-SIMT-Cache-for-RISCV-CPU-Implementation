import chisel3._
import chisel3.utils._

//in order for divison algorithms, the divisor has to be scaled until between 0 and 1
class scaling extends Module{
    val io = IO(new Bundle{
        val divisor = Input(UInt(32.W))
        val dividend = Input(UInt(32.W))
        val scaled_dividend = Input(UInt(32.W))
    })
        val div = io.divisor
        val new_divdend = io.dividend
        while(div != 1){
            new_dividend := new_dividend >> 1
            div := div >> 1;
        }
        io.scaled_dividend := new_dividend
}

object float_scale_states{
    object State extends ChiselEnum{
        val sdet, srecur, scheck, sfinal, sdecrement = Value
    }
}

class floating_point_scaling extends Module{
    val io = IO(new Bundle{
        val divd_float = Input(UInt(32.W))
        val divs_float = Input(UInt(32.W))
        val state = Output(State())
        val scaled_divs = Output(UInt(32.W))
        val scaled_divd = Output(UInt(32.W))
    })
    val tracked_divd = io.divd_float
    val tracked_divs = io.divs_float
    val state = RegInit(sdet)

    io.state := state

    switch(state){
        is(sdet){
            when(tracked_divd(31:20) === "0h3ff".U){
                state := scheck
            }
            otherwise{
                state := srecur
            }
        }
        is(srecur){
            tracked_divd = (tracked_divd(31:20) - 1.U, tracked_divd(19:0))
            tracked_divs = (tracked_divs(31:20) - 1.U, tracked_divs(19:0))
            state := sdet
        }
        is(scheck){
            when(tracked_divs(19:0) === 0.U){
                state := sfinal
            }
            otherwise{
                state := sdecrement
            }
        }
        is(sdecrement){
            tracked_divd = (tracked_divd(31:20) - 1.U, tracked_divd(19:0))
            tracked_divs = (tracked_divs(31:20) - 1.U, tracked_divs(19:0))
            state := sfinal
        }
        is(sfinal){
            io.scaled_divd := tracked_divd
            io.scaled_divs := tracked_divs
        }
        is(otherwise){
            state := sdet
        }
    }
}


//extension from fixed_pont to floating point via standard extensions and standard techs
class extend_to_float extends Module{
    io = IO(new Bundle{
        val unsigned_in = Input(UInt(32.W))
        val unsigned_float_out = Output(UInt(32.W))
    })
    val num_bi = io.unsigned_in
    val result_float_out = UInt(32.W)
    val toppower = h"3ff".asUInt
    while(num_bi!=1){
        num_bi := num_bi >> 1
        toppower := toppower + 1.U
    }
    result_float_out := (toppower, io.unsigned_in(31:12))
}


object fixed_point_iteration_states{
    object State extends ChiselEnum{
        val sdcheck, sret, siter = Value
    }
} 

class fixed_point_rootfinding extends Module{
    io = IO(new Bundle{
        val shifted_original = Input(UInt(32.W))
        val out_state = Output(State())
        val out_result = Ouput(UInt(32.W))
    })
    val iterations := 4.U
    val current := 0.U
    val state = RegInit(scheck)
    io.state := state 
    switch (state){
        is(scheck){
            when(current === iterations){
                state := sret
            }ohterwise{
                state := siter
            }
        }
        is(siter){
            
        }
    }
}

//this is unsigned subtraction

class fixed_sub extends Module{
    io = IO(new Bundle{
        val input_val_1 = Input(UInt(32.W))
        val input_val_2 = Input(UInt(32.W))
        val ret = Ouput(UInt(32.W))
    })
    val deg_difference = Wire(UInt(0.U(32.W)))
    val first_expanded_num = Wire(UInt(0.U(32.W)))
    val second_expanded_num = Wire(UInt(0.U(32.W)))
    val difference_expanded = Wire(UInt(0.U(32.W)))

    deg_difference := io.input_val_1[31:20] - io.input_val_2[31:20]
    when(deg_difference > 20){
        io.ret := io.input_val_1
    } otherwise{
        first_expanded_num := (1.U, io.input_val_1(19, 0))
        second_expanded_num := (1.U, io.input_val_2(19, 0))
        second_expanded_num := second_expanded_num >> deg_difference
        difference_expanded := first_expanded_num - second_expanded_num
        when(difference_expanded(20) === 0){
            io.ret := (io.input_val_1(31, 20) - 1, difference_expanded(18, 0), 0)
        }otherwise{
            io.ret := (io.input_val_1(31, 20), difference_expanded(19, 0))
        }
    }
}

object mult_recur_states{
    object State extends ChiselEnum{
        val sdrecur, sddone = Value
    }
} 


class fixed_multiply extends Module{
    io = IO(new Bundle{
        val input_val_1 = Input(UInt(32.W))
        val input_val_2 = Input(UInt(32.W))
        val state = Output(State())
        val ret = Output(UInt(32.W))
    })
    val first_power = Wire(UInt(0.U(32.W)))
    val second_power = Wire(UInt(0.U(32.W)))
    val first_extension = Wire(UInt(0.U(32.W)))
    val second_extension = Wire(UInt(0.U(32.W)))

    first_power := io.input_val_1(31, 20)
    second_power := io.input_val_2(31, 20)
    first_extension := io.input_val_1(1.U, (19, 0))
    second_extension := io.input_val_2 (1.U, (19, 0))
    
    first_extension := first_extension >> 6
    second_extension := second_extension >> 6
    
    switch 
}