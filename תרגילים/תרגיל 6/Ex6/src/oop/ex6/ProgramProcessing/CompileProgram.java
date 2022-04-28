package oop.ex6.ProgramProcessing;
import oop.ex6.ProgramProcessing.Enums.ParamType;
import oop.ex6.ProgramProcessing.Enums.ScopesType;
import oop.ex6.Utils.RegexUtils;

import java.util.*;

/**
 * the class that manage the compile of the program - the second test of the program
 */
public class CompileProgram {

	static private List<Scope> scopes;

	/**
	 * the compile method - the main method of the class
	 * @param scopeSet set of the scopes from the first test
	 * @param lines the lines of the codes
	 * @throws CompilingException if the compile wont work, throws the CompilingException to the main class
	 */
	static public void compile(Set<Scope> scopeSet, String[] lines)
			throws CompilingException
	{
		scopes = new ArrayList<>(scopeSet);
		Collections.sort(scopes);
		for(int i = 0; i < lines.length; i++)
		{
			String line = lines[i];
			String lineWithoutComments = line.split("//", 2)[0];
			lineWithoutComments = lineWithoutComments.replace(";", "");
			Scope currentScope = getScopeByLineIndex(i);
			checkLine(currentScope, lineWithoutComments.trim());
		}
	}

	/**
	 * checker for every line in the file
	 * @param currentScope the scope we are in now
	 * @param line the line
	 * @throws CompilingException if the line is not valid, throw CompilingException
	 */
	private static void checkLine(Scope currentScope, String line)
			throws CompilingException
	{
		String scopeType;
		//method call
		if(RegexUtils.test(ScopesType.Constants.FUNC_CALL, line))
		{
			scopeType = ScopesType.Constants.FUNC_CALL;
			String methodName = RegexUtils.getValue(scopeType, line, 1);
			Method method = getMethodByName(methodName);

			String paramsString = RegexUtils.getValue(scopeType, line, 3);
			ArrayList<Parameter> actualParam =  getExportParams(currentScope, paramsString);
			checkValidCallOfMethod(actualParam, method);
		}
		//if / while
		if(RegexUtils.test(ScopesType.IF_OR_WHILE.toString(), line))
		{
			scopeType = ScopesType.IF_OR_WHILE.toString();
			String condition = RegexUtils.getValue(scopeType, line, 5);
			checkBooleanCondition(currentScope, condition);
		}
		//variants
		if(line.contains("="))
		{
			checkVariantAssigment(currentScope, line);
		}
	}

	/**
	 * checker for a line of an assigment of variant.
	 * @param currentScope the current scope
	 * @param line the line to check
	 * @throws CompilingException if the line is not valid, throw CompilingException
	 */
	private static void checkVariantAssigment(Scope currentScope, String line) throws CompilingException {
		line = line.replace("final", "").trim();
		String[] sides = line.split("\\s+", 2);
		String firstWord = sides[0].trim();
		String[] parts = sides[1].split(",", -1);
		ParamType paramType;
		Parameter param;
		try {
			paramType = Parameter.getParamType(firstWord);
		}
		catch (CompilingException e) {
			param = currentScope.getParamByName(firstWord);
			if(param == null)
			{
				throw new CompilingException("ERROR: non existing variant");
			}
			if(param.getIsFinal())
			{
				throw new CompilingException("ERROR: trying to change final variant");
			}
			paramType = param.getType();
			parts = line.split(",", -1);
		}
		manageParts(parts, currentScope, paramType);
	}

	/**
	 * checker for the part of the line - parted by ","
	 * @param parts the parts of the line
	 * @param currentScope the current scope
	 * @param paramType the type of the parameter
	 * @throws CompilingException if one of the parts of the line is not valid, throw CompilingException
	 */
	private static void manageParts(String[] parts, Scope currentScope, ParamType paramType)
			throws CompilingException {
		Parameter param;
		for(String part : parts) {
			part = part.trim();
			if(part.contains("=")) {
				String[] paramInfoParts = part.split("=", 2);
				String value = paramInfoParts[1].trim();
				String name = paramInfoParts[0].trim();
				Parameter currentParameter = currentScope.getParamByName(name);
				param = currentScope.getParamByName(value);
				ParamType assignedType;  //check if the value is an existing variant with value
				if(param != null && !param.getIsInitial())
				{
					throw new CompilingException("ERROR: non valid assigment to variant with uninitialized "
												 + "param");
				}
				else if(param != null){ //get current type of right side variant
					checkGlobalAssigment(param, currentParameter, currentScope);
					assignedType = param.getType();
				}
				else { //get current type of value
					assignedType =  getParamTypeByValue(currentScope, value);
				}
				if(assignedType == null || Parameter.notEqualsType(paramType, assignedType)) {
					throw new CompilingException("ERROR: non valid assigment to variant");
				}
				if(!currentParameter.getIsInitial()) {
					manageNotInit(currentParameter, currentScope);
				}
			}
		}
	}

	/**
	 * manage assigment for a not init variable
	 * @param currentParameter the not init parameter
	 * @param currentScope the current scope
	 * @throws CompilingException if the param is not created well, throw CompilingException
	 */
	private static void manageNotInit(Parameter currentParameter, Scope currentScope)
			throws CompilingException {
		if(currentParameter.getIsGlobal() && currentScope.getOuterScope() != null) {
			Parameter copyParam = new Parameter(currentParameter);
			copyParam.setIsInitial(true);
			currentScope.addToParameters(copyParam);
		}
		else {
			currentParameter.setIsInitial(true);
		}
	}

	/**
	 * checks if boolean condition is valid
	 * @param currentScope the current scope
	 * @param condition the condition to check
	 * @throws CompilingException if the condition is not valid , throw CompilingException
	 */
	private static void checkBooleanCondition(Scope currentScope, String condition)
			throws CompilingException
	{
		condition = condition.trim();
		if(condition.length() == 0) {
			throw new CompilingException("ERROR: Non valid boolean condition");
		}
		String[] conditionParts = condition.split("&&|\\|\\|", -1);
		for(String conditionPart : conditionParts) {
			String value = conditionPart.trim();
			Parameter parameter = currentScope.getParamByName(value);
			ParamType paramType;
			if(parameter == null) {
				paramType = getParamTypeByValue(currentScope, value);
			}
			else {
				paramType = parameter.getType();
				if(!parameter.getIsInitial())
				{
					throw new CompilingException("ERROR: Non valid boolean condition, variant with no value");
				}
			}

			if(paramType != ParamType.BOOLEAN && paramType != ParamType.DOUBLE && paramType != ParamType.INT)
			{
					throw new CompilingException("ERROR: Non valid boolean condition");
			}
		}
	}

	/**
	 * manage the assigment of global variable
	 * @param dest the variable to assign to
	 * @param source the variable that assign from
	 * @param currentScope the current scope
	 * @throws CompilingException if the assigment is not valid, throw CompilingException
	 */
	private static void checkGlobalAssigment(Parameter dest, Parameter source, Scope currentScope)
			throws CompilingException
	{
		if(dest.getIsGlobal() && source.getIsGlobal())
		{
			ArrayList<Parameter> param = currentScope.getParams();
			int indexS = param.indexOf(source);
			int indexD = param.indexOf(dest);
			if(indexD > indexS)
			{
				throw new CompilingException("ERROR: non valid assigment to variant with predefined global " +
											 "variant");
			}
		}
	}

	/**
	 * gets the parameters from the function stamp
	 * @param currentScope the current scope(the method)
	 * @param paramsString the string that would be parted to parameters
	 * @return a list of parameters
	 * @throws CompilingException if the stamp is not valid, throws CompilingException
	 */
	private static ArrayList<Parameter> getExportParams(Scope currentScope, String paramsString)
			throws CompilingException {
		ArrayList<Parameter> parameters = new ArrayList<>();
		paramsString = paramsString.trim();
		if (paramsString.length() == 0){
			return parameters;
		}
		String[] parametersInfo = paramsString.split(",");
		int index = 1;
		for (String parameter : parametersInfo)
		{
			parameter = parameter.trim();
			ParamType paramType;
			Parameter param = currentScope.getParamByName(parameter);
			if(param != null)
			{
				if(!param.getIsInitial())
				{
					throw new CompilingException("ERROR: passing variant without value to method");
				}
				parameters.add(param);
			}
			else
			{
				paramType = getParamTypeByValue(currentScope, parameter.trim());
				parameters.add(new Parameter(paramType, "temp" + index, true, false, true));
			}

		}

		return parameters;
	}

	/**
	 * gets a type of parameter by its value - name or argument
	 * @param currentScope the current scope
	 * @param value the argument
	 * @return the type
	 * @throws CompilingException if there is no match for any type, throw CompilingException
	 */
	private static ParamType getParamTypeByValue(Scope currentScope, String value)
			throws CompilingException
	{
		//check if this is a variant name
		value = value.replace(";", "").trim();
		Parameter parameter = currentScope.getParamByName(value);
		if(parameter != null) {
			return parameter.getType();
		}
		//must be const
		List<ParamType> paramTypes = ParamType.getParamTypes();
		for (ParamType paramType : paramTypes){
			if(RegexUtils.test(paramType.getValue(), value)) {
				return paramType;
		}

		}
		throw new CompilingException("ERROR: " + value + " is not a valid value for parameter");
	}

	/**
	 * checker for a call to another method
	 * @param actualParam the params that used
	 * @param method the original method
	 * @throws CompilingException throw CompilingException if the call is not valid
	 */
	private static void checkValidCallOfMethod(ArrayList<Parameter> actualParam,
											   Method method)
			throws CompilingException
	{
		ArrayList<Parameter> importParams = method.getImportParameters();
		if(actualParam.size() != importParams.size())
		{
			throw new CompilingException("ERROR: Non valid call of method: " + method.getName());
		}
		for(int i = 0; i < actualParam.size(); i++)
		{
			if(Parameter.notEqualsType(importParams.get(i).getType(), actualParam.get(i).getType()))
			{
				throw new CompilingException("ERROR: Non valid call of method: " + method.getName());
			}
		}

	}

	/**
	 * getter for the scope by the index of the line
	 * @param index the index
	 * @return the current scope
	 */
	static public Scope getScopeByLineIndex(int index)
	{
		for(Scope scope : scopes)
		{
			if(index >= scope.getStartIndex() && index <= scope.getEndIndex())
			{
				return scope;
			}
		}
		return null;
	}

	/**
	 * getter for a method by its name
	 * @param name the name
	 * @return the method
	 * @throws CompilingException if the method is not exist, throw CompilingException
	 */
	static public Method getMethodByName(String name)
			throws CompilingException
	{
		for(Scope scope : scopes)
		{
			if(scope.getName().equals(name))
			{
				return (Method)scope;
			}
		}
		throw new CompilingException("ERROR: calling non existing method");
	}

}
