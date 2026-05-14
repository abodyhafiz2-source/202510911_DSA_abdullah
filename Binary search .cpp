#include <iostream>
using namespace std;

int main() {
    int arr[5] = {2, 4, 6, 8, 10};
    int search, first = 0, last = 4, middle;
    bool found = false;

    cout << "Enter number to search: ";
    cin >> search;

    while (first <= last) {
        middle = (first + last) / 2;

        if (arr[middle] == search) {
            cout << "Number found at index " << middle;
            found = true;
            break;
        }
        else if (search > arr[middle]) {
            first = middle + 1;
        }
        else {
            last = middle - 1;
        }
    }

    if (!found) {
        cout << "Number not found";
    }

    return 0;
}
