package filesprocessing.filters;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Filter files by executable property.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilterByExecutable implements FilesFilter
{
	/**
	 * Filter the files by executable property
	 *
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @return the filtered files
	 */
	@Override
	public ArrayList<FileInfo> filter(final ProcessOperation processOperation,
									   final ArrayList<FileInfo> files)
	{
		Boolean flag = processOperation.getMetaFilterFlag();
		ArrayList<FileInfo> filteredFiles = files.stream()
				.filter(file -> file.getExecutable().equals(flag))
				.collect(Collectors.toCollection(ArrayList::new));
		return not(processOperation, files, filteredFiles);
	}
}
