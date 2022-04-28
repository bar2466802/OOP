package oop.ex6.main;

import oop.ex6.ProgramProcessing.CompileProgram;
import oop.ex6.ProgramProcessing.CompilingException;
import oop.ex6.ProgramProcessing.ProcessesFile;
import oop.ex6.ProgramProcessing.Scope;
import oop.ex6.Utils.FileUtils;
import java.util.Set;
/**
 * Main class to fetch the user's args and run the program testing
 * @author Bar Melinarskiy & Moshe Bengi
 * @version 22/9/20
 */
public class Sjavac
{
	/**
	 * main function, get the arguments from the user & Runs the program processing.
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		try {
			final int NUM_OF_ARGS = 1;
			//check program usage
			if(args.length == NUM_OF_ARGS)
			{
				final String path = args[0];
				//fetch the sjava file lines
				String[] lines = FileUtils.file2array(path);
				if(lines != null){ //check we retrieved valid lines
					//initial testing of program
					Set<Scope> scopeSet = ProcessesFile.processRunner(lines);
					//deep testing of program
					CompileProgram.compile(scopeSet, lines);
				}
				else
				{
					//something went wrong when fetching the file's lines
					System.out.println(2);
					return;
				}
			}
		}
		catch (CompilingException e)
		{ //something is wrong with the program
			System.err.println(e.getMessage());
			System.out.println(1);
			return;
		}
		catch (Exception e)
		{ //something went wrong while trying to compile the program probably an I/O exception
			System.err.println(e.getMessage());
			System.out.println(2);
			return;
		}

		System.out.println(0);
	}
}
