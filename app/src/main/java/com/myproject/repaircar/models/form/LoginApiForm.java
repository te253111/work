package com.myproject.repaircar.models.form;

import com.google.gson.annotations.SerializedName;
import com.myproject.repaircar.models.UserModel;

import lombok.Data;

public class LoginApiForm {
    @Data
    public static class Request{
        @SerializedName("email")
        private String username;
        @SerializedName("password")
        private String password;
    }

    @Data
    public static class Response{
        @SerializedName("isAuthen")
        private boolean isValid;
        @SerializedName("status_code")
        private int statusCode;
        @SerializedName("memberItem")
        private UserModel user;
    }
}
