#!/bin/sh

if [ "x$0" = "x./setup_meta-dev.sh" ] ; then
	echo "Error: This script needs to be sourced. "
	echo "Please run as '. ./setup_meta-dev.sh <machine> <build>'"
	exit 1
fi

if [ $# -ne 2 ] ; then
	echo "usage: $0 <machine> <build>"
	exit 1
fi

MACHINE=$1
BUILD=$2
DOWNLOADS=/home/yocto/yocto_dl_v16

export TEMPLATECONF="meta-poky/conf"

echo -e "\nNOTICE : If you want to change bblayer.conf, change it under meta-dev"
cat << EOF >> ${TEMPLATECONF}/bblayers.conf.sample
BBLAYERS += "\\
  ##COREBASE##/meta-dev \\
  "
EOF
DEVROOT="`pwd`"

. ./oe-init-build-env ${BUILD}


add_conf_append ()
{
cat << EOF >> conf/local.conf
MACHINE = "${MACHINE}"
#BB_NUMBER_THREADS = "4"
#PARALLEL_MAKE = "-j 8"
SOURCE_MIRROR_URL ?= "file://${DOWNLOADS}"
INHERIT += "own-mirrors"
BB_GENERATE_MIRROR_TARBALLS = "1"
DL_DIR ?= "${DOWNLOADS}"

NO_RECOMMENDATIONS = "1"

IMAGE_LINGUAS = ""
INHERIT += "buildhistory"
BUILDHISTORY_COMMIT = "1"

EOF
}


if [ -f $BUILDDIR/conf/local.conf ]; then
	have_set=`grep '^MACHINE =' ${BUILDDIR}/conf/local.conf |tail -n 1 |awk '{print $3}'`
	if [ "x$have_set" != "x\"$MACHINE\"" ]; then
		add_conf_append
	fi
else 
	add_conf_append
fi
