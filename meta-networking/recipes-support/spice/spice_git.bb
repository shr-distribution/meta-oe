#
# Copyright (C) 2013 Wind River Systems, Inc.
#

SUMMARY = "Simple Protocol for Independent Computing Environments"
DESCRIPTION = "SPICE (the Simple Protocol for Independent Computing \
Environments) is a remote-display system built for virtual \
environments which allows users to view a computing 'desktop' \ 
environment - not only on its computer-server machine, but also from \
anywhere on the Internet and using a wide variety of machine \
architectures."

LICENSE = "BSD & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

PV = "0.14.0+git${SRCPV}"

SRCREV_spice = "2a3d5624382ba49c4eb906e69697b92f79d06cf4"
SRCREV_spice-common = "4c2d0e977272c5540634d24f485dd64c424f6748"

SRCREV_FORMAT = "spice_spice-common"

SRC_URI = " \
    git://anongit.freedesktop.org/spice/spice;name=spice \
    git://anongit.freedesktop.org/spice/spice-common;destsuffix=git/spice-common;name=spice-common \
"
FOO = "\
    file://0001-build-allow-separated-src-and-build-dirs.patch \
"

S = "${WORKDIR}/git"

inherit autotools gettext pythonnative python-dir pkgconfig

DEPENDS += "spice-protocol jpeg pixman alsa-lib glib-2.0 python-pyparsing-native python-six-native glib-2.0-native"
DEPENDS_append_class-nativesdk = "nativesdk-openssl"

# Otherwise nativesdk-spice fails like this:
# http://errors.yoctoproject.org/Errors/Details/164866/
LDFLAGS_append_class-nativesdk = " -lssp"

export PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python"
export PYTHONPATH="${PKG_CONFIG_SYSROOT_DIR}${libdir}/python2.7/site-packages"

PACKAGECONFIG_class-native = ""
PACKAGECONFIG_class-nativesdk = ""
PACKAGECONFIG ?= "sasl"

PACKAGECONFIG[celt051] = "--enable-celt051,--disable-celt051,celt051"
PACKAGECONFIG[smartcard] = "--enable-smartcard,--disable-smartcard,libcacard,"
PACKAGECONFIG[sasl] = "--with-sasl,--without-sasl,cyrus-sasl,"
PACKAGECONFIG[client] = "--enable-client,--disable-client,,"
PACKAGECONFIG[gui] = "--enable-gui,--disable-gui,,"
PACKAGECONFIG[opengl] = "--enable-opengl,--disable-opengl,,"
PACKAGECONFIG[xinerama] = "--enable-xinerama,--disable-xinerama,libxinerama,"

COMPATIBLE_HOST = '(x86_64|i.86).*-linux'

BBCLASSEXTEND = "native nativesdk"
