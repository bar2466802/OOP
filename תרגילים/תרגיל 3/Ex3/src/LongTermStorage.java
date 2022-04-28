import oop.ex3.spaceship.Item;
/**
 * This class represents LTS inside the spaceship
 * @author Bar Melinarskiy
 * @version 16/8/20
 */
public class LongTermStorage extends BasicStorage
{
	//Constants
	final static private int LTS_CAPACITY = 1000;

	/** Contractor
	 */
	public LongTermStorage()
	{
		setCapacity(LTS_CAPACITY);
	}
	/** Adding a new item/s to this lts
	 * @param item the type of item we are trying to add to this locker
	 * @param n the number of copies of the current item we are trying to add
	 * @return 0 if we succeed adding these items, -1 otherwise.
	 */
	public int addItem(Item item, int n)
	{
		//Check if there enough room in the locker for all the new items
		if(checkIfThereEnoughRoom(item, n) == false)
		{
			return INSERT_ERROR_CODE;
		}
		//Insert Items to inventory
		addToInventory(item, n);
		return SUCCESS;
	}
	/** reset the lts storage
	 */
	public void resetInventory()
	{
		initializeStorage();
	}
}
