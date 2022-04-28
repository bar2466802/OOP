package filesprocessing;
import filesprocessing.filters.FilterCommand;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.regex.*;
/**
 * Utils class for regex related methods.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class RegexUtils
{
	/**
	 * Check if given string matches the given regex pattern.
	 * @param patternString the regex pattern to test with
	 * @param text the string to test on
	 * @return true if it was a match, false otherwise
	 */
	protected static Boolean test(String patternString, String text)
	{
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	/**
	 * Check if given string matches the given regex pattern.
	 * @param patternString the regex pattern to test with
	 * @param text the string to test on
	 * @return true if it was a match, false otherwise
	 */
	protected static Boolean contains(String patternString, String text)
	{
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}
	/**
	 * Get all the number values from the line.
	 * @param text the string to fetch values from
	 * @param count the expected count of numbers to be found
	 * @return the found values if we found them all, null otherwise
	 * @throws MissingFormatArgumentException if we couldn't get the filter values
	 * @throws NullPointerException  if the string is null
	 * @throws NumberFormatException if the string does not contain
	 *         a parsable {@code double}.
	 */
	protected static ArrayList<Double> getNumbers(String text, int count)
		throws MissingFormatArgumentException,
			   NullPointerException,
			   NumberFormatException
	{
		ArrayList<Double> values = new ArrayList<Double>();
		Pattern pattern = Pattern.compile(FilterCommand.Constants.DOUBLE_NUM);
		Matcher matcher = pattern.matcher(text);
		while(matcher.find())
		{
			values.add(Double.parseDouble(matcher.group()));
		}

		if(values.size() == count)
		{
			return values;
		}
		throw new MissingFormatArgumentException("ERROR: Could not retrieve filter values.");
	}
	/**
	 * Get the filter value from the line.
	 * @param patternString the regex pattern to test with
	 * @param text the string to fetch value from
	 * @return the found value, null otherwise
	 * @throws MissingFormatArgumentException if we couldn't get the filter values
	 */
	protected static String getValue(String patternString, String text)
		throws MissingFormatArgumentException
	{
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find())
		{
			String result =  matcher.group(1);
			if(result == null)
			{
				result = "";
			}
			return result;
		}

		throw new MissingFormatArgumentException("ERROR: Could not retrieve filter values.");
	}
}
