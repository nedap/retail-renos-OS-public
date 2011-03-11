DESCRIPTION = "The SAM Boot Assistant (SAM-BA) software provides a means of easily programming different Atmel AT91SAM devices"
HOMEPAGE = "http://www.atmel.com/dyn/products/tools_card.asp?tool_id=3883"
SECTION = "devel"

PR = "r0"

inherit native

SRC_URI = " \
	http://www.codesourcery.com/downloads/public/public/gnu_toolchain/arm-none-eabi/arm-${PV}-arm-none-eabi-i686-pc-linux-gnu.tar.bz2 \
	"

S = "${WORKDIR}/arm-2009q3"

SRC_URI[md5sum] = "e133e37f617910541804634f10a17f6e"
SRC_URI[sha256sum] = "467104b61dc8b2bf561b47ff65b937aa169bcd41be3243805a962d73272d04b8"

do_stage() {
	cp -r arm-none-eabi bin lib libexec ${STAGING_DIR_NATIVE}/usr
}

