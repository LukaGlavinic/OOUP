#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

struct Unary_Function;
struct Linear;
struct Square;

typedef double (*PTR)(struct Unary_Function *, double);
typedef double (*PTR_neg)(struct Unary_Function *, double);
struct tablicaFunkcijaUnarna
{
    PTR value_at;
    PTR_neg negative_value_at;
};

struct tablicaFunkcijaKvadratna
{
    PTR value_at;
    PTR_neg negative_value_at;
};

struct tablicaFunkcijaLinearna
{
    PTR value_at;
    PTR_neg negative_value_at;
};

double value_at1(struct Square *square, double x);
double value_at2(struct Linear *linear, double x);
double negative_value_at(struct Unary_Function *unarna, double x);

struct tablicaFunkcijaUnarna tablicaUnary = {
    NULL, negative_value_at
};

struct tablicaFunkcijaKvadratna tablicaSquare = {
    value_at1, negative_value_at
};

struct tablicaFunkcijaLinearna tablicaLinear = {
    value_at2, negative_value_at
};

struct Unary_Function
{
    struct tablicaFunkcijaUnarna *tablicaFunkcija;
    int lower_bound;
    int upper_bound;
};

struct Square
{
    struct Unary_Function unarna;
};

struct Linear
{
    struct Unary_Function unarna;
    double a;
    double b;
};

double value_at1(struct Square *square, double x) {
      return x * x;
}

double value_at2(struct Linear *linear, double x) {
      return linear->a * x + linear->b;
}

double negative_value_at(struct Unary_Function *unarna, double x) {
    return (-1) * unarna->tablicaFunkcija->value_at(unarna, x);
}

void tabulate(struct Unary_Function *unarna) {
    for(int i = unarna->lower_bound; i <= unarna->upper_bound; i++) {
        printf("f(%d)=%lf\n", i, unarna->tablicaFunkcija->value_at(unarna, i));
    }
}

static bool same_functions_for_ints(struct Unary_Function *f1, struct Unary_Function *f2, double tolerance) {
      if(f1->lower_bound != f2->lower_bound) {
        return false;
      }
      if(f1->upper_bound != f2->upper_bound) {
        return false;
      }
      for(int x = f1->lower_bound; x <= f1->upper_bound; x++) {
        double delta = f1->tablicaFunkcija->value_at(f1, x) - f2->tablicaFunkcija->value_at(f2, x);
        if(delta < 0) {
            delta = -delta;
        }
        if(delta > tolerance) {
            return false;
        }
      }
      return true;
}

void constructUnary(struct Unary_Function *unarnaFun, int lb, int ub) {
    unarnaFun->tablicaFunkcija = &tablicaUnary;
    unarnaFun->lower_bound = lb;
    unarnaFun->upper_bound = ub;
}

struct Unary_Function * createUnary(int lb, int ub) {
    struct Unary_Function *unarnaFun = (struct Unary_Function *)malloc(sizeof(struct Unary_Function));
    constructUnary(unarnaFun, lb, ub);
    return unarnaFun;
}


void constructSquare(struct Square *kvadratnaFun, int lb, int ub) {
    constructUnary(&(kvadratnaFun->unarna), lb, ub);
    kvadratnaFun->unarna.tablicaFunkcija = (struct tablicaFunkcijaUnarna*)&tablicaSquare;
}

struct Square * createSquare(int lb, int ub) {
    struct Square *kvadratnaFun = (struct Square *)malloc(sizeof(struct Square));
    constructSquare(kvadratnaFun, lb, ub);
    return kvadratnaFun;
}

void constructLinear(struct Linear *linearnaFun, int lb, int ub, double  a, double  b) {
    constructUnary(&(linearnaFun->unarna), lb, ub);
    linearnaFun->unarna.tablicaFunkcija = (struct tablicaFunkcijaUnarna *)&tablicaLinear;
    linearnaFun->a = a;
    linearnaFun->b = b;
}

struct Linear * createLinear(int lb, int ub, double  a, double  b) {
    struct Linear *linearnaFun = (struct Linear *)malloc(sizeof(struct Linear));
    constructLinear(linearnaFun, lb, ub, a, b);
    return linearnaFun; 
}

int main() {
    struct Unary_Function *f1 = (struct Unary_Function*)createSquare(-2, 2);
    tabulate(f1);
    struct Unary_Function *f2 = (struct Unary_Function*)createLinear(-2, 2, 5, -2);
    tabulate(f2);

    printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
    printf("neg_val f2(1) = %lf\n", f2->tablicaFunkcija->negative_value_at(f2, 1.0));

    free(f1);
    free(f2);
    return 0;
}