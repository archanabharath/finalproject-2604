package com.itm.food.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoLocator {

	private static final Logger log = Logger.getLogger(GeoLocator.class);

	private static String GOOGLE_API = "https://maps.googleapis.com/maps/api/geocode/json";
	private static String GOOGLE_LAT_LNG_PARAM = "?latlng=%f,%f&key=%s";
	private static String GOOGLE_API_KEY = "AIzaSyDn711I7K9deULNR9VBNXWRfdh1V3LX99w";

	private static String GEONAME_API = "http://api.geonames.org/findNearbyPostalCodesJSON";
	private static String GEONAME_LAT_LNG_PARAM = "?lat=%f&lng=%f&username=%s";
	private static String GEONAME_KEY = "archana220";

	private double latitude;
	private double longitude;

	public GeoLocator() {
		getGeoCordinates();
	}

	public GeoLocator(double latitude, double longitude) {
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Method to get Http response from a Rest client
	 * 
	 * @param urlStr
	 * @return
	 */
	private String getHttpResponse(String urlStr) {
		String output = "";
		try {
			URL url = new URL(urlStr);
			log.debug("Connecting to " + urlStr + " ...");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				log.error("Failed : HTTP error code : " + conn.getResponseCode());
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String out = "";
				while ((out = br.readLine()) != null) {
					output += out;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return output;
	}

	public String getReverseGeocoding() {
		String address = "";
		try {
			String response = getHttpResponse(
					GOOGLE_API + String.format(GOOGLE_LAT_LNG_PARAM, this.getLatitude(), this.getLongitude(), GOOGLE_API_KEY).toString());
			JSONObject resObj = (JSONObject) new JSONParser().parse(response);
			JSONArray results = (JSONArray) resObj.get("results");
			JSONObject addrObj = (JSONObject) results.get(0);
			address = (String) addrObj.get("formatted_address");
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.debug("Fetched address: " + address);
		return address;
	}

	public List<Integer> getNearByZipCodes() {
		List<Integer> nearByZipCodes = new ArrayList<Integer>();
		try {
			String response = getHttpResponse(
					GEONAME_API + String.format(GEONAME_LAT_LNG_PARAM, this.getLatitude(), this.getLongitude(), GEONAME_KEY).toString());
			JSONObject resObj = (JSONObject) new JSONParser().parse(response);
			JSONArray results = (JSONArray) resObj.get("postalCodes");
			for (int i = 0; i < results.size(); i++) {
				JSONObject obj = (JSONObject) results.get(i);
				nearByZipCodes.add(Integer.parseInt((String) obj.get("postalCode")));
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.debug("Fetched near by ZIP's: " + nearByZipCodes);
		return nearByZipCodes;
	}

	public void getGeoCordinates() {
		// TODO - Replace the below code with code to get lat & lng from IP address or Location service
		this.setLatitude(41.779291699999995);
		this.setLongitude(-87.98360319999999);
	}

	public static void main(String[] args) throws ParseException {
		GeoLocator locator = new GeoLocator();
		locator.getReverseGeocoding();
		locator.getNearByZipCodes();
	}

}