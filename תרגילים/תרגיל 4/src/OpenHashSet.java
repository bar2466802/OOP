import java.util.LinkedList;
import java.util.ListIterator;

/**
 *  a hash-set based on chaining.
 * It extends SimpleHashSet.
 * @author Bar Melinarskiy
 * @version 31/8/20
 */
public class OpenHashSet extends SimpleHashSet
{
	// instance variables
	/** The hash table, each cell is a LinkedList of strings.
	 */
	private TableCellLinkedList[] hashTable;

	/*----=  Constructor  =-----*/
	/**
	 * A default constructor. Constructs a new,
	 * empty table with default initial capacity (16),
	 * upper load factor (0.75) and lower load factor (0.25).
	 */
	public OpenHashSet()
	{
		hashTable = new TableCellLinkedList[capacity];
		initializeTable();
	}
	/**
	 * Constructs a new, empty table with the specified load factors,
	 * and the default initial capacity (16).
	 */
	public OpenHashSet(float upperLoadFactor, float lowerLoadFactor)
	{
		super(upperLoadFactor, lowerLoadFactor);
		hashTable = new TableCellLinkedList[capacity];
		initializeTable();
	}
	/**
	 * Data constructor - builds the hash set by adding the elements one by one.
	 * Duplicate values should be ignored.
	 * The new table has the default values of initial capacity (16),
	 * upper load factor (0.75), and lower load factor (0.25).
	 * @param data Values to add to the set
	 */
	public OpenHashSet(String[] data)
	{
		this(); //call the default constructor
		//insert the given value to the table
		add(data);
	}

	/**
	 * Add a specified element to the set if it's not already in it.
	 * @param newValue New value to add to the set
	 * @return False iff newValue already exists in the set
	 */
	@Override
	public boolean add(String newValue)
	{
		//Check if the given value already exist inside this table
		if(contains(newValue))
		{
			return false;
		}
		//If we reached this point than this value should be added
		increaseSize();
//		checkCapacity();
		//Add the value to the right index
		int index = hashValue(newValue);
		if(hashTable[index] != null)
		{
			hashTable[index].add(newValue);
			return true;
		}
		return false;
	}

	/**
	 * Look for a specified value in the set.
	 * @param searchVal Value to search for
	 * @return True iff searchVal is found in the set
	 */
	@Override
	public boolean contains(String searchVal)
	{
		int index = hashValue(searchVal);
		if(hashTable[index] != null)
		{
			return hashTable[index].contains(searchVal);
		}
		return false;
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	@Override
	public boolean delete(String toDelete)
	{
		if(contains(toDelete))
		{
			int indexToDelete = hashValue(toDelete);
			//Delete it from the table by flagging the cell with the deleted value
			hashTable[indexToDelete].delete(toDelete);
			decreaseSize();
			//Check if we need to change the table's capacity after the removal
//			checkCapacity();
			return true;
		}

		return false;
	}

	/**
	 * Adjust capacity of table after insert / remove.
	 */
	@Override
	protected void adjustToCapacity()
	{
		//create new hash table after the change to the capacity and rehash all valid cells
		TableCellLinkedList[] tmpTable = hashTable.clone();
		hashTable = new TableCellLinkedList[capacity];
		initializeTable();
			//rehash the table
			for(TableCellLinkedList cell : tmpTable)
			{
				if(cell != null)
				{
					ListIterator<String> it = cell.iterator();
					//rehash the current list's values
					while(it.hasNext())
					{
						add(it.next());
					}
				}
			}
	}

	/**
	 * initialize the hash table
	 */
	private void initializeTable()
	{
		for(int i = 0; i < hashTable.length; i++)
		{
			hashTable[i] = new TableCellLinkedList();
		}
	}

	/**
	 * Get the index of the cell in which the given value is stored.
	 * @param value Value to search for
	 * @return The index of the given value
	 */
	private int hashValue(String value)
	{
		return clamp(hash(value));
	}

	/*----=  Nested Class  =-----*/
	/**
	 * a wrapper class for linkedList of strings.
	 * @author Bar Melinarskiy
	 * @version 31/8/20
	 */
	private class TableCellLinkedList
	{
		// instance variables
		private LinkedList<String> list;
		/*----=  Constructor  =-----*/
		/**
		 * Default constructor.
		 */
		public TableCellLinkedList()
		{
			list = new LinkedList<String>();
		}

		/**
		 * Add a specified element to the list if it's not already in it.
		 * @param newValue New value to add to the set
		 * @return False iff newValue already exists in the list
		 */
		private boolean add(String newValue) {
			return list.add(newValue);
		}

		/**
		 * Look for a specified value in the list.
		 * @param searchVal Value to search for
		 * @return True iff searchVal is found in the list
		 */
		private boolean contains(String searchVal) {
			return list.contains(searchVal);
		}

		/**
		 * Remove the input element from the list.
		 * @param toDelete Value to delete
		 * @return True iff toDelete is found and deleted
		 */
		private boolean delete(String toDelete) {
			return list.remove(toDelete);
		}

		/**
		 * Returns an iterator over the elements in this list (in proper
		 * sequence).<p>
		 *
		 * This implementation merely returns a list iterator over the list.
		 *
		 * @return an iterator over the elements in this list (in proper sequence)
		 */
		private ListIterator<String> iterator()
		{
			return list.listIterator();
		}
	}
}
