package com.example.forev.vetappadmin.Models;

public class QuestionsModel{
	private boolean tf;
	private String questionid;
	private String question;
	private String phone;
	private String custid;
	private String username;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setQuestionid(String questionid){
		this.questionid = questionid;
	}

	public String getQuestionid(){
		return questionid;
	}

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return question;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setCustid(String custid){
		this.custid = custid;
	}

	public String getCustid(){
		return custid;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"QuestionsModel{" + 
			"tf = '" + tf + '\'' + 
			",questionid = '" + questionid + '\'' + 
			",question = '" + question + '\'' + 
			",phone = '" + phone + '\'' + 
			",custid = '" + custid + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}
