#!/bin/bash

set -e

cd tdlib/jni

if ! [ -d "openssl" ] || ! [ "$(ls -A openssl)" ]; then
	echo -e "\033[31mFailed! Submodule 'openssl' not found!\033[0m"
	echo -e "\033[31mTry to run: 'git submodule update --init --recursive'\033[0m"
	exit
fi

if [ -z "$NDK" -a "$NDK" == "" ]; then
	echo -e "\033[31mFailed! NDK is empty. Run 'export NDK=[PATH_TO_NDK]'\033[0m"
	exit
fi

export ANDROID_NDK=$NDK
HOST_ARCH=$(printf `ls $NDK/toolchains/llvm/prebuilt`)
export PATH=$NDK/toolchains/llvm/prebuilt/$HOST_ARCH/bin:$PATH
JOBS=`grep processor /proc/cpuinfo|wc -l`

cd openssl

function build {
	for arg in "$@"; do
		echo -e "\033[32mBuilding ${arg}...\033[0m"

		if [ -f "Makefile" ] ; then
			make clean
			rm Makefile
			rm configdata.pm
		fi
	
		case "${arg}" in
			x86_64)
				ARCH=x86_64
				./Configure android-x86_64 no-shared -D__ANDROID_API__=23
			;;
			arm64)
				ARCH=arm64
				./Configure android-arm64 no-shared -D__ANDROID_API__=23
			;;
			arm)
				ARCH=arm
				./Configure android-arm no-shared -D__ANDROID_API__=23 -D__ARM_MAX_ARCH__=8
			;;
			x86)
				ARCH=x86
				./Configure android-x86 no-shared -D__ANDROID_API__=23
			;;
			*)
				echo -e "\033[31mFailed! ARCH not valid: ${arg}\033[0m"
				exit
			;;
		esac

		sed -i 's/-O3/-O3 -ffunction-sections -fdata-sections/g' Makefile
		make depend -s
		make -j$JOBS -s
		
		mkdir -p ../out/$ARCH/lib/
		cp libcrypto.a libssl.a ../out/$ARCH/lib/
		cp -r include ../out/$ARCH/
		
		if [ -f "Makefile" ] ; then
			make clean
			rm Makefile
			rm configdata.pm
		fi
	done
}

if (( $# == 0 )); then
	build x86_64 arm64 arm x86
else
	build $@
fi
