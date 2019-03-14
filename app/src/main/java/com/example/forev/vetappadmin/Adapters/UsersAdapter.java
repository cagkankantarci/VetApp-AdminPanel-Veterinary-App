package com.example.forev.vetappadmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forev.vetappadmin.Fragments.PetsofUserFragment;
import com.example.forev.vetappadmin.Models.DeleteUserModel;
import com.example.forev.vetappadmin.Models.UsersModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.ChangeFragments;
import com.example.forev.vetappadmin.Utils.Warning;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    List<UsersModel> list;
    Context context;
    Activity activity;
    ChangeFragments changeFragments;
    Fragment fragment;

    public UsersAdapter(List<UsersModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.users_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.userNameText.setText(list.get(position).getUsername().toString());

        viewHolder.userCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(list.get(position).getPhone().toString());
            }
        });

        viewHolder.userPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.changeWithParameters(new PetsofUserFragment(),list.get(position).getId().toString());
            }
        });

        viewHolder.usersCardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDeleteUser(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView userNameText;
        Button userPets,userCall;
        CardView usersCardViewLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            userNameText = (TextView)itemView.findViewById(R.id.userNameText);
            userPets = (Button)itemView.findViewById(R.id.userPetList);
            userCall = (Button)itemView.findViewById(R.id.userCall);
            usersCardViewLayout = (CardView)itemView.findViewById(R.id.usersCardViewLayout);
        }
    }

    public void alertDeleteUser(final int position)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.user_delete_layout,null);

        Button userYesToDelete = (Button)view.findViewById(R.id.userYesToDelete);
        Button userNoToDelete = (Button)view.findViewById(R.id.userNoToDelete);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();


        userYesToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(list.get(position).getId().toString(),position);
                alertDialog.cancel();
            }
        });

        userNoToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }


    public void deleteUser(String id,final int position)
    {
        Call<DeleteUserModel> req = ManagerAll.getInstance().getDelete(id);
        req.enqueue(new Callback<DeleteUserModel>() {
            @Override
            public void onResponse(Call<DeleteUserModel> call, Response<DeleteUserModel> response) {
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
            public void onFailure(Call<DeleteUserModel> call, Throwable t) {
                Toast.makeText(context,Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void call(String number)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+number));
        activity.startActivity(intent);
    }

    public void deleteAndUpdateList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
