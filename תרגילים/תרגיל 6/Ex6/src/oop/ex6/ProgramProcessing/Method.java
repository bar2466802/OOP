package oop.ex6.ProgramProcessing;

import java.util.ArrayList;
/**
 * Class represent a method inside the program
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public class Method extends Scope
{
	private final ArrayList<Parameter> importParameters;
	/**
	 * create new Method
	 * @param parent the parent scope, in sJava is always the program
	 * @param parameters the parameters to add to the new scope
	 * @param startIndex the first line index of the scope
	 * @throws CompilingException when the new scope is not valid, for example
	 * the we have parameters with the same name
	 */
	public Method(Scope parent, String name, ArrayList<Parameter> parameters, int startIndex)
			throws CompilingException {
		super(parent, parameters, startIndex);
		this.name = name;
		this.importParameters = new ArrayList<>(parameters);
	}
	/**
	 * Get the method's import parameters
	 * @return the method's import parameters
	 */
	public ArrayList<Parameter> getImportParameters()
	{
		return importParameters;
	}
}
