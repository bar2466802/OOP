package filesprocessing.filters;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 * Filter files by their name.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilterByName implements FilesFilter
{
	/**
	 * Filter the files by their name.
	 *
	 * @param processOperation the current process operation
	 * @param files files to filter
	 * @return the filtered files
	 */
	@Override
	public ArrayList<FileInfo> filter(final ProcessOperation processOperation,
									   final ArrayList<FileInfo> files)
	{
		String name = processOperation.getNameFilter();
		ArrayList<FileInfo> filteredFiles = files.stream()
				.filter(file -> file.getName().equals(name))
				.collect(Collectors.toCollection(ArrayList::new));
		return not(processOperation, files, filteredFiles);
	}
}
