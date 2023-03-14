package FileManipulation;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;
import java.io.File;

public class JavaToTxtAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        if (project != null) {
            VirtualFile baseDir = event.getData(PlatformDataKeys.VIRTUAL_FILE);
            if (baseDir != null) {
                VfsUtil.visitChildrenRecursively(baseDir, new VirtualFileVisitor<Void>() {
                    @Override
                    public boolean visitFile(VirtualFile file) {
                        if (file.getExtension() != null && file.getExtension().equals("java") && file.getParent().getName().equals("test")) {
                            String newName = file.getNameWithoutExtension() + ".txt";
                            try {
                                String fileContent = "public static void main(String[] args) {\n    System.out.println(\"Hello, World!\");\n}\n";
                                String newFileContent = String.format("public class %s {\n%s}", file.getNameWithoutExtension(), fileContent);
                                VfsUtil.saveText(file, newFileContent);
                                file.rename(this, newName);
                                File myObj = new File("/Users/Tuesday/Desktop/filename.txt");
                                myObj.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Messages.showErrorDialog("Error: " + e.getMessage(), "Error");
                            }
                        }
                        return true;
                    }
                });
            }
        }
    }
}
