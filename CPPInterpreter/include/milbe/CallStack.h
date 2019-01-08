//
// Created by Bittner on 08/01/2019.
//

#ifndef PAXMILB_CALLSTACK_H
#define PAXMILB_CALLSTACK_H

#include <stack>
#include "ProgramAddress.h"
#include "VariableRegister.h"

namespace PAX {
    namespace Milbe {
        struct CallStackFrame {
            VariableRegister variableRegister;
            ProgramAddress returnAddress;

            /*implicit*/ CallStackFrame(ProgramAddress returnAddress);
        };

        using CallStack = std::stack<CallStackFrame>;
    }
}
#endif //PAXMILB_CALLSTACK_H
