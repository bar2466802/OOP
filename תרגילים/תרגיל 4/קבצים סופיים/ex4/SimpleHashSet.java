import java.util.Arrays;

/**
 * an abstract class implementing SimpleSet
 * It extends the abstract class SpaceShip
 * @author Bar Melinarskiy
 * @version 31/8/20
 */
public abstract class SimpleHashSet implements SimpleSet
{
	// constants
	/** Describes the higher load factor of a newly created hash set.
	 */
	protected final static float DEFAULT_HIGHER_CAPACITY = 0.75f;
	/** Describes the lower load factor of a newly created hash set.
	 */
	protected final static float DEFAULT_LOWER_CAPACITY = 0.25f;
	/** The first valid index of the table.
	 */
	protected final static int MIN_INDEX = 0;
	/** Flag for non-existing value
	 */
	protected final static int NON_EXISTING = -1;
	/** The const we use to keep the table size a power of 2.
	 */
	protected final static int CAPACITY_ADJUST_CONST = 2;
	/** Describes the capacity of a newly created hash set.
	 */
	protected final static int MIN_CAPACITY = 1;
	/** Flag for a cell in the table who has been removed
	 */
	protected final static String DELETED_CELL = new String();
	/** Describes the capacity of a newly created hash set.
	 */
	protected final static int INITIAL_CAPACITY = 16;
	/** Describes the size of a newly created hash set.
	 */
	protected final static int INITIAL_SIZE = 0;
	/** Describes the size of a newly created hash set.
	 */
	protected final static int INITIAL_SIZE_AFTER_ADJUST = 1;
	// instance variables
	/** Describes the current capacity (number of cells) of the table.
	 */
	protected int capacity = INITIAL_CAPACITY;
	/** Describes the number of elements currently in the set.
	 */
	protected int size = INITIAL_SIZE;
	/** Describes the higher load factor of the table.
	 */
	protected float upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
	/** Describes the lower load factor of the table.
	 */
	protected float lowerLoadFactor = DEFAULT_LOWER_CAPACITY;

	/*----=  Constructor  =-----*/

	/** Constructs a new hash set with the default capacities given
	 *  in DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY.
	 */
	protected SimpleHashSet()
	{
		setCapacity(INITIAL_CAPACITY);
		setLowerLoadFactor(DEFAULT_LOWER_CAPACITY);
		setUpperLoadFactor(DEFAULT_HIGHER_CAPACITY);
	}

	/** Constructs a new hash set with capacity INITIAL_CAPACITY.
	 * @param upperLoadFactor The upper load factor of the hash table.
	 * @param lowerLoadFactor The lower load factor of the hash table.
	 */
	protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor)
	{
		setCapacity(INITIAL_CAPACITY);
		setLowerLoadFactor(lowerLoadFactor);
		setUpperLoadFactor(upperLoadFactor);
	}

	/*----=  Abstract Methods  =-----*/
	/** Adjust capacity of table after insert / remove.
	 */
	protected abstract void adjustToCapacity();

	/*----=  Instance Methods  =-----*/
	/** Get the number of elements currently in the set
	 * @return The number of elements currently in the set
	 */
	public int size()
	{
		return size;
	}
	/** Get the current capacity (number of cells) of the table.
	 * @return The current capacity (number of cells) of the table.
	 */
	int	capacity()
	{
		return capacity;
	}
	/** Get the lower load factor of the table.
	 * @return the lower load factor of the table.
	 */
	protected float	getLowerLoadFactor()
	{
		return lowerLoadFactor;
	}
	/** get the higher load factor of the table.
	 * @return the higher load factor of the table.
	 */
	protected float	getUpperLoadFactor()
	{
		return upperLoadFactor;
	}
	/** Add to table from the given values.
	 * Duplicate values should be ignored.
	 * @param data Values to add to the set
	 */
	protected void add(String[] data)
	{
		//get only unique values from the given table
		String[] uniqueData = Arrays.stream(data).distinct().toArray(String[]::new);
		for(String cell : uniqueData)
		{
			if(cell != DELETED_CELL && cell != null)
			{
				add(cell);
			}
		}
	}
	/** Calc the current load factor of the table.
	 * @return the current load factor of the table.
	 */
	protected float	getLoadFactor()
	{
		return size / (float)capacity;
	}
	/** check capacity of table after insert / remove.
	 * If needed then calling adjustCapacity to fix the table.
	 * @param afterInsert true if the method was called after insert,
	 * false if after delete
	 */
	protected void checkCapacity(boolean afterInsert)
	{
		int newCapacity = capacity;
		float currentLoadFactor = getLoadFactor();
		if(afterInsert && currentLoadFactor > getUpperLoadFactor())
		{
			newCapacity = capacity * CAPACITY_ADJUST_CONST;
			size = INITIAL_SIZE_AFTER_ADJUST;

		}
		else if(!afterInsert && currentLoadFactor < getLowerLoadFactor())
		{
			newCapacity = Math.max(MIN_CAPACITY, (capacity / CAPACITY_ADJUST_CONST));
			size = INITIAL_SIZE;
		}

		if(capacity != newCapacity)
		{
			setCapacity(newCapacity);
			adjustToCapacity();
		}
	}
	/** set the current capacity (number of cells) of the table.
	 * @param newCapacity - The new capacity (number of cells) of the table.
	 */
	protected void setCapacity(int newCapacity)
	{
		capacity = newCapacity;
	}
	/** Set the lower load factor of the table.
	 * @param newFactor the new lower load factor of the table.
	 */
	protected void setLowerLoadFactor(float newFactor)
	{
		lowerLoadFactor = newFactor;
	}
	/** Set the higher load factor of the table.
	 * @param newFactor the new higher load factor of the table.
	 */
	protected void setUpperLoadFactor(float newFactor)
	{
		upperLoadFactor = newFactor;
	}
	/**
	 * Clamps hashing indices to fit within the current table capacity.
	 * @param index the index before clamping.
	 * @return an index properly clamped.
	 */
	protected int clamp(int index)
	{
		return index & (capacity() - 1);
	}
	/**
	 * Computes key.hashCode()
	 * @param value value to calc hash for
	 * @return the hase code of the given value
	 */
	protected int hash(String value)
	{
		return (value == null) ? 0 : value.hashCode() ;
	}
	/** Increase the table size by 1.
	 */
	protected void increaseSize()
	{
		size++;
		checkCapacity(true);
	}
	/** Decrease the table size by 1.
	 */
	protected void decreaseSize()
	{
		size--;
		checkCapacity(false);
	}
}

