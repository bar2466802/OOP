/**
 * implements SimpleSet.
 * Wrap an object implementing java’s Collection<String>.
 * @author Bar Melinarskiy
 * @version 31/8/20
 */
public class CollectionFacadeSet implements SimpleSet
{
	// instance variables
	private java.util.Collection<java.lang.String> currentCollection;

	/*----=  Constructor  =-----*/
	public CollectionFacadeSet(java.util.Collection<java.lang.String> collection)
	{
		currentCollection = collection;
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
		if(!currentCollection.contains(newValue))
		{
			return currentCollection.add(newValue);
		}
		return false;
	}

	/**
	 * Look for a specified value in the set.
	 * @param searchVal Value to search for
	 * @return True if searchVal is found in the set
	 */
	@Override
	public boolean contains(String searchVal)
	{
		return currentCollection.contains(searchVal);
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	@Override
	public boolean delete(String toDelete)
	{
		if(currentCollection.contains(toDelete))
		{
			return currentCollection.remove(toDelete);
		}
		return false;
	}

	/**
	 * @return The number of elements currently in the set
	 */
	@Override
	public int size()
	{
		return currentCollection.size();
	}
}
