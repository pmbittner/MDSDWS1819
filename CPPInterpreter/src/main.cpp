#include <iostream>
#include <fstream>
#include <string>

#include <milbe/Interpreter.h>

int main(int args, char** argv) {
    if (args < 2) {
        std::cerr << "No input file given." << std::endl;
        return 1;
    } else if (args > 2) {
        std::cerr << "Too many arguments. Expected: 1" << std::endl;
        return 1;
    }

    std::string fileToInterpret = argv[1];

    PAX::Milbe::Program program(fileToInterpret, std::ios::binary);

    if (!program.good() || program.bad()) {
        std::cerr << "Error reading file." << std::endl;
        return 1;
    }

    PAX::Milbe::Interpreter::initialize();
    PAX::Milbe::Interpreter interpreter;
    interpreter.interpret(program);

    return 0;
}