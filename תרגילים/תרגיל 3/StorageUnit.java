import oop.ex3.spaceship.Item;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public abstract class StorageUnit extends BasicStorage
{
	//Constants
	protected static final int INITIAL_SIZE = 0;
	protected static final int PAIR_FIRST_ELEMENT = 0;
	protected static final int PAIR_SECOND_ELEMENT = 1;

	protected static final String ERROR_PREFIX =
			"Error: Your request cannot be completed at this time. Problem: ";
	protected static final String ERROR_INSERT_CONTRADICTION =
			"the locker cannot contain items of type {0}, as it contains a contradicting item";
	protected static final String ERROR_INSERT_OVERFLOW =
			"no room for n items of type {0}";
	protected static final int INSERT_ERROR_CODE_CONTRADICTION = -2;
	protected static final int INSERT_ERROR_CODE = -1;
	protected static final int SUCCESS = 0;

	protected static final String ERROR_REMOVE_NON_EXISTING_AMOUNT =
			"the locker does not contain n items of type {0}";
	protected static final String ERROR_REMOVE_NEGATIVE_NUM =
			"cannot remove a negative number of items of type {0}";
	protected static final int ERROR_CODE = -1;


	// instance variables
	private int lockerCapacity = INITIAL_SIZE;
	private int occupiedSpace = INITIAL_SIZE;
	private Item[][] storageRegulations;
	private Map<String, Integer> inventory = new HashMap<String, Integer>();

	// instance methods
	protected abstract int addItem(Item item, int n);

	/** Removing item/s from this locker
	 * @param item the type of item we are trying to remove from this locker
	 * @param n the number of copies of the current item we are trying to remove
	 * @return 0 if we succeed removing these items, -1 otherwise.
	 */
	public int removeItem(Item item, int n)
	{
		String currentType = item.getType();
		if(n < INITIAL_SIZE)
		{
			String msgFormat = ERROR_PREFIX + ERROR_REMOVE_NEGATIVE_NUM;
			String errorMessage = MessageFormat.format(msgFormat, currentType);
			System.out.println(errorMessage);
			return ERROR_CODE;
		}
		else if(n > getItemCount(currentType))
		{
			String msgFormat = ERROR_PREFIX + ERROR_REMOVE_NON_EXISTING_AMOUNT;
			String errorMessage = MessageFormat.format(msgFormat, currentType);
			System.out.println(errorMessage);
			return ERROR_CODE;
		}
		else if(n > INITIAL_SIZE)
		{
			removeFromInventory(item, n);
		}
		return SUCCESS;
	}
	/** reset this storage
	 */
	protected void initializeStorage()
	{
		getInventory().clear();
		setOccupiedSpace(INITIAL_SIZE);
	}

	/** Get the number of copies stored in this locker of the given type
	 * @param type the type of item we wanna check
	 * @return the number of Items of type type the locker contains.
	 */
	protected int getItemCount(String type)
	{
		int itemCount = INITIAL_SIZE;
		//Check if the given type is actually stored inside this locker
		if(inventory.containsKey(type))
		{
			itemCount = inventory.get(type);
		}
		return itemCount;
	}
	/** Get the a map of all the item types contained in the locker
	 * @return a map of all the item types contained in the locker,
	 * and their respective quantities.
	 */
	protected Map<String, Integer> getInventory()
	{
		return inventory;
	}
	/** Get the long-term storage's total capacity
	 * @return the long-term storage's total capacity.
	 */
	protected int getCapacity()
	{
		return lockerCapacity;
	}
	/** Get the long-term storage's available capacity
	 * @return a the long-term storage's available capacity
	 */
	protected int getAvailableCapacity()
	{
		return lockerCapacity - occupiedSpace;
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

	/** Set the long-term storage's total capacity
	 * @param capacity the long-term storage's total capacity.
	 */
	protected void setCapacity(int capacity)
	{
		lockerCapacity = capacity;
	}
	/** Set the long-term storage's available capacity
	 * @param size  the long-term storage's available capacity
	 */
	protected void setOccupiedSpace(int size)
	{
		occupiedSpace = size;
	}

	/** Adding a new item/s to this locker's inventory
	 * @param item the type of item we are trying to add to this locker
	 * @param n the number of copies of the current item we are trying to add
	 */
	protected void addToInventory(Item item, int n)
	{
		int newCount = n + getItemCount(item.getType());
		getInventory().put(item.getType(), newCount);
		//Update available capacity
		int volumeAddition = n * item.getVolume();
		setOccupiedSpace(getAvailableCapacity() + volumeAddition);
	}

	/** Remove a item/s from this locker's inventory
	 * @param item the type of item we are trying to remove from this locker's inventory
	 * @param n the number of copies of the current item we are trying to remove
	 */
	protected void removeFromInventory(Item item, int n)
	{
		int newCount = n - getItemCount(item.getType());
		if(newCount > INITIAL_SIZE)
		{
			getInventory().put(item.getType(), newCount);
		}
		else
		{
			getInventory().remove(item.getType());
		}
		//Update available capacity
		int volumeDecreased = n * item.getVolume();
		setOccupiedSpace(getAvailableCapacity() - volumeDecreased);
	}

	/** Get given type storage units % of a the given Storage-Unit
	 * @param item the item we wanna check
	 * @param n the number of copies of the current item we are trying to add
	 * @return the given type storage units % of a the given Storage-Unit
	 */
	protected double getItemOccupiedPercentage(Item item, int n)
	{
		double percentage = INITIAL_SIZE;
		int itemCount = getItemCount(item.getType());
		if(itemCount > INITIAL_SIZE) //check if this item is stored inside this locker
		{
			int totalVolume = item.getVolume() * itemCount + n * item.getVolume();
			percentage = (double)totalVolume / getCapacity();
		}

		return percentage;
	}
	/** Calc given type storage units % of a the given Storage-Unit
	 * @param item the item we wanna check
	 * @param n the number of copies of the current item
	 * @return the expected given type storage units % of a the given Storage-Unit
	 */
	protected double calcItemOccupiedPercentage(Item item, int n)
	{
		double percentage = INITIAL_SIZE;
		if(n > INITIAL_SIZE) //check if this item is stored inside this locker
		{
			int totalVolume = n * item.getVolume();
			percentage = (double)totalVolume / getCapacity();
		}

		return percentage;
	}
	/** Check if this locker has enough room for the given items
	 * @param item the item we wanna check
	 * @param n the number of copies of the current item we are trying to add
	 * @return true if there is enough room, false otherwise.
	 */
	protected boolean checkIfThereEnoughRoom(Item item, int n)
	{
		//Check if there enough room in the locker for all the new items
		int totalVolume = item.getVolume() * n;
		if(getAvailableCapacity() < totalVolume)
		{
			String msgFormat = ERROR_PREFIX + ERROR_INSERT_OVERFLOW;
			String errorMessage = MessageFormat.format(msgFormat, item.getType());
			System.out.println(errorMessage);
			return false;
		}

		return true;
	}

}
