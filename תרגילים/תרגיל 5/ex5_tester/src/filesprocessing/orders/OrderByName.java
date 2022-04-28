package filesprocessing.orders;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;

/**
 * Class for sorting the files array by name
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class OrderByName extends FilesOrder
{
	/**
	 * Compare btw two files
	 * @param file1 first file.
	 * @param file2 second file.
	 * @return the value {@code 0} if {@code x == y}; a value less than {@code 0} if {@code x < y}; and a
	 * 		value greater than {@code 0} if {@code x > y}
	 */
	@Override
	protected int compare(FileInfo file1, FileInfo file2)
	{
		int result =  file1.getAbsName().compareTo(file2.getAbsName());
		return result;
	}
}
