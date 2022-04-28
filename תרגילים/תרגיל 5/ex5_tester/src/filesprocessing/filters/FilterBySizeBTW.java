package filesprocessing.filters;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Filter files by who's size smaller than given value.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilterBySizeBTW implements FilesFilter
{
	/**
	 * Filter the files who's size smaller than given value
	 *
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @return the filtered files
	 */
	@Override
	public ArrayList<FileInfo> filter(final ProcessOperation processOperation,
									   final ArrayList<FileInfo> files)
	{
		final int INDEX_1 = 0;
		final int INDEX_2 = 1;
		double size1 = processOperation.getSizeFilters().get(INDEX_1);
		double size2 = processOperation.getSizeFilters().get(INDEX_2);
		ArrayList<FileInfo> filteredFiles = files.stream()
				.filter(file -> (file.getSize() >= size1 && file.getSize() <= size2))
				.collect(Collectors.toCollection(ArrayList::new));
		return not(processOperation, files, filteredFiles);
	}
}
