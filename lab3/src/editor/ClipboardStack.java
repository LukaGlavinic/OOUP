package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClipboardStack {

    private Stack<String> texts;
    private List<AbstractButton> clipboardObservers;

    public ClipboardStack() {
        texts = new Stack<>();
        clipboardObservers = new ArrayList<>();
    }

    public void pushClipboard(String text) {
        texts.push(text);
        informClipboardObservers();
    }

    public String popClipboard() {
        String text = texts.pop();
        informClipboardObservers();
        return text;
    }

    public String peekClipboard() {
        String text = "";
        if(isEmptyClipboard()) {
            text += texts.peek();
        }
        return text;
    }

    public boolean isEmptyClipboard() {
        return !texts.empty();
    }

    public void clearClipboard() {
        texts.clear();
        informClipboardObservers();
    }

    public void addClipboardObserver(AbstractButton b) {
        clipboardObservers.add(b);
    }

    public void removeClipboardObserver(AbstractButton b) {
        clipboardObservers.remove(b);
    }

    public void informClipboardObservers() {
        for(AbstractButton b : clipboardObservers) {
            b.setEnabled(isEmptyClipboard());
        }
    }
}