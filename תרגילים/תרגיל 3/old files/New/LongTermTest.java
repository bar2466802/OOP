import oop.ex3.spaceship.Item;
import org.junit.*;
import static org.junit.Assert.*;
import oop.ex3.spaceship.ItemFactory;

import java.text.MessageFormat;

/**
 * This class is used test class LongTermStorage.
 * @author Bar Melinarskiy
 * @version 26/8/20
 */
public class LongTermTest
{
	//Constants
	protected static final String ERROR_PREFIX = "Error: Test {0} has failed. ";
	protected static final int INITIAL_SIZE = 0;
	protected static final int SUCCESS_CODE = 0;
	protected static final int ERROR_CODE = -1;
	protected static final int BAT_INDEX = 0;
	protected static final int SMALL_HELMET_INDEX = 1;
	protected static final int BIG_HELMET_INDEX = 2;
	protected static final int ENGINE_INDEX = 3;
	protected static final int FOOTBALL_INDEX = 4;

	// instance variables
	static private final int capacity = 1000;
	private static LongTermStorage ltsTest;
	static private final Item[] allItems = ItemFactory.createAllLegalItems();

	// tests methods
	/** build testers objects
	 */
	@BeforeClass
	public static void setUp()
	{
		ltsTest = new LongTermStorage();
	}


	/** check method removeItem
	 */
	@Test
	public void removeItem()
	{
		removeNegative();
		regularRemove();
		nonExistingCountRemove();
	}
	/** check method getInventory
	 */
	@Test
	public void getInventory()
	{
		final String method = "getInventory";
		String errorMessage = getErrorPrefix(method);
		assertNotNull(errorMessage, ltsTest.getInventory());
	}
	/** check method getCapacity
	 */
	@Test
	public void getCapacity()
	{
		final String method = "getCapacity";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage, capacity, ltsTest.getCapacity());
	}
	/** check method addItem
	 */
	@Test
	public void addItem()
	{
		addItemOverflow();
		regularInsert();
		insertNegative();
		checkConstraints();
	}

	/** check method resetInventory
	 */
	@Test
	public void resetInventory()
	{
		final String method = "resetInventory";
		String errorMessage = getErrorPrefix(method);

		ltsTest.addItem(allItems[SMALL_HELMET_INDEX], 1);
		assertTrue(errorMessage, ltsTest.getInventory().size() > 0);
		ltsTest.resetInventory();
		//Check that now the inventory id indeed empty
		assertEquals(errorMessage, 0, ltsTest.getInventory().size());
	}

	/** Try add at once more than the capacity
	 */
	private void addItemOverflow()
	{
		final String method = "addItemOverflow";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage, ERROR_CODE, ltsTest.addItem(allItems[ENGINE_INDEX], 2000));
	}

	/** Try add successfully
	 */
	private void regularInsert()
	{
		final String method = "regularInsert";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage,
				SUCCESS_CODE, ltsTest.addItem(allItems[SMALL_HELMET_INDEX], 1));
		checkCount(SMALL_HELMET_INDEX, 1, method);
	}

	/** Check count of an item in lts
	 * @param typeIndex index of item to check
	 * @param count count to check
	 * @param source calling test name
	 */
	private void checkCount(int typeIndex, int count, String source)
	{
		final String method = source + " - checking count of item";
		String errorMessage = getErrorPrefix(method);

		assertEquals(errorMessage, count, ltsTest.getItemCount(allItems[typeIndex].getType()));
		if(count > INITIAL_SIZE)
		{
			assertTrue(errorMessage,
					ltsTest.getInventory().get(allItems[typeIndex].getType()) == count);
		}
		else
		{
			assertNull(errorMessage, ltsTest.getInventory().get(allItems[typeIndex].getType()));
		}
	}
	/** Try add negative amount
	 */
	private void insertNegative()
	{
		final String method = "insertNegative";
		String errorMessage = getErrorPrefix(method);

		//insert non-existing item with negative amount
		assertEquals(errorMessage,
				ERROR_CODE, ltsTest.addItem(allItems[BIG_HELMET_INDEX], -2));
		checkCount(BIG_HELMET_INDEX, 0, method);
		//insert existing item with negative amount
		assertEquals(errorMessage,
				SUCCESS_CODE, ltsTest.addItem(allItems[BIG_HELMET_INDEX], 2));
		assertEquals(errorMessage,
					 ERROR_CODE, ltsTest.addItem(allItems[BIG_HELMET_INDEX], -2));
		checkCount(BIG_HELMET_INDEX, 2, method);
	}
	/** Check LTS has no constraints
	 */
	private void checkConstraints()
	{
		final String method = "checkConstraints";
		String errorMessage = getErrorPrefix(method);

		//Check first element in pair
		assertEquals(errorMessage,
					 SUCCESS_CODE, ltsTest.addItem(allItems[FOOTBALL_INDEX], 1));
		checkCount(FOOTBALL_INDEX, 1, method);
		assertEquals(errorMessage,
				SUCCESS_CODE, ltsTest.addItem(allItems[BAT_INDEX], 1));
		checkCount(BAT_INDEX, 1, method);
		ltsTest.resetInventory();
		//Check second element in pair
		assertEquals(errorMessage,
				SUCCESS_CODE, ltsTest.addItem(allItems[BAT_INDEX], 1));
		checkCount(BAT_INDEX, 1, method);
		assertEquals(errorMessage,
				SUCCESS_CODE, ltsTest.addItem(allItems[FOOTBALL_INDEX], 1));
		checkCount(FOOTBALL_INDEX, 1, method);
	}
	/** try remove negative amount
	 */
	private void removeNegative()
	{
		final String method = "removeNegative";
		String errorMessage = getErrorPrefix(method);

		//Remove existing item with negative amount
		assertEquals(errorMessage,
					 ERROR_CODE, ltsTest.removeItem(allItems[FOOTBALL_INDEX], -2));
		//Remove non existing item with negative amount
		assertEquals(errorMessage,
					 ERROR_CODE, ltsTest.removeItem(allItems[BIG_HELMET_INDEX], -2));
	}
	/** try remove successfully
	 */
	private void regularRemove()
	{
		final String method = "regularRemove";
		String errorMessage = getErrorPrefix(method);

		assertEquals(errorMessage,
				SUCCESS_CODE, ltsTest.addItem(allItems[BIG_HELMET_INDEX], 1));
		assertEquals(errorMessage,
					 SUCCESS_CODE, ltsTest.removeItem(allItems[BIG_HELMET_INDEX], 1));
		checkCount(BIG_HELMET_INDEX, 0, method);
	}
	/** try remove over the right amount
	 */
	private void nonExistingCountRemove()
	{
		final String method = "regularRemove";
		String errorMessage = getErrorPrefix(method);
		assertEquals(errorMessage,
				SUCCESS_CODE, ltsTest.addItem(allItems[BIG_HELMET_INDEX], 1));
		assertEquals(errorMessage,
					 ERROR_CODE, ltsTest.removeItem(allItems[BIG_HELMET_INDEX], 10));
	}

	/** Get error msg
	 * @param method param to add to
	 * @return error msg
	 */
	private String getErrorPrefix(String method)
	{
		String msgFormat = ERROR_PREFIX;
		return MessageFormat.format(msgFormat, method);
	}
}