package VISTA;

import CONTROLADOR.DoctorController;
import MODELO.Doctor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class JFDoctor extends javax.swing.JFrame {

    private JTextField txtIdDoctor;
    private JTextField txtNombre;
    private JTextField txtEspecialidad;
    private JTextField txtTelefono;
    private JCheckBox chkActivo;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnDesactivar;
    private JButton btnSalir;
    private JTable tableDoctores;
    private DefaultTableModel tableModel;

    private DoctorController controller;

    public JFDoctor() {
        initComponents();
        this.controller = new DoctorController(); // Inicialización del controlador

        // Configuración de la ventana
        setTitle("Gestión de Doctores");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2)); // Layout en grid de 8 filas y 2 columnas

        // Inicializar componentes
        txtIdDoctor = new JTextField();
        txtNombre = new JTextField();
        txtEspecialidad = new JTextField();
        txtTelefono = new JTextField();
        chkActivo = new JCheckBox("Activo");
        btnAgregar = new JButton("Agregar Doctor");
        btnActualizar = new JButton("Actualizar Doctor");
        btnDesactivar = new JButton("Desactivar Doctor");
        btnSalir = new JButton("Salir");

        // Inicializar JTable y DefaultTableModel
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Especialidad", "Teléfono", "Activo"}, 0);
        tableDoctores = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableDoctores);
        tableDoctores.setFillsViewportHeight(true); // Para llenar el área de scroll

        // Agregar componentes a la ventana
        add(new JLabel("ID Doctor:"));
        add(txtIdDoctor);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Especialidad:"));
        add(txtEspecialidad);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(chkActivo);
        add(btnAgregar);
        add(btnActualizar);
        add(btnDesactivar);
        add(btnSalir);
        add(scrollPane); // Usar el JScrollPane con JTable

        // Acciones de los botones
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDoctor();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDoctor();
            }
        });

        btnDesactivar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desactivarDoctor();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cierra la ventana actual
                ((JFrame) ((JButton) e.getSource()).getTopLevelAncestor()).dispose();
            }
        });

        // Cargar lista de doctores en el área de texto
        cargarDoctores();
    }

    private void agregarDoctor() {
        String nombre = txtNombre.getText();
        String especialidad = txtEspecialidad.getText();
        String telefono = txtTelefono.getText();

        if (controller.agregarDoctor(nombre, especialidad, telefono)) {
            JOptionPane.showMessageDialog(this, "Doctor agregado con éxito");
            cargarDoctores();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar doctor");
        }
    }

    private void actualizarDoctor() {
        try {
            int idDoctor = Integer.parseInt(txtIdDoctor.getText());
            String nombre = txtNombre.getText();
            String especialidad = txtEspecialidad.getText();
            String telefono = txtTelefono.getText();
            boolean activo = chkActivo.isSelected();

            if (controller.actualizarDoctor(idDoctor, nombre, especialidad, telefono, activo)) {
                JOptionPane.showMessageDialog(this, "Doctor actualizado con éxito");
                cargarDoctores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar doctor");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Doctor inválido");
        }
    }

    private void desactivarDoctor() {
        try {
            int idDoctor = Integer.parseInt(txtIdDoctor.getText());

            if (controller.desactivarDoctor(idDoctor)) {
                JOptionPane.showMessageDialog(this, "Doctor desactivado con éxito");
                cargarDoctores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al desactivar doctor");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Doctor inválido");
        }
    }

    private void cargarDoctores() {
        List<Doctor> doctores = controller.obtenerDoctores();
        tableModel.setRowCount(0); // Limpiar el modelo antes de cargar

        for (Doctor doctor : doctores) {
            tableModel.addRow(new Object[]{
                doctor.getIdDoctor(),
                doctor.getNombre(),
                doctor.getEspecialidad(),
                doctor.getTelefono(),
                doctor.isActivo() ? "Sí" : "No"
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFDoctor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFDoctor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFDoctor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFDoctor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFDoctor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
