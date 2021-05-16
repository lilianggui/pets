package com.llg.pets.utils;

import lombok.Data;

@Data
public class ResponseResult<T> {
    private String code;
    private String msg;
    private T data;


    public static<T> ResponseResult<T> buildSuccess(T data){
        return new ResponseResult<>("0", "success", data);
    }

    public static<T> ResponseResult<T> buildFail(String msg, T data){
        return new ResponseResult<>("1", msg, data);
    }

    public static<T> ResponseResult<T> buildFail(String msg){
        return buildFail(msg, null);
    }

    private ResponseResult(String code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
