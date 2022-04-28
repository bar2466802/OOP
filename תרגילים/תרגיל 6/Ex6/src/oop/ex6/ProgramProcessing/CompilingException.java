package oop.ex6.ProgramProcessing;
/**
 * Class for exceptions related to the program processing and compiling.
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public class CompilingException extends Exception
{
	/**
	 * Create a new CompilingExceptions exception
	 * @param errorMessage the error message to throw
	 */
	public CompilingException(String errorMessage) {
		super(errorMessage);
	}
}
