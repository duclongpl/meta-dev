FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Fix issue when compile poky with mender
SRC_URI += "\
  file://v2017.11-fw-utils-build-fix.patch \ 
"