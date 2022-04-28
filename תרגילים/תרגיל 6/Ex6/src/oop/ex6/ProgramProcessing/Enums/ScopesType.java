package oop.ex6.ProgramProcessing.Enums;

import oop.ex6.ProgramProcessing.Enums.ParamType;

/**
 * Enum class for scope types: may be method or if/while
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public enum ScopesType
{
	IF_OR_WHILE("^" + Constants.SPACE + "(while|if)" + Constants.SPACE + "\\(" +
				Constants.SPACE + Constants.MULTI_CONDITION + Constants.SPACE +  "\\)"
		+ Constants.SPACE + "\\{?"),
	METHOD("(void)" + Constants.SPACE + Constants.FUNC_CALL);

	private final String value;
	/**
	 * Create a new property inside the enum.
	 * @param value the inner value
	 */
	ScopesType(final String value)
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
		public static final String SPACE = "(\\s)*";
		public static final String FUNC_NAME = "([a-zA-Z][a-zA-Z_0-9]*)";
		public static final String FUNC_CALL = FUNC_NAME + Constants.SPACE
											   + "\\((.*)\\)" + Constants.SPACE + ";?";
		public static final String BOOLEAN_VALUE = "(true|false|(_(\\w+)|[a-zA-Z](\\w*))|"
												   + ParamType.DOUBLE.getValue() + "|"
		                                           + ParamType.INT.getValue() + ")";
		public static final String MULTI_CONDITION = "(" + BOOLEAN_VALUE + Constants.SPACE +
													 "((&&|\\|\\|)" + Constants.SPACE + BOOLEAN_VALUE
													 + Constants.SPACE + ")*)";}
}
