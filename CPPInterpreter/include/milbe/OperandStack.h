//
// Created by Bittner on 08/01/2019.
//

#ifndef PAXMILB_OPERANDSTACK_H
#define PAXMILB_OPERANDSTACK_H

#include <stack>
#include "VariableRegister.h"

namespace PAX {
    namespace Milbe {
        using OperandStack = std::stack<Value>;
    }
}
#endif //PAXMILB_OPERANDSTACK_H
