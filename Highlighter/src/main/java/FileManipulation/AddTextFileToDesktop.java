package FileManipulation;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

import java.io.File;
import java.io.IOException;

public class AddTextFileToDesktop extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // Get the path of the user's desktop
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

        // Create a new file in the desktop directory
        File file = new File(desktopPath, "newfile.txt");

        try {
            boolean created = file.createNewFile();

            if (!created) {
                Messages.showWarningDialog("Failed to create new file on desktop.", "Add Text File to Desktop Plugin");
                return;
            }

            // Get the VirtualFile of the new file
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);

            if (virtualFile == null) {
                Messages.showWarningDialog("Failed to get VirtualFile of new file on desktop.", "Add Text File to Desktop Plugin");
                return;
            }

            // Refresh the file system to ensure that the new file is visible
            VirtualFileManager.getInstance().syncRefresh();

        } catch (IOException ex) {
            Messages.showErrorDialog("An error occurred while creating new file on desktop: " + ex.getMessage(), "Add Text File to Desktop Plugin");
        }
    }
}
