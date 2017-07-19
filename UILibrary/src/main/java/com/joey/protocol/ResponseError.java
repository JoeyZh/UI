package com.joey.protocol;

/**
 * Created by Administrator on 2016/7/22.
 */
public class ResponseError {

    private int status;
    private String message;
    public static final int ERROR_BY_PARSE = -3;
    public static final int ERRPR_BY_NET = -2;
    public static final int ERRPR_NO_RESPONCE = -1;
    public static final int ERROR_OFF_LINE = -4;
    public String json;

    public ResponseError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
