package com.atrs.airticketreservationsystem.entity;

public enum Result {
    SUCCESS(200,"成功"),
    ERROR(5001, "失败"),
    UPLOADERROR(5002,"文件上传错误"),
    REGISTERERROR(5003,"注册错误"),
    LOGINERROR(5004,"登陆错误"),
    CODEERROR(5005,"验证码错误");

    public int code;
    public String msg;

    Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}