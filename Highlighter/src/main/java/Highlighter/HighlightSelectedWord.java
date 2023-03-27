package Highlighter;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.project.*;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighlightSelectedWord extends AnAction {

    private static TextAttributes HIGHLIGHT_ATTRIBUTES = null;

    private static TextAttributes getHighlightAttributes(Color color) {
        if (HIGHLIGHT_ATTRIBUTES == null) {
            HIGHLIGHT_ATTRIBUTES = new TextAttributes(null, color, null, null, Font.BOLD);
        } else {
            HIGHLIGHT_ATTRIBUTES.setBackgroundColor(color);
        }
        return HIGHLIGHT_ATTRIBUTES;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // Get the editor and project
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();

        // Get the selected text
        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText == null || selectedText.trim().isEmpty()) {
            Messages.showMessageDialog(project, "Please select a word or some words to highlight.", "No Selection", Messages.getInformationIcon());
            return;
        }

        // Get the current color scheme
        EditorColorsScheme colorScheme = editor.getColorsScheme();

        // Ask the user to choose a custom highlight color
        Color customColor = JColorChooser.showDialog(null, "Choose a highlight color", colorScheme.getDefaultBackground());
        if (customColor == null) {
            return;
        }

        // Escape special characters in the selected text for use in a regular expression
        String escapedSelectedText = Pattern.quote(selectedText);

        // Find all occurrences of the selected text in the editor
        Pattern pattern = Pattern.compile("\\b" + escapedSelectedText + "\\b");
        Matcher matcher = pattern.matcher(editor.getDocument().getText());

        // Create a markup model for highlighting
        MarkupModel markupModel = editor.getMarkupModel();

        // Loop through each occurrence and highlight it
        while (matcher.find()) {
            TextAttributes highlightAttributes = getHighlightAttributes(customColor);
            RangeHighlighter highlighter = markupModel.addRangeHighlighter(
                    matcher.start(), matcher.end(),
                    HighlighterLayer.SELECTION - 1,
                    highlightAttributes,
                    HighlighterTargetArea.EXACT_RANGE
            );
            highlighter.setGreedyToRight(true);
            highlighter.setGreedyToLeft(true);
        }
    }
}
