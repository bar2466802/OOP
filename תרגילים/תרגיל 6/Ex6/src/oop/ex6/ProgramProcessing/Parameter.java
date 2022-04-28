package oop.ex6.ProgramProcessing;

import oop.ex6.ProgramProcessing.Enums.ParamType;
import oop.ex6.Utils.RegexUtils;

/**
 * Class represent a parameter inside the program
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public class Parameter
{
	//constants
	private static final String validName = "^_(\\w+)|^[a-zA-Z](\\w*)";
	//instance properties
	private final ParamType type;
	private final String name;
	private final Boolean isFinal;
	private final Boolean isGlobal;
	private String value;
	private Boolean isInitial;
	/**
	 * create new Parameter with now initial value
	 * @param type the parameter's type
	 * @param name the parameter's name
	 * @param isFinal flag to state if the parameter is final
	 * @param isGlobal flag to state if the parameter is global
	 * @throws CompilingException when the parameter's info is not valid, for example
	 * it's name is no valid or the value doesn't fit the current type
	 */
	Parameter(ParamType type, String name, boolean isFinal, boolean isGlobal, boolean isInitial)
			throws CompilingException {
		this.type = type;
		checkName(name);
		this.name = name;
		this.isFinal = isFinal;
		this.isGlobal = isGlobal;
		this.isInitial = isInitial;
	}
	/**
	 * create new Parameter
	 * @param type the parameter's type
	 * @param name the parameter's name
	 * @param value the parameter value
	 * @param isFinal flag to state if the parameter is final
	 * @param isGlobal flag to state if the parameter is global
	 * @throws CompilingException when the parameter's info is not valid, for example
	 * it's name is no valid or the value doesn't fit the current type
	 */
	Parameter(ParamType type, String name, String value, boolean isFinal, boolean isGlobal)
			throws CompilingException
	{
		this.type = type;
		checkName(name); //check the parameter's name
		this.name = name;
		this.value = value;
		checkParamValue(); //check the parameter's value
		isInitial = true;
		this.isFinal = isFinal;
		this.isGlobal = isGlobal;
	}
	/**
	 * Copy constructor
	 * @param parameter the source parameter to copy from
	 */
	Parameter(Parameter parameter)
	{
		this.type = parameter.type;
		this.name = parameter.name;
		this.isFinal = parameter.isFinal;
		this.isGlobal = parameter.isGlobal;
		this.value = parameter.value;
		isInitial = parameter.isInitial;
	}
	/**
	 * Check the parameter's value according to it's type
	 * @throws CompilingException when the value doesn't fit the current type
	 */
	private void checkParamValue()
			throws CompilingException
	{
		if(!RegexUtils.test(type.getValue(), value))
		{
			try
			{
				checkName(value);
			}
			catch (CompilingException e)
			{
				throw new CompilingException("ERROR: bad value of param " + type.getType());
			}

		}
	}
	/**
	 * Get the param type enum property by the string type
	 * @param type the type to fetch
	 * @throws CompilingException when the given type is not valid
	 */
	public static ParamType getParamType(String type)
			throws CompilingException
	{
		//loop through the predefined types
		for (ParamType paramType : ParamType.values())
		{
			if(paramType.getType().equals(type))
			{
				return paramType;
			}
		}
		throw new CompilingException("ERROR: " + type + " is not a valid type of param");
	}
	/**
	 * Get the param's type
	 * @return the param's type
	 */
	public ParamType getType()
	{
		return type;
	}
	/**
	 * Get the param's name
	 * @return the param's name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Get the param's final flag
	 * @return the param's final flag
	 */
	public Boolean getIsFinal()
	{
		return isFinal;
	}
	/**
	 * Get the param's initialize flag
	 * @return the param's initialize flag
	 */
	public Boolean getIsInitial()
	{
		return isInitial;
	}
	/**
	 * Get the param's global flag
	 * @return the param's global flag
	 */
	public Boolean getIsGlobal()
	{
		return isGlobal;
	}
	/**
	 * Set the param's initialize flag
	 * @param isInitial the param's initialize flag
	 */
	public void setIsInitial(Boolean isInitial)
	{
		this.isInitial = isInitial;
	}
	/**
	 * Check the param's name
	 * @throws CompilingException if the given name is not valid
	 */
	public static void checkName(String name)
			throws CompilingException
	{
		if (!RegexUtils.test(validName, name)){
			throw new CompilingException("ERROR: bad param name");
		}
	}
	/**
	 * Check if source paramType can accept the second one
	 * @return true is the assigment is not valid, false otherwise
	 */
	public static boolean notEqualsType(ParamType source, ParamType assigned)
	{
		if(source ==  null || assigned == null) return true;
		//check type
		switch (source) {
		case INT:
		case CHAR:
		case STRING:
			return !source.equals(assigned);
		case BOOLEAN:
			return !source.equals(assigned) && !assigned.equals(ParamType.INT)
					&& !assigned.equals(ParamType.DOUBLE);
		case DOUBLE:
			return !source.equals(assigned) && !assigned.equals(ParamType.INT);

		}
		return true;
	}

	/**
	 * over ride the hashcode function
	 * @return the hashcode of the name
	 */
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

	/**
	 * override the equals method
	 * @param other another parameter
	 * @return true if the names equals
	 */
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof  Parameter)) {
			return false;
		}
		Parameter p = (Parameter)other;
		return this.name.equals(p.getName());
	}
}
