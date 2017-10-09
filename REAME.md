http://git.yoctoproject.org/cgit.cgi/poky/

mkdir yocto
git clone https://git.yoctoproject.org/git/poky
git clone https://github.com/Xilinx/meta-xilinx.git
cd poky
git checkout yocto-2.3 -b dev
source meta-dev/scripts/setup_meta-dev.sh dev-armv7 build
nano ../meta/conf/sanity.conf