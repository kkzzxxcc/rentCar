package com.oracle.rent.ch23.res.vo;

public class ResVO {
	private String resNumber;  //예약 번호
	private String resCarNumber;  //예약 차번호
	private String resDate;
	private String useBeginDate;
	private String returnDate;
	private String resUserId;  //예약자 아이디
	private double price;           // 가격
	private String status;          // 예약 상태

	// 기존 생성자와 새로운 생성자 추가
	public ResVO(String resNumber, String resCarNumber, String resDate, String useBeginDate, String returnDate, String resUserId, double price, String status) {
		this.resNumber = resNumber;
		this.resCarNumber = resCarNumber;
		this.resDate = resDate;
		this.useBeginDate = useBeginDate;
		this.returnDate = returnDate;
		this.resUserId = resUserId;
		this.price = price;
		this.status = status;
	}

	public ResVO() {

	}


	public String getResNumber() {
		return resNumber;
	}
	public void setResNumber(String resNumber) {
		this.resNumber = resNumber;
	}
	public String getResCarNumber() {
		return resCarNumber;
	}
	public void setResCarNumber(String resCarNumber) {
		this.resCarNumber = resCarNumber;
	}
	public String getResDate() {
		return resDate;
	}
	public void setResDate(String resDate) {
		this.resDate = resDate;
	}
	public String getUseBeginDate() {
		return useBeginDate;
	}
	public void setUseBeginDate(String useBeginDate) {
		this.useBeginDate = useBeginDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getResUserId() {
		return resUserId;
	}
	public void setResUserId(String resUserId) {
		this.resUserId = resUserId;
	}

	// getter와 setter 추가
	public double getPrice() {

		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
