#!/bin/sh

CP=tinapos.jar

java -cp $CP -Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel net.adrianromero.tpv.forms.JFrmConfig
