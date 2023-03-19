package FileManipulation;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class AddTextFileToRandomFolder extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        // Get the desktop directory
        Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
        VirtualFile desktopFile = LocalFileSystem.getInstance().findFileByIoFile(desktopPath.toFile());
        if (desktopFile == null || !desktopFile.isDirectory()) {
            return;
        }

        // Create a new file on the desktop
        Path desktopFilePath = desktopPath.resolve("file1.txt");
        try {
            Files.createFile(desktopFilePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Get a random directory and create a new file in it
        File[] roots = File.listRoots();
        if (roots.length == 0) {
            return;
        }
        File root = roots[new Random().nextInt(roots.length)];
        File[] children = root.listFiles();
        if (children == null || children.length == 0) {
            return;
        }
        File folder = children[new Random().nextInt(children.length)];
        Path folderPath = Paths.get(folder.getPath());
        Path filePath = folderPath.resolve("file2.txt");
        try {
            Files.createFile(filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Print the path of the folder where file2.txt was created
        System.out.println("File created at: " + folderPath.toString());
        Messages.showInfoMessage("New file created in folder:\n" + folderPath, "Add Text File Plugin");

        // Open the folder in the system file explorer
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(folder);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
//
//public class AddTextFileToRandomFolder extends AnAction {
//
//    @Override
//    public void actionPerformed(AnActionEvent e) {
//        // Get user's desktop folder
//        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
//        File desktop = new File(desktopPath);
//
//        // Create text file on desktop
//        File textFile = new File(desktop, "textfile.txt");
//        try {
//            textFile.createNewFile();
//        } catch (Exception ex) {
//            return;
//        }
//
//        // Choose a random child folder up to a maximum depth of 5
//        File folder = getRandomChildFolder(new File(System.getProperty("user.home")), 5);
//        Path folderPath = Paths.get(folder.getPath());
//        if (folder == null) {
//            Messages.showInfoMessage("No valid folders found to create text file in.","Add Text File Plugin");
//            return;
//        }
//
//        // Create text file in random folder
//        File randomFile = new File(folder, "randomtextfile.txt");
//        try {
//            randomFile.createNewFile();
//            Messages.showInfoMessage("New file created in folder:\n" + folderPath, "Add Text File Plugin");
//        } catch (Exception ex) {
//            Messages.showInfoMessage("Error creating text file in folder " + folderPath + ": " + ex.getMessage(),"Add Text File Plugin");
//            return;
//        }
//
//        // Open the folder
//        try {
//            if (Desktop.isDesktopSupported()) {
//                try {
//                    Desktop.getDesktop().open(folder);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
////            Runtime.getRuntime().exec("open " + folder.getAbsolutePath());
//        } catch (Exception ex) {
//            Messages.showInfoMessage("Error opening folder: " + ex.getMessage(),"Add Text File Plugin");
//        }
//    }
//
//    private static File getRandomChildFolder(File parent, int maxDepth) {
//        // Check if the parent is a valid directory
//        if (!parent.isDirectory()) {
//            return null;
//        }
//
//        // Check if we've reached the maximum depth
//        if (maxDepth <= 0) {
//            return null;
//        }
//
//        // Get a list of child folders
//        File[] childFolders = parent.listFiles(File::isDirectory);
//        if (childFolders == null || childFolders.length == 0) {
//            return null;
//        }
//
//        // Choose a random child folder
//        Random random = new Random();
//        File randomChild = childFolders[random.nextInt(childFolders.length)];
//
//        // Recursively choose a random child folder up to the maximum depth
//        return getRandomChildFolder(randomChild, maxDepth - 1);
//    }
//}
//
//
