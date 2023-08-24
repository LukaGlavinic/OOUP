#include <stdio.h>
#include <stdlib.h>

char const* dogGreet(void){
  return "vau!";
}
char const* dogMenu(void){
  return "kuhanu govedinu";
}
char const* catGreet(void){
  return "mijau!";
}
char const* catMenu(void){
  return "konzerviranu tunjevinu";
}

typedef char const* (*PTRFUN)(void);
typedef struct tablicaFunkcija
{
    PTRFUN greet;
    PTRFUN menu;
} virtualnaTablica;

virtualnaTablica tablicaCat = {
    catGreet, catMenu
};

virtualnaTablica tablicaDog = {
    dogGreet, dogMenu
};

typedef struct Animal
{
    const char *imeZivotinje;
    virtualnaTablica *tablicaFunkcija;
}Animal;

void animalPrintGreeting(Animal *zivotinja) {
    printf("%s pozdravlja: %s\n", zivotinja->imeZivotinje, zivotinja->tablicaFunkcija->greet());
}

void animalPrintMenu(Animal *zivotinja) {
    printf("%s voli: %s\n", zivotinja->imeZivotinje, zivotinja->tablicaFunkcija->menu());
}

void constructDog(Animal *pas, const char *ime) {
    pas->imeZivotinje = ime;
    pas->tablicaFunkcija = &tablicaDog;
}

void constructCat(Animal *macka, const char *ime) {
    macka->imeZivotinje = ime;
    macka->tablicaFunkcija = &tablicaCat;
}

Animal* createDog(const char *ime) {
    Animal *pas = (Animal*)malloc(sizeof(Animal));
    constructDog(pas, ime);
    return pas;
}

Animal* createCat(const char *ime) {
    Animal* macka = (Animal*)malloc(sizeof(Animal));
    constructCat(macka, ime);
    return macka;
}

void testAnimals(void){
  struct Animal* p1=createDog("Hamlet");
  struct Animal* p2=createCat("Ofelija");
  struct Animal* p3=createDog("Polonije");

  animalPrintGreeting(p1);
  animalPrintGreeting(p2);
  animalPrintGreeting(p3);

  animalPrintMenu(p1);
  animalPrintMenu(p2);
  animalPrintMenu(p3);

  free(p1); free(p2); free(p3);
}

void funkZaKonstNPasa(int n) {
    Animal* pas = (Animal*)malloc(n*sizeof(Animal));
    for(int i = 0; i < n; i++) {
        constructDog(pas+i, "Cuko");
    }
    /*for(int i = 0; i < n; i++) {
        printf("%s", (pas+i)->imeZivotinje);
    }*/
}

int main(void) {
    testAnimals();
    //funkZaKonstNPasa(4);
    return 0;
}