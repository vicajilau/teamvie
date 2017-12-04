package engine;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class VentanaLogs extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea;

	/**
	 * Actualiza el valor del cuadro de texto
	 */
	public void actualizarLogs(String texto) {
		textArea.setText(texto);
	}
	
	/**
	 * Create the dialog.
	 */
	public VentanaLogs(String texto) {
		setTitle("Logs del sistema");
		setBounds(100, 100, 700, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{	
				textArea = new JTextArea(texto);
				textArea.setEditable(false);
				scrollPane.setViewportView(textArea);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnArchivo = new JMenu("Archivo");
				menuBar.add(mnArchivo);
				{
					JMenuItem mntmExportar = new JMenuItem("Exportar");
					mntmExportar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						
							JFileChooser fc=new JFileChooser();	//Creamos el objeto JFileChooser
							fc.setSelectedFile(new File("daemon-log." + LocalDate.now() + ".txt"));
							fc.setFileSelectionMode(JFileChooser.FILES_ONLY);	//Indicamos lo que podemos seleccionar				
							FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");	//Creamos el filtro		
							fc.setAcceptAllFileFilterUsed(false);	//Bloqueamos que pueda aceptar el filtro de todos los archivos
							fc.setFileFilter(filtro);	//Le indicamos el filtro	
							int seleccion=fc.showSaveDialog(getContentPane());	//Abrimos la ventana, guardamos la opcion seleccionada por el usuario						
							if(seleccion==JFileChooser.APPROVE_OPTION){	//Si el usuario, pincha en aceptar						     
							File fichero=fc.getSelectedFile();	//Seleccionamos el fichero
							
								if(!fichero.exists()) {
									try(FileWriter fw=new FileWriter(fichero)){
							 
							        //Escribimos el texto en el fichero
							        fw.write(textArea.getText());
							 
									} catch (IOException e1) {
										e1.printStackTrace();
							    	}
								}else {
									int resultado = JOptionPane.showConfirmDialog(null, "El fichero ya existe, ¿deseas sobreescribirlo?", "Advertencia de escritura de datos", JOptionPane.INFORMATION_MESSAGE);
									
									if (resultado == JOptionPane.OK_OPTION) {
										try(FileWriter fw=new FileWriter(fichero)){
										 
											//Escribimos el texto en el fichero
											fw.write(textArea.getText());
											
								 
										} catch (IOException e1) {
											e1.printStackTrace();
								    	}
									}else {
										JOptionPane.showMessageDialog(null, "Operación cancelada");
									}
								}
							}
						}
					});
					mnArchivo.add(mntmExportar);
				}
			}
		}
		setVisible(true);
	}

}
