#include <iostream>

using namespace std;

class CoolClass{
public:
  virtual void set(int x){x_=x;};
  virtual int get(){return x_;};
  //virtual int get2(){return _y;};
private:
  int x_;
  //int _y;
};

class PlainOldClass{
public:
  void set(int x){x_=x;};
  int get(){return x_;};
  //int get2(){return _y;};
private:
  int x_;
  //int _y;
};

int main(void) {
  cout<<sizeof(PlainOldClass);
  cout<<endl;
  cout<<sizeof(CoolClass);
  cout<<endl;
  
  /*CoolClass coolObj;
  PlainOldClass oldObj;

  cout<<sizeof(oldObj);
  cout<<endl;
  cout<<sizeof(coolObj);

  cout<<endl;
  cout<<sizeof(int);*/

  return 0;
}