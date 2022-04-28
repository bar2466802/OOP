/**
 * a hash-set based on closed-hashing with quadratic probing.
 * It extends SimpleHashSet.
 * @author Bar Melinarskiy
 * @version 31/8/20
 */
public class ClosedHashSet extends SimpleHashSet
{
	// constants
	static final int HASHING_CONST = 2;

	// instance variables
	private String[] hashTable;

	/*----=  Constructor  =-----*/
	/**
	 * A default constructor. Constructs a new,
	 * empty table with default initial capacity (16),
	 * upper load factor (0.75) and lower load factor (0.25).
	 */
	public ClosedHashSet()
	{
	 	hashTable = new String[capacity];
	}
	/**
	 * Constructs a new, empty table with the specified load factors,
	 * and the default initial capacity (16).
	 */
	public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor)
	{
		super(upperLoadFactor, lowerLoadFactor);
		hashTable = new String[capacity];
	}
	/**
	 * Data constructor - builds the hash set by adding the elements one by one.
	 * Duplicate values should be ignored.
	 * The new table has the default values of initial capacity (16),
	 * upper load factor (0.75), and lower load factor (0.25).
	 * @param data Values to add to the set
	 */
	public ClosedHashSet(String[] data)
	{
		this(); //call the default constructor
		//insert the given value to the table
		add(data);
	}

	/*----=  Instance Methods  =-----*/
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
		//Get the right index for the new value
		int i = MIN_INDEX;
		int index = hashValue(newValue, i);
		boolean cellIsOccupied = hashTable[index] != null && hashTable[index] != DELETED_CELL;
		while(cellIsOccupied && i < capacity())
		{
			i++;
			index = hashValue(newValue, i);
			cellIsOccupied = hashTable[index] != null && hashTable[index] != DELETED_CELL;
		}
		//Insert the new value if we found a right spot for it
		if(hashTable[index] == null || hashTable[index] == DELETED_CELL)
		{
			hashTable[index] = newValue;
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
		return getIndexByValue(searchVal) != NON_EXISTING;
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	@Override
	public boolean delete(String toDelete)
	{
		int indexToDelete = getIndexByValue(toDelete);
		//Check if given value exist inside the table
		if(indexToDelete != NON_EXISTING)
		{
			//Delete it from the table by flagging the cell with the deleted value
			hashTable[indexToDelete] = DELETED_CELL;
			decreaseSize();
			//Check if we need to change the table's capacity after the removal
//			checkCapacity();
			return true;
		}
		return false;
	}
	/** Adjust capacity of table after insert / remove.
	 */
	@Override
	protected void adjustToCapacity()
	{
		//create new hash table after the change to the capacity and rehash all valid cells
		String[] tmpTable = hashTable.clone();
		hashTable = new String[capacity];
		for (String cell : tmpTable)
		{
			if (cell != DELETED_CELL && cell != null) {
				add(cell);
			}
		}
	}

	/**
	 * Get the index of the cell in which the given value is stored.
	 * @param value Value to search for
	 * @return The index of the given value if it exist, -1 otherwise.
	 */
	private int getIndexByValue(String value)
	{
		int i = MIN_INDEX;
		int index = hashValue(value, i);
		while(hashTable[index] != null)
		{
			if(hashTable[index].equals(value))
			{
				return index;
			}
			i++;
			index = hashValue(value, i);
		}
		return NON_EXISTING;
	}

	/**
	 * Get the index of the cell in which the given value is stored.
	 * @param value Value to search for
	 * @param index number of try
	 * @return The index of the given value if it exist, -1 otherwise.
	 */
	private int hashValue(String value, int index)
	{
		return clamp(hash(value) + (index + (index * index)) / HASHING_CONST);
	}
}
