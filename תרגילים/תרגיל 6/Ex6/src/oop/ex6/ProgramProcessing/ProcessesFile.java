package oop.ex6.ProgramProcessing;
import oop.ex6.ProgramProcessing.Enums.ParamType;
import oop.ex6.ProgramProcessing.Enums.ScopesType;
import oop.ex6.ProgramProcessing.Enums.ValidEndOfLine;
import oop.ex6.Utils.RegexUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Class for initial testing of given sJava program
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public class ProcessesFile {
	// constants
	static private final String RETURN = "return";
	static private final String ERROR_PARAMS_FORMAT = "ERROR: bad format of method import parameters";
	static private final int SCOPE_IS_ONLY_PROGRAM = 1;
	static private final int EMPTY_LINE = 0;
	// static variables
	static private Stack<Scope> scopes;
	static private Set<Scope> setOfScopes;
	static private String[] fileLines;
	// methods
	/**
	 * Running through the sJava program lines and checking it's structure.
	 * @param lines the program's lines of code to check
	 * @return a set of the program scopes
	 * @throws CompilingException whenever an error was found in the program's code
	 */
	public static Set<Scope> processRunner(String[] lines) throws CompilingException {
		//init the class variables before we start to process the program
		initializing(lines);
		//Loop through the program lines
		for(int i = 0; i < lines.length; i++)
		{
			String line = lines[i];
			//removing the user's comments because they are not relevant for the code testing
			String lineWithoutComments = line.split("//", 2)[0];
			checkLineType(lineWithoutComments, i);
		}
		//check we are only left with the program scope, otherwise we are missing } somewhere
		checkStackIsOnlyWithProgram();
		return setOfScopes;
	}
	/**
	 * Init the class variables before we start to process the program.
	 * @param lines the program's lines of code to check
	 */
	private static void initializing(String[] lines)
	{
		//initializing the class scopes' sets & global lines array
		scopes = new Stack<>();
		setOfScopes = new HashSet<>();
		fileLines = lines;
		//Create first scope of program
		Program program = new Program();
		program.setEndIndex(lines.length);
		scopes.push(program);
		setOfScopes.add(program);
	}
	/**
	 * Check line type.
	 * It can only end with ; or { or }
	 * @param line the current code line to test.
	 * @throws CompilingException of the line dose not end with a valid closing
	 *
	 */
	private static void checkLineType(String line, int index)
			throws CompilingException
	{
		//Loop through valid end of line enum properties to find the current line type
		for (ValidEndOfLine lineType : ValidEndOfLine.values())
		{
			if(RegexUtils.test(lineType.toString(), line))
			{
				saveLineInfo(line, lineType, index);
				return;
			}
		}

		throw new CompilingException("ERROR: non valid type of line");
	}
	/**
	 * Save the line's info for future usage.
	 * @param line the current line.
	 * @throws CompilingException if the line's content was not valid when retrieved
	 */
	private static void saveLineInfo(String line, ValidEndOfLine lineType, int index)
			throws CompilingException {
		switch (lineType)
		{
		case START_SCOPE:
			createNewScope(line, index);
			break;
		case END_OF_LINE:
			insertNewLine(line);
			break;
		case END_SCOPE:
			closeScope(index);
			break;
		}
	}
	/**
	 * When the line ends with { we insert a new scope to the scopes set.
	 * @param line the current line.
	 * @param index the line index in the file
	 * @throws CompilingException when the new scope is not valid
	 * meaning it's not a new function or while/if or the something else is
	 * found to be wrong with the scope opening line format
	 */
	private static void createNewScope(String line, int index)
			throws CompilingException
	{
		//remove the { before with continue
		line = line.replace("{", "").trim();
		//get the new scope type: can be method or if/while
		ScopesType scopeType = getScopeType(line);
		if (scopeType == ScopesType.METHOD) //the new scope is a method definition
		{
			createNewMethod(line, index);
		}
		else //the new scope is if or while
		{
			Scope scope = new Scope(scopes.peek(), index);
			scopes.push(scope);
			setOfScopes.add(scope);
		}
	}
	/**
	 * Create new scope for the found method declaration.
	 * @param line the current line.
	 * @param index the line index in the file
	 * @throws CompilingException when the new method is not valid
	 */
	private static void createNewMethod(String line, int index)
			throws CompilingException
	{
		//fetch the method's name and import params
		String methodName = RegexUtils.getValue(ScopesType.METHOD.toString(), line, 3);
		String paramsString = RegexUtils.getValue(ScopesType.METHOD.toString(), line, 5);
		ArrayList<Parameter> parameters = getMethodParams(paramsString);
		//Check the current father is the program
		checkStackIsOnlyWithProgram();
		//Check that there isn't already a method with the same name
		checkDuplicateMethods(methodName);
		Scope scope = new Method(scopes.peek(), methodName, parameters, index);
		scopes.push(scope);
		setOfScopes.add(scope);
	}
	/**
	 * When the line ends with { we insert a new scope to the scopes set.
	 * @throws CompilingException error if the scope stack is not filled with only the program's
	 * scope, meaning we are missing a } somewhere in the code
	 */
	private static void checkStackIsOnlyWithProgram()
			throws CompilingException
	{
		if(scopes.size() != SCOPE_IS_ONLY_PROGRAM)
		{
			throw new CompilingException("ERROR: problem with scopes");
		}
	}
	/**
	 * Check that there isn't already a method with the same name.
	 * @param methodName the new function name to test if exist
	 * @throws CompilingException error if there is indeed a method with the same
	 * name as the new method we found
	 */
	private static void checkDuplicateMethods(String methodName)
			throws CompilingException
	{
		//Loop through the current existing scopes
		for(Scope scope : scopes)
		{
			if(scope.getName().equals(methodName)) //compare names
			{
				throw new CompilingException("ERROR: duplicate method names");
			}
		}
	}
	/**
	 * Fetch the method's import parameters from the code line.
	 * @param line the current line.
	 * @throws CompilingException when the parameters are not valid
	 */
	private static ArrayList<Parameter> getMethodParams(String line)
			throws CompilingException
	{
		//array ro save the import parameters in
		line = line.trim(); //remove spaces in both ends of the line
		//check if the method dose not accept any parameters
		if (line.length() == EMPTY_LINE)
		{
			return new ArrayList<>();
		}
		//split the import definition by , to get each parameter
		String[] parametersInfo = line.split(",");
		return processMethodParameters(parametersInfo);
	}
	/**
	 * Process each import parameter of the current method.
	 * @param parametersInfo array of strings containing the import parameters
	 * @throws CompilingException when a parameter has a bad format
	 */
	private static ArrayList<Parameter> processMethodParameters(String[] parametersInfo)
			throws CompilingException
	{
		final int REGULAR_PARAM = 2;
		ArrayList<Parameter> parameters = new ArrayList<>();
		//Loop through each import parameter
		for (String parameter : parametersInfo)
		{
			//get the parameter's declaration info
			String[] parameterInfo = parameter.trim().split("\\s+");
			//Check if this parameter is final or not
			boolean isFinal = checkIfImportParamIsFinal(parameterInfo);
			//At this point the parameter must be a regular one or the final flag is on
			if (parameterInfo.length == REGULAR_PARAM || isFinal)
			{
				parameters.add(createImportParameter(parameterInfo, isFinal));
			}
			else //then we only have one word in parameter definition
			{
				throw new CompilingException(ERROR_PARAMS_FORMAT);
			}
		}

		return parameters;
	}
	/**
	 * Check if the current method's import parameter is final or not.
	 * @param parameterInfo array of the parameter parts
	 * @return  flag stating if this parameter is final or not
	 * @throws CompilingException when a parameter has a bad format
	 */
	private static boolean checkIfImportParamIsFinal(String[] parameterInfo)
	 throws CompilingException
	{
		final int PARAM_IS_FINAL = 3;
		//Check if the parameter definition has 3 words then it must be final
		if (parameterInfo.length == PARAM_IS_FINAL)
		{
			//the first word must be final then
			parameterInfo[0] = parameterInfo[0].trim();
			if (!parameterInfo[0].equals(ParamType.Constants.FINAL))
			{
				throw new CompilingException(ERROR_PARAMS_FORMAT);
			}
			return true;
		}
		return false;
	}
	/**
	 * Save the current import parameter info.
	 * @param parameterInfo array of the parameter parts
	 * @param isFinal flag stating if this parameter is final or not
	 * @return the new parameter to add to the import parameters array
	 * @throws CompilingException when a parameter has a bad format
	 */
	private static Parameter createImportParameter(String[] parameterInfo, boolean isFinal)
			throws CompilingException
	{
		//Get the parameter type & name
		String type = parameterInfo[0].trim();
		String name = parameterInfo[1].trim();
		if (isFinal) //if the parameter is final then the name and type are the 2 & 3 words
		{
			type = parameterInfo[1].trim();
			name = parameterInfo[2].trim();
		}
		ParamType paramType = Parameter.getParamType(type); //get the enum of the current param type
		//create the new Parameter
		return new Parameter(paramType, name, isFinal, false, true);
	}
	/**
	 * Get the current new scope type.
	 * @return the new scope type, can be a method or if/while
	 * @throws CompilingException when no fitting scope was found
	 */
	private static ScopesType getScopeType(String line) throws CompilingException {
		//Loop through each scope type to fetch the current one
		for (ScopesType scopesType : ScopesType.values())
		{
			if(RegexUtils.test(scopesType.toString(), line))
			{
				return scopesType;
			}
		}
		//if we reached this point then we didn't managed to find a valid scope to match the current one
		throw new CompilingException("ERROR: a non valid scope was found here: " + line);
	}
	/**
	 * Close the current scope after we found a line ending with }.
	 * @param index the current line index in the program file
	 * @throws CompilingException when the closing of the scope was not valid
	 * meaning there was no matching { or the method didn't end with return;
	 */
	private static void closeScope(int index) throws CompilingException
	{
		if(scopes.size() <= SCOPE_IS_ONLY_PROGRAM)
		{
			throw new CompilingException("ERROR: no matching { was found, no scope to close");
		}
		//close the scope and update it's last line index
		Scope scope = scopes.pop();
		scope.setEndIndex(index);
		//check if the last line inside a method is "return;"
		checkMethodEnd(index);
	}

	/**
	 * Check the method is ending with a return statement.
	 * @param index the current line index in the program file
	 * @throws CompilingException when the method didn't end with return;
	 */
	private static void checkMethodEnd(int index) throws CompilingException
	{
		//check if the last line inside a method is "return;"
		if(scopes.size() == SCOPE_IS_ONLY_PROGRAM)
		{
			String prevLine = fileLines[index - 1];
			if(!prevLine.contains(RETURN))
			{
				throw new CompilingException("ERROR: a method is not ending with return statement");
			}
		}
	}
	/**
	 * Process the line when it ends with ;
	 * for example save the variants in the current scope.
	 * @param line the current line in the program
	 * @throws CompilingException when the line has a bad format
	 */
	private static void insertNewLine(String line) throws CompilingException {
		Scope currentScope = scopes.peek();
		//Remove the ; from the end of the line
		final String SEMICOLON = ";";
		int lastIndex = line.lastIndexOf(SEMICOLON);
		line = line.substring(0, lastIndex).trim();
		//check the current scope level
		if(scopes.size() > SCOPE_IS_ONLY_PROGRAM) //we are inside a method
		{
			//check for return statement
			if(line.equals(RETURN))
			{
				return;
			}
			//check for calling of a method
			if(RegexUtils.test(ScopesType.Constants.FUNC_CALL, line))
			{
				return;
			}
		}
		//check for variants statement and save them in the current scope
		currentScope.addToParameters(line);
	}
}
