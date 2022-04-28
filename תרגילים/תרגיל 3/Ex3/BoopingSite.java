import oop.ex3.searchengine.*;

import java.text.MessageFormat;
import java.util.*;
/**
 * This class is part of the - Booping.com - a new hotel booking site.
 * @author Bar Melinarskiy
 * @version 26/8/20
 */
public class BoopingSite
{
	//Constants
	private static final int EMPTY = 0;
	// instance variables
	private String datasetName;

	/** Contractor
	 * @param name the name of the dataset
	 */
	public BoopingSite(String name)
	{
		datasetName = name;
	}
	/** Get hotels located in the given city sorted by ratings
	 * @param city the city the check
	 * @return an array of hotels located in the given city, sorted from
	 * the highest star-rating to the lowest. Hotels that have the
	 * same rating will be organized according to the alphabet order of
	 * the hotel's (property) name. In case there are no hotels in the
	 * given city, this method returns an empty array
	 */
	public Hotel[] getHotelsInCityByRating(String city)
	{
		ArrayList<Hotel> hotelsToSort = getHotelsInCity(city);
		if(!hotelsToSort.isEmpty())
		{
			Collections.sort(hotelsToSort, new HotelSortingByRatingsComparator());
		}

		Hotel[] hotelsToReturn = new Hotel[hotelsToSort.size()];
		hotelsToSort.toArray(hotelsToReturn);
		return hotelsToReturn;
	}
	/** Get hotels sorted according to their Euclidean distance from the given geographic location
	 * @param latitude geographic coordinate that specifies the north–south position of a point
	 * on the Earth's surface. valid values are btw [-90,90] degrees.
	 * @param longitude a geographic coordinate that specifies the east–west position of a point
	 * on the Earth's surface. valid values are btw [-180,180] degrees.
	 * @return an array of hotels, sorted according to their Euclidean distance from the given geographic
	 * location, in ascending order. Hotels that are at the same distance from the given location are
	 * organized according to the number of points-of-interest for which
	 * they are close to (in a decreasing order).
	 * In case of illegal input, this method returns an empty array.
	 */
	public Hotel[] getHotelsByProximity(double latitude, double longitude)
	{
		ArrayList<Hotel> hotelsToSort = new ArrayList<Hotel>();
		if(checkCoordinates(latitude, longitude) == true)
		{
			hotelsToSort = getAllHotels();
			Collections.sort(hotelsToSort, new HotelSortingByProximityComparator(latitude, longitude));
		}

		Hotel[] hotelsToReturn = new Hotel[hotelsToSort.size()];
		hotelsToSort.toArray(hotelsToReturn);
		return hotelsToReturn;
	}
	/** Get hotels in given city sorted according to their Euclidean distance
	 * from the given geographic location
	 * @param city the city the check
	 * @param latitude geographic coordinate that specifies the north–south position of a point
	 * on the Earth's surface. valid values are btw [-90,90] degrees.
	 * @param longitude a geographic coordinate that specifies the east–west position of a point
	 * on the Earth's surface. valid values are btw [-180,180] degrees.
	 * @return an array of hotels in the given city, sorted according to their Euclidean distance
	 * from the given geographic location, in ascending order.
	 * Hotels that are at the same distance from the given location
	 * are organized according to the number of points-of-interest for
	 * which they are close to (in a decreasing order).
	 * In case of illegal input, this method returns an empty array.
	 */
	public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude)
	{
		ArrayList<Hotel> hotelsToSort = new ArrayList<Hotel>();
		if(checkCoordinates(latitude, longitude) == true)
		{
			hotelsToSort = getHotelsInCity(city);
			Collections.sort(hotelsToSort, new HotelSortingByProximityComparator(latitude, longitude));
		}

		Hotel[] hotelsToReturn = new Hotel[hotelsToSort.size()];
		hotelsToSort.toArray(hotelsToReturn);
		return hotelsToReturn;
	}

	/** Get all the hotels from the dataset in the given city
	 * @param city - the city to filter with
	 * @return list of all the hotels from the dataset in the given city
	 */
	private ArrayList<Hotel> getHotelsInCity(String city)
	{
		ArrayList<Hotel> hotels = getAllHotels();
		ArrayList<Hotel> filteredList = new ArrayList<Hotel>();
		for (Hotel hotel : hotels)
		{
			if (hotel.getCity().equals(city))
			{
				filteredList.add(hotel);
			}
		}

		return filteredList;
	}

	/** Get all the hotels from the dataset
	 * @return list of all the hotels from the dataset
	 */
	private ArrayList<Hotel> getAllHotels()
	{
		Hotel[] hotels = HotelDataset.getHotels(datasetName);
		ArrayList<Hotel> hotelList = new ArrayList<Hotel>(Arrays.asList(hotels));
		return hotelList;
	}
	/** Check if the given coordinates are valid
	 * @param latitude geographic coordinate that specifies the north–south position of a point
	 * on the Earth's surface. valid values are btw [-90,90] degrees.
	 * @param longitude a geographic coordinate that specifies the east–west position of a point
	 * on the Earth's surface. valid values are btw [-180,180] degrees.
	 * @return true if the given coordinates are valid, false otherwise.
	 */
	private boolean checkCoordinates(double latitude, double longitude)
	{
		final int LATITUDE_LOW = -90;
		final int LATITUDE_HIGH = 90;
		final int LONGITUDE_LOW = -180;
		final int LONGITUDE_HIGH = 180;

		if(latitude < LATITUDE_LOW || latitude > LATITUDE_HIGH || longitude < LONGITUDE_LOW ||
		   longitude > LONGITUDE_HIGH)
		{
			return false;
		}

		return true;
	}
}
