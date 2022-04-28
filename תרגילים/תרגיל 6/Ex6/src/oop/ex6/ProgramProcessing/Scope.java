package oop.ex6.ProgramProcessing;

import oop.ex6.ProgramProcessing.Enums.ParamType;
import oop.ex6.Utils.RegexUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
/**
 * Scope inside the program may be the program itself, a method or if/while
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public class Scope implements Comparable<Scope> {
	private static final String FINAL = "final";
	private final ArrayList<Parameter> params;
	protected String name = "";
	private final Scope outerScope;
	private int startIndex;
	private int endIndex;
	/**
	 * create new default scope with no father scope and empty array of parameters.
	 */
	public Scope()
	{
		outerScope = null;
		params = new ArrayList<>();
	}
	/**
	 * create new scope
	 * @param outerScope the parent scope
	 * @param parameters the parameters to add to the new scope
	 * @param startIndex the first line index of the scope
	 * @throws CompilingException when the new scope is not valid, for example
	 * the we have parameters with the same name
	 */
	public Scope(Scope outerScope, ArrayList<Parameter> parameters, int startIndex)
			throws CompilingException
	{
		this.outerScope = outerScope;
		checkDuplicateParams(parameters);
		params = new ArrayList<>(parameters);
		this.startIndex = startIndex;
	}
	/**
	 * Check the given scope's parameters are valid
	 * @param parameters the parameters to add to the new scope
	 * @throws CompilingException when we have parameters with the same name
	 */
	private void checkDuplicateParams(ArrayList<Parameter> parameters)
			throws CompilingException
	{
		Set<Parameter> set = new HashSet<>(parameters);

		if(set.size() < parameters.size())
		{
			throw new CompilingException("ERROR: duplicate variant names");
		}
	}
	/**
	 * create new scope
	 * @param outerScope the parent scope
	 * @param startIndex the first line index of the scope
	 */
	public Scope(Scope outerScope, int startIndex)
	{
		this.outerScope = outerScope;
		params = new ArrayList<>();
		this.startIndex = startIndex;
	}
	/**
	 * Get the scope's parameters
	 * @return the scope's parameters' array
	 */
	public ArrayList<Parameter> getParams()
	{
		return params;
	}
	/**
	 * Set the scope's end index
	 * @param endIndex the end line index of the current scope
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	/**
	 * Get the scope's line index
	 * @return the end line index of the current scope
	 */
	public int getEndIndex() {
		return endIndex;
	}
	/**
	 * Get the scope's start index
	 * @return the start line index of the current scope
	 */
	public int getStartIndex() {
		return startIndex;
	}
	/**
	 * Get the scope's parent
	 * @return the scope's parent
	 */
	public Scope getOuterScope(){
		return outerScope;
	}
	/**
	 * Add a single param to the scope's parameters array
	 * @param parameter the param to add
	 */
	public void addToParameters(Parameter parameter)
			throws CompilingException
	{
		params.add(parameter);
		checkDuplicateParams(params);
	}
	/**
	 * Get the scope's name
	 * @return the scope's name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Add to the scope's parameters array from a line in the program file
	 * @param line the current line to process
	 */
	public void addToParameters(String line)
			throws CompilingException
	{
		//check if param is final
		boolean isFinal = checkIfFinal(line);
		if(isFinal) {
			//remove the final
			line = line.replace(FINAL, "").trim();
		}
		boolean isGlobal = this.getOuterScope() == null; //check if param is global
		//Get the first word of the declaration
		String type = line.split("\\s+", 2)[0];
		ParamType paramType;
		try {
			//Check if the first word is a type
			paramType = Parameter.getParamType(type);
		}
		catch(CompilingException e)
		{
			checkIfParamAssignment(line);
			return;
		}

		String rightSide = line.split("\\s+", 2)[1];
		String[] lineParts = rightSide.split(",", -1);
		manageLineParts(lineParts, paramType, isFinal, isGlobal);
		checkDuplicateParams(params);
	}

	/**
	 * manage the different parts of the line (split by ",")
	 * @param lineParts the parts of the line
	 * @param paramType the type of a parameter
	 * @param isFinal a flag - whether the param is final
	 * @param isGlobal a flag - whether the param is global
	 * @throws CompilingException if one of the parts is not valid, throw CompilingException
	 */
	private void manageLineParts(String[] lineParts, ParamType paramType, boolean isFinal, boolean isGlobal)
			throws CompilingException {
		for(String part : lineParts) {
			String[] paramInfoParts = part.trim().split("=", 2);
			String name = paramInfoParts[0].trim();
			if(part.contains("=")) {
				String value = paramInfoParts[1].trim();
				if(!RegexUtils.test(paramType.getValue(), value)) {
					try {
						Parameter.checkName(value);
						Parameter valueParam = getParamByName(value);
						if(valueParam != null && valueParam.getIsInitial()) {
							params.add(new Parameter(paramType, name, value, isFinal, isGlobal));
						}
						else {
							params.add(new Parameter(paramType, name, isFinal, isGlobal, false));
						}
					}
					catch (CompilingException e) {
						throw new CompilingException("ERROR: bad value of " + paramType.getType() + " param");
					}
				}
				else {
					params.add(new Parameter(paramType, name, value, isFinal, isGlobal));
				}
			}
			else {
				if(isFinal) {
					throw new CompilingException("ERROR: final parameter without initial value");
				}
				params.add(new Parameter(paramType, name, false, isGlobal, false));
			}
		}
	}

	/**
	 * Check the parameter assignment
	 * @param line the current line to process
	 */
	public void checkIfParamAssignment(String line)
			throws CompilingException
	{
		//the first word wasn't a valid parameter type, split line by commas
		String[] lineParts = line.split(",", -1);
		//loop through the line parts after we split by commas
		for(String part : lineParts)
		{
			processLinePartInAssignment(part);
		}
	}

	/**
	 * Check the parameter assignment
	 * @param part the current line part to process
	 */
	public void processLinePartInAssignment(String part)
			throws CompilingException
	{
		part = part.trim();
		//get the part first word
		String name = part.split("\\s+", 2)[0];
		//check if this parameter already exist
		Parameter parameter = getParamByName(name);
		//if this is not a valid name of parameter and is not assignment after all
		if(parameter == null || !part.contains("=")) {
			throw new CompilingException("ERROR: " + name + " is not a valid type of param");
		}
		//if this global param and we are currently in the program scope
		if(parameter.getIsGlobal() && this.getOuterScope() == null)
		{
			final String ERROR_BAD_VALUE = "ERROR: bad value of global param " + parameter.getName();
			//split the part by the =
			String[] paramInfoParts = part.split("=", 2);
			String value = paramInfoParts[1].trim();
			//check if the value is a const like b = 5 or another param name like b = a
			if(!RegexUtils.test(parameter.getType().getValue(), value)) {
				try {
					//if we reached this point then it must be in the form of b = a
					Parameter.checkName(value);
					//check if this variant indeed exist and is with a value
					Parameter valueParam = getParamByName(value);
					if (valueParam == null || valueParam.getIsInitial())
					{
						throw new CompilingException(ERROR_BAD_VALUE);
					}
				}
				catch (CompilingException error) {
					throw new CompilingException(ERROR_BAD_VALUE);
				}
			}
			//if we reached this point then the assigned value is valid
			parameter.setIsInitial(true);
		}
	}

	/**
	 * checks if a line start with final
	 * @param line the line
	 * @return boolean - is start with final
	 */
	private boolean checkIfFinal(String line)
	{
		return line.startsWith(FINAL);
	}

	/**
	 * Get a parameter in the scope by it's name
	 * checks the parents scopes as well
	 * @return the parameter if found, null otherwise
	 */
	public Parameter getParamByName(String name)
	{
		Scope scope = this;
		Parameter param;
		while (scope != null)
		{
			param = getParamInScope(scope, name);
			if(param != null)
			{
				return param;
			}
			scope = scope.getOuterScope();
		}
		return null;
	}
	/**
	 * Get a parameter in the given scope by it's name
	 * @return the parameter if found, null otherwise
	 */
	private static Parameter getParamInScope(Scope currentScope, String name)
	{
		ArrayList<Parameter> parameters =  currentScope.getParams();
		return parameters.stream()
				.filter(p -> name.equals(p.getName()))
				.findAny()
				.orElse(null);
	}

	/**
	 * override of the compareTo method
	 * @param other another scope
	 * @return the compare of the sizes of the scopes
	 */
	@Override
	public int compareTo(Scope other)
	{
		return Integer.compare(getEndIndex() - getStartIndex(),
							   other.getEndIndex() - other.getStartIndex());
	}
}
