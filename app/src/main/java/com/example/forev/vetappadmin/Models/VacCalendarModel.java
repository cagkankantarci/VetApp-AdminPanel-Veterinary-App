package com.example.forev.vetappadmin.Models;

public class VacCalendarModel{
	private String vacdate;
	private boolean tf;
	private String petname;
	private String phone;
	private String vacname;
	private String petgenus;
	private String petimage;
	private String petkind;
	private String vacId;
	private String username;

	public void setVacdate(String vacdate){
		this.vacdate = vacdate;
	}

	public String getVacdate(){
		return vacdate;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setPetname(String petname){
		this.petname = petname;
	}

	public String getPetname(){
		return petname;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setVacname(String vacname){
		this.vacname = vacname;
	}

	public String getVacname(){
		return vacname;
	}

	public void setPetgenus(String petgenus){
		this.petgenus = petgenus;
	}

	public String getPetgenus(){
		return petgenus;
	}

	public void setPetimage(String petimage){
		this.petimage = petimage;
	}

	public String getPetimage(){
		return petimage;
	}

	public void setPetkind(String petkind){
		this.petkind = petkind;
	}

	public String getPetkind(){
		return petkind;
	}

	public void setVacId(String vacId){
		this.vacId = vacId;
	}

	public String getVacId(){
		return vacId;
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
			"VacCalendarModel{" + 
			"vacdate = '" + vacdate + '\'' + 
			",tf = '" + tf + '\'' + 
			",petname = '" + petname + '\'' + 
			",phone = '" + phone + '\'' + 
			",vacname = '" + vacname + '\'' + 
			",petgenus = '" + petgenus + '\'' + 
			",petimage = '" + petimage + '\'' + 
			",petkind = '" + petkind + '\'' + 
			",vacId = '" + vacId + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}
