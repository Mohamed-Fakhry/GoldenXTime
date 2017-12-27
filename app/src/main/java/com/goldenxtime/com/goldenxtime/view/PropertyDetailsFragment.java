package com.goldenxtime.com.goldenxtime.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.view.adapter.DetailsAdapter;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PropertyDetailsFragment extends ViewPageFragment {

    @BindView(R.id.classBuildingTV)
    TextView classBuildingTV;
    @BindView(R.id.addressTV)
    TextView addressTV;
    @BindView(R.id.notesTV)
    TextView notesTV;
    @BindView(R.id.detailsRV)
    RecyclerView detailsRV;
    @BindView(R.id.classBuildingL)
    LinearLayout classBuildingL;
    @BindView(R.id.addressL)
    LinearLayout addressL;
    @BindView(R.id.notesL)
    LinearLayout notesL;

    DetailsAdapter detailsAdapter;
    Unbinder unbinder;

    public static PropertyDetailsFragment newInstance(Property property) {
        PropertyDetailsFragment fragment = new PropertyDetailsFragment();
        fragment.setTitle("تفاصيل العقار");
        Bundle bundle = new Bundle();
        bundle.putParcelable("content", property);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Property property = bundle.getParcelable("content");

            if (property.getClassProperty() == null || property.getClassProperty().isEmpty())
                classBuildingL.setVisibility(View.GONE);
            else
                classBuildingTV.setText(property.getClassProperty());

            if (property.getAddress() == null || property.getAddress().isEmpty())
                addressL.setVisibility(View.GONE);
            else
                addressTV.setText(property.getAddress());

            if (property.getNotes() == null || property.getNotes().isEmpty())
                notesL.setVisibility(View.GONE);
            else
                notesTV.setText(property.getNotes());

            initDetailsRV();
            setDetailsAdapter(property.getDetails());
        }

        return view;
    }

    public void initDetailsRV() {
        detailsRV.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }

    public void setDetailsAdapter(ArrayList<Property.Detail> details) {
        this.detailsAdapter = new DetailsAdapter(details, getActivity());
        detailsRV.setAdapter(detailsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
