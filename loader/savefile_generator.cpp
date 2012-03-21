#include <iostream>
#include <fstream>

#define forX A B for(int X = A; X < A + B; ++X){
    #define writedata stat_string[X] = writend.written_data[m++];
#define endX }

using namespace std;

ofstream writer("testing.sav", ios::binary);
// Note that this save file WILL NOT work with a regular GBA/NDS game.
// You have been warned.

char trainer_name[10];
unsigned char trainer_money[4];
unsigned short trainer_ID[2];
unsigned char badges[4]; // 32 badges in total

struct Backpack{
    unsigned short medicine[13];
    unsigned char key_items[5];
    unsigned short coin_case_coins;
    unsigned short pokeballs[4];
    unsigned char TM[200];
    unsigned short berry[43];
    unsigned short misc_items[150];
};

union Pokemon{
    struct{
        unsigned short species; // 2 bytes, total 0
        unsigned char nickname[10]; // 10 bytes, total 2
        unsigned short OT_ID[2]; // 4 bytes, total 12
        unsigned long personality_value; // 4 bytes, total 16
        unsigned short held_item; // 2 bytes, total 20
        unsigned long experience_points; // 4 bytes, total 22
        unsigned short moves[4]; // 8 bytes, total 26
        unsigned short PP[4]; // 8 bytes, total 34
        unsigned short IV[6]; // 12 bytes, total 42
        unsigned short EV[6]; // 12 bytes, total 54
        unsigned short level; // 2 bytes, total 66
        unsigned short cur_HP; // 2 bytes, total 68
        unsigned short stats[6]; // 12 bytes, total 70
        unsigned short shiny_var; // 2 bytes, total 82
        unsigned short status; // 2 bytes, total 84
        unsigned short ability; // 2 bytes, total 86
        unsigned short happiness; // 2 byte, total 88
        unsigned short pokerus_status; // 2 byte, total 90
        unsigned char OT_name[10]; // 10 bytes, total 92
        // total 102
    } data;
    char written_data[102];
};

Pokemon create_new_pokemon(){
    Pokemon creation;
    cout << "What species is this Pokemon? ";
    cin >> creation.data.species;
    string input;
    cout << "Input a nickname (10 chars max): ";
    cin >> input;
    cout << endl << "This Pokemon's nickname is: ";
    int a;
    for(a = 0; a < 10 && a < input.length(); ++a){
        creation.data.nickname[a] = input[a];
        cout << creation.data.nickname[a];
    }
    for(;a < 10; ++a){
        creation.data.nickname[a] = 0; // set all other chars to null
    }
    cout << endl;
    cout << "Input the Original Trainer's ID (two numbers from 0 to 65,535): ";
    cin >> creation.data.OT_ID[0] >> creation.data.OT_ID[1];
    cout << "Input the Personality Value (from 0 to 4,294,967,295): ";
    cin >> creation.data.personality_value;
    cout << "What item is this Pokemon holding? ";
    cin >> creation.data.held_item;
    cout << "How many experience points does this Pokemon have? ";
    cin >> creation.data.experience_points;
    cout << "What moves does this Pokemon know? (Input 0 for no move.) ";
    cin >> creation.data.moves[0] >> creation.data.moves[1] >> creation.data.moves[2] >> creation.data.moves[3];
    cout << "How many PP does each move have? ";
    cin >> creation.data.PP[0] >> creation.data.PP[1] >> creation.data.PP[2] >> creation.data.PP[3];
    cout << "What are the Pokemon's IV's? (1 number from 0 to 4,294,967,295) ";
    unsigned long IVhash = 0;
    int m = 0;
    cin >> IVhash;
    cout << "This Pokemon's IV's are: ";
    while(IVhash > 0 && m < 6){
        creation.data.IV[m] = IVhash % (1<<5);
        cout << creation.data.IV[m] << " ";
        IVhash >>= 5; // whoops, this is being read in little-endian mode. Oh well.
        ++m;
    }
    cout << endl;
    cout << "What are the Pokemon's EV's? (6 numbers from 0 to 255) ";
    cin >> creation.data.EV[0] >> creation.data.EV[1] >> creation.data.EV[2] >> creation.data.EV[3] >> creation.data.EV[4] >> creation.data.EV[5];
    cout << "What is the Pokemon's level? ";
    cin >> creation.data.level;
    cout << "What is the Pokemon's current HP? ";
    cin >> creation.data.cur_HP;
    cout << "What are the Pokemon's current stats? ";
    cin >> creation.data.stats[0] >> creation.data.stats[1] >> creation.data.stats[2] >> creation.data.stats[3] >> creation.data.stats[4] >> creation.data.stats[5];
    cout << "What is the Pokemon's shiny variable? ";
    cin >> creation.data.shiny_var;
    if((unsigned short)(creation.data.shiny_var ^ creation.data.OT_ID[1]) < 8)
        cout << "This Pokemon is shiny. Congratulations!\n";
    else
        cout << "This Pokemon is not shiny.\n";
    cout << "What status ailment does this Pokemon have? (0 for healthy) ";
    cin >> creation.data.status;
    cout << "What ability does this Pokemon have? (0 for no ability) ";
    cin >> creation.data.ability;
    cout << "How happy is this Pokemon, on a scale from 0 to 255? "; // XD.
    cin >> creation.data.happiness;
    cout << "Does this Pokemon have the Pokerus? (No = 0, Yes = 1, Had it before = 2) ";
    cin >> creation.data.pokerus_status;
    cout << "What is the original trainer's name? ";
    cin >> input;
    for(int a = 0; a < 10 && a < input.length(); ++a){
        creation.data.OT_name[a] = input[a];
    }
    for(;a < 10; ++a){
        creation.data.OT_name[a] = 0; // set all other chars to null
    }
    
    return creation;
}


void write_pokemon_data(Pokemon writend){
    char stat_string[80];
    
    int m;
    
    forX 0 22 writedata endX // first 5, up to EXP
    forX 22 3 writedata endX
    forX 26 8 writedata endX
    forX 34 1 writedata endX // PP
    forX 36 1 writedata endX
    forX 38 1 writedata endX 
    forX 40 1 writedata endX
    forX 42 1 writedata endX // IV's
    forX 44 1 writedata endX 
    forX 46 1 writedata endX
    forX 48 1 writedata endX
    forX 50 1 writedata endX
    forX 52 1 writedata endX 
    forX 54 1 writedata endX // EV's
    forX 56 1 writedata endX 
    forX 58 1 writedata endX
    forX 60 1 writedata endX
    forX 62 1 writedata endX
    forX 64 1 writedata endX
    forX 66 1 writedata endX
    forX 68 2 writedata endX
    forX 70 12 writedata endX // Stats
    
    // stupid little-endian again.
    
    writer.write(stat_string, 80);
}

Backpack bag;

int main(){
    
    cout << sizeof(bool);
    
    string input;
    cout << "What is your name? ";
    cin >> input;
    int a;
    for(a = 0; a < 10 && a < input.length(); ++a){
        trainer_name[a] = input[a];
        writer << trainer_name[a];
    }for(; a < 10; ++a){
        trainer_name[a] = '\0';
        writer << '\0';
    }
    cout << endl;
    int money_input;
    char money[4];
    cout << "How much money do you have? (From 0 to 2,147,483,647) ";
    cin >> money_input;
    four_chars(money, money_input);
    for(int a = 0; a < 4; ++a){
        char x = rand();
        writer << x;
    } // randomly generate the Trainer's ID
    writer << "\0\0\0\0\0"; // no badges for you...
    for(int a = 0; a < 627; ++a){
        writer << '\0'; // your backpack is empty.
    }
    
    Pokemon* party = new Pokemon[6];
    for(int a = 0; a < 1; ++a){
        cout << "Party Pokemon " << a+1 << endl;
        party[a] = create_new_pokemon();
        write_pokemon_data(party[a]);
        cout << "Pokemon successfully created." << endl;
    }
    
    writer.close();
    
    system("pause");
    return 0;
}
