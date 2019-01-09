//
// Created by Bittner on 08/01/2019.
//

#include <milbe/Interpreter.h>

#include <sstream>
#include <iostream>

namespace PAX {
    namespace Milbe {
        void(*Interpreter::instructionInterpreters[InstructionNumeral(LastInstruction) + 1])(Program&, Interpreter&);

        static inline InstructionNumeral readNextInstruction(Program & program, Instruction & instruction) {
            Byte b;
            program.read(&b, sizeof(char));
            instruction = static_cast<Instruction>(b);
            return InstructionNumeral(instruction);
        }

        Interpreter::Interpreter() = default;

        void Interpreter::initialize() {
            initFunctionTable<Instruction(0)>();
        }

        void Interpreter::interpret(PAX::Milbe::Program &program) {
            currentAddress = 0;
            callStack.push(currentAddress);

            Instruction instruction;
            InstructionNumeral instructionIndex;

            while (true) {
                instructionIndex = readNextInstruction(program, instruction);
                currentAddress += sizeof(Byte);

                if (instructionIndex > LastInstructionIndex) {
                    break;
                }

                instructionInterpreters[instructionIndex](program, *this);
            }

            std::cout << "[Interpreter::interpret] Done" << std::endl;
        }

#define PAX_MILBE_READ(name) { \
    program.read(reinterpret_cast<char *>(&name), sizeof(name)); \
    interpreter.currentAddress += sizeof(name); \
}
#define PAX_MILBE_JUMP_TO(address) { \
    interpreter.currentAddress = address; \
    program.seekg(address, std::ios_base::beg); \
}
#define PAX_MILBE_POP_OPERANDSTACK interpreter.operandStack.top(); interpreter.operandStack.pop();
#define PAX_MILBE_POP_BINARY_OPERATORS \
Value operand2 = PAX_MILBE_POP_OPERANDSTACK \
Value operand1 = PAX_MILBE_POP_OPERANDSTACK
#define PAX_MILBE_GENERATE_BINARY_OPERATION(name, op) \
template<> \
void interpretInstruction<Instruction::name>(Program & program, Interpreter & interpreter) { \
    PAX_MILBE_POP_BINARY_OPERATORS \
    interpreter.operandStack.push(operand1 op operand2); \
}

        template<>
        void interpretInstruction<Instruction::NOOP>(Program & program, Interpreter & interpreter) {}

        template<>
        void interpretInstruction<Instruction::LOD>(Program & program, Interpreter & interpreter) {
            Value value;
            PAX_MILBE_READ(value)
            interpreter.operandStack.push(value);
        }

        template<>
        void interpretInstruction<Instruction::LDV>(Program & program, Interpreter & interpreter) {
            Variable var;
            PAX_MILBE_READ(var)
            Value val = interpreter.callStack.top().variableRegister[var];
            interpreter.operandStack.push(val);
        }

        template<>
        void interpretInstruction<Instruction::STO>(Program & program, Interpreter & interpreter) {
            interpreter.operandStack.pop();
        }

        template<>
        void interpretInstruction<Instruction::STT>(Program & program, Interpreter & interpreter) {
            Variable var;
            PAX_MILBE_READ(var)
            Value val = PAX_MILBE_POP_OPERANDSTACK;
            interpreter.callStack.top().variableRegister[var] = val;
        }

        template<>
        void interpretInstruction<Instruction::JMP>(Program & program, Interpreter & interpreter) {
            ProgramAddress jumpTarget;
            PAX_MILBE_READ(jumpTarget)
            PAX_MILBE_JUMP_TO(jumpTarget);
        }

        template<>
        void interpretInstruction<Instruction::JPC>(Program & program, Interpreter & interpreter) {
            ProgramAddress jumpTarget;
            PAX_MILBE_READ(jumpTarget)

            Value condition = PAX_MILBE_POP_OPERANDSTACK;

            if (condition)
                PAX_MILBE_JUMP_TO(jumpTarget);
        }

        template<>
        void interpretInstruction<Instruction::CAL>(Program & program, Interpreter & interpreter) {
            ProgramAddress jumpTarget;
            PAX_MILBE_READ(jumpTarget) // Important: Read before retrieving current address!
            interpreter.callStack.push(interpreter.currentAddress);
            PAX_MILBE_JUMP_TO(jumpTarget)
        }

        template<>
        void interpretInstruction<Instruction::RET>(Program & program, Interpreter & interpreter) {
            ProgramAddress returnAddress = interpreter.callStack.top().returnAddress;
            interpreter.callStack.pop();
            PAX_MILBE_JUMP_TO(returnAddress);
        }

        template<>
        void interpretInstruction<Instruction::NEG>(Program & program, Interpreter & interpreter) {
            Value val = PAX_MILBE_POP_OPERANDSTACK;
            interpreter.operandStack.push(Value(!static_cast<bool>(val)));
        }

        template<>
        void interpretInstruction<Instruction::YLD>(Program & program, Interpreter & interpreter) {
            Value val = PAX_MILBE_POP_OPERANDSTACK;
            std::cout << val << std::endl;
        }

        template<>
        void interpretInstruction<Instruction::PRT>(Program & program, Interpreter & interpreter) {
            // read chars until we reach the 0 termination
            std::string str;
            char next;
            // We do not expect empty strings here, so this is ok.
            PAX_MILBE_READ(next)

            while(next != '\0') {
                str += next;
                PAX_MILBE_READ(next)
            }

            std::cout << str;
        }

        PAX_MILBE_GENERATE_BINARY_OPERATION(ADD, +)
        PAX_MILBE_GENERATE_BINARY_OPERATION(SUB, -)
        PAX_MILBE_GENERATE_BINARY_OPERATION(MUL, *)
        PAX_MILBE_GENERATE_BINARY_OPERATION(DIV, /)

        PAX_MILBE_GENERATE_BINARY_OPERATION(EQ, ==)
        PAX_MILBE_GENERATE_BINARY_OPERATION(NEQ, !=)
        PAX_MILBE_GENERATE_BINARY_OPERATION(LT, <)
        PAX_MILBE_GENERATE_BINARY_OPERATION(LEQ, <=)
        PAX_MILBE_GENERATE_BINARY_OPERATION(GT, >)
        PAX_MILBE_GENERATE_BINARY_OPERATION(GEQ, >=)

#undef PAX_MILBE_GENERATE_BINARY_OPERATION
#undef PAX_MILBE_POP_BINARY_OPERATORS
#undef PAX_MILBE_POP_OPERANDSTACK
#undef PAX_MILBE_JUMP_TO
#undef PAX_MILBE_READ
    }
}