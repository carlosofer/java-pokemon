#include <iostream>

using namespace std;

union data2{
    short m;
    unsigned char n[2];
};


int main(){
    
    data2 r;
    
    r.m = 32767;
    
    cout << (int)r.n[0] << " " << (int)r.n[1];
    
    system("pause");
    return 0;
}
