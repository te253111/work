package com.myproject.repaircar.consts;

/**
 * Created by Semicolon07 on 3/14/2017 AD.
 */

public enum MStatus {
    None(0),
    Save(1),
    Confirm(2),
    Available(3),
    NotAvailable(4),
    Paid(5);

    private int value;

    MStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static MStatus get(int value){
        if(Save.getValue()  == value)
            return Save;
        if(Confirm.getValue()  == value)
            return Confirm;
        if(Available.getValue()  == value)
            return Available;
        if(NotAvailable.getValue()  == value)
            return NotAvailable;
        if(Paid.getValue()  == value)
            return Paid;

        return None;
    }
}
