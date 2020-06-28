package com.docker.country.widget.recycleIndex;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.docker.country.widget.recycleIndex.EntityWrapper;
import com.docker.country.widget.recycleIndex.IndexableAdapter;
import com.docker.country.widget.recycleIndex.IndexableEntity;
import com.docker.country.widget.recycleIndex.IndexableFooterAdapter;

import java.util.List;

/**
 * 该HeaderAdapter 接收一个IndexableAdapter, 使其布局以及点击事件和IndexableAdapter一致
 * Created by YoKey on 16/10/14.
 */
public class SimpleFooterAdapter<T extends IndexableEntity> extends IndexableFooterAdapter<T> {
    private IndexableAdapter<T> mAdapter;

    public SimpleFooterAdapter(IndexableAdapter<T> adapter, String index, String indexTitle, List<T> datas) {
        super(index, indexTitle, datas);
        this.mAdapter = adapter;
    }

    @Override
    public int getItemViewType() {
        return EntityWrapper.TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        return mAdapter.onCreateContentViewHolder(parent);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, T entity) {
        mAdapter.onBindContentViewHolder(holder, entity);
    }
}
