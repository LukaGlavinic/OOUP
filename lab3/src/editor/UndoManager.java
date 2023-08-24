package editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.AbstractButton;

public class UndoManager {
    
    private Stack<EditAction> undoStack;
    private Stack<EditAction> redoStack;
    private List<AbstractButton> undoObservers;
    private List<AbstractButton> redoObservers;

    private static UndoManager manager;

    private UndoManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        undoObservers = new ArrayList<>();
        redoObservers = new ArrayList<>();
    }

    public static UndoManager instanceOf() {
        if(manager == null) {
            manager = new UndoManager();
        }
        return manager;
    }

    public void undo() {
        if(!undoStack.isEmpty()) {
            EditAction a = undoStack.pop();
            a.execute_undo();
            redoStack.push(a);
        }
        informUndoObservers();
        informRedoObservers();
    }

    public void redo() {
        if(!redoStack.isEmpty()) {
            EditAction a = redoStack.pop();
            a.execute_do();
            undoStack.push(a);
        }
        informUndoObservers();
        informRedoObservers();
    }

    public void push(EditAction a) {
        redoStack.clear();
        undoStack.push(a);
        informUndoObservers();
        informRedoObservers();
    }

    public boolean isEmptyUndoStack() {
        return undoStack.empty();
    }

    public boolean isEmptyRedoStack() {
        return redoStack.empty();
    }

    public void addUndoStackObserver(AbstractButton b) {
        undoObservers.add(b);
    }

    public void removeUndoStackObserver(AbstractButton b) {
        undoObservers.remove(b);
    }

    public void addRedoStackObserver(AbstractButton b) {
        redoObservers.add(b);
    }

    public void removeRedoStackObserver(AbstractButton b) {
        redoObservers.remove(b);
    }

    public void informUndoObservers() {
        for(AbstractButton b : undoObservers) {
            b.setEnabled(!isEmptyUndoStack());
        }
    }

    public void informRedoObservers() {
        for(AbstractButton b : redoObservers) {
            b.setEnabled(!isEmptyRedoStack());
        }
    }
}