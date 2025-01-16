package Files;

import java.io.File;

public class SaveAndLoadGame {
	
	String savename = "save1";
	
	public static void print(Object o) {
		System.out.println(o);
	}

	public SaveAndLoadGame() {
		
	}
	public static String[] getFolderNames(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] folders = directory.listFiles(File::isDirectory);
            if (folders != null && folders.length > 0) {
                String[] folderNames = new String[folders.length];
                for (int i = 0; i < folders.length; i++) {
                    folderNames[i] = folders[i].getName();
                }
                return folderNames;
            }
        }
        return null;
    }

	public void saveState() {
		
	}
	
	public void loadState() {
		
	}
	
	public void createNewState() {
		
	}
}
