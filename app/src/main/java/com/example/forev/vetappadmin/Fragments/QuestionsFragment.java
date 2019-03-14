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

import com.example.forev.vetappadmin.Adapters.QuestionsAdapter;
import com.example.forev.vetappadmin.Models.QuestionsModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.ChangeFragments;
import com.example.forev.vetappadmin.Utils.Warning;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionsFragment extends Fragment {

    private View view;
    private RecyclerView questionRecyclerView;
    private List<QuestionsModel> questionsModelList;
    private QuestionsAdapter questionsAdapter;
    private ChangeFragments changeFragments;
    private ImageView questionBackImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_questions, container, false);
        defineLayout();
        click();
        getRequest();
        return view;
    }

    public void click()
    {
        questionBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
    }
    public void defineLayout()
    {
        questionRecyclerView = (RecyclerView)view.findViewById(R.id.questionRecyclerView);
        questionsModelList = new ArrayList<>();
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        questionRecyclerView.setLayoutManager(mng);
        changeFragments = new ChangeFragments(getContext());
        questionBackImage = (ImageView)view.findViewById(R.id.questionBackImage);
    }

    public void getRequest(){
        Call<List<QuestionsModel>> req = ManagerAll.getInstance().getQuestion();
        req.enqueue(new Callback<List<QuestionsModel>>() {
            @Override
            public void onResponse(Call<List<QuestionsModel>> call, Response<List<QuestionsModel>> response) {
                if(response.body().get(0).isTf())
                {
                    questionsModelList = response.body();
                    questionsAdapter = new QuestionsAdapter(questionsModelList,getContext(),getActivity());
                    questionRecyclerView.setAdapter(questionsAdapter);
                }
                else{
                    Toast.makeText(getContext(),"There are no questions...",Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<QuestionsModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warning.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });
    }
}
