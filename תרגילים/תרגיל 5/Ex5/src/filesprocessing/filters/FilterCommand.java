package filesprocessing.filters;
/**
 * All the possible commands.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public enum FilterCommand
{
	GREATER("^greater_than#" + Constants.DOUBLE_NUM + Constants.NOT),
	BETWEEN("^between#" + Constants.DOUBLE_NUM + "#" + Constants.DOUBLE_NUM + Constants.NOT),
	SMALLER("^smaller_than#" + Constants.DOUBLE_NUM + Constants.NOT),
	FILE("^file" + Constants.VALID_NAME + Constants.NOT),
	CONTAINS("^contains" + Constants.VALID_NAME + Constants.NOT),
	PREFIX("^prefix" + Constants.VALID_NAME + Constants.NOT),
	SUFFIX("^suffix" + Constants.VALID_NAME + Constants.NOT),
	WRITABLE("^writable" + Constants.BOOLEAN_VALUE + Constants.NOT),
	EXECUTABLE("^executable" + Constants.BOOLEAN_VALUE + Constants.NOT),
	HIDDEN("^hidden" + Constants.BOOLEAN_VALUE + Constants.NOT),
	ALL("^all" + Constants.NOT);

	private final String value;
	/**
	 * Create a new property inside the enum.
	 * @param value the inner value
	 */
	FilterCommand(final String value)
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
		public static final String DOUBLE_NUM = "\\+?((\\d+([.]\\d*)?)|([.]\\d+))";
		public static final String VALID_NAME = "#([a-zA-z\\d \\/\\.-_]*)#?";
												//"#([a-zA-z]|\\d| |\\/|\\.|-|_)*";
		public static final String BOOLEAN_VALUE = "#(YES|NO)#?";
		public static final String BOOLEAN_YES = "YES";
		public static final String BOOLEAN_NO = "NO";
		public static final String NOT = "(#NOT)*";
		public static final String NOT_EXIST = "(#NOT)";
	}
}
