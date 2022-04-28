package filesprocessing;
import filesprocessing.filters.FilterCommand;
import filesprocessing.orders.OrderCommand;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

/**
 * Class for retrieving the desired processing tasks from the commands file.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class ProcessOperationFactory
{
	private static boolean isSectionTitleNow = true;
	// constants
	static private final String BAD_COMMANDS_FORMAT = "ERROR: Bad format of Commands File";
	static private final String WARNING_IN_LINE = "Warning in line ";
	/**
	 * Get an array of all files' info inside the given folder
	 * @param commandsFilePath command file path to check.
	 * @return an array of the processing operations
	 * @throws DirectoryProcessingExceptions.EmptyFileException if the commands file is empty
	 * @throws DirectoryProcessingExceptions.BadCommandsFileException if the commands file
	 * is not in the right format
	 */
	public static ArrayList<ProcessOperation> createProcessingOperations(String commandsFilePath)
			throws DirectoryProcessingExceptions.EmptyFileException,
				   DirectoryProcessingExceptions.BadCommandsFileException
	{
		String[] lines = FileUtils.file2array(commandsFilePath);
		if(lines != null)
		{
			//Check if the file is empty
			checkIfCommandsEmpty(lines);
			return processLines(lines);
		}
		throw new DirectoryProcessingExceptions.BadCommandsFileException(BAD_COMMANDS_FORMAT);
	}
	/**
	 * Process the given lines into an array of process operations
	 * @param lines lines from commands file to process.
	 * @return an array of the processing operations
	 * @throws DirectoryProcessingExceptions.BadCommandsFileException if the commands file is not valid
	 */
	private static ArrayList<ProcessOperation> processLines(String[] lines)
			throws DirectoryProcessingExceptions.BadCommandsFileException
	{
		ArrayList<ProcessOperation> processOperations = new ArrayList<ProcessOperation>();
		int numberOfLines = lines.length;
		boolean isFilterSectionNow = true;
		isSectionTitleNow = true;
		for(int i = 0; i < numberOfLines; i++)
		{
			if(isSectionTitleNow)
			{
				isSectionTitleNow = false;
				checkSectionTitle(lines[i], isFilterSectionNow);
			}
			else
			{
				isSectionTitleNow = true;
				checkSectionContent(lines[i], i, isFilterSectionNow, processOperations);
				isFilterSectionNow = !isFilterSectionNow;

			}
		}

		if(!isSectionTitleNow && isFilterSectionNow)
		{
			throw new DirectoryProcessingExceptions.BadCommandsFileException(BAD_COMMANDS_FORMAT);
		}

		return processOperations;
	}
	/**
	 * Check that the file is not empty and have enough lines
	 * @param lines lines from file to check.
	 * @throws DirectoryProcessingExceptions.EmptyFileException if the
	 * section title is not valid
	 * @throws DirectoryProcessingExceptions.BadCommandsFileException if there isn't
	 * enough lines in the file
	 */
	private static void checkIfCommandsEmpty(String[] lines)
			throws DirectoryProcessingExceptions.EmptyFileException,
				   DirectoryProcessingExceptions.BadCommandsFileException
	{
		final int NOT_LINES = 0;
		final int NOT_ENOUGH_LINES = 2;
		int numberOfLines = lines.length;
		//Check if the file is empty
		if(numberOfLines == NOT_LINES)
		{
			throw new DirectoryProcessingExceptions.EmptyFileException("Command file is empty");
		}
		else if(numberOfLines <= NOT_ENOUGH_LINES)
		{
			throw new DirectoryProcessingExceptions.BadCommandsFileException(BAD_COMMANDS_FORMAT);
		}
	}

	/**
	 * Check that the order/filter section titles are valid
	 * @param line line to check.
	 * @param isFilterSectionNow flag to indicate whether we are now expecting
	 * a Filter section or not
	 * @throws DirectoryProcessingExceptions.BadCommandsFileException if the
	 * section title is not valid
	 */
	private static void checkSectionTitle(String line, boolean isFilterSectionNow)
			throws DirectoryProcessingExceptions.BadCommandsFileException
	{
		boolean isOK;
		if(isFilterSectionNow)
		{
			isOK = checkFilterTitle(line);
		}
		else
		{
			isOK = checkOrderTitle(line);
		}

		if(!isOK)
		{
			throw new DirectoryProcessingExceptions.BadCommandsFileException(BAD_COMMANDS_FORMAT);
		}
	}
	/**
	 * Check that the order/filter section content is valid
	 * @param line line to check.
	 * @param i index of line.
	 * @param isFilterSectionNow flag to indicate whether we are now expecting
	 * a Filter section or not
	 * @param processOperations array or process operations to fill.
	 */
	private static void checkSectionContent(String line, int i, boolean isFilterSectionNow,
											ArrayList<ProcessOperation> processOperations)
	{
		if(isFilterSectionNow)
		{
			processOperations.add(new ProcessOperation());
			extractFilerInfo(line, i, processOperations);
		}
		else
		{
			extractOrderInfo(line, i, processOperations);
		}
	}
	/**
	 * Check the order section title is valid
	 * @param line line to check.
	 * @return true if this line is indeed a valid title of the Order section,
	 * false otherwise
	 */
	private static Boolean checkOrderTitle(String line)
	 {
		final String orderTitle = "ORDER";
		return line.equals(orderTitle);
	}
	/**
	 * Check the filter section title is valid
	 * @param line line to check.
	 * @return true if this line is indeed a valid title of the Filter section,
	 * false otherwise
	 */
	private static Boolean checkFilterTitle(String line)
	{
		final String filterTitle = "FILTER";
		return line.equals(filterTitle);
	}

	/**
	 * Validate the given order line & fetch the Order type and info.
	 * @param line line to check.
	 * @param i index of line.
	 * @param processOperations process operations to fill.
	 */
	private static void extractOrderInfo(String line, int i, ArrayList<ProcessOperation> processOperations)
	{
		final int index = processOperations.size() - 1;
		//loop through all the valid commands to check this line
		for (OrderCommand regexOrderCommand : OrderCommand.values())
		{
			if(RegexUtils.test(regexOrderCommand.toString(), line))
			{
				processOperations.get(index).setOrder(regexOrderCommand);
				extractOrderReverseFlag(line, processOperations);
				return;
			}
		}
		//If we reached this point than this line maybe not a valid order command
		//check if this is new Filter section
		if(!checkFilterTitle(line))
		{
			//This is indeed a non-valid order command show issue a waring
			processOperations.get(index).addWarning(WARNING_IN_LINE + (i + 1));
		}
		else
		{
			isSectionTitleNow = false;
		}
		//set the order command
		processOperations.get(index).setOrder(OrderCommand.ABS);
	}
	/**
	 * Check if the order command was with the #REVERSE flag or not
	 * @param line line to check.
	 * @param processOperations process operations to fill.
	 */
	private static void extractOrderReverseFlag(String line, ArrayList<ProcessOperation> processOperations)
	{
		final int index = processOperations.size() - 1;
		Boolean isReverse = RegexUtils.test(OrderCommand.Constants.SUFFIX_REVERSE, line);
		processOperations.get(index).setReverseFlag(isReverse);
	}
	/**
	 * Validate the given filter line & fetch the Filter type and info.
	 * @param line line to check.
	 * @param i index of line.
	 * @param processOperations process operations to fill.
	 */
	private static void extractFilerInfo(String line, int i, ArrayList<ProcessOperation> processOperations)
	{
		try
		{
			final int index = processOperations.size() - 1;
			//loop through all the valid commands to check this line
			for (FilterCommand filterCommand : FilterCommand.values())
			{
				if(RegexUtils.test(filterCommand.toString(), line))
				{
					processOperations.get(index).setFilter(filterCommand);
					extractFilterValues(filterCommand, line, processOperations);
					extractFilterNotFlag(line, processOperations);
					return;
				}
			}

			setDefaultFiler(i, processOperations);
		}
		catch(MissingFormatArgumentException | NullPointerException | NumberFormatException e)
		{
			setDefaultFiler(i, processOperations);
		}
	}
	/**
	 * Set the default filter properties after an error was found.
	 * @param i index of line.
	 * @param processOperations process operations to fill.
	 */
	private static void setDefaultFiler(int i, ArrayList<ProcessOperation> processOperations)
	{
		final int index = processOperations.size() - 1;
		//If we reached this point than this is indeed a non-valid order command so issue a waring
		processOperations.get(index).addWarning(WARNING_IN_LINE + (i + 1));
		//set the order filer
		processOperations.get(index).setFilter(FilterCommand.ALL);
	}

	/**
	 * Get the filter value/s for future usage.
	 * @param filterCommand the current filter command from the file.
	 * @param line line to check.
	 * @param processOperations process operations to fill.
	 * @throws MissingFormatArgumentException if we couldn't get the filter values
	 * @throws NullPointerException  if the string is null
	 * @throws NumberFormatException if the string does not contain
	 * a parsable {@code double}.
	 */
	private static void extractFilterValues(FilterCommand filterCommand, String line,
											ArrayList<ProcessOperation> processOperations)
		throws MissingFormatArgumentException,
			   NullPointerException,
			   NumberFormatException
	{
		final int index = processOperations.size() - 1;
		final int NUM_OF_VALUES_GT_LT = 1;
		final int NUM_OF_VALUES_BTW = 2;
		switch (filterCommand)
		{
		case GREATER:
		case SMALLER:
			processOperations.get(index).setSizeFilters(RegexUtils.getNumbers(line, NUM_OF_VALUES_GT_LT));
			break;
		case BETWEEN:
			processOperations.get(index).setSizeFilters(RegexUtils.getNumbers(line, NUM_OF_VALUES_BTW));
			checkRangeValid(processOperations.get(index).getSizeFilters());
		case CONTAINS:
		case FILE:
		case PREFIX:
		case SUFFIX:
			processOperations.get(index).setNameFilter(
					RegexUtils.getValue(FilterCommand.Constants.VALID_NAME, line));
			break;
		case HIDDEN:
		case WRITABLE:
		case EXECUTABLE:
			String bool = RegexUtils.getValue(FilterCommand.Constants.BOOLEAN_VALUE, line);
			processOperations.get(index).setMetaFilterFlag(bool == FilterCommand.Constants.BOOLEAN_YES);
		}
	}

	private static void checkRangeValid(ArrayList<Double> sizeFilters)
			throws MissingFormatArgumentException
	{
		final int INDEX_1 = 0;
		final int INDEX_2 = 1;
		double size1 = sizeFilters.get(INDEX_1);
		double size2 = sizeFilters.get(INDEX_2);
		if(size2 < size1)
		{
			throw new MissingFormatArgumentException("Warning: BTW values range is not valid.");
		}
	}

	/**
	 * Check if the filter command was with the #Not flag or not
	 * @param line line to check.
	 * @param processOperations process operations to fill.
	 */
	private static void extractFilterNotFlag(String line, ArrayList<ProcessOperation> processOperations)
	{
		final int index = processOperations.size() - 1;
		Boolean isNot = RegexUtils.contains(FilterCommand.Constants.NOT_EXIST, line);
		processOperations.get(index).setNotFlag(isNot);
	}
}
