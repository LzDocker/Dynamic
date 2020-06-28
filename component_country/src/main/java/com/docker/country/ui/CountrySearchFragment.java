package com.docker.country.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.docker.common.databinding.CommonRecycleList2Binding;
import com.docker.common.ui.base.NitCommonFragment;
import com.docker.common.vm.EmptyViewModel;
import com.docker.country.R;
import com.docker.country.vo.WorldNumList;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * : 搜索结果显示Fragment
 */
@AndroidEntryPoint
public class CountrySearchFragment extends NitCommonFragment<EmptyViewModel, CommonRecycleList2Binding> {
    private SearchAdapter mAdapter;
    public List<WorldNumList.WorldEnty> mDatas;

    private String mQueryText;
    private static int curtype = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.common_recycle_list_2;
    }

    @Override
    protected EmptyViewModel getViewModel() {
        return new ViewModelProvider(this).get(EmptyViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        mBinding.get().recyclerView.setLayoutManager(new LinearLayoutManager(this.getHoldingActivity()));
        mBinding.get().recyclerView.setHasFixedSize(true);
        mBinding.get().recyclerView.setAdapter(mAdapter);
        if (mQueryText != null) {
            mAdapter.getFilter().filter(mQueryText);
        }
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this.getHoldingActivity())));
    }

    public void bindDatas(List<WorldNumList.WorldEnty> datas) {
        this.mDatas = datas;
        if (mAdapter == null) {
            mAdapter = new SearchAdapter();
        }
    }


    public void bindDatas(List<WorldNumList.WorldEnty> datas, int position) {
        this.mDatas = datas;
        if (mAdapter == null) {
            mAdapter = new SearchAdapter();
        }
        this.curtype = position;
    }

    /**
     * 根据newText 进行查找, 显示
     */
    public void bindQueryText(String newText) {
        if (mDatas == null) {
            mQueryText = newText.toLowerCase();
        } else if (!TextUtils.isEmpty(newText)) {
            mAdapter.getFilter().filter(newText.toLowerCase());
        }
    }

    @Override
    public void initImmersionBar() {

    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> implements Filterable {
        private List<WorldNumList.WorldEnty> items = new ArrayList<>();

        public SearchAdapter() {
            items.clear();
            items.addAll(mDatas);
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            final VH holder = new VH(LayoutInflater.from(getActivity()).inflate(R.layout.component_item_contact, parent, false));
            holder.itemView.setOnClickListener(v -> {
                int position = holder.getAdapterPosition();
                WorldNumList.WorldEnty worldEnty = items.get(position);
                worldEnty.curtype = String.valueOf(curtype);
           /*     Intent intent = new Intent();
                intent.putExtra("data", worldEnty.id);
                intent.putExtra("num", worldEnty.global_num);
                intent.putExtra("name", worldEnty.name);
                intent.putExtra("datasource", worldEnty);
                getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                getHoldingActivity().finish();*/
                ((CountrySelectActivity)getActivity()).onHanleData(worldEnty);
            });
            return holder;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tvName.setText(items.get(position).name);
            holder.tvNum.setText(items.get(position).global_num + "");

        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    ArrayList<WorldNumList.WorldEnty> list = new ArrayList<>();
                    for (WorldNumList.WorldEnty item : mDatas) {
                        if (item.pinyin.startsWith(constraint.toString()) || item.name.contains(constraint)) {
                            list.add(item);
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.count = list.size();
                    results.values = list;
                    return results;
                }

                @Override
                @SuppressWarnings("unchecked")
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    ArrayList<WorldNumList.WorldEnty> list = (ArrayList<WorldNumList.WorldEnty>) results.values;
                    mAdapter.items.clear();
                    mAdapter.items.addAll(list);
                    if (results.count == 0) {
                        mBinding.get().empty.showNodata();
                    } else {
                        mBinding.get().empty.hide();
                    }
                    mAdapter.notifyDataSetChanged();
                }
            };
        }

        class VH extends RecyclerView.ViewHolder {
            private TextView tvName;
            private TextView tvNum;

            public VH(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            }
        }
    }
}
