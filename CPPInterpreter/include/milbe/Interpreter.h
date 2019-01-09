//
// Created by Bittner on 08/01/2019.
//

#ifndef PAXMILB_INTERPRETER_H
#define PAXMILB_INTERPRETER_H

#include <sstream>
#include <iostream>

#include "Program.h"
#include "Instruction.h"
#include "OperandStack.h"
#include "CallStack.h"

#define PAX_MILBE_INTERPRETER_SUPPORTS(instruction)  \
template<> \
void interpretInstruction<Instruction::instruction>(Program & program, Interpreter & interpreter);

namespace PAX {
    namespace Milbe {
        class Interpreter;

        template<Instruction instruction>
        void interpretInstruction(Program & program, Interpreter & interpreter) {
            std::stringstream messageStream;
            messageStream << "The Instruction " << InstructionNumeral(instruction) << " is not supported!";
            throw std::logic_error(messageStream.str());
        }

        PAX_MILBE_INTERPRETER_SUPPORTS(NOOP)
        PAX_MILBE_INTERPRETER_SUPPORTS(LOD)
        PAX_MILBE_INTERPRETER_SUPPORTS(LDV)
        PAX_MILBE_INTERPRETER_SUPPORTS(STO)
        PAX_MILBE_INTERPRETER_SUPPORTS(STT)

        PAX_MILBE_INTERPRETER_SUPPORTS(NEG)

        PAX_MILBE_INTERPRETER_SUPPORTS(JMP)
        PAX_MILBE_INTERPRETER_SUPPORTS(JPC)
        PAX_MILBE_INTERPRETER_SUPPORTS(CAL)
        PAX_MILBE_INTERPRETER_SUPPORTS(RET)

        PAX_MILBE_INTERPRETER_SUPPORTS(ADD)
        PAX_MILBE_INTERPRETER_SUPPORTS(SUB)
        PAX_MILBE_INTERPRETER_SUPPORTS(MUL)
        PAX_MILBE_INTERPRETER_SUPPORTS(DIV)

        PAX_MILBE_INTERPRETER_SUPPORTS(YLD)
        PAX_MILBE_INTERPRETER_SUPPORTS(PRT)

        /**
         * Convention for reading: LITTLE_ENDIAN
         */
        class Interpreter {
            template<Instruction instruction>
            friend void interpretInstruction(Program & program, Interpreter & interpreter);

            // Array of function pointers with length = LastInstruction
            static void(*instructionInterpreters[static_cast<int>(LastInstruction) + 1])(Program&, Interpreter&);

            OperandStack operandStack;
            CallStack callStack;

            // we need this because std::ifstream::tellg is not reliable
            ProgramAddress currentAddress;

        public:
            template<Instruction i>
            static void initFunctionTable() {
                constexpr auto index = InstructionNumeral(i);
                instructionInterpreters[index] = &interpretInstruction<i>;
                initFunctionTable<Instruction(index + 1)>();
            }

            template<>
            static void initFunctionTable<LastInstruction>() {
                instructionInterpreters[LastInstructionIndex] = &interpretInstruction<LastInstruction>;
            }

            static void initialize();

            Interpreter();

            void interpret(Program & program);
        };
    }
}

#endif //PAXMILB_INTERPRETER_H
