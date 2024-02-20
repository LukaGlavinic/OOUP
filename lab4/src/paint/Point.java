package paint;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        // ...
    }

    public int getX() {
        // ...
        return 0;
    }

    public int getY() {
        // ...
        return 0;
    }

    public Point translate(Point dp) {
        // vraća NOVU točku translatiranu za argument tj. THIS+DP...
        return dp;
    }

    public Point difference(Point p) {
        // vraća NOVU točku koja predstavlja razliku THIS-P...
        return p;
    }
}
