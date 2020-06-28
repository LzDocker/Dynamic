package com.docker.country.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.docker.country.R;
import com.docker.country.vo.WorldNumList;
import com.docker.country.widget.recycleIndex.IndexableAdapter;

/**
 *
 */
public class ContactAdapter extends IndexableAdapter<WorldNumList.WorldEnty> {
    private LayoutInflater mInflater;
    private String type;

    /*
     * 1  城市
     * 2  国家
     * */
    public ContactAdapter(Context context, String type) {
        mInflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.component_item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.component_item_contact, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, WorldNumList.WorldEnty entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.name);
        if ("2".equals(type)) {
            vh.tvMobile.setText(entity.global_num + "");
        }
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName, tvMobile;

        public ContentVH(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMobile = (TextView) itemView.findViewById(R.id.tv_num);
        }
    }
}
