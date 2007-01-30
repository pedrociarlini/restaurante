@echo off

set CP=tinapos.jar

set CP=%CP%;lib/l2fprod-common-tasks.jar
set CP=%CP%;lib/jasperreports-1.2.8.jar
set CP=%CP%;lib/jcommon-1.0.0.jar
set CP=%CP%;lib/jfreechart-1.0.0.jar
set CP=%CP%;lib/jdt-compiler-3.1.1.jar
set CP=%CP%;lib/commons-beanutils-1.5.jar
set CP=%CP%;lib/commons-collections-2.1.jar
set CP=%CP%;lib/commons-digester-1.7.jar
set CP=%CP%;lib/itext-1.3.1.jar
set CP=%CP%;lib/poi-2.0-final-20040126.jar
set CP=%CP%;lib/velocity-1.4.jar
set CP=%CP%;lib/bsh-core-2.0b4.jar
set CP=%CP%;lib/RXTXcomm.jar
set CP=%CP%;lib/jpos110.jar

rem Apache Axis SOAP libraries for SECPay payment gateway.
set CP=%CP%;lib/axis.jar
set CP=%CP%;lib/jaxrpc.jar
set CP=%CP%;lib/saaj.jar
set CP=%CP%;lib/commons-discovery-0.2.jar
set CP=%CP%;lib/commons-logging-1.0.4.jar

java -cp %CP% -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel -Djava.library.path=lib/Windows/i368-mingw32 net.adrianromero.tpv.forms.JFrmTPV
