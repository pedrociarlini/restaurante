@echo off

set CP=tinapos.jar

java -cp %CP% -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel net.adrianromero.tpv.forms.JFrmConfig
