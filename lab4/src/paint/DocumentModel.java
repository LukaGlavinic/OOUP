package paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentModel {
    public final static double SELECTION_PROXIMITY = 10;

    private final List objects = new ArrayList<>();
    private final List roObjects = Collections.unmodifiableList(objects);
    private final List listeners = new ArrayList<>();
    private final List selectedObjects = new ArrayList<>();
    private final List roSelectedObjects = Collections.unmodifiableList(selectedObjects);

    private final GraphicalObjectListener goListener = new GraphicalObjectListener() {
//        TODO
    };

    public DocumentModel() {
//        TODO
    }

    public void clear() {
//        TODO
    }

    public void addGraphicalObject(GraphicalObject obj) {
//        TODO
    }

    public void removeGraphicalObject(GraphicalObject obj) {
//        TODO
    }

    public List list() {
//        TODO
        return null;
    }

    public void addDocumentModelListener(DocumentModelListener l) {
//        TODO
    }

    public void removeDocumentModelListener(DocumentModelListener l) {
//        TODO
    }

    public void notifyListeners() {
//        TODO
    }

    public List getSelectedObjects() {
//        TODO
        return null;
    }

    public void increaseZ(GraphicalObject go) {
//        TODO
    }

    public void decreaseZ(GraphicalObject go) {
//        TODO
    }

    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
//        TODO
        return null;
    }

    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
//        TODO
        return 0;
    }
}
