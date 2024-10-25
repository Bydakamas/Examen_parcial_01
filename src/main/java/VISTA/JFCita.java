package VISTA;

import CONTROLADOR.CitaController;
import MODELO.Cita;
import MODELO.Doctor;
import MODELO.DoctorDAO;
import MODELO.Paciente;
import MODELO.PacienteDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class JFCita extends javax.swing.JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnSalir;
    private CitaController citaController;

    public JFCita() {
        citaController = new CitaController();  // Inicializa el controlador
        setTitle("Gestión de Citas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración de la tabla
        tableModel = new DefaultTableModel(new Object[]{"ID Cita", "Nombre Doctor", "Nombre Paciente", "Fecha", "Hora", "Descripción", "Estado"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Botones
        btnAgregar = new JButton("Agregar Cita");
        btnActualizar = new JButton("Actualizar Cita");
        btnEliminar = new JButton("Eliminar Cita");
        btnSalir = new JButton("Salir");

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnSalir);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Cargar citas al iniciar
        cargarCitas();

        // Acciones de los botones
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCita();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCita();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCita();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cierra la ventana actual
                ((JFrame) ((JButton) e.getSource()).getTopLevelAncestor()).dispose();
            }
        });
    }

    private void cargarCitas() {
        List<Cita> citas = citaController.listarCitas();
        tableModel.setRowCount(0); // Limpiar tabla antes de cargar

        for (Cita cita : citas) {
            // Obtener nombre del paciente
            Paciente paciente = new PacienteDAO().buscarPacientePorId(cita.getIdPaciente());
            String nombrePaciente = (paciente != null) ? paciente.getNombre() : "Desconocido";

            // Obtener nombre del doctor (asumiendo que tienes un método similar en un DoctorDAO)
            Doctor doctor = new DoctorDAO().buscarDoctorPorId(cita.getIdDoctor()); // Asegúrate de tener esta clase y método
            String nombreDoctor = (doctor != null) ? doctor.getNombre() : "Desconocido";

            // Agregar fila a la tabla
            tableModel.addRow(new Object[]{
                cita.getIdCita(),
                nombreDoctor, // Mostrar nombre del doctor
                nombrePaciente, // Mostrar nombre del paciente
                cita.getFecha(),
                cita.getHora(),
                cita.getDescripcion(),
                cita.getEstado()
            });
        }
    }

    private void agregarCita() {
        // Crear un formulario para agregar una nueva cita
        JTextField tfIdDoctor = new JTextField();
        JTextField tfNombrePaciente = new JTextField();
        JTextField tfDniPaciente = new JTextField();
        JTextField tfTelefonoPaciente = new JTextField();
        JTextField tfFecha = new JTextField(); // formato: YYYY-MM-DD
        JTextField tfHora = new JTextField();  // formato: HH:mm
        JTextField tfDescripcion = new JTextField();
        JTextField tfEstado = new JTextField();

        Object[] message = {
            "ID Doctor:", tfIdDoctor,
            "Nombre Paciente:", tfNombrePaciente,
            "DNI Paciente:", tfDniPaciente,
            "Teléfono Paciente:", tfTelefonoPaciente,
            "Fecha (YYYY-MM-DD):", tfFecha,
            "Hora (HH:mm):", tfHora,
            "Descripción:", tfDescripcion,
            "Estado:", tfEstado
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Obtener valores
            int idDoctor = Integer.parseInt(tfIdDoctor.getText());
            String nombrePaciente = tfNombrePaciente.getText();
            String dniPaciente = tfDniPaciente.getText();
            String telefonoPaciente = tfTelefonoPaciente.getText();

            LocalDate fecha = LocalDate.parse(tfFecha.getText());
            LocalTime hora = LocalTime.parse(tfHora.getText());
            String descripcion = tfDescripcion.getText();
            String estado = tfEstado.getText();

            // Primero, agregar el paciente
            Paciente nuevoPaciente = new Paciente();
            nuevoPaciente.setNombre(nombrePaciente);
            nuevoPaciente.setDni(dniPaciente);
            nuevoPaciente.setTelefono(telefonoPaciente);

            // Agregar paciente al controlador
            if (citaController.agregarPaciente(nuevoPaciente)) {
                // Obtener el ID del paciente recién agregado
                int idPaciente = nuevoPaciente.getIdPaciente(); // Asegúrate de que el ID se establece correctamente en el DAO

                // Luego, agregar la cita
                if (citaController.agregarCita(dniPaciente, idDoctor, fecha, hora, descripcion, estado)) {
                    JOptionPane.showMessageDialog(this, "Cita agregada exitosamente.");
                    cargarCitas(); // Recargar la lista después de agregar
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar la cita.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el paciente.");
            }
        }
    }

    private void actualizarCita() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idCita = (int) tableModel.getValueAt(selectedRow, 0);

            // Crear un formulario para actualizar la cita
            String estadoActual = (String) tableModel.getValueAt(selectedRow, 6);
            String descripcionActual = (String) tableModel.getValueAt(selectedRow, 5);

            JTextField tfEstado = new JTextField(estadoActual);
            JTextField tfDescripcion = new JTextField(descripcionActual);

            Object[] message = {
                "Estado:", tfEstado,
                "Descripción:", tfDescripcion
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Actualizar Cita", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String nuevoEstado = tfEstado.getText();
                String nuevaDescripcion = tfDescripcion.getText();

                if (citaController.actualizarCita(idCita, nuevoEstado, nuevaDescripcion)) {
                    JOptionPane.showMessageDialog(this, "Cita actualizada exitosamente.");
                    cargarCitas(); // Recargar la lista después de actualizar
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la cita.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una cita para actualizar.");
        }
    }

    private void eliminarCita() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idCita = (int) tableModel.getValueAt(selectedRow, 0);
            if (citaController.eliminarCita(idCita)) {
                JOptionPane.showMessageDialog(this, "Cita eliminada exitosamente.");
                cargarCitas(); // Recargar la lista después de eliminar
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la cita.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una cita para eliminar.");
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
            java.util.logging.Logger.getLogger(JFCita.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFCita.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFCita.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFCita.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFCita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
