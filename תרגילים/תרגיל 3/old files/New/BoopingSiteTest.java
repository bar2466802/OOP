import oop.ex3.searchengine.*;
import org.junit.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * This class is used test class BoopingSite.
 * @author Bar Melinarskiy
 * @version 26/8/20
 */
public class BoopingSiteTest
{
	//Constants
	protected static final String ERROR_PREFIX = "Error: Test {0} has failed. ";
	private static final String BIG_DATASET = "hotels_dataset.txt";
	private static final String SMALL_DATASET_1 = "hotels_tst1.txt"; //all hotels in Manali
	private static final String SMALL_DATASET_2 = "hotels_tst2.txt"; //empty file
	private static final String NON_EXISTING_CITY = "#$#@$#@$#@$.nonExisting";
	private static final int EMPTY = 0;
	private static final int ZERO = 0;
	private static final Hotel[] emptyHotelArray = new Hotel[EMPTY];
	// instance variables
	private static ArrayList<Hotel> longHotelList;
	private static ArrayList<Hotel> shortHotelList1; //all hotels in Manali
	private static ArrayList<Hotel> shortHotelList2; //empty list

	private static ArrayList<String> citiesLongHotelList;
	private static ArrayList<String> citiesShortHotelList1; // Only Manali
	private static ArrayList<String> citiesShortHotelList2; //empty list

	private BoopingSite boopingSiteTest1 = new BoopingSite(BIG_DATASET);
	private BoopingSite boopingSiteTest2 = new BoopingSite(SMALL_DATASET_1);
	private BoopingSite boopingSiteTest3 = new BoopingSite(SMALL_DATASET_2);

	private static String firstLongCity;
	private static String firstShort1City;
	private static String firstShort2City;

	// tests methods
	/** build testers objects
	 */
	@BeforeClass
	public static void setUp()
	{
		longHotelList = getAllHotels(BIG_DATASET);
		shortHotelList1 = getAllHotels(SMALL_DATASET_1);
		shortHotelList2 = getAllHotels(SMALL_DATASET_2);

		citiesLongHotelList = getAllCities(longHotelList);
		citiesShortHotelList1 = getAllCities(shortHotelList1);
		citiesShortHotelList2 = getAllCities(shortHotelList2);

		firstLongCity = citiesLongHotelList.get(ZERO);
		firstShort1City = citiesShortHotelList1.get(ZERO);
		firstShort2City = "";
	}

	/** check method getHotelsInCityByRating
	 */
	@Test
	public void getHotelsInCityByRating()
	{
		final String method = "getHotelsInCityByRating";
		//Check method on the large dataset
		Hotel[] returnedHotelsLong = boopingSiteTest1.getHotelsInCityByRating(firstLongCity);
		ArrayList<Hotel> firstCityHotelsLong =  getAllHotelsInCities(longHotelList, firstLongCity);
		checkAllHotelsExist(returnedHotelsLong, firstCityHotelsLong, method);
		checkRatingOrder(returnedHotelsLong, method);
		//Check method on the small dataset
		Hotel[] returnedHotelsShort1 = boopingSiteTest2.getHotelsInCityByRating(firstShort1City);
		ArrayList<Hotel> firstCityHotelsShort1 =  getAllHotelsInCities(shortHotelList1, firstShort1City);
		checkAllHotelsExist(returnedHotelsShort1, firstCityHotelsShort1, method);
		checkRatingOrder(returnedHotelsShort1, method);
		//Check method on the empty dataset
		Hotel[] returnedHotelsShort2 = boopingSiteTest3.getHotelsInCityByRating(firstShort2City);
		ArrayList<Hotel> firstCityHotelsShort2 =  getAllHotelsInCities(shortHotelList1, firstShort2City);
		checkAllHotelsExist(returnedHotelsShort2, firstCityHotelsShort2, method);
	}

	/** check method getHotelsByProximity
	 */
	@Test
	public void getHotelsByProximity()
	{
		final String method = "getHotelsByProximity";
		checkCoordinatesByProximity(boopingSiteTest1);

		//Check method on the large dataset
		Hotel hotel = longHotelList.get(ZERO);
		Hotel[] returnedHotelsLong = boopingSiteTest1.getHotelsByProximity(
				hotel.getLatitude(), hotel.getLongitude());
		checkAllHotelsExist(returnedHotelsLong, longHotelList, method);
		checkProximityOrder(returnedHotelsLong, hotel.getLatitude(), hotel.getLongitude(), method);
		//Check method on the large dataset - another hotel
		hotel = longHotelList.get(8);
		returnedHotelsLong = boopingSiteTest1.getHotelsByProximity(
				hotel.getLatitude(), hotel.getLongitude());
		checkAllHotelsExist(returnedHotelsLong, longHotelList, method);
		checkProximityOrder(returnedHotelsLong, hotel.getLatitude(), hotel.getLongitude(), method);
		//Check method on the small dataset
		hotel = shortHotelList1.get(ZERO);
		Hotel[] returnedHotelsShort1 = boopingSiteTest2.getHotelsByProximity(
				hotel.getLatitude(), hotel.getLongitude());
		checkAllHotelsExist(returnedHotelsShort1, shortHotelList1, method);
		checkProximityOrder(returnedHotelsShort1, hotel.getLatitude(), hotel.getLongitude(), method);
		//Check method on the empty dataset
		Hotel[] returnedHotelsShort2 = boopingSiteTest3.getHotelsByProximity(
				hotel.getLatitude(), hotel.getLongitude());
		checkAllHotelsExist(returnedHotelsShort2, shortHotelList2, method);
	}

	/** check method getHotelsInCityByProximity
	 */
	@Test
	public void getHotelsInCityByProximity()
	{
		final String method = "getHotelsInCityByProximity";
		checkCoordinatesInCityByProximity(boopingSiteTest1);

		//Check method on the large dataset
		Hotel hotel = longHotelList.get(ZERO);
		Hotel[] returnedHotelsLong = boopingSiteTest1.getHotelsInCityByProximity(firstLongCity,
				hotel.getLatitude(), hotel.getLongitude());
		ArrayList<Hotel> firstCityHotelsLong =  getAllHotelsInCities(longHotelList, firstLongCity);
		checkAllHotelsExist(returnedHotelsLong, firstCityHotelsLong, method);
		checkProximityOrder(returnedHotelsLong, hotel.getLatitude(), hotel.getLongitude(), method);
		//Check method on the large dataset - another hotel
		hotel = longHotelList.get(8);
		returnedHotelsLong = boopingSiteTest1.getHotelsInCityByProximity(firstLongCity,
																				 hotel.getLatitude(), hotel.getLongitude());
		firstCityHotelsLong =  getAllHotelsInCities(longHotelList, firstLongCity);
		checkAllHotelsExist(returnedHotelsLong, firstCityHotelsLong, method);
		checkProximityOrder(returnedHotelsLong, hotel.getLatitude(), hotel.getLongitude(), method);
		//Check method on the small dataset
		hotel = shortHotelList1.get(ZERO);
		Hotel[] returnedHotelsShort1 = boopingSiteTest2.getHotelsInCityByProximity(firstShort1City,
				hotel.getLatitude(), hotel.getLongitude());
		ArrayList<Hotel> firstCityHotelsShort1 =  getAllHotelsInCities(shortHotelList1, firstShort1City);
		checkAllHotelsExist(returnedHotelsShort1, firstCityHotelsShort1, method);
		checkProximityOrder(returnedHotelsShort1, hotel.getLatitude(), hotel.getLongitude(), method);
		//Check method on the empty dataset
		Hotel[] returnedHotelsShort2 = boopingSiteTest3.getHotelsInCityByProximity(firstShort2City,
				hotel.getLatitude(), hotel.getLongitude());
		checkAllHotelsExist(returnedHotelsShort2, shortHotelList2, method);
	}

	/** check method checkNonExistingCity
	 */
	@Test
	public void checkNonExistingCity()
	{
		final String method = "checkNonExistingCity";
		Hotel hotel = shortHotelList1.get(ZERO);
		//Check method on the small dataset - getHotelsInCityByRating
		Hotel[] returnedHotelsShort1 = boopingSiteTest2.getHotelsInCityByRating(NON_EXISTING_CITY);
		checkAllHotelsExist(returnedHotelsShort1, shortHotelList2, method);

		//Check method on the small dataset - getHotelsInCityByProximity

		Hotel[] returnedHotelsShort2 = boopingSiteTest2.getHotelsInCityByProximity(NON_EXISTING_CITY,
													   hotel.getLatitude(), hotel.getLongitude());
		checkAllHotelsExist(returnedHotelsShort2, shortHotelList2, method);
	}

	/** check the hotels are order correctly by proximity
	 * @param boopingSiteTest the boopingSite to test on
	 */
	private void checkCoordinatesByProximity(BoopingSite boopingSiteTest)
	{
		final String method = "ByProximity - Checking non-valid coordinates";
		String errorMessage = getErrorPrefix(method);
		assertArrayEquals(errorMessage, emptyHotelArray,
						  boopingSiteTest.getHotelsByProximity(-900,-900));
		assertArrayEquals(errorMessage, emptyHotelArray,
						  boopingSiteTest.getHotelsByProximity(900,-900));
		assertArrayEquals(errorMessage, emptyHotelArray,
						  boopingSiteTest.getHotelsByProximity(-900,900));
		assertArrayEquals(errorMessage, emptyHotelArray,
						  boopingSiteTest.getHotelsByProximity(900,900));
	}
	/** check the hotels in the current city are order correctly by proximity
	 * @param boopingSiteTest the boopingSite to test on
	 */
	private void checkCoordinatesInCityByProximity(BoopingSite boopingSiteTest)
	{
		final String method = "InCityByProximity - Checking non-valid coordinates";
		String errorMessage = getErrorPrefix(method);

		String city = citiesLongHotelList.get(0);
		assertArrayEquals(errorMessage, emptyHotelArray,
						  boopingSiteTest.getHotelsInCityByProximity(city,-900,-900));
		assertArrayEquals(errorMessage, emptyHotelArray,
						  boopingSiteTest.getHotelsInCityByProximity(city,900,-900));
		assertArrayEquals(errorMessage, emptyHotelArray,
						  boopingSiteTest.getHotelsInCityByProximity(city,-900,900));
		assertArrayEquals(errorMessage, emptyHotelArray,
						  boopingSiteTest.getHotelsInCityByProximity(city,900,900));
	}

	/** Get all the hotels from the dataset
	 * @param datasetName the dataset to fetch hotels from
	 * @return list of all the hotels from the dataset
	 */
	private static ArrayList<Hotel> getAllHotels(String datasetName)
	{
		Hotel[] hotels = HotelDataset.getHotels(datasetName);
		ArrayList<Hotel> hotelList = new ArrayList<Hotel>(Arrays.asList(hotels));
		return hotelList;
	}

	/** Get all the cities in the given hotels list
	 * @param hotels given hotels list to fetch cities from
	 * @return list of all the cities
	 */
	private static ArrayList<String> getAllCities(ArrayList<Hotel> hotels)
	{
		ArrayList<String> citiesList = new ArrayList<String>();
		for (Hotel hotel : hotels)
		{
			if (citiesList.contains(hotel.getCity()) == false)
			{
				citiesList.add(hotel.getCity());
			}
		}

		return citiesList;
	}

	/** Get all the hotels in a given city
	 * @param hotels given hotels list to fetch from
	 * @param city city to check
	 * @return list of all the hotels in the given city
	 */
	private ArrayList<Hotel> getAllHotelsInCities(ArrayList<Hotel> hotels, String city)
	{
		ArrayList<Hotel> hotelsInCityList = new ArrayList<Hotel>();
		for (Hotel hotel : hotels)
		{
			if (city.equals(hotel.getCity()) && hotelsInCityList.contains(hotel) == false)
			{
				hotelsInCityList.add(hotel);
			}
		}

		return hotelsInCityList;
	}

	/** Check all hotels exist
	 * @param hotels given hotels list to check
	 * @param hotelsSource hotels to check against
	 * @param source calling test name
	 */
	private void checkAllHotelsExist(Hotel[] hotels, ArrayList<Hotel> hotelsSource, String source)
	{
		final String method = source + " - checking all hotels exist";
		String errorMessage = getErrorPrefix(method);

		assertNotNull(errorMessage, hotels);
		ArrayList<Hotel> hotelList = new ArrayList<Hotel>(Arrays.asList(hotels));
		assertEquals(errorMessage, hotels.length, hotelsSource.size());
		for (Hotel hotel : hotelList)
		{
			boolean exist = false;
			for (Hotel hotelSource : hotelsSource)
			{
				if(hotelSource.getUniqueId().equals(hotel.getUniqueId()))
				{
					exist = true;
					break;
				}
			}
			assertTrue(errorMessage, exist);
		}
	}

	/** Check all hotels are ordered by ratings
	 * @param hotels given hotels list to check
	 * @param source calling test name
	 */
	private void checkRatingOrder(Hotel[] hotels, String source)
	{
		final String method = source + " - checking hotels order by rating";
		String errorMessage = getErrorPrefix(method);

		assertNotNull(errorMessage, hotels);
		Hotel previousHotel = null;
		for (Hotel hotel : hotels)
		{
			if(previousHotel == null)
			{
				previousHotel = hotel;
			}
			else
			{
				boolean ratingsOrder = previousHotel.getStarRating() > hotel.getStarRating();
				boolean ratingsEq = previousHotel.getStarRating() == hotel.getStarRating();
				boolean nameOrder = previousHotel.getPropertyName().compareTo(
						hotel.getPropertyName()) <= ZERO;
				assertTrue(errorMessage,  ratingsOrder || (ratingsEq && nameOrder));
			}
		}
	}
	/** Check all hotels are ordered by proximity
	 * @param hotels given hotels list to check
	 * @param latitude point latitude
	 * @param longitude point longitude
	 * @param source calling test name
	 */
	private void checkProximityOrder(Hotel[] hotels, double latitude, double longitude, String source)
	{
		final String method = source + " - checking hotels order by proximity";
		String errorMessage = getErrorPrefix(method);

		assertNotNull(errorMessage, hotels);
		Hotel previousHotel = null;
		for (Hotel hotel : hotels)
		{
			if(previousHotel == null)
			{
				previousHotel = hotel;
			}
			else
			{
				double distancePrevious = calcDistance(latitude, longitude,
						previousHotel.getLatitude(), previousHotel.getLongitude());
				double distanceCurrent = calcDistance(latitude, longitude,
													  hotel.getLatitude(), hotel.getLongitude());
				boolean distanceOrder = distancePrevious < distanceCurrent;
				boolean distanceEq = distancePrevious == distanceCurrent;
				boolean poiOrder = previousHotel.getNumPOI() >= hotel.getNumPOI();
				assertTrue(errorMessage,  distanceOrder || (distanceEq && poiOrder));
			}
		}
	}

	/**
	 * Calc distance btw given point and the geographic location
	 * @param  x1 first point longitude
	 * @param  y1 first point latitude
	 * @param  x2 second point longitude
	 * @param  y2 second point latitude
	 * @return distance btw given point and the geographic location
	 */
	private double calcDistance(double x1, double y1, double x2, double y2)
	{
		final int TWO = 2;
		return Math.sqrt(Math.pow(x1 - x2, TWO) +
						 Math.pow(y1 - y2, TWO));
	}

	/** Get error msg
	 * @param method param to add to msg
	 * @return error msg
	 */
	private String getErrorPrefix(String method)
	{
		String msgFormat = ERROR_PREFIX;
		return MessageFormat.format(msgFormat, method);
	}
}