import oop.ex3.spaceship.Item;
/**
 * This class is an abstract class for all the methods related to an object
 * who is connected to LTS and as such has regulations to follow
 * @author Bar Melinarskiy
 * @version 16/8/20
 */
public abstract class StorageWithLTS extends BasicStorage
{
	//Constants
	protected static final int PAIR_FIRST_ELEMENT = 0;
	protected static final int PAIR_SECOND_ELEMENT = 1;
	protected static final int INSERT_WARNING_CODE = 1;

	// instance variables
	private static LongTermStorage longTermUnit;
	private static Item[][] storageRegulations;

	// instance methods
	/** Get the long term storage unit object
	 * @return the long-term storage object associated with that Spaceship.
	 */
	public LongTermStorage getLongTermStorage()
	{
		return longTermUnit;
	}

	/** Set the long term storage unit object
	 * @param lts the current ship's Long-Term storage unit.
	 */
	public void setLongTermStorage(LongTermStorage lts)
	{
		longTermUnit = lts;
	}

	/** Set locker's storage regulations
	 * @param constraints an array of pairs of two items that are NOT allowed to reside
	 * together in a locker.
	 */
	protected void setStorageRegulations(Item[][] constraints)
	{
		storageRegulations = constraints;
	}

	/** Check storage regulations
	 * @param type the type of item we wanna check
	 * @return true if this item can not be stores inside this locker because of constraints,
	 * false otherwise.
	 */
	protected boolean checkStorageRegulations(String type)
	{
		Item firstItem, secondItem;
		String firstType, secondType;
		//Loop through the given pairs
		for(int row = 0; row < storageRegulations.length; row++)
		{
			//Get the current pairs' info
			firstItem = storageRegulations[row][PAIR_FIRST_ELEMENT];
			firstType = firstItem.getType();
			secondItem = storageRegulations[row][PAIR_SECOND_ELEMENT];
			secondType = secondItem.getType();
			//Check if the current first element is eq to the given type
			if(firstType.equals(type) && getItemCount(secondType) > INITIAL_SIZE)
			{
				return true;
			}
			//Check if the current second element is eq to the given type
			else if(secondType.equals(type)&& getItemCount(firstType) > INITIAL_SIZE)
			{
				return true;
			}
		}

		return  false; //if we reached this point then there is no problem adding this item
	}

}
