package com.myproject.repaircar.models.resp;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BaseActionResponseApiModel {
    @SerializedName("action")
    private ActionRespModel action;
}
