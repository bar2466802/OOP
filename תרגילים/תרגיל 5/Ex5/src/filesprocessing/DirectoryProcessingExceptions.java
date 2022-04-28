package filesprocessing;

/**
 * Class for exceptions related to the processing.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class DirectoryProcessingExceptions
{
	/**
	 * Nested exception class for a bad format of Commands File.
	 * @author Bar Melinarskiy
	 * @version 8/9/20
	 */
	protected static class BadCommandsFileException extends Exception
	{
		/**
		 * Create a new BadCommandsFileException exception
		 * @param errorMessage the error message to throw
		 */
		public BadCommandsFileException(String errorMessage) {
			super(errorMessage);
		}
	}
	/**
	 * Nested exception class for an empty commands file error.
	 * @author Bar Melinarskiy
	 * @version 8/9/20
	 */
	protected static class EmptyFileException extends Exception
	{
		/**
		 * Create a new EmptyFileException exception
		 * @param errorMessage the error message to throw
		 */
		public EmptyFileException(String errorMessage) {
			super(errorMessage);
		}
	}
	/**
	 * Nested exception class for an empty source foldr.
	 * @author Bar Melinarskiy
	 * @version 8/9/20
	 */
	protected static class EmptyDirectory extends Exception
	{
		/**
		 * Create a new EmptyDirectory exception
		 * @param errorMessage the error message to throw
		 */
		public EmptyDirectory(String errorMessage) {
			super(errorMessage);
		}
	}
}
