cmake_minimum_required(VERSION 3.4.1)

add_library( # Sets the name of the library.
helloJni
# Sets the library as a shared library.
SHARED
# Provides a relative path to your source file(s).
src/main/jni/com_lpan_mine_jnitest_HelloJni.c)

find_library( # Sets the name of the path variable.
log-lib
# Specifies the name of the NDK library that
# you want CMake to locate.
log )

target_link_libraries( # Specifies the target library.
helloJni
# Links the target library to the i library
# included in the NDK.
${log-lib} )
