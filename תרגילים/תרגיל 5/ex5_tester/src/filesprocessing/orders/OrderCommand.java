package filesprocessing.orders;
/**
 * All the possible filters.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public enum OrderCommand
{
	ABS("^abs" + Constants.REVERSE),
	TYPE("^type" + Constants.REVERSE),
	SIZE("^size" + Constants.REVERSE);

	private final String value;
	/**
	 * Create a new property inside the enum.
	 * @param value the inner value
	 */
	OrderCommand(final String value)
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
	 * @author Bar Melinarskiy
	 * @version 8/9/20
	 */
	public static class Constants
	{
		public static final String REVERSE = "(#REVERSE)*";
		public static final String SUFFIX_REVERSE = ".*#REVERSE";
	}
}
