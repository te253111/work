package com.myproject.repaircar.models.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class BaseDataResponseApiModel<T> extends BaseActionResponseApiModel {
    @SerializedName("item")
    private T data;
}
