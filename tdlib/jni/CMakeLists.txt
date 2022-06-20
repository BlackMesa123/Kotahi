cmake_minimum_required(VERSION 3.4.1)

option(TD_ENABLE_JNI "Enable JNI-compatible TDLib API" ON)

set(TD_DIR ${CMAKE_CURRENT_SOURCE_DIR}/td)
list(APPEND CMAKE_FIND_ROOT_PATH "${CMAKE_CURRENT_SOURCE_DIR}/out/${ANDROID_ARCH_NAME}")

set(CMAKE_CXX_FLAGS_MINSIZEREL "${CMAKE_CXX_FLAGS_MINSIZEREL} -flto=thin -Oz")
add_subdirectory(${TD_DIR} td)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14 -fno-omit-frame-pointer -fno-exceptions -fno-rtti")
set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,--gc-sections -Wl,--exclude-libs,ALL")

add_library(tdjni SHARED td_jni.cpp)

target_link_libraries(tdjni PRIVATE Td::TdStatic)
target_compile_definitions(tdjni PRIVATE PACKAGE_NAME="org/drinkless/tdlib")