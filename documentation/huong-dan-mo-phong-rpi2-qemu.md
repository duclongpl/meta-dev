Hướng dẫn mô phỏng raspberrypi2 
===============================

Download meta-dev
-----------------

```
~$ mkdir ~/working 
~$ cd ~/working 
~$ git clone https://github.com/duclongpl/meta-dev.git 
```

Download submodule
------------------

```
~$ cd ~/working/meta-dev 
~$ git submodule init 
~$ git submodule update 
```

Build rootfs cho raspberrypi2
-----------------------------

```
~$ cd ~/working
~$ source meta-dev/poky/oe-init-build-env build/
~$ cp ~/working/meta-dev/meta-image/conf/templates/* ~/working/build/conf/
~$ bitbake rpi2-minimal-image
```

Build kernel zImage cho raspberrypi2
------------------------------------

```
~$ bitbake linux-raspberrypi
```

Tạo folder mô phỏng
-------------------

```
~$ mkdir ~/QEMU-rpi2
~$ cd ~/QEMU-rpi2
```

* Copy rootfs:

```
~$ cp ~/working/build/tmp/deploy/images/raspberrypi2/rpi2-minimal-image-raspberrypi2.tar.bz2 ~/QEMU-rpi2
```

* Copy kernel:

```
~$ cp ~/working/build/tmp/deploy/images/raspberrypi2/zImage ~/QEMU-rpi2
```

* Copy device tree:

```
~$ cp ~/working/build/tmp/deploy/images/raspberrypi2/zImage-bcm2709-rpi-2-b.dtb ~/QEMU-rpi2
```

Tạo file "rpi2.img" để chứa rootfs
----------------------------------

```
~$ qemu-img create -f raw rpi2.img 200M
```

* Tạo partition table cho rpi2.img:

```
~$ sudo fdisk rpi2.img

    -> "n"      : tạo thêm một partition mới
    -> "p"      : chọn kiểu partition là "primary"
    -> "1"      : chọn partition thứ nhất 
    -> Enter    : chọn First sector mặc định là 2048
    -> "+20M"   : cài đặt dung lượng sector là 20M
    -> "t"      : thay đổi loại partition
    -> "83"     : loại partition LINUX
```

* Tương tự với partition 2:

```
    -> "n" -> "p" -> "2" -> Enter -> Enter -> "t" -> "2" -> "83" 
    -> "w" : ghi và lưu lại vào disk
```

Đổ rootfs vào rpi2.img sử dụng loop device
------------------------------------------

* Kết nối rpi2.img với loop device

```
~$ sudo losetup /dev/loop1 rpi2.img
```

* Liệt kê các partition:

```
~$ sudo fdisk -lu /dev/loop1

    Disk /dev/loop1: 200 MiB, 209715200 bytes, 409600 sectors
    Units: sectors of 1 * 512 = 512 bytes
    Sector size (logical/physical): 512 bytes / 512 bytes
    I/O size (minimum/optimal): 512 bytes / 512 bytes
    Disklabel type: dos
    Disk identifier: 0xa3534b98

    Device       Boot Start    End Sectors  Size Id Type
    /dev/loop1p1       2048  43007   40960   20M 83 Linux
    /dev/loop1p2      43008 409599  366592  179M 83 Linux
```

* Tạo loopback device:

```
~$ sudo losetup -o $((2048*512)) /dev/loop2 /dev/loop1
~$ sudo losetup -o $((43008*512)) /dev/loop3 /dev/loop1
```

* Tạo ext4 filesystem cho loopback device:

```
~$ sudo mkfs.ext4 /dev/loop2
~$ sudo mkfs.ext4 /dev/loop3
```

* Đổ rootfs vào rpi2.img:

```
~$ mkdir tmp
~$ sudo mount /dev/loop3 tmp/
~$ tar xjf rpi2-minimal-image-raspberrypi2.tar.bz2 -C tmp/
~$ sudo umount tmp/
```

Mô phỏng raspberrypi2 sử dụng QEMU
----------------------------------

```
~$ sudo qemu-system-arm -M raspi2 -kernel zImage -sd rpi2.img -append "rw earlyprintk  console=ttyAMA0,115200 root=/dev/mmcblk0p2 rootwait" -dtb zImage-bcm2709-rpi-2-b.dtb -serial stdio
```

