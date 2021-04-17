package com.comsats.restaurantandroid.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

public class URLConnectionReader {
    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append(inputLine);

        in.close();

        return response.toString();
    }
    public static String getIP()
    {
    	return "http://10.0.2.2:8080/RestAutomationMobile/";

    }
    public static String getMediaIP()
    {
    	return "http://10.0.2.2:8080/RestAutomationAdmin/";

    }
	public static void checkHosts(String subnet) {
		try {
			int timeout = 1000;
			for (int i = 1; i < 254; i++) {
				String host = subnet + "." + i;
				if (InetAddress.getByName(host).isReachable(timeout)) {
					System.out.println(host + " is reachable");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String a[]) {
		//checkHosts("192.168.1"); 
		
		try {
			Enumeration<NetworkInterface> hello = java.net.NetworkInterface.getNetworkInterfaces();
			while (hello.hasMoreElements()) {
				System.out.println(((NetworkInterface)(hello.nextElement())).getDisplayName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}