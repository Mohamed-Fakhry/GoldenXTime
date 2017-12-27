package com.goldenxtime.com.goldenxtime.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.view.adapter.PropertiesAdapter;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;
import com.goldenxtime.com.goldenxtime.view.layoutmanager.RtlGridLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubPropertiesFragment extends ViewPageFragment {

    @BindView(R.id.subproperityRV)
    RecyclerView subproperityRV;


    PropertiesAdapter subpropertiesAdapter;
    Unbinder unbinder;

    public static SubPropertiesFragment newInstance(ArrayList<Property> subProperties) {
        SubPropertiesFragment fragment = new SubPropertiesFragment();
        if(subProperties != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("content", subProperties);
            fragment.setTitle("تحتوى على");
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_properties, container, false);
        unbinder = ButterKnife.bind(this, view);

        initPropertiesRV();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ArrayList<Property> properties = bundle.getParcelableArrayList("content");
            setSubpropertiesAdapter(properties);
        }

        return view;
    }

    public void initPropertiesRV() {
        subproperityRV.setLayoutManager(new RtlGridLayoutManager(getActivity(), 2));
    }

    public void setSubpropertiesAdapter(ArrayList<Property> properties) {
        this.subpropertiesAdapter = new PropertiesAdapter(properties, getActivity());
        subproperityRV.setAdapter(subpropertiesAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}