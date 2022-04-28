package filesprocessing;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A utilities class for file manipulation.
 *
 */
public class FileUtils
{
	/**
	 * Reads a text file (such that each line contains a single word),
	 * and returns a string array of its lines.
	 * @param fileName Text file to read.
	 * @return Array with the file's content (returns null if the IOException occurred).
	 */
	public static String[] file2array(String fileName)
	{
		// A list to hold the file's content
		List<String> fileContent = new ArrayList<String>();

		// Reader object for reading the file
		BufferedReader reader = null;

		try
		{
			// Open a reader
			reader = new BufferedReader(new FileReader(fileName));

			// Read the first line
			String line = reader.readLine();

			// Go over the rest of the file
			while (line != null)
			{

				// Add the line to the list
				fileContent.add(line);

				// Read the next line
				line = reader.readLine();
			}

		} catch (FileNotFoundException e)
		{
			System.err.println("ERROR: The file: " + fileName + " is not found.");
			return null;
		} catch (IOException e)
		{
			System.err.println("ERROR: An IO error occurred.");
			return null;
		} finally
		{
			// Try to close the file
			try
			{
				if (reader != null)
				{
					reader.close();
				}
				else
				{
					return null;
				}

			}
			catch (IOException e)
			{
				System.err.println("ERROR: Could not close the file " + fileName + ".");
			}
		}

		// Convert the list to an array and return the array
		String[] result = new String[fileContent.size()];
		fileContent.toArray(result);
		return result;
	}
	/**
	 * Get the file size
	 * @param file file to check.
	 * @return file size.
	 */
	protected static double getFileSizeKiloBytes (File file)
	{
		final int CONVERT_TO_KB = 1024;
		return (double) file.length() / CONVERT_TO_KB;
	}
	/**
	 * Check if the given file is hidden or not
	 * @param file file to check.
	 * @return true if the file is indeed hidden, false otherwise.
	 */
	protected static Boolean checkIfHidden(File file)
	{
		if (file != null && (file.isHidden() || file.getName().startsWith(".")))
		{
			return true;
		}
		return false;
	}

	/**
	 * Check if the given file is executable or not
	 * @param file file to check.
	 * @return true if the file is indeed executable, false otherwise.
	 */
	protected static Boolean checkIfExecutable(File file)
	{
		if (file != null && file.canExecute())
		{
			return true;
		}
		return false;
	}

	/**
	 * Check if the given file is writable or not
	 * @param file file to check.
	 * @return true if the file is indeed writable, false otherwise.
	 */
	protected static Boolean checkIfWritable(File file)
	{
		if (file != null && file.canWrite())
		{
			return true;
		}
		return false;
	}

	/**
	 * Get the given file name
	 * @param file file to check.
	 * @return file's name.
	 */
	protected static String getName(File file)
	{
		return file.getName();
	}

	/**
	 * Get the given file abs name
	 * @param file file to check.
	 * @return file's abs name.
	 */
	protected static String getAbsName(File file)
	{
		return file.getAbsolutePath();
	}
	/**
	 * Get a list of all files inside the given folder
	 * @param folderPath folder to check.
	 * @return list of files inside the given folder.
	 */
	protected static File[] getAllFilesInFolder(String folderPath)
	{
		// try-catch block to handle exceptions
		try
		{

			// Create a file object
			File directory = new File(folderPath);

			//First we create a FileFilter, get only files and no directories
			FileFilter filter = new FileFilter()
			{
				@Override
				public boolean accept(File f)
				{
					return Files.isRegularFile(f.toPath());
				}
			};
			// Get all  the files present in the given directory
			File[] files = directory.listFiles(filter);
			if(files != null)
			{
				return files;
			}
		}
		catch (Exception e)
		{
			System.err.println("ERROR: Could not retrieve files from folder " + folderPath + ".");
		}
		//if we reached this point then we didn't managed to get the files from the folder
		System.err.println("ERROR: Could not retrieve files from folder " + folderPath + ".");
		return null;
	}

	/**
	 * Get the given file type
	 * @param file file to check.
	 * @return file's type.
	 */
	public static String getType(File file)
	{
		String type = "";
		String name = file.getName();
		int i = name.lastIndexOf('.');
		if (i > 0)
		{
			type = name.substring(i + 1);
		}
		return type;
	}
}