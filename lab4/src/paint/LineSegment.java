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
}
