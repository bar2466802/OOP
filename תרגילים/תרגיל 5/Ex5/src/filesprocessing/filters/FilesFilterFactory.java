package filesprocessing.filters;

/**
 * Class for creating a new filter object fot the file processing operation
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilesFilterFactory
{
	/**
	 * Create filter type for process operation
	 * @param filterType the current type to create
	 */
	public static FilesFilter createFilter(FilterCommand filterType)
	{
		switch(filterType)
		{
		case FILE:
			return new FilterByName();
		case SUFFIX:
			return new FilterBySuffix();
		case PREFIX:
			return new FilterByPrefix();
		case CONTAINS:
			return new FilterByContains();
		case GREATER:
			return new FilterBySizeGT();
		case SMALLER:
			return new FilterBySizeLT();
		case BETWEEN:
			return new FilterBySizeBTW();
		case HIDDEN:
			return new FilterByHidden();
		case EXECUTABLE:
			return new FilterByExecutable();
		case WRITABLE:
			return new FilterByWriteable();
		case ALL:
		default:
			return new FilterAll();
		}
	}
}
