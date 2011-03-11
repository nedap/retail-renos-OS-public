PR = "r0"
require u-boot.inc

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_nedap9263 = "1"

SRC_URI = " \
	ftp://ftp.denx.de/pub/u-boot/u-boot-${PV}.tar.bz2 \
    	file://nand-watchdog-fix.patch \
        file://fw_env.config \
	"

SRC_URI[md5sum] = "6dcc2cc47ecc96d4da984556d601a8c0"
SRC_URI[sha256sum] = "96e7f9a0790a385256ee42845ccd7aa5bbd1866eebca2ee8755bf9bef189f121"

SRC_URI_append_nedap9263 = " \
	file://nedap9263.patch \
	file://2xxMHz.patch \
	"

TARGET_LDFLAGS = ""

inherit base

