package com.myproject.repaircar.template;

import android.view.View;

import com.unigainfo.android.meview.adapter.ItemViewHolder;
import com.unigainfo.android.meview.adapter.ViewHolderFactory;

import butterknife.ButterKnife;

/**
 * Created by Semicolon07 on 3/14/2017 AD.
 */

public class AdapterTemplate extends ViewHolderFactory<String,AdapterTemplate.ViewHolder> {

    public interface Listener{
       //test
    }

    private Listener listener;

    public AdapterTemplate(Listener listener){
        this.listener = listener;
    }


    @Override
    protected void initViewHolder(ViewHolder viewHolder) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(listener != null)
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public void bindData(ViewHolder viewHolder, String item, int position) {

    }

    public static class ViewHolder extends ItemViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
