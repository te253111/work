package com.myproject.repaircar.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.myproject.repaircar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Semicolon07 on 9/29/2016 AD.
 */

public class MyProgressDialog extends DialogFragment {
    private static final String ARG_MESSAGE = "com.myproject.ARG_MESSAGE";
    @BindView(R.id.progressDialogMessage_textView)
    TextView progressDialogMessageTextView;
    private String message;

    public static MyProgressDialog newInstance(String message) {
        MyProgressDialog fragment = new MyProgressDialog();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    public static MyProgressDialog newInstance() {
        MyProgressDialog fragment = new MyProgressDialog();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, null);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getArguments().getString(ARG_MESSAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_progress, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        message = message == null ? getString(R.string.loading) : message;
        progressDialogMessageTextView.setText(message);
        super.onViewCreated(view, savedInstanceState);
    }
}
