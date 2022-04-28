package filesprocessing;
import java.util.ArrayList;
/**
 * The main class running the files processing according to user's input.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class DirectoryProcessor
{
	// constants
	private static final int ERROR_EXIT_CODE = -1;
	// instance variables
	private static ArrayList<FileInfo> filesInfo = new ArrayList<FileInfo>();
	private static ArrayList<ProcessOperation> processOperations = new ArrayList<ProcessOperation>();;
	/**
	 * Creates a new game.
	 *
	 * @param args the command line arguments.
	 * @throws DirectoryProcessingExceptions.EmptyDirectory
	 * @throws DirectoryProcessingExceptions.BadCommandsFileException
	 * @throws DirectoryProcessingExceptions.EmptyFileException
	 * @throws NullPointerException
	 * @throws UnsupportedOperationException
	 */
	public DirectoryProcessor(String[] args)
		throws DirectoryProcessingExceptions.EmptyDirectory,
			   DirectoryProcessingExceptions.BadCommandsFileException,
			   DirectoryProcessingExceptions.EmptyFileException,
			   NullPointerException,
			   UnsupportedOperationException
	{
		final int MIN_ARGS_COUNT = 2;
		final int SOURCE_DIRECTORY_INDEX = 0;
		final int COMMANDS_INDEX = 1;
		if(args.length == MIN_ARGS_COUNT)
		{
			String sourceDirectoryPath = args[SOURCE_DIRECTORY_INDEX];
			String commandsFilePath = args[COMMANDS_INDEX];
			filesInfo = FileInfoFactory.createFilesInfoArray(sourceDirectoryPath);
			processOperations = ProcessOperationFactory.createProcessingOperations(commandsFilePath);
		}
		else
		{
			printUsageAndExit();
		}
	}
	/**
	 * Prints a usage message and exit.
	 * @throws UnsupportedOperationException
	 */
	private static void printUsageAndExit()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("ERROR: usage error, you must enter 2 valid paths");
	}
	/**
	 * Runs the files processing.
	 */
	private void process()
	{
		for(ProcessOperation process : processOperations)
		{
			process.run(filesInfo);
		}
	}

	/**
	 * main function, get the arguments from the user & Runs the files processing.
	 *
	 * @param args command line arguments.
	 */
	public static void main(String[] args)
	{
		try
		{
			DirectoryProcessor filesProcessing = new DirectoryProcessor(args);
			filesProcessing.process();
		}
		catch(DirectoryProcessingExceptions.EmptyDirectory | UnsupportedOperationException |
				DirectoryProcessingExceptions.BadCommandsFileException e)
		{
			System.err.println(e.getMessage());
			return;
		}
		catch(DirectoryProcessingExceptions.EmptyFileException | NullPointerException e)
		{
			return;
		}
	}
}
