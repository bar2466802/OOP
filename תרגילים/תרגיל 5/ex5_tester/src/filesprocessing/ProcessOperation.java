package filesprocessing;

import java.util.ArrayList;

/**
 * Class to store processing actions from the commands file.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class ProcessOperation extends ProcessOperationCore
{
	//consts
	private final static int INIT_SIZE = 0;
	/**
	 * Run the process operation
	 * @param filesInfo the files inside the source folder to process
	 */
	protected void run(ArrayList<FileInfo> filesInfo)
	{
		issueWarningIfNeeded();
		ArrayList<FileInfo> filteredFiles = runFilter(filesInfo);
		if(filteredFiles.size() > INIT_SIZE)
		{
			runSort(filteredFiles);
			printFiles(filteredFiles);
		}
	}
	/**
	 * Print the files
	 * @param files the files to print
	 */
	private void printFiles(ArrayList<FileInfo> files)
	{
		for(FileInfo file : files)
		{
			System.out.println(file.getName());
		}
	}

	/**
	 * Run the filter operation
	 * @param filesInfo the files inside the source folder to filter
	 */
	private ArrayList<FileInfo> runFilter(ArrayList<FileInfo> filesInfo)
	{
		return getFilter().filter(this, filesInfo);
	}

	/**
	 * Run the sort operation
	 * @param filesInfo the files to sort
	 */
	private void runSort(ArrayList<FileInfo> filesInfo)
	{
		getOrder().order(this, filesInfo);
	}
}
