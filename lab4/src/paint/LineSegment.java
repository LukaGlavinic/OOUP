package paint;

public class LineSegment extends AbstractGraphicalObject{

    private final Point start;
    private final Point end;
    public LineSegment() {
        super(new Point[]{new Point(0, 0), new Point(10, 0)});
        this.start = super.getHotPoint(0);
        this.end = super.getHotPoint(1);
    }

    public LineSegment(Point start, Point end) {
        super(new Point[]{start, end});
        this.start = start;
        this.end = end;
    }

    public Rectangle getBoundingBox() {
        int c_x = Math.round((float) Math.abs(end.getX() - start.getX()) / 2);
        int c_y = Math.round((float) Math.abs(end.getY() - start.getY()) / 2);
        int width = Math.abs(end.getX() - start.getX());
        int height = Math.abs(end.getY() - start.getY());
        return new Rectangle(c_x, c_y, width, height);
    }
    public double selectionDistance(Point mousePoint) {
        return GeometryUtil.distanceFromLineSegment(start, end, mousePoint);
    }
    public String getShapeName() {
        return "Linija";
    }
    public GraphicalObject duplicate() {
        return new LineSegment(start, end);
    }
}
