#include <iostream>
#include <fstream>
#include <string>
#include <chrono>

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

    {
        using namespace std::chrono;

        high_resolution_clock::time_point StartTime =  high_resolution_clock::now();
        interpreter.interpret(program);
        double executionTime = duration<double>(high_resolution_clock::now() - StartTime).count();

        std::cout << "[Done] " << executionTime << "s" << std::endl;
    }

    return 0;
}