#!/bin/bash

set -e

cd tdlib/jni

if ! [ -d "td" ] || ! [ "$(ls -A td)" ]; then
	echo -e "\033[31mFailed! Submodule 'td' not found!\033[0m"
	echo -e "\033[31mRun this script from the repo root folder or run: 'git submodule update --init --recursive'\033[0m"
	exit
fi

OPENSSL_VERSION=$(openssl version | awk '{print $2}')
if ! [[ $OPENSSL_VERSION == "1.1.1"* ]]; then
	echo -e "\033[31mFailed! openssl is either not installed or not version 1.1.1\033[0m"
	exit
fi

cd td

sed -i -e '/21724/,+3d' ./td/telegram/Td.cpp

mkdir -p build_native
cd build_native

if [ -z "$var" ]; then
	JAVA_HOME=`/usr/libexec/java_home`
fi

cmake -DJAVA_INCLUDE_PATH="$(JAVA_HOME)/include" -DJAVA_INCLUDE_PATH2="$(JAVA_HOME)/include/darwin" -DJAVA_AWT_INCLUDE_PATH="$(JAVA_HOME)/include" -DTD_ENABLE_JNI=ON ..
cmake --build . --target prepare_cross_compiling
