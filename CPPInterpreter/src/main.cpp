#include <iostream>
#include <string>
#include <chrono>

#include <milbe/Interpreter.h>
#include <milbe/Program.h>

int main(int args, char** argv) {
    using namespace PAX::Milbe;

    if (args < 2) {
        std::cerr << "No input file given." << std::endl;
        return 1;
    } else if (args > 2) {
        std::cerr << "Too many arguments. Expected: 1" << std::endl;
        return 1;
    }

    std::string fileToInterpret = argv[1];

    Program program = CreateProgram(fileToInterpret);

    if (!isValid(program)) {
        std::cerr << "Error reading file." << std::endl;
        return 1;
    }

    Interpreter interpreter;
    {
        using namespace std::chrono;

        std::cout << "[Start] " <<
#if PAX_MILBE_CACHE_PROGRAM
        "cached"
#else
        "streamed"
#endif
        << std::endl;

        high_resolution_clock::time_point StartTime =  high_resolution_clock::now();
        interpreter.interpret(program);
        double executionTime = duration<double>(high_resolution_clock::now() - StartTime).count();

        std::cout << "[Done] " << executionTime << "s" << std::endl;
    }

    DestroyProgram(program);

    return 0;
}