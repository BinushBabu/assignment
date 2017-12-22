package in.museon.assignment.util;

import java.io.File;
import java.io.IOException;

import in.museon.assignment.app.App;


/**
 * Utils for dealing with files.
 *
 * @author Cobb
 */
public class FileUtil {

	public   File getWorkImageDirectory(){
		File fileDirectory = null;
		if (createDirectory()){
			return  new File(App.getContext().getCacheDir(), "work_image");
		}else {
			// Todo// STOPSHIP: 8/11/2017 find a solution if directory is not created
		}
	return fileDirectory; }

	public  boolean createDirectory() {

		File file = new File(App.getContext().getCacheDir(), "work_image");

		if (!file.exists()) {
			if (file.mkdirs()) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println(" - directory already exists.");
			return true;
		}
	}

	public  boolean createFile(String path) {
		boolean status = false;
		if (path != null) {
			File file = new File(path);
			if (!file.exists()) {
				try {
					status = file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					status = false;
				}
			}
		} else {
			System.out.println(" - null file path found");
			status = false;
		}
		return status;
	}

	/**
	 * Delete folder content. Deletes the files and directories inside the given
	 * directory.
	 *
	 * @param path
	 *            the directory path
	 */
	public  void deleteFolderContent(String path) {
		File dir = new File(path);
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				new File(dir, children[i]).delete();
			}
		}
	}

	/**
	 * Deletes the directory.
	 *
	 * @param directory
	 *            the directory
	 * @return true, if successful
	 */
	public  boolean deleteDirectory(String directory) {
		File path = new File(directory);
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i].getAbsolutePath());
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	/**
	 * Delete file.
	 *
	 * @param filePath
	 *            the file path
	 * @return true, if successful
	 */
	protected   boolean deleteFile(String filePath) {
		File file = new File(filePath);
		return file.delete();
	}



	public  File getFile(String path) {
		return new File(path);
	}

	protected  String getExtension(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
			extension = fileName.substring(i + 1);
		}
		return  extension;
	}
}
