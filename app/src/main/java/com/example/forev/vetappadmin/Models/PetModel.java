package com.example.forev.vetappadmin.Models;

public class PetModel{
	private Object petName;
	private boolean tf;
	private Object petId;
	private Object petKind;
	private Object petGenus;
	private Object petImage;

	public void setPetName(Object petName){
		this.petName = petName;
	}

	public Object getPetName(){
		return petName;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setPetId(Object petId){
		this.petId = petId;
	}

	public Object getPetId(){
		return petId;
	}

	public void setPetKind(Object petKind){
		this.petKind = petKind;
	}

	public Object getPetKind(){
		return petKind;
	}

	public void setPetGenus(Object petGenus){
		this.petGenus = petGenus;
	}

	public Object getPetGenus(){
		return petGenus;
	}

	public void setPetImage(Object petImage){
		this.petImage = petImage;
	}

	public Object getPetImage(){
		return petImage;
	}

	@Override
 	public String toString(){
		return 
			"PetModel{" + 
			"petName = '" + petName + '\'' + 
			",tf = '" + tf + '\'' + 
			",petId = '" + petId + '\'' + 
			",petKind = '" + petKind + '\'' + 
			",petGenus = '" + petGenus + '\'' + 
			",petImage = '" + petImage + '\'' + 
			"}";
		}
}
