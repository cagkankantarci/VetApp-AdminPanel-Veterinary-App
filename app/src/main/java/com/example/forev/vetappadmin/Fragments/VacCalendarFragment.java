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

import com.example.forev.vetappadmin.Adapters.VacCalendarAdapter;
import com.example.forev.vetappadmin.Models.VacCalendarModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.ChangeFragments;
import com.example.forev.vetappadmin.Utils.Warning;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacCalendarFragment extends Fragment {

    private View view;
    private DateFormat format;
    private Date today;
    private String todayDate;
    private ChangeFragments changeFragments;
    private RecyclerView vacCalendarRecyclerView;
    private List<VacCalendarModel> vacCalendarModelList;
    private VacCalendarAdapter vacCalendarAdapter;
    private ImageView vacBackImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vac_calendar, container, false);
        defineLayout();
        click();
        sendRequest(todayDate);
        return view;
    }

    public void click(){
        vacBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void defineLayout()
    {
        format = new SimpleDateFormat("dd/MM/yyyy");
        today = Calendar.getInstance().getTime();
        todayDate = format.format(today);
        changeFragments = new ChangeFragments(getContext());
        vacCalendarRecyclerView = (RecyclerView)view.findViewById(R.id.vacCalendarRecyclerView);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        vacCalendarRecyclerView.setLayoutManager(mng);
        vacCalendarModelList = new ArrayList<>();
        vacBackImage = (ImageView)view.findViewById(R.id.vacBackImage);
    }

    public void sendRequest(String date)
    {
        Call<List<VacCalendarModel>> req = ManagerAll.getInstance().getVac(date);
        req.enqueue(new Callback<List<VacCalendarModel>>() {
            @Override
            public void onResponse(Call<List<VacCalendarModel>> call, Response<List<VacCalendarModel>> response) {
                if(response.body().get(0).isTf())
                {
                    Toast.makeText(getContext(),"Today, "+ response.body().size()+ "pets will be vaccinated.",Toast.LENGTH_LONG).show();
                    vacCalendarModelList = response.body();
                    vacCalendarAdapter = new VacCalendarAdapter(vacCalendarModelList,getContext(),getActivity());
                    vacCalendarRecyclerView.setAdapter(vacCalendarAdapter);
                }else{
                    Toast.makeText(getContext(),"There are no pets to be vaccinated today.",Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<VacCalendarModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }
}
