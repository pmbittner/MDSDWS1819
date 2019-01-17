//
// Created by Bittner on 08/01/2019.
//

#ifndef PAXMILB_INSTRUCTION_H
#define PAXMILB_INSTRUCTION_H

#include "Types.h"

namespace PAX {
    namespace Milbe {
        enum class Instruction : Byte {
            // nullary
            NOOP = 0,

            RET = 1,

            NEG = 2,

            ADD = 3,
            SUB = 4,
            MUL = 5,
            DIV = 6,

            EQ  = 7,
            NEQ = 8,
            LT  = 9,
            LEQ = 10,
            GT  = 11,
            GEQ = 12,

            YLD = 13,

            // unary
            LOD = 14, // 14
            LDV = 15, // load var
            STO = 16,
            STT = 17, // store to

            JMP = 18,
            JPC = 19,
            CAL = 20,

            PRT = 21
        };

        using InstructionNumeral = unsigned;
        constexpr auto LastInstruction = Instruction::PRT;
        constexpr auto LastInstructionIndex = InstructionNumeral(LastInstruction);
    }
}

#endif //PAXMILB_INSTRUCTION_H
