package com.example.forev.vetappadmin.RestApi;

import com.example.forev.vetappadmin.Models.AddPetModel;
import com.example.forev.vetappadmin.Models.AddVacModel;
import com.example.forev.vetappadmin.Models.AnswerModel;
import com.example.forev.vetappadmin.Models.CampaignAddModel;
import com.example.forev.vetappadmin.Models.CampaignDeleteModel;
import com.example.forev.vetappadmin.Models.CampaignModel;
import com.example.forev.vetappadmin.Models.DeletePetModel;
import com.example.forev.vetappadmin.Models.DeleteUserModel;
import com.example.forev.vetappadmin.Models.PetModel;
import com.example.forev.vetappadmin.Models.QuestionsModel;
import com.example.forev.vetappadmin.Models.UsersModel;
import com.example.forev.vetappadmin.Models.VacApprovedModel;
import com.example.forev.vetappadmin.Models.VacCalendarModel;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private  static ManagerAll ourInstance = new ManagerAll();

    public  static synchronized ManagerAll getInstance()
    {
        return  ourInstance;
    }

    public Call<List<CampaignModel>> getCampaigns()
    {
        Call<List<CampaignModel>> x = getRestApi().getCampaigns();
        return  x ;
    }

    public Call<CampaignAddModel> addCampaign(String title, String text, String imageString)
    {
        Call<CampaignAddModel> x = getRestApi().addCampaign(title,text,imageString);
        return  x ;
    }

    public Call<CampaignDeleteModel> deleteCampaign(String campaignId)
    {
        Call<CampaignDeleteModel> x = getRestApi().deleteCampaign(campaignId);
        return  x ;
    }

    public Call<List<VacCalendarModel>> getVac(String date)
    {
        Call<List<VacCalendarModel>> x = getRestApi().getVac(date);
        return  x ;
    }

    public Call<VacApprovedModel> getApprove(String id)
    {
        Call<VacApprovedModel> x = getRestApi().getApprove(id);
        return  x ;
    }

    public Call<VacApprovedModel> getCancel(String id)
    {
        Call<VacApprovedModel> x = getRestApi().getCancel(id);
        return  x ;
    }

    public Call<DeleteUserModel> getDelete(String id)
    {
        Call<DeleteUserModel> x = getRestApi().getDelete(id);
        return  x ;
    }

    public Call<DeletePetModel> getDeletePet(String id)
    {
        Call<DeletePetModel> x = getRestApi().getDeletePet(id);
        return  x ;
    }

    public Call<List<QuestionsModel>> getQuestion()
    {
        Call<List<QuestionsModel>> x = getRestApi().getQuestion();
        return  x ;
    }

    public Call<AddPetModel> addPet(String custid, String petname, String petkind,String petgenus, String petimage)
    {
        Call<AddPetModel> x = getRestApi().addPet(custid,petname,petkind,petgenus,petimage);
        return  x ;
    }

    public Call<AddVacModel> addVac(String custid, String petid, String vacname, String date)
    {
        Call<AddVacModel> x = getRestApi().addVac(custid,petid,vacname,date);
        return  x ;
    }

    public Call<AnswerModel> getAnswer(String custid,String questionid, String answer)
    {
        Call<AnswerModel> x = getRestApi().getAnswer(custid,questionid,answer);
        return  x ;
    }

    public Call<List<UsersModel>> getUsers()
    {
        Call<List<UsersModel>> x = getRestApi().getUsers();
        return  x ;
    }

    public Call<List<PetModel>> getPets(String petid)
    {
        Call<List<PetModel>> x = getRestApi().getPets(petid);
        return  x ;
    }
}
