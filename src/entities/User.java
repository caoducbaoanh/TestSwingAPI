package entities;

import java.io.Serializable;
import java.sql.Date;

import com.google.gson.annotations.SerializedName;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	private String id;
	
	@SerializedName("amountToPay")
	private String amountToPay;
	
	@SerializedName("citizenName")
	private String citizenName;
	
	@SerializedName("dateOfPayment")
	private String dateOfPayment;
	
	@SerializedName("fee")
	private double fee;
	
	@SerializedName("identityID")
	private String identityID;
	
	@SerializedName("password")
	private String password;
	
	@SerializedName("phoneNumber")
	private String phoneNumber;
	
	@SerializedName("typeOfHouse")
	private String typeOfHouse;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(String amountToPay) {
		this.amountToPay = amountToPay;
	}

	public String getCitizenName() {
		return citizenName;
	}

	public void setCitizenName(String citizenName) {
		this.citizenName = citizenName;
	}

	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}
	
	

	public String getIdentityID() {
		return identityID;
	}

	public void setIdentityID(String identityid) {
		this.identityID = identityid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTypeOfHouse() {
		return typeOfHouse;
	}

	public void setTypeOfHouse(String typeOfHouse) {
		this.typeOfHouse = typeOfHouse;
	}

	

	
	
	
	

}
