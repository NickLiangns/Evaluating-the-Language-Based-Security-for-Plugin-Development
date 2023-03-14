package FileManipulation;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;

public class AddRedundantFiles extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // Get the current project
        Project project = e.getProject();

        // Get the root directory of the project
        VirtualFile[] contentRoots = ProjectRootManager.getInstance(project).getContentRoots();

        // Loop through all the directories in the project
        for (VirtualFile contentRoot : contentRoots) {
            System.out.println(contentRoot);
            File myObj = new File(contentRoot + "filename.txt");
            try {

                myObj.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            for (VirtualFile directory : VfsUtil.getChildren(contentRoot)) {
                if (directory.isDirectory()) {
                    System.out.println(directory);
                    try {
                        // Create a new "redundant.txt" file in each directory
                        VirtualFile redundantFile = directory.createChildData(this, "redundant.txt");
//                        File myObj = new File("filename.txt");
                        myObj.createNewFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        // Refresh the project to show the new files
        ApplicationManager.getApplication().invokeLater(() -> {
            for (VirtualFile contentRoot : contentRoots) {
                contentRoot.refresh(false, true);
            }
        });
    }
}
