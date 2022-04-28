package oop.ex6.ProgramProcessing.Enums;

import java.util.LinkedList;
import java.util.List;

/**
 * Enum class for parameter type: may be int, double, string, char or boolean
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public enum ParamType
{
	STRING("String", "\".*\""),
	INT("int" , "-?\\d+"),
	DOUBLE("double" , Constants.DOUBLE_NUM),
	CHAR("char", "'.'"),
	BOOLEAN("boolean", "true|false|" + Constants.DOUBLE_NUM);
	private final String type; //parameter valid type
	private final String value; //parameter valid value format
	private static final List<ParamType> paramTypes = new LinkedList<>();

	/**
	 * Create a new property inside the enum.
	 * @param value the inner value
	 */
	ParamType(final String type, final String value) {
		this.type = type;
		this.value = value;
	}
	public static List<ParamType> getParamTypes(){
		if (paramTypes.size() != 0){
			return paramTypes;
		}
		paramTypes.add(STRING);
		paramTypes.add(INT);
		paramTypes.add(DOUBLE);
		paramTypes.add(CHAR);
		paramTypes.add(BOOLEAN);
		return paramTypes;
	}
	/**
	 * Get the current type
	 * @return the parameter type
	 */
	public String getType() {
		return type;
	}
	/**
	 * Get the current type valid value format
	 * @return the parameter type value format
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Nested class of constants values reused amongst the enum values.
	 * @author Bar Melinarskiy & Moshe Bengi
	 * @version 22/9/20
	 */
	public static class Constants {
		public static final String DOUBLE_NUM = "-?(\\d+([.]\\d*)|\\d+)";
		public static final String FINAL = "final";
	}
}
