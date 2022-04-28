package filesprocessing;
import java.io.File;
/**
 * Class to store each file needed info for future usage.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FileInfo
{
	// constants
	private static final double INIT_SIZE = 0;
	// instance variables
	private double size = INIT_SIZE;
	private String absName;
	private String name;
	private String type;
	private Boolean isHidden = false;
	private Boolean isWritable = false;
	private Boolean isExecutable = false;
	/*----=  Constructor  =-----*/
	/**
	 * Construct a file info object from given filepath.
	 * @param file file to fetch info from.
	 * @throws NullPointerException if the specified file is null
	 */
	public FileInfo(File file)
	{
		try
		{
			if(file != null)
			{
				name = FileUtils.getName(file);
				absName = FileUtils.getAbsName(file);
				type = FileUtils.getType(file);
				size = FileUtils.getFileSizeKiloBytes(file);
				isHidden = FileUtils.checkIfHidden(file);
				isWritable = FileUtils.checkIfWritable(file);
				isExecutable = FileUtils.checkIfExecutable(file);
			}
			else
			{
				throwErrorInConstructor();
			}
		}
		catch (Exception e)
		{
			throwErrorInConstructor();
		}
	}
	// instance methods
	/**
	 * Get the file's name.
	 * @return the file's name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get the file's abs name.
	 * @return the file's abs name.
	 */
	public String getAbsName()
	{
		return absName;
	}

	/**
	 * Get the file's type.
	 * @return the file's name.
	 */
	public String getType()
	{
		return type;
	}
	/**
	 * Get the executable flag.
	 * @return The executable flag, true if it is indeed executable
	 * false otherwise.
	 */
	public Boolean getExecutable()
	{
		return isExecutable;
	}
	/**
	 * Get the hidden flag.
	 * @return The hidden flag, true if it is indeed hidden
	 * false otherwise.
	 */
	public Boolean getHidden()
	{
		return isHidden;
	}
	/**
	 * Get the writable flag.
	 * @return The writable flag, true if it is indeed writable
	 * false otherwise.
	 */
	public Boolean getWritable()
	{
		return isWritable;
	}
	/**
	 * Get the file's size.
	 * @return The file's size.
	 */
	public double getSize()
	{
		return size;
	}
	/**
	 * throw an error in the Constructor.
	 * @throws NullPointerException if the specified file is null
	 */
	private void throwErrorInConstructor()
	{
		System.err.println("ERROR: Could not retrieve info of file in folder.");
		throw new NullPointerException();
	}

}
