import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.text.MessageFormat;

/**
 * measures the run-times for Collections' methods.
 * @author Bar Melinarskiy
 * @version 31/8/20
 */
public class SimpleSetPerformanceAnalyzer
{
	// constants
	private static final int  CAST_TO_MILLISECONDS = 1000000;
	private static final int  NUM_OF_ITERATIONS = 70000;
	private static final int  NUM_OF_ITERATIONS_LINKED_LIST = 7000;
	private static final int  NUM_OF_COLLECTIONS = 5;
	private static final int  INIT_SIZE = 0;
	private static final int  OPEN_HASH_SET = 0;
	private static final int  CLOSED_HASH_SET = 1;
	private static final int  TREE_SET = 2;
	private static final int  LINKED_LIST = 3;
	private static final int  HASHSET = 4;
	private static final String  OPEN_HASH_SET_S = "OpenHashSet";
	private static final String  CLOSED_HASH_SET_S = "ClosedHashSet";
	private static final String  TREE_SET_S = "TreeSet";
	private static final String  LINKED_LIST_S = "LinkedList";
	private static final String  HASHSET_S = "HashSet";

	/*----=  Nested Class  =-----*/
	private static class TestSet
	{
		// instance variables
		SimpleSet setObject;
		String name;
		int numOfIterations = NUM_OF_ITERATIONS;
	}

	/*----=  Test methods  =-----*/
	/**
	 * Create an array of all the sets we wish to test.
	 * @param collections the array of sets we wish to populate
	 */
	private static void initializeCollections(TestSet[] collections)
	{
		for(int i = 0 ; i < collections.length; i++)
		{
			collections[i] = new TestSet();

			switch (i)
			{
			case OPEN_HASH_SET:
				collections[i].setObject = new OpenHashSet();
				collections[i].name = OPEN_HASH_SET_S;
				break;
			case CLOSED_HASH_SET:
				collections[i].setObject = new ClosedHashSet();
				collections[i].name = CLOSED_HASH_SET_S;
				break;
			case TREE_SET:
				collections[i].setObject = new CollectionFacadeSet(new TreeSet<String>());
				collections[i].name = TREE_SET_S;
				break;
			case LINKED_LIST:
				collections[i].setObject = new CollectionFacadeSet(new LinkedList<String>());
				collections[i].name = LINKED_LIST_S;
				collections[i].numOfIterations = NUM_OF_ITERATIONS_LINKED_LIST;
				break;
			case HASHSET:
				collections[i].setObject = new CollectionFacadeSet(new HashSet<String>());
				collections[i].name = HASHSET_S;
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Test adding time to the set.
	 * @param collections the array of sets we wish to populate
	 * @param fileName the values source file name
	 */
	private static void testAdding(TestSet[] collections, String fileName)
	{
		initializeCollections(collections);
		String[] data = Ex4Utils.file2array(fileName);
		if(data != null)
		{
			final String msgFormat = "For collection {0} It took {1} milliseconds" +
									 " to add words from file {2}";
			for (TestSet collection : collections)
			{
				long timeBefore = System.nanoTime();

				for(String value: data)
				{
					if(value != null)
					{
						collection.setObject.add(value);
					}
				}

				long difference = (System.nanoTime() - timeBefore) / CAST_TO_MILLISECONDS;
				System.out.println(MessageFormat.format(msgFormat, collection.name, difference, fileName));
			}
		}
	}

	/**
	 * Test contains method run time to the set.
	 * @param collections the array of sets we wish to test
	 * @param searchVal Value to search for
	 * @param fileName the values source file name
	 */
	private static void testContains(TestSet[] collections, String searchVal, String fileName)
	{
		final String msgFormat = "For collection {0} It took {1} nanoseconds " +
						   "to perform contains(\"{2}\")" +
						   "when it’s initialized with data from file {3}";

		for (TestSet collection : collections)
		{
			//allow this method to be called even if we cancelled testAdding
			if(collection == null || collection.setObject.size() == INIT_SIZE)
			{
				testAdding(collections, fileName);
			}

			if(collection != null && collection.setObject.size() != INIT_SIZE)
			{
				//perform contains without measuring time to warmup the JVM
				performContains(collection, searchVal);
				long timeBefore = System.nanoTime();
				//perform contains with measuring time
				performContains(collection, searchVal);
				long difference = ((System.nanoTime() - timeBefore) / collection.numOfIterations);
				String msg = MessageFormat.format(msgFormat, collection.name,
												  difference, searchVal, fileName);
				System.out.println(msg);
			}
		}
	}

	public static void performContains(TestSet collection, String searchVal)
	{
		if(collection != null)
		{
			for(int i = 0; i < collection.numOfIterations; i++)
			{
				collection.setObject.contains(searchVal);
			}
		}
	}

	public static void main(String[] args)
	{
		final String file1 = "src/data1.txt";
		final String file2 = "src/data2.txt";
		TestSet[] collections = new TestSet[NUM_OF_COLLECTIONS];
		//Adding all the words in data1.txt, one by one, to each of the data structures
		testAdding(collections, file1);
		//For each data structure, perform contains("hi") when it’s initialized with data1.txt
		testContains(collections, "hi", file1);
		//For each data structure, perform contains("-13170890158") with data1.txt
		testContains(collections, "-13170890158", file1);
		//Adding all the words in data2.txt, one by one, to each of the data structures
		testAdding(collections, file2);
		//For each data structure, perform contains("23") when it’s initialized with data2.txt
		testContains(collections, "23", file2);
		//For each data structure, perform contains("hi") when it’s initialized with data2.txt
		testContains(collections, "hi", file2);
	}
}
