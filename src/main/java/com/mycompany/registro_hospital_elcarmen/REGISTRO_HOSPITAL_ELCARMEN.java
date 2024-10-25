package com.mycompany.registro_hospital_elcarmen;

import CONEXION.Conexion;
import VISTA.JFInicio;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import javax.swing.UIManager;


public class REGISTRO_HOSPITAL_ELCARMEN {

    public static void main(String[] args) {
        try {
            // Establecer el Look and Feel de HiFi desde JTattoo
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFInicio().setVisible(true);
            }
        });
    }
}
