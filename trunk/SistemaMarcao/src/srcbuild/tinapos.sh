#!/bin/sh

CP=tinapos.jar

CP=$CP:lib/l2fprod-common-tasks.jar
CP=$CP:lib/jasperreports-1.2.8.jar
CP=$CP:lib/jcommon-1.0.0.jar
CP=$CP:lib/jfreechart-1.0.0.jar
CP=$CP:lib/jdt-compiler-3.1.1.jar
CP=$CP:lib/commons-beanutils-1.5.jar
CP=$CP:lib/commons-collections-2.1.jar
CP=$CP:lib/commons-digester-1.7.jar
CP=$CP:lib/itext-1.3.1.jar
CP=$CP:lib/poi-2.0-final-20040126.jar
CP=$CP:lib/velocity-1.4.jar
CP=$CP:lib/bsh-core-2.0b4.jar
CP=$CP:lib/RXTXcomm.jar
CP=$CP:lib/jpos110.jar

# Apache Axis SOAP libraries for SECPay payment gateway.
CP=$CP:lib/axis.jar
CP=$CP:lib/jaxrpc.jar
CP=$CP:lib/saaj.jar
CP=$CP:lib/commons-discovery-0.2.jar
CP=$CP:lib/commons-logging-1.0.4.jar

java -cp $CP -Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel -Djava.library.path=lib/Linux/i686-unknown-linux-gnu net.adrianromero.tpv.forms.JFrmTPV
