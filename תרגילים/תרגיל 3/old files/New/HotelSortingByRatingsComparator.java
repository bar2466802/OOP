import oop.ex3.searchengine.*;
import java.util.Comparator;
/**
 * This class is used to sort given list of hotels by Ratings.
 * @author Bar Melinarskiy
 * @version 26/8/20
 */
public class HotelSortingByRatingsComparator implements Comparator<Hotel>
{
	//Constants
	protected static final int EQ = 0;
	protected static final int GT = 1;
	protected static final int LT = -1;

	/**
	 * Compare btw two hotel by ratings & name.
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
		int ratingCompare = LT;
		if(hotel2.getStarRating() == hotel1.getStarRating())
		{
			ratingCompare = EQ;
		}
		else if(hotel2.getStarRating() > hotel1.getStarRating())
		{
			ratingCompare = GT;
		}
		int nameCompare = hotel1.getPropertyName().compareTo(hotel2.getPropertyName());

		// 2-level comparison using if-else block
		if (ratingCompare == 0)
		{
			return ((nameCompare == 0) ? ratingCompare : nameCompare);
		} else
		{
			return ratingCompare;
		}
	}
}
