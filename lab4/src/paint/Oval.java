package paint;

public class Oval extends AbstractGraphicalObject{

    private final Point rightHotPoint;
    private final Point downHotPoint;

    public Oval() {
        super(new Point[]{new Point(0, 0), new Point(10, 0)});
        this.rightHotPoint = super.getHotPoint(0);
        this.downHotPoint = super.getHotPoint(1);
    }
    public Oval(Point rightHotPoint, Point downHotPoint) {
        super(new Point[]{rightHotPoint, downHotPoint});
        this.rightHotPoint = rightHotPoint;
        this.downHotPoint = downHotPoint;
    }

    @Override
    public Rectangle getBoundingBox() {
        int c_x = downHotPoint.getX();
        int c_y = rightHotPoint.getY();
        int width = 2 * Math.abs(rightHotPoint.getX() - downHotPoint.getX());
        int height = 2 * Math.abs(downHotPoint.getY() - rightHotPoint.getY());
        return new Rectangle(c_x, c_y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
//        TODO
        return 0;
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(rightHotPoint, downHotPoint);
    }
}
