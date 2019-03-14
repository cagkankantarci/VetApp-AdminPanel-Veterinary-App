package com.example.forev.vetappadmin.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forev.vetappadmin.Adapters.PetsAdapter;
import com.example.forev.vetappadmin.Models.AddPetModel;
import com.example.forev.vetappadmin.Models.PetModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.ChangeFragments;
import com.example.forev.vetappadmin.Utils.Warning;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetsofUserFragment extends Fragment {

    private View view;
    private String custid;
    private ChangeFragments changeFragments;
    RecyclerView userPetListRecView;
    ImageView petsofuserImage,addPetImageView,petBackImage;
    TextView petsofuserText;
    Button userAddPet;
    private List<PetModel> petModelList;
    private PetsAdapter petsAdapter;
    Bitmap bitmap;
    private String imageString="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_petsofuser, container, false);
        defineLayout();
        getPets(custid);
        click();
        return view;
    }

    public void defineLayout()
    {
        custid = getArguments().get("userid").toString();
        userPetListRecView = (RecyclerView)view.findViewById(R.id.userPetListRecView);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        userPetListRecView.setLayoutManager(mng);
        petModelList = new ArrayList<>();
        petsofuserImage = (ImageView)view.findViewById(R.id.petsofuserImage);
        petBackImage = (ImageView)view.findViewById(R.id.petBackImage);
        petsofuserText = (TextView)view.findViewById(R.id.petsofuserText);
        userAddPet = (Button)view.findViewById(R.id.userAddPet);
        changeFragments = new ChangeFragments(getContext());
        bitmap = null;
    }

    public void click()
    {
        userAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPetAlert();
            }
        });

        petBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new UsersFragment());
            }
        });
    }

    public void getPets(String id)
    {
        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                if(response.body().get(0).isTf())
                {
                    userPetListRecView.setVisibility(View.VISIBLE);
                    petsofuserImage.setVisibility(View.GONE);
                    petsofuserText.setVisibility(View.GONE);
                    petModelList = response.body();
                    petsAdapter = new PetsAdapter(petModelList,getContext(),getActivity(),custid);
                    userPetListRecView.setAdapter(petsAdapter);
                    Toast.makeText(getContext(),"The user has "+ response.body().size() +" pets.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(),"There are no pets.",Toast.LENGTH_LONG).show();
                    petsofuserImage.setVisibility(View.VISIBLE);
                    petsofuserText.setVisibility(View.VISIBLE);
                    userPetListRecView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addPetAlert()
    {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alert_layout_pets,null);

        final EditText addPetNameEditText = (EditText)view.findViewById(R.id.addPetNameEditText);
        final EditText addPetKindEditText = (EditText)view.findViewById(R.id.addPetKindEditText);
        final EditText addPetGenusEditText = (EditText)view.findViewById(R.id.addPetGenusEditText);

        addPetImageView = (ImageView)view.findViewById(R.id.addPetImageView);
        Button addPetButton = (Button)view.findViewById(R.id.addPetButton);
        Button addPetUpload = (Button)view.findViewById(R.id.addPetUpload);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        addPetUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToGallery();
            }
        });
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!imageToString().equals("") && !addPetNameEditText.getText().toString().equals("") &&
                        !addPetKindEditText.getText().toString().equals("") &&
                        !addPetGenusEditText.getText().toString().equals(""))
                {
                    addPet(custid,addPetNameEditText.getText().toString(),addPetKindEditText.getText().toString(),addPetGenusEditText.getText().toString(),imageToString(),alertDialog);
                    addPetNameEditText.setText("");
                    addPetKindEditText.setText("");
                    addPetGenusEditText.setText("");
                }
                else
                {
                    Toast.makeText(getContext(),"Please fill in all fields and select image.",Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.show();
    }

    public void openToGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,777);
    }

    public String imageToString()
    {
        if(bitmap!=null)
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //jpeg tipinde,100 kalitesinde, nesne
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] byt = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(byt,Base64.DEFAULT);
            return imageString;
        }
        else
        {
            return imageString;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==777 && data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                addPetImageView.setImageBitmap(bitmap);
                addPetImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void addPet(final String custid, String petname, String petkind, String petgenus, String petimage, final AlertDialog alertDialog)
    {
        Call<AddPetModel> req = ManagerAll.getInstance().addPet(custid,petname,petkind,petgenus,petimage);
        req.enqueue(new Callback<AddPetModel>() {
            @Override
            public void onResponse(Call<AddPetModel> call, Response<AddPetModel> response) {
                if(response.body().isTf())
                {
                    getPets(custid);
                    Toast.makeText(getContext(),response.body().getText().toString(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }else{
                    Toast.makeText(getContext(),response.body().getText().toString(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<AddPetModel> call, Throwable t) {
                Toast.makeText(getContext(),Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }
}
