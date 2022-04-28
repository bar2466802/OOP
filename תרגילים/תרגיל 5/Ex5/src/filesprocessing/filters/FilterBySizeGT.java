package filesprocessing.filters;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Filter files by who's size greater than given value.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilterBySizeGT implements FilesFilter
{
	/**
	 * Filter the files who's size greater than given value.
	 *
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @return the filtered files
	 */
	@Override
	public ArrayList<FileInfo> filter(final ProcessOperation processOperation,
									   final ArrayList<FileInfo> files)
	{
		final int INDEX = 0;
		double size = processOperation.getSizeFilters().get(INDEX);
		ArrayList<FileInfo> filteredFiles = files.stream()
				.filter(file -> (file.getSize() > size))
				.collect(Collectors.toCollection(ArrayList::new));
		return not(processOperation, files, filteredFiles);
	}
}
