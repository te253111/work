package com.myproject.repaircar.models.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class BaseListResponseApiModel<T> extends BaseActionResponseApiModel {
    @SerializedName("items")
    private List<T> items;
}
