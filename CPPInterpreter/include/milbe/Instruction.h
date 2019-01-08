//
// Created by Bittner on 08/01/2019.
//

#ifndef PAXMILB_INSTRUCTION_H
#define PAXMILB_INSTRUCTION_H

#include "Program.h"

namespace PAX {
    namespace Milbe {
        enum class Instruction : Byte {
            // nullary
            NOOP,

            RET,

            NEG,

            ADD,
            SUB,
            MUL,
            DIV,

            EQ,
            NEQ,
            LT,
            LEQ,
            GT,
            GEQ,

            YLD,

            // unary
            LOD, // 14
            LDV, // load var
            STO,
            STT, // store to

            JMP,
            JPC,
            CAL,

            PRT
        };

        constexpr auto LastInstruction = Instruction::PRT;
        using InstructionNumeral = unsigned;
    }
}

#endif //PAXMILB_INSTRUCTION_H
