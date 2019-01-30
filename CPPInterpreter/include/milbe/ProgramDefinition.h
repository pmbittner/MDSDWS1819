//
// Created by Bittner on 17/01/2019.
//

#ifndef PAXMILB_PROGRAMDEFINITION_H
#define PAXMILB_PROGRAMDEFINITION_H

#if PAX_MILBE_CACHE_PROGRAM
#include "Types.h"
#else
#include <fstream>
#endif


namespace PAX {
    namespace Milbe {
#if PAX_MILBE_CACHE_PROGRAM
        struct Program {
            int64_t length = 0;
            Byte* code = nullptr;
        };
#else
        using Program = std::ifstream;
#endif
    }
}
#endif //PAXMILB_PROGRAMDEFINITION_H
