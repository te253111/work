package com.myproject.repaircar.manager;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.myproject.repaircar.R;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Semicolon07 on 9/29/2016 AD.
 */

public abstract class BaseDataManager {
    protected final Context context;

    public BaseDataManager(Context context){
        this.context = context;
    }

    protected Subscription executeObservable(Observable observable, DefaultSubscriber subscriber){
        return observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    Subscription execute(Observable observable, DefaultSubscriber subscriber){
        Observable _observable = isThereInternetConnection()
                .flatMap(result -> observable);

        return executeObservable(_observable,subscriber);
    }

    protected Observable<Boolean> isThereInternetConnection() {
        final boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return Observable.create(subscriber -> {
            if(!isConnected){
                subscriber.onError(new NetworkErrorException(context.getString(R.string.no_internet_connection)));
            }else{
                subscriber.onNext(isConnected);
                subscriber.onCompleted();
            }
        });
    }
}
