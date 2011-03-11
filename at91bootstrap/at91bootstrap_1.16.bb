DESCRIPTION = "at91bootstrap: loaded into internal SRAM by AT91 BootROM"
SECTION = "bootloaders"
PR = "r0"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_nedap9263 = "1"

SRC_URI = " \
	http://www.atmel.com/dyn/resources/prod_documents/AT91Bootstrap${PV}.zip \
	file://romboot.patch \
	"

SRC_URI[md5sum] = "2d222312cf0af81c56db8747d6a38c7c"
SRC_URI[sha256sum] = "d66192a274247f4baa39fa932eadf903d7add55641d89d30402f967c4f2282a5"

SRC_URI_append_nedap9263 = " \
	file://nedap9263.patch \
	file://nandflash.patch \
	file://240MHz.patch \
	"
	
S = "${WORKDIR}/Bootstrap-v${PV}"
PACKAGE_ARCH = "${MACHINE_ARCH}"
EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} DESTDIR=${DEPLOY_DIR_IMAGE} REVISION=${PR}"

AT91BOOTSTRAP_MEDIA ?= "nandflash"

do_compile () {
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	rm -Rf ${S}/board/${MACHINE}/${AT91BOOTSTRAP_MEDIA}/${AT91BOOTSTRAP_MEDIA}_${MACHINE}.bin
	rm -Rf ${S}/board/${MACHINE}/${AT91BOOTSTRAP_MEDIA}/${AT91BOOTSTRAP_MEDIA}_${MACHINE}.elf
	rm -Rf ${S}/board/${MACHINE}/${AT91BOOTSTRAP_MEDIA}/${AT91BOOTSTRAP_MEDIA}_${MACHINE}.map
	oe_runmake -C ${S}/board/${MACHINE}/${AT91BOOTSTRAP_MEDIA} AT91_CUSTOM_FLAGS="${AT91BOOTSTRAP_FLAGS}" rebuild
}

do_install () {
	file=${MACHINE}-${AT91BOOTSTRAP_MEDIA}boot-${PV}-${PR}.bin
	dest=${DEPLOY_DIR_IMAGE}/${file}

	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755	\
		${S}/board/${MACHINE}/${AT91BOOTSTRAP_MEDIA}/${AT91BOOTSTRAP_MEDIA}_${MACHINE}.bin \
		${dest}

	rm -Rf ${DEPLOY_DIR_IMAGE}/at91bootstrap.bin
	ln -sf ${file} ${DEPLOY_DIR_IMAGE}/at91bootstrap.bin

	install -d ${D}/boot
	install -m 0755 \
		${S}/board/${MACHINE}/${AT91BOOTSTRAP_MEDIA}/${AT91BOOTSTRAP_MEDIA}_${MACHINE}.bin \
		${D}/boot/${file}

	ln -sf ${file} ${D}/boot/at91bootstrap.bin
}

FILES_${PN} = "/"

