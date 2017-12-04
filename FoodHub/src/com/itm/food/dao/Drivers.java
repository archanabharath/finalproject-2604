package com.itm.food.dao;
/**
 * 
 */

/**
 * @author alish
 *
 */
public class Drivers extends AbstractDomain {

	int driverId;
	String firstName;
	String lastName;
	String phoneNo;
	String email;
	String vehicleNo;
	String licenseNo;

	/**
	 * @return the driverId
	 */
	public int getDriverId() {
		return driverId;
	}

	/**
	 * @param driverId
	 *            the driverId to set
	 */
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo
	 *            the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo
	 *            the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the licenseNo
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * @param licenseNo
	 *            the licenseNo to set
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	// void addDrivers() {}
	// void updateDriverDetails(int driverId){}
	// void deleteDriver(int driverId) {}
}
