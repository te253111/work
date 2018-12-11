package com.myproject.repaircar.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.myproject.repaircar.R;
import com.myproject.repaircar.customview.state.BundleSavedState;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Pongphop on 11/16/2014.
 */
public class TextWithLabelView extends BaseCustomViewGroup {
    @BindView(R.id.label_textView)
    TextView labelTextView;
    @BindView(R.id.description_textView)
    TextView descriptionTextView;

    private static class AttrModel {
        String labelText;
        String descText;
        int labelColor;
        int descColor;
    }

    private static final int DEFAULT_LABEL_COLOR = 0;
    private static final int DEFAULT_DESC_COLOR = 0;

    private AttrModel attrModel;

    public TextWithLabelView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public TextWithLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public TextWithLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public TextWithLabelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        View v = inflate(getContext(), R.layout.view_text_with_label, this);
        ButterKnife.bind(v);
    }

    private void initInstances() {
        // findViewById here
        attrModel = new AttrModel();
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextWithLabelView,
                defStyleAttr, defStyleRes);

        try {
            attrModel.labelText = a.getString(R.styleable.TextWithLabelView_twl_labelText);
            attrModel.descText = a.getString(R.styleable.TextWithLabelView_twl_descText);

            setAttrValue();
        } finally {
            a.recycle();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Repair Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    private void setAttrValue() {
        setLabelText(attrModel.labelText);
        setDescText(attrModel.descText);
    }

    public void setLabelText(String labelText) {
        labelTextView.setText(labelText);
    }

    public void setDescText(String descText) {
        descriptionTextView.setText(descText);
    }

}
