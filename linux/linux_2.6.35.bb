require linux.inc

R = "10"
PR = "r${R}"
V = "linux-${PV}.${R}"
S = "${WORKDIR}/${V}"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_nedap9263 = "1"

SRC_URI = " \
	${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/${V}.tar.bz2 \
	file://defconfig \
	"

SRC_URI[md5sum] = "8d3732acee1b7ae9c26c70be15b8c8cc"
SRC_URI[sha256sum] = "dc85afbf821b633bc9c9144fc5b6cc7a12fbcd984ba11f45924958ff7bb384bd"

SRC_URI_append_nedap9263 = " \
	file://2.6.30-at91-exp-0009-Add-MACB-TX-Buffer-in-SRAM-support.patch \
	file://nedap9263.patch \
	file://rs485-port1and2.patch \
	file://spi.patch \
	file://2xxMHz.patch \
	"

