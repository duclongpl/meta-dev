DESCRIPTION = "Create Image of rpi${PV}"
ALLOW_EMPTY = "1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

IMAGE_INSTALL = " \
        ${CORE_BOOT_MINIMAL} \
        ${CORE_IMAGE_EXTRA_INSTALL} \
"
CORE_BOOT_MINIMAL = " \
        base-files \
        base-passwd \
        busybox \
        initscripts \
        mingetty \
        modutils-initscripts \
        packagegroup-core-boot \
"

DEPENDS = ""
RDEPENDS = ""

inherit core-image
