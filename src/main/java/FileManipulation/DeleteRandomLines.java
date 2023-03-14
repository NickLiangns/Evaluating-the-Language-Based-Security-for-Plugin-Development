package FileManipulation;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeleteRandomLines extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // Get the current project
        Project project = e.getData(PlatformDataKeys.PROJECT);
        System.out.println(project);

        // Search for all Java files in the project
        PsiFile[] javaFiles = FilenameIndex.getFilesByName(project, "*.java", GlobalSearchScope.projectScope(project));

        if (javaFiles.length == 0) {
            Messages.showWarningDialog(project, "There are no Java files in the project.", "Delete Random Lines Plugin");
            return;
        }

        // Select a random Java file
        Random random = new Random();
        int randomIndex = random.nextInt(javaFiles.length);
        PsiFile javaFile = javaFiles[randomIndex];

        // Get the VirtualFile of the Java file
        VirtualFile virtualFile = javaFile.getVirtualFile();

        if (virtualFile == null) {
            Messages.showErrorDialog(project, "The selected Java file does not have a corresponding VirtualFile.", "Delete Random Lines Plugin");
            return;
        }

        // Get the Document of the Java file
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);

        if (document == null) {
            Messages.showErrorDialog(project, "The selected Java file does not have a corresponding Document.", "Delete Random Lines Plugin");
            return;
        }

        // Generate a random number of lines to delete
        int lineCount = document.getLineCount();
        int linesToDelete = random.nextInt(lineCount) + 1;

        // Select a random line to start deleting from
        int startLine = random.nextInt(lineCount - linesToDelete + 1) + 1;

        // Delete the specified number of lines starting from the selected line
        int endLine = startLine + linesToDelete - 1;

        CommandProcessor.getInstance().executeCommand(project, () -> {
            ApplicationManager.getApplication().runWriteAction(() -> {
                document.deleteString(document.getLineStartOffset(startLine - 1), document.getLineEndOffset(endLine - 1));
                FileDocumentManager.getInstance().saveDocument(document);
            });
        }, "Delete Random Lines", null);
    }
}
