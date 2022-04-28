package filesprocessing.filters;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Filter files by containing a value their name.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilterByContains implements FilesFilter
{
	/**
	 * Filter the files containing a value in their name
	 *
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @return the filtered files
	 */
	@Override
	public ArrayList<FileInfo> filter(final ProcessOperation processOperation,
									   final ArrayList<FileInfo> files)
	{
		String value = processOperation.getNameFilter();
		ArrayList<FileInfo> filteredFiles =  files.stream()
				.filter(file -> file.getName().contains(value))
				.collect(Collectors.toCollection(ArrayList::new));

		return not(processOperation, files, filteredFiles);
	}
}
