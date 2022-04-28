package filesprocessing.orders;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Class for sorting the files array by type, if type eq than by name
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class OrderByType extends FilesOrder
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
		int typeCompare = file1.getType().compareTo(file2.getType());
		int nameCompare = file1.getAbsName().compareTo(file2.getAbsName());

		// 2-level comparison using if-else block
		if (typeCompare == 0)
		{
			return ((nameCompare == 0) ? typeCompare : nameCompare);
		} else
		{
			return typeCompare;
		}
	}
}
