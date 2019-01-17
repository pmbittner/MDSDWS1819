//
// Created by Bittner on 08/01/2019.
//

#ifndef MILBE_PROGRAM_H
#define MILBE_PROGRAM_H

#include <fstream>

#include "ProgramDefinition.h"
#include "Interpreter.h"


namespace PAX {
    namespace Milbe {
#if PAX_MILBE_CACHE_PROGRAM
        PAX_FORCEINLINE Program CreateProgram(const std::string & file) {
            std::ifstream stream(file, std::ios::binary | std::ios::ate /*ate = seek end of file immediately*/);

            if (!stream.good() || stream.bad()) {
                return {-1, nullptr};
            }

            std::ifstream::pos_type programLength = stream.tellg();

            Program p = {programLength, new Byte[programLength]};
            stream.seekg(0, std::ios::beg);
            stream.read(p.code, programLength);

            /*
            for (unsigned long pos = 0; pos < programLength; ++pos) {
                std::cout << +p.code[pos] << " ";
            }
            std::cout << std::endl;
            //*/

            return p;
        }

        PAX_FORCEINLINE void DestroyProgram(Program & p) {
            delete[] p.code;
            p.length = -1;
        }

        template<typename T>
        PAX_FORCEINLINE void Get(Program& program, Interpreter & interpreter, T * value) {
            *value = *reinterpret_cast<T*>(program.code + interpreter.currentAddress);
            interpreter.currentAddress += sizeof(T);

            //std::cout << "read " << +*value << " of size " << sizeof(T) << std::endl;
        }

        template<>
        PAX_FORCEINLINE void Get<std::string>(Program& program, Interpreter & interpreter, std::string * value) {
            // reinterpret_cast is unnecessary at the moment, but is just for type safety in the future.
            char* cstr = reinterpret_cast<char*>(program.code + interpreter.currentAddress);
            *value = cstr;
            // use strlen instead of value->size() to support multi-char encodings like utf8.
            interpreter.currentAddress += strlen(cstr) + 1 /*count null termination char*/;
        }

        PAX_FORCEINLINE bool isValid(const Program & program) {
            return program.length > 0;
        }

        PAX_FORCEINLINE void JumpTo(Program& program, Interpreter & interpreter, ProgramAddress address) {
            interpreter.currentAddress = address;
        }

        PAX_FORCEINLINE bool IsFinished(Program& program, Interpreter & interpreter) {
            return interpreter.currentAddress >= program.length;
        }
#else
        PAX_FORCEINLINE Program CreateProgram(const std::string & file) {
            return Program(file, std::ios::binary);
        }

        PAX_FORCEINLINE void DestroyProgram(Program & p) {
            // do nothing in respect to RAII
            // the ifstream destructor will do the job
        }

        template<typename T>
        PAX_FORCEINLINE void Get(Program& program, Interpreter & interpreter, T * value) {
            program.read(reinterpret_cast<char*>(value), sizeof(T));
            interpreter.currentAddress += sizeof(T);
        }

        template<>
        PAX_FORCEINLINE void Get<std::string>(Program& program, Interpreter & interpreter, std::string * value) {
            // read chars until we reach the 0 termination
            char next;
            // We do not expect empty strings here, so this is ok.
            Get(program, interpreter, &next);

            while(next != '\0') {
                *value += next;
                Get(program, interpreter, &next);
            }
        }

        PAX_FORCEINLINE bool isValid(const Program & program) {
            return program.good() && !program.bad();
        }

        PAX_FORCEINLINE void JumpTo(Program& program, Interpreter & interpreter, ProgramAddress address) {
            program.seekg(address, std::ios_base::beg);
            interpreter.currentAddress = address;
        }

        PAX_FORCEINLINE bool IsFinished(Program& program, Interpreter & interpreter) {
            return program.peek() == EOF;
        }
#endif
    }
}

#endif //MILBE_PROGRAM_H
