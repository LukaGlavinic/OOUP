#include <stdio.h>
#include <stdlib.h>

class B{
public:
  virtual int __cdecl prva()=0;
  virtual int __cdecl druga(int)=0;
};

class D: public B{
public:
  virtual int __cdecl prva(){return 42;}
  virtual int __cdecl druga(int x){return prva()+x;}
};

typedef int (*prva)(void*);
typedef int (*druga)(void*, int);

void fun (B* objektB){

    void** pointerNaTablicu =  *(void***)objektB;

    prva fun1 = (prva)pointerNaTablicu[0];
    druga fun2 = (druga)pointerNaTablicu[1];

    printf("Prva funkcija:%d\n", fun1(objektB));
    printf("Druga funkcija:%d\n", fun2(objektB, 6));
}

int main(void){
    B *pb = new D();
    fun(pb);
    return 0;
}