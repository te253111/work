package com.myproject.repaircar.models.resp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Semicolon07 on 3/15/2017 AD.
 */

public class ActionRespModel<T> {
    @SerializedName("success")
    private boolean success;
    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
