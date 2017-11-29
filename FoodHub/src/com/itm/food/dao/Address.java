package com.itm.food.dao;

public class Address extends AbstractDomain {

	int addrId;
	String fname;
	String lname;
	int custId;
	String addr1;
	String addr2;
	String city;
	int pincode;
	String addrphoneNo;

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	
	/**
	 * @return the custId
	 */
	public int getCustId() {
		return custId;
	}

	/**
	 * @param custId
	 *            the custId to set
	 */
	public void setCustId(int custId) {
		this.custId = custId;
	}

	/**
	 * @return the addrId
	 */
	public int getAddrId() {
		return addrId;
	}

	/**
	 * @param addrId
	 *            the addrId to set
	 */
	public void setAddrId(int addrId) {
		this.addrId = addrId;
	}

	/**
	 * @return the addr1
	 */
	public String getAddr1() {
		return addr1;
	}

	/**
	 * @param addr1
	 *            the addr1 to set
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	/**
	 * @return the addr2
	 */
	public String getAddr2() {
		return addr2;
	}

	/**
	 * @param addr2
	 *            the addr2 to set
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the pincode
	 */
	public int getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the addrphoneNo
	 */
	public String getAddrphoneNo() {
		return addrphoneNo;
	}

	/**
	 * @param addrphoneNo
	 *            the addrphoneNo to set
	 */
	public void setAddrphoneNo(String addrphoneNo) {
		this.addrphoneNo = addrphoneNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getFname());
		builder.append(" - ");
		builder.append(this.getAddr1());
		builder.append(" ");
		builder.append(this.getAddr2());
		builder.append(" ");
		builder.append(this.getCity());
		builder.append(" ");
		builder.append(this.getPincode());
		return builder.toString();
	}

}
