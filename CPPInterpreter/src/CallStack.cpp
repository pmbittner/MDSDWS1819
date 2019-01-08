//
// Created by Bittner on 08/01/2019.
//

#include <milbe/CallStack.h>

namespace PAX {
    namespace Milbe {
        CallStackFrame::CallStackFrame(ProgramAddress returnAddress) : returnAddress(returnAddress) {

        }
    }
}