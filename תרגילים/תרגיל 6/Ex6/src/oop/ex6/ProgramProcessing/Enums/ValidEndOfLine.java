package oop.ex6.ProgramProcessing.Enums;
/**
 * Enum class for valid end of code line may be ; or {  or } or blank line
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public enum ValidEndOfLine
{
	END_SCOPE("^" + Constants.SPACE + "}" + Constants.SPACE),
	START_SCOPE(".*\\{" + Constants.SPACE + "$"),
	END_OF_LINE("(.*);" + Constants.SPACE ),
	SPACES(Constants.SPACE);

	private final String value;
	/**
	 * Create a new property inside the enum.
	 * @param value the inner value
	 */
	ValidEndOfLine(final String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value;
	}

	/**
	 * Nested class of constants values reused amongst the enum values.
	 * @author Bar Melinarskiy & Moshe Bengi
	 * @version 22/9/20
	 */
	public static class Constants
	{
		public static final String SPACE = "\\s*";
	}
}
