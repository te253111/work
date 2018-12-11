package com.myproject.repaircar.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import lombok.Data;

/**
 * Created by Semicolon07 on 3/30/2017 AD.
 */
@Parcel
@Data
public class UserModel {
    @SerializedName("memberId")
    int memberId;
    @SerializedName("fname")
    String firstName;
    @SerializedName("lname")
    String lastName;
    @SerializedName("email")
    String email;
    @SerializedName("address")
    String address;
    @SerializedName("phone")
    String phone;

    public String getFullName(){
        String name = "-";
        if(firstName != null && firstName.length() > 0) name = firstName;
        if(lastName != null && lastName.length() > 0) name += " "+lastName;
        return name;
    }
}
