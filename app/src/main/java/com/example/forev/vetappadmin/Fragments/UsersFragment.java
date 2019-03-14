package com.example.forev.vetappadmin.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.forev.vetappadmin.Adapters.UsersAdapter;
import com.example.forev.vetappadmin.Models.UsersModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.ChangeFragments;
import com.example.forev.vetappadmin.Utils.Warning;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    private View view;
    private ChangeFragments changeFragments;
    private RecyclerView userRecyclerView;
    private UsersAdapter usersAdapter;
    private List<UsersModel> usersModelList;
    private ImageView userBackImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_users, container, false);
        defineLayout();
        click();
        getUsers();
        return view;
    }

    public void click()
    {
        userBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void defineLayout(){
        changeFragments = new ChangeFragments(getContext());
        userRecyclerView = (RecyclerView)view.findViewById(R.id.userRecyclerView);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        userRecyclerView.setLayoutManager(mng);
        usersModelList = new ArrayList<>();
        userBackImage = (ImageView)view.findViewById(R.id.userBackImage);
    }

    public void getUsers()
    {
        Call<List<UsersModel>> req = ManagerAll.getInstance().getUsers();
        req.enqueue(new Callback<List<UsersModel>>() {
            @Override
            public void onResponse(Call<List<UsersModel>> call, Response<List<UsersModel>> response) {
                if(response.body().get(0).isTf())
                {
                    usersModelList = response.body();
                    usersAdapter = new UsersAdapter(usersModelList,getContext(),getActivity());
                    userRecyclerView.setAdapter(usersAdapter);
                }
                else
                {
                    changeFragments.change(new HomeFragment());
                    Toast.makeText(getContext(),"There are no users.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<UsersModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }


}
