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
import android.widget.Toast;

import com.example.forev.vetappadmin.Adapters.CampaignsAdapter;
import com.example.forev.vetappadmin.Models.CampaignAddModel;
import com.example.forev.vetappadmin.Models.CampaignModel;
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

public class CampaignFragment extends Fragment {

    private View view;
    private RecyclerView campaignRecyclerview;
    private List<CampaignModel> campaignModelList;
    private CampaignsAdapter campaignsAdapter;
    private ChangeFragments changeFragments;
    private Button buttonAddCampaigns;
    private ImageView campaignImage,campaignBackImage;
    private Bitmap bitmap;
    String imageString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_campaign, container, false);
        defineLayout();
        getCampaigns();
        click();
        return view;
    }

    public void defineLayout(){
        campaignRecyclerview = (RecyclerView)view.findViewById(R.id.campaignRecyclerview);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        campaignRecyclerview.setLayoutManager(mng);
        campaignModelList = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());
        buttonAddCampaigns = (Button)view.findViewById(R.id.buttonAddCampaigns);
        campaignBackImage = (ImageView)view.findViewById(R.id.campaignBackImage);
        bitmap = null;
        imageString = "";
    }

    public void getCampaigns()
    {
        final Call<List<CampaignModel>> req = ManagerAll.getInstance().getCampaigns();
        req.enqueue(new Callback<List<CampaignModel>>() {
            @Override
            public void onResponse(Call<List<CampaignModel>> call, Response<List<CampaignModel>> response) {
                if(response.body().get(0).isTf())
                {
                    campaignModelList = response.body();
                    campaignsAdapter = new CampaignsAdapter(campaignModelList,getContext(),getActivity());
                    campaignRecyclerview.setAdapter(campaignsAdapter);
                }
                else
                {
                    Toast.makeText(getContext(),"There are no campaigns.",Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<CampaignModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void click()
    {
        buttonAddCampaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCampaigns();
            }
        });

        campaignBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void addCampaigns()
    {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alert_layout_campaigns,null);

        final EditText campaignTitle = (EditText)view.findViewById(R.id.enterTitle);
        final EditText campaignContent = (EditText)view.findViewById(R.id.enterContent);
        campaignImage = (ImageView)view.findViewById(R.id.enterImageView);
        Button campaignAddButton = (Button)view.findViewById(R.id.enterCampaign);
        Button campaignUploadButton = (Button)view.findViewById(R.id.enterImage);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        campaignUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToGallery();
            }
        });
        campaignAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!imageToString().equals("") && !campaignTitle.getText().toString().equals("") &&
                        !campaignContent.getText().toString().equals(""))
                {
                    addCampaign(campaignTitle.getText().toString(),campaignContent.getText().toString(),imageToString(),alertDialog);
                    campaignTitle.setText("");
                    campaignContent.setText("");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==777 && data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                campaignImage.setImageBitmap(bitmap);
                campaignImage.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    public void addCampaign(String title, String text, String imageString, final AlertDialog alertDialog)
    {
        Call<CampaignAddModel> req = ManagerAll.getInstance().addCampaign(title,text,imageString);
        req.enqueue(new Callback<CampaignAddModel>() {
            @Override
            public void onResponse(Call<CampaignAddModel> call, Response<CampaignAddModel> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(getContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    getCampaigns();
                    alertDialog.cancel();
                }
                else
                {
                    Toast.makeText(getContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<CampaignAddModel> call, Throwable t) {
                Toast.makeText(getContext(),Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });

    }

}
