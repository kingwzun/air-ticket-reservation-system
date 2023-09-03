package com.atrs.airticketreservationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse<T> {
    private int code;
    private String msg;
    private T data;

    public static<T> JsonResponse<T> success(T data){
        return new JsonResponse<>(Result.SUCCESS.code,Result.SUCCESS.msg,data);
    }
    public static<T> JsonResponse<T> error(T data){
        return new JsonResponse<>(Result.ERROR.code,Result.ERROR.msg,data);
    }

    public static<T> JsonResponse<T> error(int code, String msg, T data){
        return new JsonResponse<>(code, msg, data);
    }
}
