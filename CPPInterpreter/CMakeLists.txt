cmake_minimum_required(VERSION 3.13)
project(paxmilb)

set(CMAKE_CXX_STANDARD 11)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /O2")
message("Compiling with options ${CMAKE_CXX_FLAGS}")

IF(PAX_MILBE_CACHE_PROGRAM)
    ADD_DEFINITIONS(-DPAX_MILBE_CACHE_PROGRAM=1)
ENDIF(PAX_MILBE_CACHE_PROGRAM)

include_directories(include)

set(INCLUDES_FOR_CLION include/milbe/Program.h include/milbe/Interpreter.h include/milbe/Instruction.h include/milbe/OperandStack.h include/milbe/CallStack.h include/milbe/ProgramAddress.h include/milbe/VariableRegister.h)
set(SOURCE_FILES src/Interpreter.cpp src/CallStack.cpp include/milbe/Types.h include/milbe/ProgramDefinition.h)

add_library(paxmilb ${INCLUDES_FOR_CLION} ${SOURCE_FILES})
add_executable(Milbe src/main.cpp)
target_link_libraries(Milbe paxmilb)