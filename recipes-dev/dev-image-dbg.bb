require rfpf-image.bb

RFPF_IMAGE_PKGS += " \
	unixbench \
	make \
	glibc-utils \
	localedef \
	perl-module-posix \
	perl-module-time-hires \
	perl-module-io-handle \
	perl-module-findbin \
"
RFPF_IMAGE_PKGS += "ldd"
RFPF_IMAGE_PKGS += "strace"
RFPF_IMAGE_PKGS += "tcpdump"
RFPF_IMAGE_PKGS += "valgrind"
RFPF_IMAGE_PKGS += "hdparm"
RFPF_IMAGE_PKGS += "procps"
RFPF_IMAGE_PKGS += "psmisc"
RFPF_IMAGE_PKGS += "util-linux"
RFPF_IMAGE_PKGS += "openssh"
RFPF_IMAGE_PKGS += "iperf3"
RFPF_IMAGE_PKGS += "linux-libc-headers-dev"
