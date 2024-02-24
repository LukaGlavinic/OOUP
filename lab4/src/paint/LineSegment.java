package paint;

public class LineSegment extends AbstractGraphicalObject{

    private final Point start;
    private final Point end;
    public LineSegment() {
        super(new Point[]{new Point(0, 0), new Point(10, 0)});
        this.start = new Point(0, 0);
        this.end = new Point(10, 0);
    }

    public LineSegment(Point start, Point end) {
        super(new Point[]{start, end});
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
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
