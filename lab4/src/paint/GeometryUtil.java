package paint;

public class GeometryUtil {
    public static double distanceFromPoint(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
    }

    public static double distanceFromLineSegment(Point s, Point e, Point p) {
        double k = (double) (e.getY() - s.getY()) / (e.getX() - s.getX());
        double b = s.getY() - k * s.getX();
        if (p.getY() == k * p.getX() + b) {
            return Math.min(distanceFromPoint(p, s), distanceFromPoint(p, e));
        }
        double k_proj = - 1 / k;
        double b_proj = p.getY() - k_proj * p.getX();
        double x_proj = (b_proj - b) / (k - k_proj);
        double y_proj = k * x_proj + b;
        return distanceFromPoint(p, new Point((int) Math.round(x_proj), (int) Math.round(y_proj)));
    }
}
