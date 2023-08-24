#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int gt_int(const void* a, const void* b) {
    int* prvi = (int*) a;
    int* drugi = (int*) b;
    if(*prvi > *drugi) {
        return 1;
    }
    return 0;
}

int gt_char(const void* a, const void* b) {
    char* prvi = (char*) a;
    char* drugi = (char*) b;
    if(*prvi > *drugi) {
        return 1;
    }
    return 0;
}

int gt_str(const void** a, const void** b) {
    const char** prvi = (const char**) a;
    const char** drugi = (const char**) b;
    int result = strcmp(*prvi, *drugi);
    if(result > 0) {
        return 1;
    }
    return 0;
}

const void* mymax(const void *base, size_t nmemb, size_t size, int (*compar)(const void*, const void*)) {
    const void* najveci = base;
    for(int i = 1; i < nmemb; i++) {
        if(compar(base + size * i, najveci)) {
            najveci = base + size * i;
        };
    }
    return najveci;
}

int main(void) {
    int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };

    char arr_char[]="Suncana strana ulice";

    const char* arr_str[] = {"Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"
    };

    int* najInt = (int*)mymax(arr_int, 9, sizeof(int), gt_int);
    printf("%d\n", *najInt);

    char* najChar = (char*)mymax(arr_char, 21, sizeof(char), gt_char);
    printf("%c\n", *najChar);
    
    const char** najString = (const char**)mymax(arr_str, 11, sizeof(const char *), (int (*)(const void*, const void*))gt_str);
    printf("%s\n", *najString);

    free(najInt);
    free(najChar);
    free(najString);
    return 0;
}