package com.myproject.repaircar.net;


import com.myproject.repaircar.consts.ServiceUrl;
import com.myproject.repaircar.models.form.LoginApiForm;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Semicolon07 on 9/29/2016 AD.
 */

public interface MyApi {

    @POST(ServiceUrl.LOGIN)
    Observable<LoginApiForm.Response> login(@Body LoginApiForm.Request request);
}
