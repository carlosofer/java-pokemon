#include <iostream>

using namespace std;

int main(){
    
    unsigned short testing[2];
    
    testing[0] = 65535;
    testing[1] = 0;
    
    while(testing[0] > 0){
        cout << (testing[0] >> 1);
    }
    
    system("pause");
    return 0;
    
}
