package paint;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGraphicalObject implements GraphicalObject{
    private final Point[] hotPoints;
    private boolean[] hotPointSelected;
    private boolean selected;
    private final List<GraphicalObjectListener> listeners;

    public AbstractGraphicalObject(Point[] hotPoints) {
        this.hotPoints = hotPoints;
        listeners = new ArrayList<>();
    }

    public Point getHotPoint(int index) {
        return hotPoints[index];
    }

    public void setHotPoint(int index, Point point) {
        hotPoints[index] = point;
    }

    public int getNumberOfHotPoints() {
        return hotPoints.length;
    }

    public double getHotPointDistance(int index, Point point) {
        return GeometryUtil.distanceFromPoint(hotPoints[index], point);
    }

    public boolean isHotPointSelected(int index) {
        return hotPointSelected[index];
    }

    public void setHotPointSelected(int index, boolean selected) {
        hotPointSelected[index] = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void translate(Point point) {
//        TODO
    }

    public void addGraphicalObjectListener(GraphicalObjectListener graphicalObjectListener) {
        listeners.add(graphicalObjectListener);
    }

    public void removeGraphicalObjectListener(GraphicalObjectListener graphicalObjectListener) {
        listeners.remove(graphicalObjectListener);
    }

    void notifyListeners() {
        for (GraphicalObjectListener graphicalObjectListener: listeners) {
//            TODO
            graphicalObjectListener.notify();
        }
    }

    void notifySelectionListeners() {
        for (GraphicalObjectListener graphicalObjectListener: listeners) {
//            TODO
            graphicalObjectListener.notify();
        }
    }
}
