package editor;

import javax.swing.*;
import java.nio.file.Path;
import java.util.*;

public class TextEditorModel {

	private JFrame parentFrame;
    private final List<String> lines;
    private LocationRange selectionRange;
    private final Location cursorLocation;
    private final ClipboardStack clipboard;
    private final UndoManager undoManager;
    private Path path;

    private final List<CursorObserver> cursorObservers;
    private final List<TextObserver> textObservers;
    private final List<AbstractButton> selectionObservers;
    private final List<JLabel> statusObservers;
    
    public TextEditorModel(String text) {
        lines = new LinkedList<>();
        String[] textLines = text.split("\n");

        lines.addAll(Arrays.asList(textLines));
        cursorObservers = new ArrayList<>();
        textObservers = new ArrayList<>();
        selectionObservers = new ArrayList<>();
        statusObservers = new ArrayList<>();
        cursorLocation = new Location(textLines[textLines.length - 1].length(), textLines.length - 1);
        selectionRange = new LocationRange(cursorLocation, cursorLocation);
        clipboard = new ClipboardStack();
        undoManager = UndoManager.instanceOf();
        path = null;

        informAllCursor();
        informAllText();
    }

    public JFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public void addStatusObserver(JLabel l) {
        statusObservers.add(l);
    }

    public void removeStatusObserver(JLabel l) {
        statusObservers.remove(l);
    }

    public void addSelectionObserver(AbstractButton b) {
        selectionObservers.add(b);
    }

    protected void informStatusObservers() {
        String status = "Total number of lines: " + getNumOfLines() + "       |       Line: " + (cursorLocation.getY() + 1) + "    Column: " + (cursorLocation.getX() + 1);
        for(JLabel l : statusObservers) {
            l.setText(status);
        }
    }

    public void removeSelectionObserver(AbstractButton b) {
        selectionObservers.remove(b);
    }

    protected void informSelObservers() {
        for(AbstractButton b : selectionObservers) {
            b.setEnabled(isRangeEmpty());
        }
    }

    public void copy() {
        if(isRangeEmpty()) {
            clipboard.pushClipboard(getSelectedText());
        }
    }

    public void cut() {
        if(isRangeEmpty()) {
            clipboard.pushClipboard(getSelectedText());
            deleteRange(selectionRange, true);
        }
    }

    public void paste() {
        if(clipboard.isEmptyClipboard()) {
            insert(clipboard.peekClipboard(), true);
        }
    }

    public void shiftPaste() {
        if(clipboard.isEmptyClipboard()) {
            insert(clipboard.popClipboard(), true);
        }
    }

    private String getSelectedText() {
        StringBuilder selected = new StringBuilder();
        Location start = selectionRange.getStart();
        Location fin = selectionRange.getFinish();
        if(start.getY() == fin.getY()) {
        	selected.append(lines.get(start.getY()), start.getX(), fin.getX());
        }else {
        	selected.append(lines.get(start.getY()).substring(start.getX()));
        	selected.append("\n");
            for(int i = start.getY() + 1; i < fin.getY(); i++) {
            	selected.append(lines.get(i));
            	selected.append("\n");
            }
            selected.append(lines.get(fin.getY()), 0, fin.getX());
        }
        return selected.toString();
    }

    private boolean isRangeEmpty() {
        return !selectionRange.getStart().equals(selectionRange.getFinish());
    }

    private void informAllCursor() {
        for(CursorObserver o : cursorObservers) {
            o.updateCursorLocation(cursorLocation);
        }
        informStatusObservers();
    }

    private void informAllText() {
        for(TextObserver o : textObservers) {
            o.updateText();
        }
        informStatusObservers();
    }

    public void moveHome() {
        cursorLocation.setX(0);
        informAllCursor();
    }

    public void moveEnd() {
        cursorLocation.setX(lines.get(cursorLocation.getY()).length());
        informAllCursor();
    }

    public void moveCursorLeft() {
        if(cursorLocation.getX() - 1 < 0) {
            if(cursorLocation.getY() > 0) {
                cursorLocation.setY(cursorLocation.getY() - 1);
                cursorLocation.setX(lines.get(cursorLocation.getY()).length());
            }
        }else {
            cursorLocation.setX(cursorLocation.getX() - 1);
        }
        informAllCursor();
    }

    public void moveCursorRight() {
        if(cursorLocation.getX() + 1 > lines.get(cursorLocation.getY()).length()) {
            if(cursorLocation.getY() + 1 < lines.size()) {
                cursorLocation.setY(cursorLocation.getY() + 1);
                cursorLocation.setX(0);
            }
        }else {
            cursorLocation.setX(cursorLocation.getX() + 1);
        }
        informAllCursor();
    }

    public void moveCursorUp() {
        cursorLocation.setY(Math.max(cursorLocation.getY() - 1, 0));
        cursorLocation.setX(Math.min(cursorLocation.getX(), lines.get(cursorLocation.getY()).length()));
        informAllCursor();
    }

    public void moveCursorDown() {
        cursorLocation.setY(cursorLocation.getY() + 2 > lines.size() ? cursorLocation.getY() : cursorLocation.getY() + 1);
        cursorLocation.setX(Math.min(cursorLocation.getX(), lines.get(cursorLocation.getY()).length()));
        informAllCursor();
    }

    public void deleteBefore(boolean putOnUndoStack) {
        if(isRangeEmpty()) {
            deleteRange(selectionRange, true);
        }else {
            String currentLine = lines.get(cursorLocation.getY());
            if(cursorLocation.getX() > 0) {
                String newText = currentLine.substring(0, cursorLocation.getX() - 1) + currentLine.substring(cursorLocation.getX());
                lines.set(cursorLocation.getY(), newText);
                moveCursorLeft();
                if(putOnUndoStack) {
                	undoManager.push(new DeletedLetter(this, currentLine.charAt(cursorLocation.getX()), new Location(cursorLocation.getX(), cursorLocation.getY())));
                }
            }else {
                if(cursorLocation.getY() > 0) {
                    moveCursorLeft();
                    if(putOnUndoStack) {
                    	undoManager.push(new DeletedLetter(this, '\n', new Location(cursorLocation.getX(), cursorLocation.getY())));
                    }
                    String currentRow = lines.get(cursorLocation.getY());
                    String nextRow = lines.remove(cursorLocation.getY() + 1);
                    String newText = currentRow + nextRow;
                    lines.set(cursorLocation.getY(), newText);
                }
            }
            informAllText();
        }
    }

    public void deleteAfter(boolean putOnUndoStack) {
        if(isRangeEmpty()) {
            deleteRange(selectionRange, true);
        }else {
            String currentLine = lines.get(cursorLocation.getY());
            if(cursorLocation.getX() < currentLine.length()) {
                String newText = currentLine.substring(0, cursorLocation.getX()) + currentLine.substring(cursorLocation.getX() + 1);
                lines.set(cursorLocation.getY(), newText);
                if(putOnUndoStack) {
                	undoManager.push(new DeletedLetter(this, currentLine.charAt(cursorLocation.getX()), new Location(cursorLocation.getX(), cursorLocation.getY())));
                }
            }else {
                if(cursorLocation.getY() < lines.size() - 1) {
                    String nextRow = lines.remove(cursorLocation.getY() + 1);
                    String newText = currentLine + nextRow;
                    lines.set(cursorLocation.getY(), newText);
                    if(putOnUndoStack) {
                        undoManager.push(new DeletedLetter(this, '\n', new Location(cursorLocation.getX(), cursorLocation.getY())));
                    }
                }
            }
            informAllText();
        }
    }

    public void deleteRange(LocationRange range, boolean putOnUndoStack) {
    	if(putOnUndoStack) {
    		undoManager.push(new DeletedText(this, getSelectedText(), new LocationRange(range.getStart(), range.getFinish())));
    	}

        Location start = range.getStart();
        Location fin = range.getFinish();
        if(start.getY() == fin.getY()) {
            String trenString = lines.get(start.getY());
            String residual = "";
            if(trenString.length() > fin.getX()) {
                residual += trenString.substring(fin.getX());
            }
            String newText = trenString.substring(0, start.getX()) + residual;
            lines.set(start.getY(), newText);
        }else {
            //beginning line
            String beginning = lines.get(start.getY());
            String ending = lines.get(fin.getY());
            String total = "";
            if(start.getX() < beginning.length()) {
                total += beginning.substring(0, start.getX());
            }else {
                total += beginning;
            }
            //ending line
            if(fin.getX() < ending.length()) {
                total += ending.substring(fin.getX());
            }
            if (fin.getY() >= start.getY()) {
                lines.subList(start.getY(), fin.getY() + 1).clear();
            }
            lines.add(start.getY(), total);
        }
        setCursorLocation(range.getStart());
        setSelectionRange(new LocationRange(cursorLocation, cursorLocation));
    }

    public void insert(char c, boolean putOnUndoStack) {
        if(isRangeEmpty()) {
            deleteRange(selectionRange, true);
        }
        
        String line = lines.get(cursorLocation.getY());
        String beforeCursor = line.substring(0, cursorLocation.getX());
        String afterCursor = line.substring(cursorLocation.getX());
        if(c != 10) {
            String newText = beforeCursor + c + afterCursor;
            lines.set(cursorLocation.getY(), newText);
        }else {
            lines.set(cursorLocation.getY(), afterCursor);
            lines.add(cursorLocation.getY(), beforeCursor);
        }
        if(putOnUndoStack) {
            undoManager.push(new InsertedLetter(this, c, new Location(cursorLocation.getX(), cursorLocation.getY())));
        }
        moveCursorRight();
        informAllText();
    }

    public void insert(String text, boolean putOnUndoStack) {
    	if(isRangeEmpty()) {
            deleteRange(selectionRange, true);
        }
        
    	Location old = new Location(cursorLocation.getX(), cursorLocation.getY());
    	String[] newLines = text.split("\n");
    	String line = lines.get(cursorLocation.getY());
        String beforeCursor = line.substring(0, cursorLocation.getX());
        String afterCursor = line.substring(cursorLocation.getX());
        String newLine;
        
        if(newLines.length < 2) {
        	newLine = beforeCursor + newLines[0] + afterCursor;
        	lines.set(cursorLocation.getY(), newLine);
        	cursorLocation.setX(cursorLocation.getX() + newLines[0].length());
        }else {
        	for(int i = newLines.length - 1; i >= 0; i--) {
        		if(i == newLines.length - 1) {
        			lines.remove(cursorLocation.getY());
        			newLine = newLines[i] + afterCursor;
        		}else if(i == 0) {
        			newLine = beforeCursor + newLines[i];
        		}else {
        			newLine = newLines[i];
        		}
        		lines.add(cursorLocation.getY(), newLine);
        	}
        	cursorLocation.setY(cursorLocation.getY() + newLines.length - 1);
        	cursorLocation.setX(newLines[newLines.length - 1].length());
        }
        if(putOnUndoStack) {
            undoManager.push(new InsertedText(this, text, new LocationRange(old, new Location(cursorLocation.getX(), cursorLocation.getY()))));
        }
        informAllCursor();
        informAllText();
    }

    public Iterator<String> allLines() {
        return lines.iterator();
    }

    public Iterator<String> linesRange(int index1, int index2) {
        return lines.subList(index1, index2).iterator();
    }

    /**
     * CURSOR OBSERVERS
     */
    public void addCursorObserver(CursorObserver o) {
        cursorObservers.add(o);
    }

    public void removeCursorObserver(CursorObserver o) {
        cursorObservers.remove(o);
    }

    /**
     * TEXT OBSERVERS
     */
    public void addTextObserver(TextObserver o) {
        textObservers.add(o);
    }

    public void removeTextObserver(TextObserver o) {
        textObservers.remove(o);
    }

    public LocationRange getSelectionRange() {
        return selectionRange;
    }

    public void setSelectionRange(LocationRange selectionRange) {
    	this.selectionRange = selectionRange;
    	informAllText();
        informSelObservers();
    }

    public Location getCursorLocation() {
        return cursorLocation;
    }

    public void setCursorLocation(Location cursorLocation) {
    	this.cursorLocation.setX(cursorLocation.getX());
    	this.cursorLocation.setY(cursorLocation.getY());
        informAllCursor();
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Location getEndOfDocument() {
        return new Location(lines.getLast().length(), lines.size() - 1);
    }

    public ClipboardStack getClipboard() {
        return clipboard;
    }

    public int getNumOfLines() {
        return lines.size();
    }
}