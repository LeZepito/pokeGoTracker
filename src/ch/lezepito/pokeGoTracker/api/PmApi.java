package ch.lezepito.pokeGoTracker.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ch.lezepito.pokeGoTracker.util.Constants;
import ch.lezepito.pokeGoTracker.util.PrivateConstants;

public class PmApi {
	
	private static final Logger LOGGER = Logger.getLogger(PmApi.class.getName());
	
	private HashMap<String, String> addDefaultParams(HashMap<String, String>inputParams) {
		inputParams.put("w", Constants.WEST);
		inputParams.put("e", Constants.EAST);
		inputParams.put("n", Constants.NORTH);
		inputParams.put("s", Constants.SOUTH);
		return inputParams;
	}
	
    private static class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
	
	public String executeRequest(String mid, String ex) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new MyTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);
		
		//compose request
		HashMap<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mid", mid);
		inputParams.put("ex", ex);
		HashMap<String, String> finalParams = addDefaultParams(inputParams);
		String finalParamsString = getPostDataString(finalParams);
			
		URL url = new URL(PrivateConstants.API_BASE_URL + "?" + finalParamsString);
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Accept", "application/json, text/javascript, *" + "/*; q=0.01");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0");
		con.setRequestProperty("Accept-Language",  "en,de-DE;q=0.8,de;q=0.5,en-US;q=0.3");
		con.setRequestProperty("Origin",  PrivateConstants.ORIGIN_REFER);
		con.setRequestProperty("Referer",  PrivateConstants.ORIGIN_REFER);
		//con.setRequestMethod("GET");

		//OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
		
		//System.out.println("Connecting go " + url);
		LOGGER.info("Sending request to " + PrivateConstants.API_BASE_URL + " ...");
		
		//writer.write(finalParamsString);
		//writer.flush();
		//writer.close();
		
		StringBuilder sb = new StringBuilder();  
		int HttpResult = con.getResponseCode(); 
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			LOGGER.info("Response received...");
		    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		    String line = null;  
		    while ((line = br.readLine()) != null) {  
		        sb.append(line + "\n");
		    }
		    br.close();
		    return sb.toString();  
		}
		
		throw new RuntimeException("Something went wrong. Response code is: '" + con.getResponseMessage() + "'");
	}
	
	  private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
	        StringBuilder result = new StringBuilder();
	        boolean first = true;
	        for(Map.Entry<String, String> entry : params.entrySet()){
	            if (first)
	                first = false;
	            else
	                result.append("&");

	            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
	            result.append("=");
	            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
	        }

	        return result.toString();
	    }
	
}
