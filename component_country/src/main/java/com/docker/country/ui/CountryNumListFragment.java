package com.docker.country.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.common.BR;
import com.docker.common.adapter.HivsAbsSampleAdapter;
import com.docker.common.adapter.SimpleCommonRecyclerAdapter;
import com.docker.common.ui.base.NitCommonFragment;
import com.docker.country.R;
import com.docker.country.databinding.ComponentFragmentCountryNumListBinding;
import com.docker.country.adapter.ContactAdapter;
import com.docker.country.databinding.ComponentItemCityBinding;
import com.docker.country.databinding.ComponentItemCityBindingImpl;
import com.docker.country.vm.CountrySelectViewModel;
import com.docker.country.vo.WorldNumList;
import com.docker.country.widget.recycleIndex.IndexableHeaderAdapter;
import com.docker.country.widget.recycleIndex.IndexableLayout;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 *
 */
@AndroidEntryPoint
public class CountryNumListFragment extends NitCommonFragment<CountrySelectViewModel, ComponentFragmentCountryNumListBinding> {

    public List<WorldNumList.WorldEnty> list = new ArrayList<>();
    private ContactAdapter mAdapter;
    private MyIndexAdapter gpsHeaderAdapter;
    private MyIndexAdapter mHotCityAdapter;
    private String type = "1";
    public LocationClient mLocationClient = null;
    private List<WorldNumList.WorldEnty> gpsCity = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.component_fragment_country_num_list;
    }

    @Override
    public CountrySelectViewModel getViewModel() {
        return new ViewModelProvider(this).get(CountrySelectViewModel.class);
    }

    public static CountryNumListFragment newinstance(String type) {
        CountryNumListFragment accountCountryNumListFragment = new CountryNumListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        accountCountryNumListFragment.setArguments(bundle);
        return accountCountryNumListFragment;
    }


    @Override
    protected void initView(View var1) {
        type = getArguments().getString("type");
        mBinding.get().indexableLayout.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new ContactAdapter(this.getHoldingActivity(),type);
        mBinding.get().indexableLayout.setAdapter(mAdapter);
        mBinding.get().indexableLayout.setOverlayStyle_Center();
        mBinding.get().indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
        mAdapter.setOnItemContentClickListener((View v, int originalPosition, int currentPosition, WorldNumList.WorldEnty entity) -> {
            if (originalPosition >= 0) {
//                Intent intent = new Intent();
                entity.curtype = String.valueOf(Integer.parseInt(type) - 1);
             /*   intent.putExtra("data", entity.id);
                intent.putExtra("name", entity.name);
                intent.putExtra("num", entity.global_num);
                intent.putExtra("datasource", entity);
                getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                getHoldingActivity().finish();*/

                ((CountrySelectActivity)getActivity()).onHanleData(entity);
            }
        });
        mAdapter.setOnItemTitleClickListener((v, currentPosition, indexTitle) -> {
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.getCountryList(type);
        mBinding.get().empty.setOnretryListener(() -> {
            mViewModel.getCountryList(type);
        });

        mViewModel.mCountryLisliveData.observe(this.getViewLifecycleOwner(), worldNumListHot -> {
            if (worldNumListHot == null) {
                mBinding.get().empty.showError();
            } else {
                mBinding.get().empty.hide();
                mAdapter.setDatas(worldNumListHot.list);
                CountryNumListFragment.this.list = worldNumListHot.list;
                if ("1".equals(type) && (worldNumListHot.hot_area != null)) {
                    List<WorldNumList.WorldEnty> hotCity = new ArrayList<>();
                    ArrayList list = new ArrayList();
                    list.add(0, (worldNumListHot).hot_area);
                    mHotCityAdapter = new MyIndexAdapter("热门", "周边热门", list);
                    mBinding.get().indexableLayout.addHeaderAdapter(mHotCityAdapter);
                    processLocation();
                } else {
                    processLocation();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        super.onDestroy();
    }


    private int locationCount = 0;
    private String mCountryStr;

    private void processLocation() {
        locationCount = 0;
        mLocationClient = new LocationClient(this.getHoldingActivity());
        RxPermissions rxPermissions = new RxPermissions(this.getHoldingActivity());
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> {
                    if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LocationClientOption option = new LocationClientOption();
                            option.setIsNeedAddress(true);
                            option.setOpenGps(true); // 打开gps
                            option.setCoorType("bd09ll"); // 设置坐标类型
                            option.setScanSpan(1000);
                            mLocationClient.setLocOption(option);
                            mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
                                @Override
                                public void onReceiveLocation(BDLocation location) {
                                    if (location.getCountry() != null) {
                                        mLocationClient.stop();
                                        mCountryStr = location.getCity();
                                        Log.d("sss", "onReceiveLocation: -----------------------" + mCountryStr);
                                        WorldNumList.WorldEnty worldEnty = new WorldNumList.WorldEnty();
                                        worldEnty.name = mCountryStr;
                                        worldEnty.id = "-1";
                                        gpsCity.clear();
                                        gpsCity.add(worldEnty);
                                        ArrayList listgps = new ArrayList();
                                        listgps.add(0, gpsCity);
                                        gpsHeaderAdapter = new MyIndexAdapter("定位", "定位", listgps);
                                        mBinding.get().indexableLayout.addHeaderAdapter(gpsHeaderAdapter);
                                        mAdapter.notifyDataSetChanged();
                                        hidWaitDialog();
                                    } else {
                                        locationCount++;
                                        if (locationCount > 3) {
                                            hidWaitDialog();
                                            mLocationClient.stop();
                                        }
                                    }
                                }
                            });
                            mLocationClient.start();
                        } else {
                            ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                            hidWaitDialog();
                        }
                    }
                });

    }

    @Override
    public void initImmersionBar() {

    }


    class MyIndexAdapter extends IndexableHeaderAdapter<List<WorldNumList.WorldEnty>> {
        private static final int TYPE = 1;

        public MyIndexAdapter(String index, String indexTitle, List<List<WorldNumList.WorldEnty>> datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            return new VH(LayoutInflater.from(CountryNumListFragment.this.getHoldingActivity()).inflate(R.layout.common_recycle_list, parent, false));
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, List<WorldNumList.WorldEnty> entity) {
            VH vh = (VH) holder;
            HivsAbsSampleAdapter simpleCommonRecyclerAdapter = new HivsAbsSampleAdapter(R.layout.component_item_city, BR.item){
                @Override
                public void onRealviewBind(ViewHolder holder, int position) {
                     ComponentItemCityBinding b = (ComponentItemCityBinding) holder.getBinding();
                     b.tvName.setText(entity.get(position).name);
                }
            };
            if (entity != null) {
                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(CountryNumListFragment.this.getActivity());
                layoutManager.setFlexWrap(FlexWrap.WRAP);
                layoutManager.setFlexDirection(FlexDirection.ROW);
                layoutManager.setAlignItems(AlignItems.STRETCH);
                layoutManager.setJustifyContent(JustifyContent.FLEX_START);

                vh.recyclerView.setLayoutManager(layoutManager);
                vh.recyclerView.setAdapter(simpleCommonRecyclerAdapter);
                simpleCommonRecyclerAdapter.add(entity);
                simpleCommonRecyclerAdapter.setOnItemClickListener((view, position) -> {
                    if ("-1".equals(((WorldNumList.WorldEnty) simpleCommonRecyclerAdapter.getmObjects().get(position)).id)) {
                        // 定位
                        CountryCoutainerFragment accountCityCoutainerFragment = (CountryCoutainerFragment) getParentFragment();
                        accountCityCoutainerFragment.processLocation((WorldNumList.WorldEnty) simpleCommonRecyclerAdapter.getmObjects().get(position), type);
                    } else {
//                        Intent intent = new Intent();
                        WorldNumList.WorldEnty curwo = (WorldNumList.WorldEnty) simpleCommonRecyclerAdapter.getmObjects().get(position);
                        curwo.curtype = String.valueOf(Integer.parseInt(type) - 1);
                        /*intent.putExtra("data", curwo.id);
                        intent.putExtra("name", curwo.name);
                        intent.putExtra("num", curwo.global_num);
                        intent.putExtra("datasource", curwo);
*/
//                        getHoldingActivity().setResult(Activity.RESULT_OK, intent);
//                        getHoldingActivity().finish();

                        ((CountrySelectActivity)getActivity()).onHanleData(curwo);
                    }
                });
            }
        }

        private class VH extends RecyclerView.ViewHolder {
            private RecyclerView recyclerView;

            public VH(View itemView) {
                super(itemView);
                recyclerView = itemView.findViewById(R.id.recyclerView);
            }
        }
    }
}