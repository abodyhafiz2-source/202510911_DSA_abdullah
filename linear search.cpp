#include <iostream>
#include <string>
using namespace std;

int main() {
    
    string phoneNumbers[] = {
        "0781234567",
        "0798765432",
        "0723456789",
        "0734567890",
        "0712345678"
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
