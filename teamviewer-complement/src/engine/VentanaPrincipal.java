package engine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Proceso proceso;
	private JLabel lblActivo;
	private JButton btnDesactivar;
	private JButton btnActivar;
	private VentanaLogs ventanalogs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	


	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/images/icon.png")));
		setTitle("Daemon Teamviewer Monitor");
		proceso = new Proceso();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 200);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnConfiguracin = new JMenu("Configuración");
		menuBar.add(mnConfiguracin);
		
		JMenuItem mntmVerLogs = new JMenuItem("Ver logs");
		mntmVerLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanalogs = new VentanaLogs(proceso.getLogs());
			}
		});
		mnConfiguracin.add(mntmVerLogs);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Proyecto desarrollado por Victor Carreras @2018", "Versión 1.2", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAyuda.add(mntmAcercaDe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblEstado = new JLabel("Estado:");
		
		lblActivo = new JLabel();
		lblActivo.setFont(new Font("Dialog", Font.BOLD, 12));
		
		btnActivar = new JButton("Activar");
		btnActivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proceso.activarServicio();
				if(proceso.getEstado()) {
					if(ventanalogs!=null) {
						ventanalogs.actualizarLogs(proceso.getLogs());
					}
					JOptionPane.showMessageDialog(null, " Servicio activado con éxito");
					cambiarEstado();
				}else {
					JOptionPane.showMessageDialog(null, "Ups, no se ha podido activar el demonio. Por favor, ponte en contacto con tu administrador del sistema", "Error al activar el servicio", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		btnDesactivar = new JButton("Desactivar");
		
		/** Si pulsas el boton desactivar */ 
		btnDesactivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(proceso.desactivarServicio()) {
					
					if(ventanalogs!=null) {
						// Si la ventana de logs está abierta refrescala
						ventanalogs.actualizarLogs(proceso.getLogs());
					}
					JOptionPane.showMessageDialog(null, " Servicio desactivado con éxito");
					cambiarEstado();
				}else {
					JOptionPane.showMessageDialog(null, "Ups, no se ha podido desactivar el demonio. Por favor, ponte en contacto con tu administrador del sistema", "Error al desactivar el servicio", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(84)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnActivar)
						.addComponent(lblEstado))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDesactivar))
					.addGap(133))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblActivo)
						.addComponent(lblEstado))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDesactivar)
						.addComponent(btnActivar))
					.addContainerGap(173, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		cambiarEstado();
	}
	private void cambiarEstado() {
		
		if(proceso.getEstado()) { // El proceso está activo
			lblActivo.setText("Activo");
			lblActivo.setForeground(new Color(0, 128, 0));
			btnActivar.setEnabled(false);
			btnDesactivar.setEnabled(true);
		}else { // El proceso está inactivo
			lblActivo.setText("Inactivo");
			lblActivo.setForeground(new Color(255, 0, 0));
			btnDesactivar.setEnabled(false);
			btnActivar.setEnabled(true);
		}
	}
}
