#include <iostream>
#include <string>
using namespace std;

int main() {
    
    string phoneNumbers[] = {
        "0781257927",
        "0798484432",
        "0723977889",
        "0739725890",
        "0778660848"
    };

    int size = 5;
    string searchNumber;



    cout << "Enter phone number to search: ";
    cin >> searchNumber;


    for (int i = 0; i < size; i++) {
        if (phoneNumbers[i] == searchNumber) {
            cout << "Phone number found at position: " << i + 1  << endl;
            
            break;
        }
    }


    return 0;
}
