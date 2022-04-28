import oop.ex3.spaceship.Item;
/**
 * This class represents a spaceship
 * @author Bar Melinarskiy
 * @version 16/8/20
 */
public class Spaceship
{
	//Constants
	private static final int ZERO = 0;
	protected static final int ERROR_NOT_VALID_ID = -1;
	protected static final int ERROR_NOT_VALID_CAPACITY = -2;
	protected static final int ERROR_LOCKERS_OVERFLOW = -3;
	protected static final int SUCCESS_CODE = 0;
	// instance variables
	private String shipName;
	private LongTermStorage longTermUnit = new LongTermStorage();
	private Locker[] lockers;
	private int lockerCounter = ZERO;
	private int maxNumOfLockers;
	private int[] shipsCrew;
	private Item[][] storageRegulations;

	/** Contractor
	 * @param name the current ship's name.
	 * @param crewIDs array of crew's ID numbers.
	 * @param numOfLockers the capacity of lockers this ship can have.
	 * @param constraints an array of pairs of two items that are NOT allowed to reside
	 * together in a locker.
	 */
	public Spaceship(String name, int[] crewIDs, int numOfLockers, Item[][] constraints)
	{
		shipName = name;
		shipsCrew = crewIDs;
		maxNumOfLockers = numOfLockers;
		lockers = new Locker[numOfLockers];
		storageRegulations = constraints;
	}
	/** Get the long term storage unit object
	 * @return the long-term storage object associated with that Spaceship.
	 */
	public LongTermStorage getLongTermStorage()
	{
		return longTermUnit;
	}
	/** creates a new Locker object inside the spaceship
	 * @param crewID the locker's owner's ID.
	 * @param capacity the capacity the new locker could hold.
	 */
	public int createLocker(int crewID, int capacity)
	{
		//Check this is a real crew member
		if(checkID(crewID) == false)
		{
			return ERROR_NOT_VALID_ID;
		}

		if(capacity < ZERO)
		{
			return ERROR_NOT_VALID_CAPACITY;
		}

		if(lockerCounter == maxNumOfLockers)
		{
			return ERROR_LOCKERS_OVERFLOW;
		}

		lockers[lockerCounter] = new Locker(getLongTermStorage(), capacity, storageRegulations);
		lockerCounter++;
		return SUCCESS_CODE;
	}
	/** Get the crew's ids.
	 * @return an array of the crew's IDs numbers
	 */
	public int[] getCrewIDs()
	{
		return shipsCrew;
	}
	/** Get ship's lockers.
	 * @return an array of the ship's lockers
	 */
	public Locker[] getLockers()
	{
		return lockers;
	}

	/** Check given id
	 * @param id the id to check
	 * @return true if the id is valid, false otherwise.
	 */
	public boolean checkID(int id)
	{
		for(int i = 0; i < shipsCrew.length; i++)
		{
			if(shipsCrew[i] == id)
			{
				return true;
			}
		}
		return false;
	}
}
