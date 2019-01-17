//
// Created by Bittner on 08/01/2019.
//

#include <sstream>
#include <iostream>

#include <milbe/Interpreter.h>
#include <milbe/Program.h>

namespace PAX {
    namespace Milbe {
        Interpreter::Interpreter() = default;

        void Interpreter::interpret(Program & program) {
            currentAddress = 0;
            callStack.push(currentAddress);

            Instruction instruction;

            while (!IsFinished(program, *this)) {
                Get(program, *this, &instruction);
                InstructionInterpreters.at(static_cast<InstructionNumeral>(instruction))(program, *this);
            }
        }

#define PAX_MILBE_POP_OPERANDSTACK interpreter.operandStack.top(); interpreter.operandStack.pop();
#define PAX_MILBE_GENERATE_BINARY_OPERATION(name, op) \
template<> \
void interpretInstruction<Instruction::name>(Program & program, Interpreter & interpreter) { \
    Value operand2 = PAX_MILBE_POP_OPERANDSTACK \
    Value operand1 = PAX_MILBE_POP_OPERANDSTACK \
    interpreter.operandStack.push(operand1 op operand2); \
}

        template<>
        void interpretInstruction<Instruction::NOOP>(Program & program, Interpreter & interpreter) {}

        template<>
        void interpretInstruction<Instruction::LOD>(Program & program, Interpreter & interpreter) {
            Value value;
            Get(program, interpreter, &value);
            interpreter.operandStack.push(value);
        }

        template<>
        void interpretInstruction<Instruction::LDV>(Program & program, Interpreter & interpreter) {
            Variable var;
            Get(program, interpreter, &var);
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
            Get(program, interpreter, &var);
            Value val = PAX_MILBE_POP_OPERANDSTACK;
            interpreter.callStack.top().variableRegister[var] = val;
        }

        template<>
        void interpretInstruction<Instruction::JMP>(Program & program, Interpreter & interpreter) {
            ProgramAddress jumpTarget;
            Get(program, interpreter, &jumpTarget);
            JumpTo(program, interpreter, jumpTarget);
        }

        template<>
        void interpretInstruction<Instruction::JPC>(Program & program, Interpreter & interpreter) {
            ProgramAddress jumpTarget;
            Get(program, interpreter, &jumpTarget);

            Value condition = PAX_MILBE_POP_OPERANDSTACK;

            if (condition)
                JumpTo(program, interpreter, jumpTarget);
        }

        template<>
        void interpretInstruction<Instruction::CAL>(Program & program, Interpreter & interpreter) {
            ProgramAddress jumpTarget;
            Get(program, interpreter, &jumpTarget); // Important: Read before retrieving current address!
            interpreter.callStack.push(interpreter.currentAddress);
            JumpTo(program, interpreter, jumpTarget);
        }

        template<>
        void interpretInstruction<Instruction::RET>(Program & program, Interpreter & interpreter) {
            ProgramAddress returnAddress = interpreter.callStack.top().returnAddress;
            interpreter.callStack.pop();
            JumpTo(program, interpreter, returnAddress);
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
            std::string str;
            Get(program, interpreter, &str);
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
#undef PAX_MILBE_POP_OPERANDSTACK
    }
}