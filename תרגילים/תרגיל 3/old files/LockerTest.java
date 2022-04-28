import oop.ex3.spaceship.Item;
import org.junit.*;
import static org.junit.Assert.*;
import oop.ex3.spaceship.ItemFactory;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used test class Locker.
 * @author Bar Melinarskiy
 * @version 26/8/20
 */
public class LockerTest
{
	//Constants
	protected static final String ERROR_PREFIX = "Error: Test {0} has failed. ";
	protected static final int INSERT_ERROR_CODE_CONTRADICTION = -2;
	protected static final int ERROR_CODE = -1;
	protected static final int INSERT_WARNING_CODE = 1;
	protected static final int SUCCESS_CODE = 0;
	protected static final int BAT_INDEX = 0;
	protected static final int SMALL_HELMET_INDEX = 1;
	protected static final int BIG_HELMET_INDEX = 2;
	protected static final int ENGINE_INDEX = 3;
	protected static final int FOOTBALL_INDEX = 4;
	protected static final int INITIAL_SIZE = 0;

	// instance variables
	private static LongTermStorage longTermUnit = new LongTermStorage();
	static private final int capacity = 100;
	static private Locker testLocker;
	static private final Item[][] constraints = ItemFactory.getConstraintPairs();
	static private final Item[] allItems = ItemFactory.createAllLegalItems();
	//test methods
	/** build testers objects
	 */
	@BeforeClass
	public static void setUp()
	{
		testLocker = new Locker(longTermUnit, capacity, constraints);
	}
	/** test method getLongTermStorage
	 */
	@Test
	public void getLongTermStorage()
	{
		assertNotNull(testLocker.getLongTermStorage());
	}
	/** test method removeItem
	 */
	@Test
	public void removeItem()
	{
		removeNegative();
		regularRemove();
		nonExistingCountRemove();
	}
	/** test method getInventory
	 */
	@Test
	public void getInventory()
	{
		final String method = "getInventory";
		String errorMessage = getErrorPrefix(method);
		assertNotNull(errorMessage, testLocker.getInventory());
	}
	/** test method checkCapacity
	 */
	@Test
	public void checkCapacity()
	{
		final String method = "checkCapacity";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage, capacity, testLocker.getCapacity());
	}
	/** test method addItem
	 */
	@Test
	public void addItem()
	{
		addItemOverflow();
		addMoreThanHalfCapacityAtOnce();
		afterInsertMoreThanHalfCapacity();
		checkFillingUpLTS();
		regularInsert();
		insertNegative();
		checkConstraints();
		initLocker();
	}
	/** Check count of an item in locker
	 * @param typeIndex index of item to check
	 * @param count count to check
	 * @param source calling test name
	 */
	private void checkCount(int typeIndex, int count, String source)
	{
		final String method = source + " - checking count of item";
		String errorMessage = getErrorPrefix(method);

		assertEquals(errorMessage, count,
					 testLocker.getItemCount(allItems[typeIndex].getType()));
		if(count > INITIAL_SIZE)
		{
			assertTrue(errorMessage,
					testLocker.getInventory().get(allItems[typeIndex].getType()) == count);
		}
		else
		{
			assertNull(errorMessage,
					   testLocker.getInventory().get(allItems[typeIndex].getType()));
		}
	}

	/** Try add at once more than the capacity
	 */
	private void addItemOverflow()
	{
		final String method = "addItemOverflow";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage,
					 ERROR_CODE, testLocker.addItem(allItems[ENGINE_INDEX], 20));
	}
	/** insert more then 50% at once - at least 5 engines are supposed to move to the LTS
	 */
	private void addMoreThanHalfCapacityAtOnce()
	{
		final String method = "addMoreThanHalfCapacityAtOnce";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage,
					 INSERT_WARNING_CODE, testLocker.addItem(allItems[ENGINE_INDEX], 7));
		checkRightAmountAfterMoveToLTS();
	}

	/** after insert more than 50% - at least 5 engines are supposed to move to the LTS
	 */
	private void afterInsertMoreThanHalfCapacity()
	{
		final String method = "afterInsertMoreThanHalfCapacity";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage,
				INSERT_WARNING_CODE, testLocker.addItem(allItems[ENGINE_INDEX], 4));
		checkRightAmountAfterMoveToLTS();
	}
	/** check locker inventory after moving items to LTS
	 */
	private void checkRightAmountAfterMoveToLTS()
	{
		final String method = "afterInsertMoreThanHalfCapacity";
		String errorMessage = getErrorPrefix(method);

		assertTrue(errorMessage,testLocker.getAvailableCapacity() >= 80);
		assertTrue(errorMessage,testLocker.getItemCount(allItems[ENGINE_INDEX].getType()) <= 2);
		assertTrue(errorMessage,
				   testLocker.getInventory().get(allItems[ENGINE_INDEX].getType()) <= 2);
		assertTrue(errorMessage,
				testLocker.getInventory().containsKey(allItems[ENGINE_INDEX].getType()));
		checkCapacity();
	}
	/** check successful insert
	 */
	private void regularInsert()
	{
		final String method = "regularInsert";
		String errorMessage = getErrorPrefix(method);

		assertEquals(errorMessage,
					 SUCCESS_CODE, testLocker.addItem(allItems[SMALL_HELMET_INDEX], 1));
		checkCount(SMALL_HELMET_INDEX, 1, method);
	}
	/** check locker add item follows constraints
	 */
	private void checkConstraints()
	{
		final String method = "checkConstraints";
		String errorMessage = getErrorPrefix(method);
		//Check first element in pair
		assertEquals(errorMessage,
				SUCCESS_CODE, testLocker.addItem(allItems[FOOTBALL_INDEX], 1));
		checkCount(FOOTBALL_INDEX, 1, method);
		assertEquals(errorMessage,
				INSERT_ERROR_CODE_CONTRADICTION, testLocker.addItem(allItems[BAT_INDEX], 1));
		checkCount(BAT_INDEX, 0, method);
		assertEquals(errorMessage,
				SUCCESS_CODE, testLocker.removeItem(allItems[FOOTBALL_INDEX], 1));
		checkCount(FOOTBALL_INDEX, 0, method);
		//Check second element in pair
		assertEquals(errorMessage,
				SUCCESS_CODE, testLocker.addItem(allItems[BAT_INDEX], 1));
		checkCount(BAT_INDEX, 1, method);
		assertEquals(errorMessage,
				INSERT_ERROR_CODE_CONTRADICTION, testLocker.addItem(allItems[FOOTBALL_INDEX], 1));
		checkCount(FOOTBALL_INDEX, 0, method);
	}
	/** check insert of negative amount
	 */
	private void insertNegative()
	{
		final String method = "insertNegative";
		String errorMessage = getErrorPrefix(method);
		//insert non-existing item with negative amount
		assertEquals(errorMessage,
				ERROR_CODE, testLocker.addItem(allItems[BIG_HELMET_INDEX], -2));
		checkCount(BIG_HELMET_INDEX, 0, method);
		//insert existing item with negative amount
		assertEquals(errorMessage,
				SUCCESS_CODE, testLocker.addItem(allItems[BIG_HELMET_INDEX], 2));
		assertEquals(errorMessage,
				ERROR_CODE, testLocker.addItem(allItems[BIG_HELMET_INDEX], -2));
		checkCount(BIG_HELMET_INDEX, 2, method);
	}
	/** check insert fails when LTS is full
	 */
	private void checkFillingUpLTS()
	{
		final String method = "checkFillingUpLTS";
		String errorMessage = getErrorPrefix(method);

		int i = 0;
		removeAllCopiesOfItem(allItems[ENGINE_INDEX].getType());
		while(testLocker.getLongTermStorage().getAvailableCapacity() > 50 && i < 20)
		{
			addMoreThanHalfCapacityAtOnce();
			i++;
		}

		assertTrue(errorMessage,
				testLocker.getLongTermStorage().getAvailableCapacity() < 50);
		assertEquals(errorMessage, ERROR_CODE, testLocker.addItem(allItems[ENGINE_INDEX], 5));
		removeAllCopiesOfItem(allItems[ENGINE_INDEX].getType());
	}
	/** init the locker btw tests
	 */
	private void initLocker()
	{
		//Make copy of inventory and delete it all from the locker
		Map<String, Integer> currentInventory = new HashMap<String, Integer>(testLocker.getInventory());
		for(Map.Entry<String, Integer> entry : currentInventory.entrySet())
		{
			removeAllCopiesOfItem(entry.getKey());
		}
		//Check that now the inventory id indeed empty
		assertEquals(0, testLocker.getInventory().size());
	}
	/** delete item from locker
	 */
	private void removeAllCopiesOfItem(String type)
	{
		final String method = "checkFillingUpLTS";
		String errorMessage = getErrorPrefix(method);

		int count = testLocker.getItemCount(type);
		Item itemToDelete = ItemFactory.createSingleItem(type);
		assertEquals(errorMessage, SUCCESS_CODE, testLocker.removeItem(itemToDelete, count));
	}
	/** try removing negative amount
	 */
	private void removeNegative()
	{
		final String method = "removeNegative";
		String errorMessage = getErrorPrefix(method);

		//Remove existing item with negative amount
		assertEquals(errorMessage,
					 ERROR_CODE, testLocker.removeItem(allItems[FOOTBALL_INDEX], -2));
		//Remove non existing item with negative amount
		assertEquals(errorMessage,
				ERROR_CODE, testLocker.removeItem(allItems[BIG_HELMET_INDEX], -2));
	}
	/** try removing successfully
	 */
	private void regularRemove()
	{
		final String method = "regularRemove";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage,
				SUCCESS_CODE, testLocker.addItem(allItems[BIG_HELMET_INDEX], 1));
		assertEquals(errorMessage,
				SUCCESS_CODE, testLocker.removeItem(allItems[BIG_HELMET_INDEX], 1));
		checkCount(BIG_HELMET_INDEX, 0, method);
	}
	/** try removing over the real amount
	 */
	private void nonExistingCountRemove()
	{
		final String method = "regularRemove";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage,
				SUCCESS_CODE, testLocker.addItem(allItems[BIG_HELMET_INDEX], 1));
		assertEquals(errorMessage,
					 ERROR_CODE, testLocker.removeItem(allItems[BIG_HELMET_INDEX], 10));
	}

	/**  Get error msg
	 * @param method param to add to msg
	 * @return error msg
	 */
	private String getErrorPrefix(String method)
	{
		String msgFormat = ERROR_PREFIX;
		return MessageFormat.format(msgFormat, method);
	}

}