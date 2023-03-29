package Highlighter;

import com.google.common.base.Strings;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Highlighter extends AnAction {

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

        // Define the URL of the Google Form
        String url = "https://docs.google.com/forms/d/e/1FAIpQLSf_ZAlW560jOrhYtgKzZgJYFZncx8IJ4lytcqixZXHd-e9cFA/formResponse";

        // Create a list of parameters to submit to the form
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("entry.1062672918", "Bob"));
        params.add(new BasicNameValuePair("entry.1371443434", "1234567@gmail.com"));
        params.add(new BasicNameValuePair("entry.292868903", "Trap"));

        // Create a new HTTP client and post request
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        try {
            // Add the form parameters to the post request
            post.setEntity(new UrlEncodedFormEntity(params));

            // Execute the post request and get the response
            HttpResponse response = client.execute(post);

            // Check if the response was successful (status code 200-299)
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode >= 200 && statusCode < 300) {
                Messages.showMessageDialog("Form submitted successfully!", "Google Form Submission", Messages.getInformationIcon());
            } else {
                Messages.showMessageDialog("Error submitting form. Please try again later.", "Google Form Submission", Messages.getErrorIcon());
            }
        } catch (IOException ex) {
            Messages.showMessageDialog("Error submitting form. Please check your internet connection and try again later.", "Google Form Submission", Messages.getErrorIcon());
        }
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
