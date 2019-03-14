package com.example.forev.vetappadmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forev.vetappadmin.Models.VacApprovedModel;
import com.example.forev.vetappadmin.Models.VacCalendarModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.Warning;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacCalendarAdapter extends RecyclerView.Adapter<VacCalendarAdapter.ViewHolder>{

    List<VacCalendarModel> list;
    Context context;
    Activity activity;

    public VacCalendarAdapter(List<VacCalendarModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.vac_calendar_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.vacCalendarPetName.setText(list.get(position).getPetname());
        viewHolder.vacCalendarDescriptionText.setText(list.get(position).getVacname()+" vaccine will be given to " +list.get(position).getUsername() +"'s pet.");

        Picasso.get().load(list.get(position).getPetimage()).into(viewHolder.vacCalendarImageView);

        viewHolder.vacCalendarCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(list.get(position).getPhone().toString());
            }
        });

        viewHolder.vacCalendarMade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vacApproved(list.get(position).getVacId().toString(),position);
            }
        });

        viewHolder.vacCalendarNonMade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vacCancel(list.get(position).getVacId().toString(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView vacCalendarPetName,vacCalendarDescriptionText;
        Button vacCalendarMade,vacCalendarNonMade,vacCalendarCall;
        ImageView vacCalendarImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            vacCalendarPetName = (TextView)itemView.findViewById(R.id.vacCalendarPetName);
            vacCalendarDescriptionText = (TextView)itemView.findViewById(R.id.vacCalendarDescriptionText);
            vacCalendarMade = (Button)itemView.findViewById(R.id.vacCalendarMade);
            vacCalendarNonMade = (Button)itemView.findViewById(R.id.vacCalendarNonMade);
            vacCalendarCall = (Button)itemView.findViewById(R.id.vacCalendarCall);
            vacCalendarImageView = (ImageView)itemView.findViewById(R.id.vacCalendarImageView);

        }
    }

    public void deleteAndUpdateList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void call(String number)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+number));
        activity.startActivity(intent);
    }

    public void vacApproved(String id, final int position)
    {
        Call<VacApprovedModel> req = ManagerAll.getInstance().getApprove(id);
        req.enqueue(new Callback<VacApprovedModel>() {
            @Override
            public void onResponse(Call<VacApprovedModel> call, Response<VacApprovedModel> response) {
                Toast.makeText(context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
                deleteAndUpdateList(position);
            }

            @Override
            public void onFailure(Call<VacApprovedModel> call, Throwable t) {
                Toast.makeText(context,Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void vacCancel(String id, final int position)
    {
        Call<VacApprovedModel> req = ManagerAll.getInstance().getCancel(id);
        req.enqueue(new Callback<VacApprovedModel>() {
            @Override
            public void onResponse(Call<VacApprovedModel> call, Response<VacApprovedModel> response) {
                Toast.makeText(context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
                deleteAndUpdateList(position);
            }

            @Override
            public void onFailure(Call<VacApprovedModel> call, Throwable t) {
                Toast.makeText(context,Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }
}
