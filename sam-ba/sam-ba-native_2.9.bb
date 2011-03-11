DESCRIPTION = "The SAM Boot Assistant (SAM-BA) software provides a means of easily programming different Atmel AT91SAM devices"
HOMEPAGE = "http://www.atmel.com/dyn/products/tools_card.asp?tool_id=3883"
SECTION = "devel"

PR = "r0"

DEPENDS ="arm-none-eabi-native"

inherit native

SRC_URI = " \
	http://www.atmel.com/dyn/resources/prod_documents/sam-ba_${PV}_cdc_linux.zip \
	file://nedap9263.patch \
	file://ubuntu-10.04.patch \
	"

SRC_URI[md5sum] = "1c18e4f9998c90df516792cdd59839ec"
SRC_URI[sha256sum] = "98924a19cd429f0b0b955e2496a47807fd23d0fc99bb1315b1bbc5a75a03f6d5"

S = "${WORKDIR}/sam-ba_cdc_${PV}.linux_cdc_linux"
CC = "arm-none-eabi-"
CFLAGS = ""
LDFLAGS = ""

addtask copystuff before do_patch after do_unpack
do_copystuff() {
	cd ${S}
	cp -r applets/at91lib/boards/at91sam9263-ek applets/at91lib/boards/nedap9263
	mkdir applets/isp-project/tcl_lib/nedap9263
	cp applets/isp-project/tcl_lib/at91sam9263-ek/at91sam9263-ek.tcl applets/isp-project/tcl_lib/nedap9263/nedap9263.tcl
}

do_compile () {
	cd applets/isp-project
	for memory in extram
	do
		cd $memory
		make clean CHIP=at91sam9263 BOARD=nedap9263 BOARD_DIR=nedap9263 CROSS_COMPILE=${CC} sram
		cd ..
	done
	for memory in nandflash dataflash eeprom serialflash norflash
	do
		cd $memory
		make clean CHIP=at91sam9263 BOARD=nedap9263 BOARD_DIR=nedap9263 CROSS_COMPILE=${CC} sdram
		cd ..
	done
}

do_stage() {
	cp -r applets sam-ba ${STAGING_BINDIR}
}

