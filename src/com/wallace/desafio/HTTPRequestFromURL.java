package com.wallace.desafio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequestFromURL {
	
	public static String requestJSONFromURLString(String urlString) {
		
		try {
			
			//Opens connection and defines request headers
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setRequestProperty("Accept", "application/json");
		
			//Verifies if expected response was obtained
			int status = connection.getResponseCode();
			if (status != 200) {
				return null;
			}
			
			//Reads response and convert it to string
			BufferedReader responseBuffer = new BufferedReader(
					  new InputStreamReader(connection.getInputStream()));
			String responseLine;
			StringBuffer response = new StringBuffer();
			while ((responseLine = responseBuffer.readLine()) != null) {
			    response.append(responseLine);
			}
			responseBuffer.close();
			return response.toString();
			
		} catch (Exception exception) {
			
			//Logs error and returns response
			exception.printStackTrace();
			return null;
			
		}
	}

}
