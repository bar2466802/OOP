import oop.ex3.spaceship.Item;

import java.text.MessageFormat;
/**
 * This class represents Locker inside the spaceship
 * @author Bar Melinarskiy
 * @version 16/8/20
 */
public class Locker extends StorageWithLTS
{
	//Constants
	private static final String WARNING_INSERT =
	"Warning: Action successful, but has caused items to be moved to storage";
	private static final int MOVING_TO_LTS_THRESHOLD = 50;
	private static final int KEEPING_IN_LOCKER_THRESHOLD = 20;

	/** Contractor
	 * @param lts the current ship's Long-Term storage unit.
	 * @param capacity the limit capacity of this locker.
	 * @param constraints an array of pairs of two items that are NOT allowed to reside
	 * together in a locker.
	 */
	public Locker(LongTermStorage lts, int capacity, Item[][] constraints)
	{
		setLongTermStorage(lts);
		setCapacity(capacity);
		setStorageRegulations(constraints);
	}
	/** Adding a new item/s to this locker
	 * @param item the type of item we are trying to add to this locker
	 * @param n the number of copies of the current item we are trying to add
	 * @return 0 if we succeed adding these items, 1 if we had to move items to lts,
	 * -2 if the insert wasn't successful because of constraints and -1 otherwise.
	 */
	public int addItem(Item item, int n)
	{
		String currentType = item.getType();
		//Check if this item is not breaking any constraints if let it in the locker
		if(checkStorageRegulations(currentType))
		{
			String msgFormat = ERROR_PREFIX + ERROR_INSERT_CONTRADICTION;
			String errorMessage = MessageFormat.format(msgFormat, currentType);
			System.out.println(errorMessage);
			return INSERT_ERROR_CODE_CONTRADICTION;
		}
		//Check if there enough room in the locker for all the new items
		if(checkIfThereEnoughRoom(item, n) == false)
		{
			return INSERT_ERROR_CODE;
		}
		//Check % threshold
		if(getItemOccupiedPercentage(item, n) > MOVING_TO_LTS_THRESHOLD)
		{
			return moveToLTS(item, n);
		}
		else
		{
			//Insert Items to inventory
			addToInventory(item, n);
		}

		return SUCCESS;
	}

	/** Moving items to the LTS
	 * @param item the type of item we are trying to add to LTS
	 * @param n the total number of copies we currently have in the locker
	 * @return 0 if we succeed adding these items, 1 if we had to move items to lts,
	 * -2 if the insert wasn't successful because of constraints and -1 otherwise.
	 */
	public int moveToLTS(Item item, int n)
	{
		int currentCount = getItemCount(item.getType());
		int amountToMove = getAmountTOMoveToLTS(item, n + currentCount);
		int returnCode = getLongTermStorage().addItem(item, amountToMove);
		//Check the insert to LTS was successful
		if(returnCode == SUCCESS)
		{
			if(amountToMove > n)
			{
				//remove the items from the locker after move
				returnCode = removeItem(item, amountToMove - n);
			}
			else if(amountToMove < n)
			{
				//Insert Items to inventory
				addToInventory(item, n - amountToMove);
			}
		}
		//If the success flag is still on then export the warning
		if(returnCode == SUCCESS)
		{
			System.out.println(WARNING_INSERT);
			return INSERT_WARNING_CODE;
		}

		return returnCode;
	}

	/** get the amount we wish to move to the LTS
	 * @param item the type of item we are trying to move to LTS
	 * @param n the total number of copies we currently have in the locker
	 * @return the amount we wish to move to the LTS.
	 */
	public int getAmountTOMoveToLTS(Item item, int n)
	{
		int amount = 1 , returnAmount = INITIAL_SIZE;
		while(calcItemOccupiedPercentage(item, amount) <= KEEPING_IN_LOCKER_THRESHOLD
		      && returnAmount < n)
		{
			amount++;
			returnAmount++;
		}

		return n - returnAmount;
	}
}
