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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @GET("/veterinaryfolder/campaignswithid.php")
    Call<List<CampaignModel>> getCampaigns();

    @FormUrlEncoded
    @POST("/veterinaryfolder/addcampaigns.php")
    Call<CampaignAddModel> addCampaign(@Field("title") String title, @Field("text") String text, @Field("image") String image);

    @FormUrlEncoded
    @POST("/veterinaryfolder/deletecampaigns.php")
    Call<CampaignDeleteModel> deleteCampaign(@Field("id") String campaignId);

    @FormUrlEncoded
    @POST("/veterinaryfolder/adminvaccalendar.php")
    Call<List<VacCalendarModel>> getVac(@Field("date") String date);

    @FormUrlEncoded
    @POST("/veterinaryfolder/vacapprove.php")
    Call<VacApprovedModel> getApprove(@Field("id") String id);

    @FormUrlEncoded
    @POST("/veterinaryfolder/deleteuser.php")
    Call<DeleteUserModel> getDelete(@Field("id") String id);

    @FormUrlEncoded
    @POST("/veterinaryfolder/deletepet.php")
    Call<DeletePetModel> getDeletePet(@Field("id") String id);

    @FormUrlEncoded
    @POST("/veterinaryfolder/vaccancel.php")
    Call<VacApprovedModel> getCancel(@Field("id") String id);

    @GET("/veterinaryfolder/questions.php")
    Call<List<QuestionsModel>> getQuestion();

    @FormUrlEncoded
    @POST("/veterinaryfolder/adminanswer.php")
    Call<AnswerModel> getAnswer(@Field("custid") String custid, @Field("questionid") String questionid, @Field("answer") String answer);

    @GET("/veterinaryfolder/users.php")
    Call<List<UsersModel>> getUsers();

    @FormUrlEncoded
    @POST("/veterinaryfolder/mypets.php")
    Call<List<PetModel>> getPets(@Field("id") String petid);

    @FormUrlEncoded
    @POST("/veterinaryfolder/addpet.php")
    Call<AddPetModel> addPet(@Field("custid") String custid, @Field("petname") String petname, @Field("petkind") String petkind, @Field("petgenus") String petgenus, @Field("petimage") String petimage);

    @FormUrlEncoded
    @POST("/veterinaryfolder/addvac.php")
    Call<AddVacModel> addVac(@Field("custid") String custid, @Field("petid") String petid, @Field("vacname") String vacname, @Field("date") String date);


}
