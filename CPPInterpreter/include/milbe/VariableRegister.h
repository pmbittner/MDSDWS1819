//
// Created by Bittner on 08/01/2019.
//

#ifndef PAXMILB_VARIABLEREGISTER_H
#define PAXMILB_VARIABLEREGISTER_H

#include <unordered_map>
#include "Types.h"

namespace PAX {
    namespace Milbe {
        // TODO: Make this more efficient
        using VariableRegister = std::unordered_map<Variable, Value>;
    }
}

#endif //PAXMILB_VARIABLEREGISTER_H
