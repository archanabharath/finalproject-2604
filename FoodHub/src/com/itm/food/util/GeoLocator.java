package com.itm.food.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoLocator {

	private static final Logger log = Logger.getLogger(GeoLocator.class);

	private static String GOOGLE_MAPS_API = "https://maps.googleapis.com/maps/api";
	private static String GOOGLE_LAT_LNG_PARAM = "/geocode/json?latlng=%s,%s&key=%s";
	private static String GOOGLE_DISTANCE_PARAM = "/distancematrix/json?units=imperial&origins=%s,%s&destinations=%s&key=%s";
	private static String GOOGLE_ADDRESS_PARAM = "/geocode/json?address=%s&key=%s";
	private static String GOOGLE_API_KEY = "AIzaSyDn711I7K9deULNR9VBNXWRfdh1V3LX99w";

	private static String GEONAME_API = "http://api.geonames.org/findNearbyPostalCodesJSON";
	private static String GEONAME_LAT_LNG_PARAM = "?lat=%s&lng=%s&username=%s";
	private static String GEONAME_KEY = "archana220";

	private String latitude;
	private String longitude;
	private String address;

	public GeoLocator() {
		getCurrentGeoCordinates();
	}

	public GeoLocator(String latitude, String longitude) {
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public GeoLocator(String address) {
		this.address = address;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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

	/**
	 * Method to get the reverse Geo-coding for the provide coordinates
	 * 
	 * @return String address
	 */
	public void lookupReverseGeocoding() {
		String address = "";
		try {
			String response = getHttpResponse(GOOGLE_MAPS_API + String
					.format(GOOGLE_LAT_LNG_PARAM, this.getLatitude(), this.getLongitude(), GOOGLE_API_KEY).toString());
			JSONObject resObj = (JSONObject) new JSONParser().parse(response);
			JSONArray results = (JSONArray) resObj.get("results");
			JSONObject addrObj = (JSONObject) results.get(0);
			address = (String) addrObj.get("formatted_address");
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.debug("lookupReverseGeocoding - Fetched address: " + address);
		this.setAddress(address);
	}

	/**
	 * Method to get the reverse Geo-coding for the provide coordinates
	 * 
	 * @return String address
	 */
	public void lookupGeocoding() {
		try {
			String response = getHttpResponse(GOOGLE_MAPS_API + String
					.format(GOOGLE_ADDRESS_PARAM, this.getAddress().replaceAll(" ", "+") , GOOGLE_API_KEY).toString());
			JSONObject resObj = (JSONObject) new JSONParser().parse(response);
			JSONArray results = (JSONArray) resObj.get("results");
			JSONObject addrObj = (JSONObject) results.get(0);
			JSONObject geometryObj = (JSONObject) addrObj.get("geometry");
			JSONObject locationObj = (JSONObject) geometryObj.get("location");
			this.setLatitude(String.valueOf(locationObj.get("lat")));
			this.setLongitude(String.valueOf(locationObj.get("lng")));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.debug("lookupGeocoding - Fetched Lat/Lng: " + this.getLatitude() + "/" + this.getLongitude());
	}

	/**
	 * Method to get near by zip code for provided coordinates
	 * 
	 * @return List<Integer>
	 */
	public List<Integer> getNearByZipCodes() {
		List<Integer> nearByZipCodes = new ArrayList<Integer>();
		try {
			String response = getHttpResponse(GEONAME_API + String
					.format(GEONAME_LAT_LNG_PARAM, this.getLatitude(), this.getLongitude(), GEONAME_KEY).toString());
			JSONObject resObj = (JSONObject) new JSONParser().parse(response);
			JSONArray results = (JSONArray) resObj.get("postalCodes");
			for (int i = 0; i < results.size(); i++) {
				JSONObject obj = (JSONObject) results.get(i);
				nearByZipCodes.add(Integer.parseInt((String) obj.get("postalCode")));
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.debug("getNearByZipCodes - Fetched near by ZIP's: " + nearByZipCodes);
		return nearByZipCodes;
	}

	public Map<String, DistanceMatrix> getDistanceMatrix(Map<String, DistanceMatrix> destinations) {
		try {
			StringBuilder destCordinateBuilder = new StringBuilder();
			int cnt = 1;
			Set<Entry<String, DistanceMatrix>> matrixSet = destinations.entrySet();
			for (Entry<String, DistanceMatrix> dest : matrixSet) {
				destCordinateBuilder.append(dest.getValue().getDestLat());
				destCordinateBuilder.append(",");
				destCordinateBuilder.append(dest.getValue().getDestLng());
				if (destinations.size() != cnt)
					destCordinateBuilder.append("|");
				cnt++;
			}

			String response = getHttpResponse(GOOGLE_MAPS_API + String.format(GOOGLE_DISTANCE_PARAM, this.getLatitude(),
					this.getLongitude(), destCordinateBuilder.toString(), GOOGLE_API_KEY).toString());

			JSONObject resObj = (JSONObject) new JSONParser().parse(response);
			JSONObject rows = (JSONObject) ((JSONArray) resObj.get("rows")).get(0);
			JSONArray elements = (JSONArray) rows.get("elements");
			cnt = 0;
			for (Entry<String, DistanceMatrix> dest : matrixSet) {

				dest.getValue().setOrginLat(this.getLatitude());
				dest.getValue().setOrginLng(this.getLongitude());

				JSONObject obj = (JSONObject) elements.get(cnt);
				JSONObject distance = (JSONObject) obj.get("distance");
				JSONObject duration = (JSONObject) obj.get("duration");

				dest.getValue().setMiles((String) distance.get("text"));
				dest.getValue().setDuration((String) duration.get("text"));
				cnt++;
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.debug("getDistanceMatrix - Fetched Distance Matrix " + destinations);

		return destinations;
	}

	/**
	 * Find and set the GeoCordinates
	 */
	public void getCurrentGeoCordinates() {
		// TODO - Replace the below code with code to get lat & lng from IP
		// address or Location service
		this.setLatitude("41.779291699999995");
		this.setLongitude("-87.98360319999999");
	}

	public static void main(String[] args) throws ParseException {
		GeoLocator locator = new GeoLocator();
		locator.lookupReverseGeocoding();
		locator.getNearByZipCodes();

		Map<String, DistanceMatrix> matrix = new HashMap<String, DistanceMatrix>();
		DistanceMatrix dMatrix1 = new DistanceMatrix();
		dMatrix1.setDestLat("41.7462");
		dMatrix1.setDestLng("-87.5526");
		matrix.put("AAA", dMatrix1);

		DistanceMatrix dMatrix2 = new DistanceMatrix();
		dMatrix2.setDestLat("41.7859");
		dMatrix2.setDestLng("-87.7157");
		matrix.put("BBB", dMatrix2);

		DistanceMatrix dMatrix3 = new DistanceMatrix();
		dMatrix3.setDestLat("41.9769");
		dMatrix3.setDestLng("-87.7691");
		matrix.put("CCC", dMatrix3);

		DistanceMatrix dMatrix4 = new DistanceMatrix();
		dMatrix4.setDestLat("41.7202");
		dMatrix4.setDestLng("-87.6433");
		matrix.put("DDD", dMatrix4);

		DistanceMatrix dMatrix5 = new DistanceMatrix();
		dMatrix5.setDestLat("41.91");
		dMatrix5.setDestLng("-87.7102");
		matrix.put("EEE", dMatrix5);

		locator.getDistanceMatrix(matrix);
		
		locator.setAddress("300 W 60th St, B408, Westmont, IL, 60559");
		locator.lookupGeocoding();
		
		
		

	}

}
