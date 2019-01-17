//
// Created by Bittner on 08/01/2019.
//

#ifndef PAXMILB_TYPES_H
#define PAXMILB_TYPES_H

#include <cstdint>

#define PAX_FORCEINLINE __forceinline

namespace PAX {
    namespace Milbe {
        using Byte = char;
        using Variable = uint32_t;
        using Value = uint32_t;
    }
}

#endif //PAXMILB_TYPES_H
