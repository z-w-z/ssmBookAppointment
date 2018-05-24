package com.zwz.ssm.exception;

/**
 * creates by ${user} on 2018/5/24
 *///*预约义务异常*/
public class AppointException extends RuntimeException {
    public AppointException(String message) {
        super(message);
    }

    public AppointException(String message, Throwable cause) {
        super(message, cause);
    }
}

