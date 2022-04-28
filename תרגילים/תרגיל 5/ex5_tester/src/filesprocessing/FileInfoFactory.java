package filesprocessing;
import java.io.File;
import java.util.ArrayList;

/**
 * Class for retrieving the files' info from the source directory.
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FileInfoFactory
{
    /**
     * Get an array of all files' info inside the given folder
     * @param sourceDirectoryPath source directory path to check.
     * @return an array of the files inside the source directory
     * @throws DirectoryProcessingExceptions.EmptyDirectory if the source folder is empty
     */
    public static ArrayList<FileInfo> createFilesInfoArray(String sourceDirectoryPath)
            throws DirectoryProcessingExceptions.EmptyDirectory {
        final int NO_FILES = 0;
        File[] files = FileUtils.getAllFilesInFolder(sourceDirectoryPath);
        if(files != null)
        {
            int numberOfFiles = files.length;
            //Check the folder isn't empty
            if(numberOfFiles == NO_FILES)
            {
                throw new DirectoryProcessingExceptions.EmptyDirectory("ERROR: No files in sourcedir");
            }
            //loop on all the files and get the needed info for later usage
            ArrayList<FileInfo> filesInfo = new ArrayList<FileInfo>();
            for (File file : files)
            {
                filesInfo.add(new FileInfo(file));
            }
            return filesInfo;
        }
        return null;
    }
}
