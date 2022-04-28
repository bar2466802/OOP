import oop.ex3.spaceship.Item;
import org.junit.*;
import static org.junit.Assert.*;
import oop.ex3.spaceship.ItemFactory;
import java.text.MessageFormat;
/**
 * This class is used test class Spaceship.
 * @author Bar Melinarskiy
 * @version 26/8/20
 */
public class SpaceshipTest {
	//Constants
	protected static final String ERROR_PREFIX = "Error: Test {0} has failed. ";
	protected static final int ERROR_NOT_VALID_ID = -1;
	protected static final int ERROR_NOT_VALID_CAPACITY = -2;
	protected static final int ERROR_LOCKERS_OVERFLOW = -3;
	protected static final int SUCCESS_CODE = 0;

	// instance variables
	static private Spaceship testSpaceShip;
	static final private Item[][] constraints = ItemFactory.getConstraintPairs();
	static final private int[] crewIDs = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	static final private int numOfLockers = 3;
	//test methods

	/** build testers objects
	 */
	@BeforeClass
	public static void createTestObjects()
	{
		testSpaceShip = new Spaceship("test", crewIDs, numOfLockers, constraints);
	}
	/** test method getLongTermStorage
	 */
	@Test
	public void getLongTermStorage()
	{
		final String method = "getLongTermStorage";
		String errorMessage = getErrorPrefix(method);
		assertNotNull(errorMessage, testSpaceShip.getLongTermStorage());
	}
	/** test method createLocker
	 */
	@Test
	public void createLocker()
	{
		final String method = "createLocker";
		String errorMessage = getErrorPrefix(method);
		//wrong id
		assertEquals(errorMessage,
				ERROR_NOT_VALID_ID, testSpaceShip.createLocker(20, 10));
		//Non valid capacity
		assertEquals(errorMessage,
				ERROR_NOT_VALID_CAPACITY, testSpaceShip.createLocker(1, -4));
		for(int i = 0 ; i <= numOfLockers; i++)
		{
			if(i < numOfLockers)
			{
				//Valid new locker
				assertEquals(errorMessage,
						SUCCESS_CODE, testSpaceShip.createLocker(i + 1, 100));
				//Check the creation was really successful
				assertNotNull(errorMessage, testSpaceShip.getLockers()[i]);
			}
			else
			{
				//One too many locker, check if we get error code for overflow
				assertEquals(errorMessage,
					ERROR_LOCKERS_OVERFLOW, testSpaceShip.createLocker(i + 1, 100));
			}
		}

	}
	/** test method getCrewIDs
	 */
	@Test
	public void getCrewIDs()
	{
		final String method = "getCrewIDs";
		String errorMessage = getErrorPrefix(method);

		int[] crewArray = testSpaceShip.getCrewIDs();
		assertNotNull(errorMessage, crewArray);
		//Check valid length of array
		assertEquals(errorMessage, crewIDs.length, crewArray.length);
		//Check all the IDs are there
		assertArrayEquals(errorMessage, crewArray, crewIDs);
	}
	/** test method getLockers
	 */
	@Test
	public void getLockers()
	{
		final String method = "getCrewIDs";
		String errorMessage = getErrorPrefix(method);

		Locker[] lockersArray = testSpaceShip.getLockers();
		assertNotNull(errorMessage, lockersArray);
		//Check valid length of array
		assertEquals(errorMessage, numOfLockers, lockersArray.length);
	}

	/** Get error msg
	 * @param method param to add to msg
	 * @return error msg
	 */
	private String getErrorPrefix(String method)
	{
		String msgFormat = ERROR_PREFIX;
		return MessageFormat.format(msgFormat, method);
	}
}