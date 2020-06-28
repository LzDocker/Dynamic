package com.docker.common.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

public abstract class HivsAbsSampleAdapter<T> extends CommonRecyclerAdapter<T, CommonRecyclerAdapter.ViewHolder<ViewDataBinding>> {

    private OnchildViewClickListener onchildViewClickListener;

    private int[] childId;

    private SimpleCommonRecyclerAdapter.OnItemClickListener onItemClickListener;

    public HivsAbsSampleAdapter(int layoutId, int brId) {
        super(layoutId, brId);
    }

    /**
     * 设置监听事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(SimpleCommonRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder<ViewDataBinding> getHolder(View view) {
        if (onchildViewClickListener != null) {
            return new ViewHolder<ViewDataBinding>(view, childId, onchildViewClickListener);
        }
        return new ViewHolder<ViewDataBinding>(view, onItemClickListener);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        onRealviewBind(holder, position);
    }

    public abstract void onRealviewBind(ViewHolder holder, int position);


    public void setOnchildViewClickListener(int[] childId, OnchildViewClickListener onchildViewClickListener) {
        this.childId = childId;
        this.onchildViewClickListener = onchildViewClickListener;
    }
}
