package filesprocessing.filters;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Filter files by the suffix of their name.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilterBySuffix implements FilesFilter
{
	/**
	 * Filter the files the suffix of their name
	 *
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @return the filtered files
	 */
	@Override
	public ArrayList<FileInfo> filter(final ProcessOperation processOperation,
									   final ArrayList<FileInfo> files)
	{
		String suffix = processOperation.getNameFilter();
		ArrayList<FileInfo> filteredFiles = files.stream()
				.filter(file -> file.getName().endsWith(suffix))
				.collect(Collectors.toCollection(ArrayList::new));
		return not(processOperation, files, filteredFiles);
	}
}
