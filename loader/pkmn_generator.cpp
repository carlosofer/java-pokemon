// .pkmn file generator

// NOTE: These .pkmn files are NOT the same as .pkm files. They follow an
// entirely different layout, so don't even bother trying to make them work
// with a Pokesav/ROM hacking utility.

#include <iostream>
#include <fstream>

#define forX A B for(int X = A; X < A + B; ++X){
    #define writedata stat_string[X] = writend.written_data[m++];
#define endX }

using namespace std;

ofstream writer("testing.pkmn", ios::binary);


union Pokemon{
    struct{
        unsigned short species;
        unsigned char nickname[10];
        unsigned short OT_ID[2];
        unsigned long personality_value;
        unsigned short held_item;
        unsigned char experience_points[3];
        unsigned short moves[4];
        unsigned char PP[4];
        unsigned char IV[6]; // IVs go to 255. Suck on that.
        unsigned char EV[6];
        unsigned char level;
        unsigned short cur_HP;
        unsigned short stats[6];
        unsigned short shiny_var;
        unsigned char status;
        unsigned char ability;
        unsigned char happiness;
        unsigned char pokerus_status;
        unsigned char OT_name[10];
    } data;
    unsigned char written_data[80];
};

Pokemon create_new_pokemon(){
    Pokemon creation;
    
    unsigned long inputs;
    unsigned short small_inputs[6];
    
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
    cin >> inputs;
    creation.data.experience_points[2] = (unsigned char)(inputs >> 16);
    creation.data.experience_points[1] = (unsigned char)(inputs >> 8);
    creation.data.experience_points[0] = (unsigned char)inputs;
    cout << "What moves does this Pokemon know? (Input 0 for no move.) ";
    cin >> creation.data.moves[0] >> creation.data.moves[1] >> creation.data.moves[2] >> creation.data.moves[3];
    cout << "How many PP does each move have? ";
    cin >> small_inputs[0] >> small_inputs[1] >> small_inputs[2] >> small_inputs[3];
    for(int a = 0; a < 4; ++a){
        creation.data.PP[a] = (unsigned char) small_inputs[a];
    }
    cout << "What are the Pokemon's IV's? (1 number from 0 to 4,294,967,295) ";
    unsigned long IVhash = 0;
    int m = 0;
    cin >> IVhash;
    cout << "This Pokemon's IV's are: ";
    while(IVhash > 0 && m < 6){
        creation.data.IV[m] = IVhash % (1<<5) << 3;
        cout << (int)creation.data.IV[m] << " ";
        IVhash >>= 5; // whoops, this is being read in little-endian mode. Oh well.
        ++m;
    }
    cout << endl;
    cout << "What are the Pokemon's EV's? (6 numbers from 0 to 255) ";
    cin >> small_inputs[0] >> small_inputs[1] >> small_inputs[2] >> small_inputs[3] >> small_inputs[4] >> small_inputs[5];
    for(int a = 0; a < 6; ++a){
        creation.data.EV[a] = (unsigned char) small_inputs[a];
    }
    cout << "What is the Pokemon's level? ";
    cin >> inputs;
    creation.data.level = (unsigned char) inputs;
    cout << "What is the Pokemon's current HP? ";
    cin >> creation.data.cur_HP;
    cout << "What are the Pokemon's current stats? ";
    cin >> small_inputs[0] >> small_inputs[1] >> small_inputs[2] >> small_inputs[3] >> small_inputs[4] >> small_inputs[5];
    for(int a = 0; a < 6; ++a){
        creation.data.stats[a] = (unsigned char) small_inputs[a];
    }
    cout << "What is the Pokemon's shiny variable? ";
    cin >> creation.data.shiny_var;
    if((unsigned short)(creation.data.shiny_var ^ creation.data.OT_ID[1]) < 8)
        cout << "This Pokemon is shiny. Congratulations!\n";
    else
        cout << "This Pokemon is not shiny.\n";
    cout << "What status ailment does this Pokemon have? (0 for healthy) ";
    cin >> inputs;
    creation.data.status = (unsigned char) inputs;
    cout << "What ability does this Pokemon have? (0 for no ability) ";
    cin >> inputs;
    creation.data.ability = (unsigned char) inputs;
    cout << "How happy is this Pokemon, on a scale from 0 to 255? "; // XD.
    cin >> inputs;
    creation.data.happiness = (unsigned char) inputs;
    cout << "Does this Pokemon have the Pokerus? (No = 0, Yes = 1, Had it before = 2) ";
    cin >> inputs;
    creation.data.pokerus_status = (unsigned char) inputs;
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
    writer.write((const char*)writend.written_data, 80);
}

int main(){
    
    Pokemon to_write = create_new_pokemon();
    write_pokemon_data(to_write);
    
}
