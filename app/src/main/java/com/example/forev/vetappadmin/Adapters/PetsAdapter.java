package com.example.forev.vetappadmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forev.vetappadmin.Models.AddVacModel;
import com.example.forev.vetappadmin.Models.DeletePetModel;
import com.example.forev.vetappadmin.Models.PetModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.ChangeFragments;
import com.example.forev.vetappadmin.Utils.Warning;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder> {

    List<PetModel> list;
    Context context;
    Activity activity;
    ChangeFragments changeFragments;
    String custid;
    String date ="", formatDate="";

    public PetsAdapter(List<PetModel> list, Context context, Activity activity, String custid) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.custid = custid;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.pets_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.petNameText.setText(list.get(position).getPetName().toString());
        viewHolder.petInfoText.setText("Click to delete this pet.");

        viewHolder.petInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDeletePet(position);
            }
        });


        viewHolder.petCardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVacAlert(list.get(position).getPetId().toString());
            }
        });


        Picasso.get().load(list.get(position).getPetImage().toString()).into(viewHolder.petImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView petInfoText, petNameText;
        ImageView petImage;
        CardView petCardViewLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            petInfoText = (TextView) itemView.findViewById(R.id.petInfoText);
            petNameText = (TextView) itemView.findViewById(R.id.petNameText);
            petImage = (ImageView) itemView.findViewById(R.id.petImage);
            petCardViewLayout = (CardView) itemView.findViewById(R.id.petCardViewLayout);

        }
    }

    public void addVacAlert(final String petid) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alert_layout_vac, null);

        CalendarView calendarView = (CalendarView)view.findViewById(R.id.addVacCalendar);
        final TextView addVacName = (TextView) view.findViewById(R.id.addVacName);
        Button addVacButton = (Button) view.findViewById(R.id.addVacButton);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

                date = i2 +"/"+(i1+1)+"/"+i;

                try {
                    Date dat = inputFormat.parse(date);
                    formatDate = outputFormat.format(dat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        addVacButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!formatDate.equals("") && !addVacName.getText().toString().equals(""))
                {
                    addVac(custid,petid,addVacName.getText().toString(),formatDate,alertDialog);
                }else{
                    Toast.makeText(context,"Choose date or enter a vaccine name.",Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.show();
    }

    public void addVac(String custid, String petid, String vacName, String date, final AlertDialog alertDialog)
    {
        Call<AddVacModel> req = ManagerAll.getInstance().addVac(custid,petid,vacName,date);
        req.enqueue(new Callback<AddVacModel>() {
            @Override
            public void onResponse(Call<AddVacModel> call, Response<AddVacModel> response) {
                alertDialog.cancel();
                Toast.makeText(context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<AddVacModel> call, Throwable t) {
                Toast.makeText(context,Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void alertDeletePet(final int position)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.pet_delete_layout,null);

        Button petYesToDelete = (Button)view.findViewById(R.id.petYesToDelete);
        Button petNoToDelete = (Button)view.findViewById(R.id.petNoToDelete);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();


        petYesToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePet(list.get(position).getPetId().toString(),position);
                alertDialog.cancel();
            }
        });

        petNoToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }


    public void deletePet(String id,final int position)
    {
        Call<DeletePetModel> req = ManagerAll.getInstance().getDeletePet(id);
        req.enqueue(new Callback<DeletePetModel>() {
            @Override
            public void onResponse(Call<DeletePetModel> call, Response<DeletePetModel> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(context,response.body().getText(),Toast.LENGTH_LONG).show();
                    deleteAndUpdateList(position);
                }
                else
                {
                    Toast.makeText(context,response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeletePetModel> call, Throwable t) {
                Toast.makeText(context,Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteAndUpdateList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
