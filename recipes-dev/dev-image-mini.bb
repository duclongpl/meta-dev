DESCRIPTION = "Create Image of ubinux${PV}"
ALLOW_EMPTY = "1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

IMAGE_INSTALL = " \
	${CORE_BOOT_MINIMAL} \
	${ROOTFS_PKGMANAGE_BOOTSTRAP} \
	${CORE_IMAGE_EXTRA_INSTALL} \
"
CORE_BOOT_MINIMAL = " \
	base-files \
	base-passwd \
	busybox \
	initscripts \
	mingetty \
	modutils-initscripts \
"
RFPF_IMAGE_PKGS = " \
	iptables \
	lxc \
	swupdate \
	zabbix \
"

DEPENDS = ""
RDEPENDS = ""

inherit core-image
