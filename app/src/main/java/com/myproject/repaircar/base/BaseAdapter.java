package com.myproject.repaircar.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Semicolon07 on 3/14/2017 AD.
 */

public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    protected Context context;
    private static final String TAG = "RecyclerViewAdapter";

    public BaseAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected abstract int getLayoutResId();
    protected abstract VH createViewHolder(View view);
    protected abstract void bindViewHolder(VH holder,int position);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        try{
            View view = this.layoutInflater.inflate(getLayoutResId(), parent, false);
            viewHolder = createViewHolder(view);
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VH vh = (VH) holder;
        bindViewHolder(vh,position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
