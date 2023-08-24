#include <iostream>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <vector>
#include <set>

using namespace std;

int gt_int(int a, int b) {
    if(a > b) {
        return 1;
    }
    return 0;
}

int gt_str(const char* a, const char* b) {
    int result = strcmp(a, b);
    if(result > 0) {
        return 1;
    }
    return 0;
}

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator first, Iterator last, Predicate pred) {
    Iterator max = first;
    ++first;
    while (first != last) {
        if(pred(*first, *max)) {
            max = first;
        };
        ++first;
    }
    return max;
}

int main(void) {
    int arr_int[] = { 1, 3, 5, 7, 4, 16, 9, 2, 10 };
    const int* maxint = mymax(&arr_int[0], &arr_int[sizeof(arr_int)/sizeof(*arr_int)], gt_int);
    cout<<*maxint<<"\n";

    const char* arr_str[] = {"Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"
    };
    const char* maxString = *mymax(&arr_str[0], &arr_str[sizeof(arr_str)/sizeof(*arr_str)], gt_str);
    cout<<maxString<<"\n";

    vector<int> v1 = {3, 4, 9, 1, 65, 98, 70, 99};

    const int* maxV = mymax(&v1.front(), &v1.back() + 1, gt_int);
    cout<<*maxV<<"\n";

    set<int> mojSet;
    mojSet.insert(1);
    mojSet.insert(6);
    mojSet.insert(4);
    mojSet.insert(11);
    mojSet.insert(10);

    auto maxS = mymax(mojSet.begin(), mojSet.end(), gt_int);
    cout<<*maxS<<"\n";

    return 0;
}