package com.zwz.ssm.exception;

/**
 * creates by ${user} on 2018/5/24
 */
//重复预约异常
public class RepeatAppointException  extends  RuntimeException{
    public RepeatAppointException (String message){
        super(message);
    }
    public RepeatAppointException(String message ,Throwable cause){
        super(message,cause);

    }
}
