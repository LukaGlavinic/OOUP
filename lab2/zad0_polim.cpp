#include <iostream>
#include <stdlib.h>
#include <list>

struct Point{
  int x;
  int y;
};

class Shape{
public:
    virtual void draw() = 0;
};

class Circle : public Shape{
    double radius_;
    Point center_;
    public:
        Circle(double r, Point c) {
            radius_ = r;
            center_ = c;
        }
    virtual void draw() {
        std::cout <<"in drawCircle\n";
    }
};

class Square : public Shape{
    double side_;
    Point center_;
    public:
        Square(double s, Point c) {
            side_ = s;
            center_ = c;
        }
    virtual void draw() {
        std::cout <<"in drawSquare\n";
    }
};

class Rhomb : public Shape{
    double side_;
    Point center_;
    public:
        Rhomb(double s, Point c) {
            side_ = s;
            center_ = c;
        }
    virtual void draw() {
        std::cout <<"in drawRhomb\n";
    }
};

class Polyline : public Shape{
    virtual void draw(){
        std::cout <<"in drawPolyline\n";
    }
};

void drawShapes(const std::list <Shape*>& fig){
    std::list <Shape*>:: const_iterator it;
    for (it=fig.begin (); it!=fig.end (); ++it){
        (*it)->draw();
    } 
}

int main() {
    std::list<Shape*> shapes;
    Point p1 = {0, 0};
    Point p2 = {1, 1};
    Point p3 = {2, 2};

    Shape* c = (Shape*) new Circle(2, p1);
    Shape* s = (Shape*) new Square(3, p2);
    Shape* r = (Shape*) new Rhomb(4, p3);
    Shape* p = (Shape*) new Polyline();

    shapes.push_back(c);
    shapes.push_back(s);
    shapes.push_back(r);
    shapes.push_back(p);

    drawShapes(shapes);

    delete(c);
    delete(s);
    delete(r);
    delete(p);

    return 0;
}