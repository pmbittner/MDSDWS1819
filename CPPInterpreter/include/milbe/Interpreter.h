//
// Created by Bittner on 08/01/2019.
//

#ifndef PAXMILB_INTERPRETER_H
#define PAXMILB_INTERPRETER_H

#include <sstream>
#include <iostream>
#include <array>

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

        PAX_MILBE_INTERPRETER_SUPPORTS(EQ)
        PAX_MILBE_INTERPRETER_SUPPORTS(NEQ)
        PAX_MILBE_INTERPRETER_SUPPORTS(LT)
        PAX_MILBE_INTERPRETER_SUPPORTS(LEQ)
        PAX_MILBE_INTERPRETER_SUPPORTS(GT)
        PAX_MILBE_INTERPRETER_SUPPORTS(GEQ)

        PAX_MILBE_INTERPRETER_SUPPORTS(YLD)
        PAX_MILBE_INTERPRETER_SUPPORTS(PRT)

        using InterpretFunction = void (*)(Program &, Interpreter &);
        constexpr size_t FunctionTableSize = size_t(LastInstruction) + 1;
        using FunctionTable = std::array<InterpretFunction, FunctionTableSize>;

        namespace detail {
            template<size_t... i>
            constexpr FunctionTable createFunctionTable(std::index_sequence<i...>) noexcept {
                return FunctionTable{{&interpretInstruction<Instruction(i)>...}};
            }

            constexpr FunctionTable createFunctionTable() noexcept {
                return createFunctionTable(std::make_index_sequence<FunctionTableSize>{});
            }
        }

        constexpr FunctionTable InstructionInterpreters = detail::createFunctionTable();

        /**
         * Convention for reading: LITTLE_ENDIAN
         */
        class Interpreter {
        public:
            // we need this because std::ifstream::tellg is not reliable
            ProgramAddress currentAddress;
            OperandStack operandStack;
            CallStack callStack;

            Interpreter();

            void interpret(Program & program);
        };
    }
}

#endif //PAXMILB_INTERPRETER_H
