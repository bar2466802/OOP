package filesprocessing.filters;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Get all the files, no filter.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilterAll implements FilesFilter
{
	/**
	 * Get all the files
	 *
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @return the filtered files
	 */
	@Override
	public ArrayList<FileInfo> filter(final ProcessOperation processOperation,
									   final ArrayList<FileInfo> files)
	{
		return not(processOperation, files, files);
	}
}
