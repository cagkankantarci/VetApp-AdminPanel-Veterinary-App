package com.example.forev.vetappadmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forev.vetappadmin.Models.AnswerModel;
import com.example.forev.vetappadmin.Models.QuestionsModel;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.RestApi.ManagerAll;
import com.example.forev.vetappadmin.Utils.Warning;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder>{

    List<QuestionsModel> list;
    Context context;
    Activity activity;

    public QuestionsAdapter(List<QuestionsModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.questions_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.questionUserText.setText(list.get(position).getUsername().toString());
        viewHolder.questionQuestText.setText(list.get(position).getQuestion().toString());
        viewHolder.questionCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(list.get(position).getPhone().toString());
            }
        });

        viewHolder.questionAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAnswerAlert(list.get(position).getCustid().toString(),list.get(position).getQuestionid().toString(),
                        position,list.get(position).getQuestion().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView questionUserText,questionQuestText;
        ImageView questionAnswer,questionCall;

        public ViewHolder(View itemView) {
            super(itemView);

            questionUserText = (TextView)itemView.findViewById(R.id.questionUserText);
            questionQuestText = (TextView)itemView.findViewById(R.id.questionQuestText);
            questionAnswer = (ImageView)itemView.findViewById(R.id.questionAnswer);
            questionCall = (ImageView)itemView.findViewById(R.id.questionCall);

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

    public void openAnswerAlert(final String custid, final String questionid, final int position,String question)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alert_layout_question,null);

        final EditText edittext_answer = (EditText)view.findViewById(R.id.edittext_answer);
        MaterialButton button_answer = (MaterialButton)view.findViewById(R.id.button_answer);
        TextView questionText = (TextView)view.findViewById(R.id.questionText);
        questionText.setText(question);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        button_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = edittext_answer.getText().toString();
                edittext_answer.setText("");
                alertDialog.cancel();
                answerQuestion(custid,questionid,answer,alertDialog,position);
            }
        });
        alertDialog.show();
    }

    public void answerQuestion(String custid, String questionid, String answer, final AlertDialog alertDialog, final int position)
    {
        Call<AnswerModel> req = ManagerAll.getInstance().getAnswer(custid,questionid,answer);
        req.enqueue(new Callback<AnswerModel>() {
            @Override
            public void onResponse(Call<AnswerModel> call, Response<AnswerModel> response) {
                if (response.body().isTf())
                {
                    Toast.makeText(context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                    deleteAndUpdateList(position);
                }
                else{
                    Toast.makeText(context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<AnswerModel> call, Throwable t) {
                Toast.makeText(context,Warning.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

}
