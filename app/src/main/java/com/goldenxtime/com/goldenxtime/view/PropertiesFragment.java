package com.goldenxtime.com.goldenxtime.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.model.Property;
import com.goldenxtime.com.goldenxtime.view.custom.adapter.PropertiesAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertiesFragment extends Fragment {
//
    @BindView(R.id.propertiesRV)
    RecyclerView propertiesRV;
    PropertiesAdapter propertiesAdapter;

    public static int icon = R.drawable.ic_location_city_black_24dp;

    public static PropertiesFragment newInstance() {
        PropertiesFragment fragment = new PropertiesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_properties, container, false);
//        propertiesRV = (RecyclerView) getActivity().findViewById(R.id.propertiesRV);
        ButterKnife.bind(this, view);

        initPropertiesRV();

        ArrayList<Property> test = new ArrayList<>();
        test.add(new Property("", "عمارة الامل"));
        test.add(new Property("", "عمارة الامل"));
        test.add(new Property("", "عمارة الامل"));
        test.add(new Property("", "عمارة الامل"));
        test.add(new Property("", "عمارة الامل"));
        test.add(new Property("", "عمارة الامل"));
        test.add(new Property("", "عمارة الامل"));

        setPropertiesAdapter(test);

        return view;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    public void initPropertiesRV() {
        propertiesRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    public void setPropertiesAdapter(ArrayList<Property> properties) {
        this.propertiesAdapter = new PropertiesAdapter(properties, getActivity());
        propertiesRV.setAdapter(propertiesAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
