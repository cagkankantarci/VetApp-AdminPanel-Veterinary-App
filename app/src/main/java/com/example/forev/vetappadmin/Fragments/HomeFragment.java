package com.example.forev.vetappadmin.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.Utils.ChangeFragments;


public class HomeFragment extends Fragment {

    private LinearLayout campaignLayout,petsLayout,questionLayout,usersLayout;
    private TextView firstname;
    private View view;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        defineLayout();
        click();
        return view;
    }

    public void defineLayout()
    {
        campaignLayout = (LinearLayout)view.findViewById(R.id.admin_campaignLayout);
        petsLayout = (LinearLayout)view.findViewById(R.id.admin_petsLayout);
        questionLayout = (LinearLayout)view.findViewById(R.id.questionLayout);
        usersLayout = (LinearLayout)view.findViewById(R.id.admin_usersLayout);
        changeFragments = new ChangeFragments(getContext());
    }

    public void click()
    {
        campaignLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new CampaignFragment());
            }
        });

        petsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new VacCalendarFragment());
            }
        });

        questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new QuestionsFragment());
            }
        });

        usersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new UsersFragment());
            }
        });
    }
}
