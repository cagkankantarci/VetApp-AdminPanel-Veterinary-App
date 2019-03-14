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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forev.vetappadmin.Models.CampaignDeleteModel;
import com.example.forev.vetappadmin.Models.CampaignModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.Warning;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignsAdapter extends RecyclerView.Adapter<CampaignsAdapter.ViewHolder>{

    List<CampaignModel> list;
    Context context;
    String shortText;
    Activity activity;

    public CampaignsAdapter(List<CampaignModel> list, Context context,Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.campaigns_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.campaigns_title.setText(list.get(position).getTitle().toString());


        if(list.get(position).getText().length()>20)
        {
            shortText = list.get(position).getText().substring(0,20) + "...";
            viewHolder.campaigns_text.setText(shortText);
        }
        else
        {
            viewHolder.campaigns_text.setText(list.get(position).getText().toString());
        }

        Picasso.get().load(list.get(position).getImage()).into(viewHolder.campaigns_image);

        viewHolder.campaignCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertCampaigns(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView campaigns_title,campaigns_text;
        ImageView campaigns_image;
        CardView campaignCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            campaigns_title = (TextView)itemView.findViewById(R.id.campaigns_title);
            campaigns_text = (TextView)itemView.findViewById(R.id.campaigns_text);
            campaigns_image = (ImageView)itemView.findViewById(R.id.campaigns_image);
            campaignCardView = (CardView)itemView.findViewById(R.id.campaignCardView);

        }
    }

    public void alertCampaigns(final int position)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.campaigns_delete_layout,null);

        Button campaignYesToDelete = (Button)view.findViewById(R.id.campaignYesToDelete);
        Button campaignNoToDelete = (Button)view.findViewById(R.id.campaignNoToDelete);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();


        campaignYesToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCampaign(list.get(position).getId().toString(),position);
                alertDialog.cancel();
            }
        });

        campaignNoToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void deleteCampaign(String id,final int position)
    {
        Call<CampaignDeleteModel> req = ManagerAll.getInstance().deleteCampaign(id);
        req.enqueue(new Callback<CampaignDeleteModel>() {
            @Override
            public void onResponse(Call<CampaignDeleteModel> call, Response<CampaignDeleteModel> response) {
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
            public void onFailure(Call<CampaignDeleteModel> call, Throwable t) {
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
