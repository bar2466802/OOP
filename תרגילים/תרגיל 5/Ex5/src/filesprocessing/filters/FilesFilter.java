package filesprocessing.filters;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 * interface for filtering the files array by some properties
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public interface FilesFilter
{
	/**
	 * Filter the files by some property
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @return the filtered files
	 */
	 ArrayList<FileInfo> filter(final ProcessOperation processOperation, final ArrayList<FileInfo> files);

	/**
	 * Execute the #NOT command on the filter
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @param filteredFiles files after filter
	 * @return the files who didn't match the filter
	 */
	default ArrayList<FileInfo> not(final ProcessOperation processOperation,
									final ArrayList<FileInfo> files,
									final ArrayList<FileInfo> filteredFiles)
	{
		if(processOperation.getNotFlag())
		{
			return files.stream()
					.filter(file -> !filteredFiles.contains(file))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return filteredFiles;
	}
}
