package oop.ex6.Utils;

import java.util.MissingFormatArgumentException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static Boolean test(String patternString, String text)
	{
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	/**
	 * Get the filter value from the line.
	 * @param patternString the regex pattern to test with
	 * @param text the string to fetch value from
	 * @return the found value, null otherwise
	 * @throws MissingFormatArgumentException if we couldn't get the filter values
	 */
	public static String getValue(String patternString, String text, int index)
			throws MissingFormatArgumentException
	{
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find())
		{
			String result =  matcher.group(index);
			if(result == null)
			{
				result = "";
			}
			return result;
		}

		throw new MissingFormatArgumentException("ERROR: Could not retrieve filter values.");
	}
}
