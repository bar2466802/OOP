import oop.ex3.searchengine.Hotel;
import java.util.Comparator;
/**
 * This class is used to sort given list of hotels by proximity.
 * @author Bar Melinarskiy
 * @version 26/8/20
 */
public class HotelSortingByProximityComparator implements Comparator<Hotel>
{
	double latitudeCoordinate;
	double longitudeCoordinate;
	/**
	 * Creates a Comparator instance.
	 * @param latitude longitude of point.
	 * @param longitude longitude of longitude.
	 */
	HotelSortingByProximityComparator(double latitude, double longitude)
	{
		latitudeCoordinate = latitude;
		longitudeCoordinate = longitude;
	}
	/**
	 * Compare btw two hotel by proximity & POI.
	 * @param hotel1 first hotel.
	 * @param hotel2 second hotel.
	 * @return the value {@code 0} if {@code x == y};
	 * a value less than {@code 0} if {@code x < y}; and
	 * a value greater than {@code 0} if {@code x > y}
	 */
	@Override
	public int compare(Hotel hotel1, Hotel hotel2)
	{
		// for comparison
		int distanceCompare = compareDistance(hotel1, hotel2);
		int poiCompare = Integer.compare(hotel2.getNumPOI(), hotel1.getNumPOI());


		// 2-level comparison using if-else block
		if (distanceCompare == 0)
		{
			return ((poiCompare == 0) ? distanceCompare : poiCompare);
		} else
		{
			return distanceCompare;
		}
	}
	/**
	 * Compares two hotel by their Euclidean distance from the given geographic location
	 * @param  hotel1 the first hotel to compare
	 * @param  hotel2 the second hotel to compare
	 * @return the value {@code 0} if {@code x == y};
	 *         a value less than {@code 0} if {@code x < y}; and
	 *         a value greater than {@code 0} if {@code x > y}
	 */
	private int compareDistance(Hotel hotel1, Hotel hotel2)
	{
		double distance1 = calcDistance(hotel1.getLongitude(), hotel1.getLatitude());
		double distance2 = calcDistance(hotel2.getLongitude(), hotel2.getLatitude());
		return Double.compare(distance1, distance2);
	}
	/**
	 * Calc distance btw given point and the geographic location
	 * @param  x longitude
	 * @param  y latitude
	 * @return distance btw given point and the geographic location
	 */
	private double calcDistance(double x, double y)
	{
		final int TWO = 2;
		return Math.sqrt(Math.pow(longitudeCoordinate - x, TWO) +
									 Math.pow(latitudeCoordinate - y, TWO));
	}



}
