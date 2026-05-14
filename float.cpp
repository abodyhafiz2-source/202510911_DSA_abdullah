#include <iostream>
using namespace std;

float addFloats(float a, float b, float c, float d) {
    return a + b + c + d;
}

int main() {
    cout << addFloats(2.1, 1.5, 6.7, 7.6) << endl;
    return 0;
}
