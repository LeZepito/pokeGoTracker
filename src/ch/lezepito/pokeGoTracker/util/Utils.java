package ch.lezepito.pokeGoTracker.util;

import java.text.DecimalFormat;

public class Utils {
	
	private static double degreesToRadians(double degrees) {
		return degrees * Math.PI / 180;
	}
	
	private static double distanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2) {
		double earthRadiusKm = 6371;

		double dLat = degreesToRadians(lat2 - lat1);
		double dLon = degreesToRadians(lon2 - lon1);

		lat1 = degreesToRadians(lat1);
		lat2 = degreesToRadians(lat2);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
				* Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadiusKm * c;
	}
	
	public static double calculateDistanceFromHome(double latitude, double longitude) {
		double distanceInKm = distanceInKmBetweenEarthCoordinates(latitude, longitude, Constants.HOME_LATITUDE, Constants.HOME_LONGITUDE); 
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(distanceInKm));
	}
}
