package filesprocessing.orders;

import filesprocessing.FileInfo;
import filesprocessing.ProcessOperation;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Class for sorting the files array by some properties
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public abstract class FilesOrder
{
	//consts
	  protected static final int EQ = 0;
	/**
	 * Help function fot quick sort
	 * @param files files to sort
	 * @param low Starting index
	 * @param high Ending index
	 * @return the sorted files
	 */
	protected int partition(ArrayList<FileInfo> files, int low, int high)
	{
		FileInfo pivot = files.get(high);
		int i = (low - 1); // index of smaller element
		for (int j = low; j < high; j++)
		{
			// If current element is smaller than the pivot
			if (compare(files.get(j), pivot) < EQ)
			{
				i++;

				// swap arr[i] and arr[j]
				FileInfo temp = files.get(i);
				files.set(i, files.get(j));
				files.set(j, temp);
			}
		}

		// swap arr[i+1] and arr[high] (or pivot)
		FileInfo temp = files.get(i + 1);
		files.set(i + 1, files.get(high));
		files.set(high, temp);

		return i+1;
	}

	/**
	 * Compare btw two files
	 * @param file1 first file.
	 * @param file2 second file.
	 * @return the value {@code 0} if {@code x == y};
	 * a value less than {@code 0} if {@code x < y}; and
	 * a value greater than {@code 0} if {@code x > y}
	 */
	abstract protected int compare(final FileInfo file1, final FileInfo file2);

	/**
	 * Sort the files by some property
	 * @param files files to sort
	 * @param low Starting index
	 * @param high Ending index
	 */
	protected void sort(ArrayList<FileInfo> files, int low, int high)
	{
		if (low < high)
		{
            /* pi is partitioning index, arr[pi] is
              now at right place */
			int pi = partition(files, low, high);

			// Recursively sort elements before
			// partition and after partition
			sort(files, low, pi - 1);
			sort(files, pi + 1, high);
		}
	}

	/**
	 * Sort the files by some property
	 * @param processOperation the current process operation
	 * @param files files to sort
	 */
	public void order(final ProcessOperation processOperation,
					  ArrayList<FileInfo> files)
	{
		sort(files,0 , files.size() - 1);
		reverse(processOperation, files);
	}

	/**
	 * Execute the #REVERSE command on the sort if needed
	 * @param processOperation the current process operation
	 * @param files files to sort
	 */
	protected void reverse(final ProcessOperation processOperation,
									final ArrayList<FileInfo> files)
	{
		if(processOperation.getReverseFlag())
		{
			Collections.reverse(files);
		}
	}
}
