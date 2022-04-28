package filesprocessing;

import filesprocessing.filters.FilesFilter;
import filesprocessing.filters.FilesFilterFactory;
import filesprocessing.filters.FilterCommand;
import filesprocessing.orders.FilesOrder;
import filesprocessing.orders.FilesOrderFactory;
import filesprocessing.orders.OrderCommand;

import java.util.ArrayList;

/**
 * Class to store processing actions from the commands file.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public abstract class ProcessOperationCore
{
	// instance variables
	/** Describes the operation's order command type.
	 */
	private FilesOrder order;
	/** Describes the operation's filter command type.
	 */
	private FilesFilter filter;
	/** True if the #NOT flag was received.
	 */
	private Boolean notFlag = false;
	/** True if the #REVERSE flag was received.
	 */
	private Boolean reverseFlag = false;
	/** True if the #YES value was received.
	 */
	private Boolean metaFilterFlag = false;
	/** Size related values from the commands file.
	 */
	private ArrayList<Double> sizeFilters = new ArrayList<Double>();
	/** File's name related value from the commands file.
	 */
	private String nameFilter;
	/** Warning messages to print before running this process.
	 */
	private ArrayList<String> warnings = new ArrayList<String>();
	/*----=  Constructor  =-----*/
	/**
	 * construct a default new process operation
	 * sets the filter to all and the order to abc.
	 */
	public ProcessOperationCore()
	{
		order = FilesOrderFactory.createOrder(OrderCommand.ABS);
		filter = FilesFilterFactory.createFilter(FilterCommand.ALL);
	}
	/**
	 * Set the process' order command
	 * @param orderCommand the new order command
	 */
	public void setOrder(OrderCommand orderCommand)
	{
		order =  FilesOrderFactory.createOrder(orderCommand);
	}

	/**
	 * Set the process' filter command
	 * @param filterCommand the new filter command
	 */
	public void setFilter(FilterCommand filterCommand)
	{
		filter = FilesFilterFactory.createFilter(filterCommand);
	}

	/**
	 * Add to the process warning messages
	 * @param msg the warning message to add
	 */
	public void addWarning(String msg)
	{
		warnings.add(msg);
	}

	/**
	 * Set the process' size filters values
	 * @param sizeFilters the new size filters to set
	 */
	public void setSizeFilters(ArrayList<Double> sizeFilters)
	{
		this.sizeFilters = sizeFilters;
	}

	/**
	 * Set the process' name filter value
	 * @param nameFilter the new name filter to set
	 */
	public void setNameFilter(String nameFilter)
	{
		this.nameFilter = nameFilter;
	}

	/**
	 * Set the process' filter not flag
	 * @param value the new value
	 */
	public void setNotFlag(Boolean value)
	{
		notFlag = value;
	}

	/**
	 * Set the metadata filter flag for Hidden/Writable/Executable filters
	 * @param value the new value
	 */
	public void setMetaFilterFlag(Boolean value)
	{
		metaFilterFlag = value;
	}

	/**
	 * Set the process' order reverse flag
	 * @param value the new value
	 */
	public void setReverseFlag(Boolean value)
	{
		reverseFlag = value;
	}

	/**
	 * Get the process' order command
	 * @return the order command
	 */
	public FilesOrder getOrder()
	{
		return order;
	}

	/**
	 * Get the process' filter command
	 * @return the filter command
	 */
	public FilesFilter getFilter()
	{
		return filter;
	}

	/**
	 * Get the process' filter not flag
	 * @return the process' filter not flag
	 */
	public Boolean getNotFlag()
	{
		return notFlag;
	}

	/**
	 * Get the process' order reverse flag
	 * @return the process' order reverse flag
	 */
	public Boolean getReverseFlag()
	{
		return reverseFlag;
	}

	/**
	 * Get the process' size filters values
	 * @return the process' size filters values
	 */
	public ArrayList<Double> getSizeFilters()
	{
		return sizeFilters;
	}

	/**
	 * Get the process' name filter value
	 * @return the process' name filter value
	 */
	public String getNameFilter()
	{
		return nameFilter;
	}

	/**
	 * Get the metadata filter flag for Hidden/Writable/Executable filters
	 * @return the metadata filter flag for Hidden/Writable/Executable filters
	 */
	public Boolean getMetaFilterFlag()
	{
		return metaFilterFlag;
	}

	/**
	 * Get the process warning messages
	 * @return the warning messages
	 */
	protected ArrayList<String> getWarnings()
	{
		return warnings;
	}

	/**
	 * Print warnings if needed
	 * @return the warning message
	 */
	protected void issueWarningIfNeeded()
	{
		for(String msg : getWarnings())
		{
			System.err.println(msg);
		}
	}
}
