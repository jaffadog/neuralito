/**
 * 
 */
package edu.unicen.experimenter.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import jxl.write.WriteException;

/**
 * @author esteban
 * 
 */
public class Main implements Observer {

	private JFrame jFrame = null; // @jve:decl-index=0:visual-constraint="184,20"
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JDialog aboutDialog = null;
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private final Controller controller = new Controller(); // @jve:decl-index=0:

	private final DataSetsTableModel dataSetTableModel = new DataSetsTableModel();
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JPanel jPanel2 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	protected JFileChooser arfFileChooser;
	private JTextArea arfFileTxt = null;
	private JButton jButton3 = null;
	protected JFileChooser arfFileChooser2;
	private JTextArea arfFileTxt2 = null;
	private JTextArea jTextArea = null;
	private JScrollPane jScrollPane1 = null;
	private JDialog jDialog = null; // @jve:decl-index=0:visual-constraint="725,10"
	private JPanel jContentPane = null;
	private JTextField jExperimentName;
	private JTextField textFieldName;
	private JButton jButtonSelectDS;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JTextField jTextField = null;
	private JButton jButton4 = null;
	private final JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField1 = null;
	private JButton jButton5 = null;

	public Main() {
		super();
		arfFileChooser = new JFileChooser(".");
		arfFileChooser2 = new JFileChooser(".");
		final Collection<JTextArea> areas = new ArrayList<JTextArea>();
		areas.add(getJTextAreaAnalyzer());
		areas.add(getArfFileTxt());
		areas.add(getArfFileTxt2());
		System.setOut(new PrintStream(new TextAreaOutputStream(areas,
				System.out)));
		System.setErr(new PrintStream(new TextAreaOutputStream(areas,
				System.err)));
		controller.experimenter.addObserver(this);

	}

	/**
	 * @return
	 */
	private JTextArea getJTextAreaEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	private JTextArea getJTextAreaGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(419, 335);
			jFrame.setContentPane(getJTabbedPane());
			jFrame.setTitle("Application");
		}
		return jFrame;
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					final JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					final Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Version 1.0");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
		}
		return saveMenuItem;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Dataset Generator", null, getJPanel(), null);
			jTabbedPane.addTab("Evaluator", null, getJPanel1(), null);

			jTabbedPane.addTab(null, null, getJPanel3(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.add(getJButton2(), null);
			jPanel.add(getArfFileTxt(), null);

			jPanel.add(getJButton3(), null);
		}
		return jPanel;
	}

	/**
	 * @return
	 */
	private JTextField getJTextFieldName() {
		if (textFieldName == null) {
			textFieldName = new JTextField();
			textFieldName.setText("Enter DS Group Name...");
		}
		return textFieldName;

	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BoxLayout(getJPanel1(), BoxLayout.Y_AXIS));
			jPanel1.add(getJScrollPane(), null);
			jPanel1.add(getJPanel2(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new Dimension(453, 380));
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			dataSetTableModel.setData(controller.getDataSets());
			jTable.setModel(dataSetTableModel);

		}
		return jTable;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BoxLayout(getJPanel2(), BoxLayout.Y_AXIS));
			jPanel2.setPreferredSize(new Dimension(1, 400));

			jPanel2.add(getJTextFieldName());
			jPanel2.add(getJButtonSelectDS(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJScrollPane1(), null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJButton(), null);
		}
		return jPanel2;
	}

	/**
	 * @return
	 */
	private JButton getJButtonSelectDS() {
		if (jButtonSelectDS == null) {
			jButtonSelectDS = new JButton();
			jButtonSelectDS.setText("Select Dataset by name");
			jButtonSelectDS
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
								final java.awt.event.ActionEvent e) {

							final String dsName = getJTextFieldName().getText();
							dataSetTableModel.selectByName(dsName);

						}
					});
		}
		return jButtonSelectDS;
	}

	/**
	 * @return
	 */
	private JTextField getJTextField() {
		if (jExperimentName == null) {
			jExperimentName = new JTextField();
			jExperimentName.setText("Enter experiment name...");
		}

		return jExperimentName;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Start Experiment");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(final java.awt.event.ActionEvent e) {
					// System.out.println("There are "
					// + dataSetTableModel.getSelectedDataSets().size()
					// + "selected data sets");
					if (dataSetTableModel.getSelectedDataSets().size() == 0) {
						JOptionPane
								.showMessageDialog(null,
										"Eggs are not supposed to be green. So please select a dataset.");
					} else {
						final File xmlClassifiers = arfFileChooser2
								.getSelectedFile();
						try {
							final String experimentName = getJTextField()
									.getText();
							controller.evaluate(dataSetTableModel
									.getSelectedDataSets(), xmlClassifiers,
									experimentName);
						} catch (final Exception e1) {
							e1.printStackTrace();
						}
						// Event stub
						// actionPerformed()
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("LoadClassifierToUse");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(final java.awt.event.ActionEvent e) {
					arfFileChooser2.showOpenDialog(null);
					if (arfFileChooser2.getSelectedFile() != null) {
						arfFileTxt2.append(arfFileChooser2.getSelectedFile()
								.getPath()
								+ "\n");
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Load DataSets");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(final java.awt.event.ActionEvent e) {
					arfFileChooser.showOpenDialog(null);
					if (arfFileChooser.getSelectedFile() != null) {
						arfFileTxt.append(arfFileChooser.getSelectedFile()
								.getPath()
								+ "\n");
					}
				}
			});
		}

		return jButton2;
	}

	/**
	 * This method initializes arfFileTxt
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextArea getArfFileTxt() {
		if (arfFileTxt == null) {
			arfFileTxt = new JTextArea();
		}
		return arfFileTxt;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("startGeneration");

			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(final java.awt.event.ActionEvent e) {
					try {
						controller.generateDataSets(arfFileChooser
								.getSelectedFile());

					} catch (final Exception e1) {
						e1.printStackTrace();

					}
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes arfFileTxt2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextArea getArfFileTxt2() {
		if (arfFileTxt2 == null) {
			arfFileTxt2 = new JTextArea();
		}
		return arfFileTxt2;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaAnalyzer() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getArfFileTxt2());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getJDialog() {
		if (jDialog == null) {
			jDialog = new JDialog(getJFrame());
			jDialog.setSize(new Dimension(132, 75));
			jDialog.setContentPane(getJContentPane());
		}
		return jDialog;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BoxLayout(getJPanel3(), BoxLayout.Y_AXIS));
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			jLabel1 = new JLabel();
			jLabel1.setText("Export results by beach: ");
			final GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 2;
			final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 2;
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.add(jLabel1, gridBagConstraints2);
			jPanel4.add(getJTextField2(), gridBagConstraints);
			jPanel4.add(getJButton4(), gridBagConstraints1);

		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 2;
			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.gridx = 0;
			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			jLabel2 = new JLabel();
			jLabel2.setText("Export results by experiment name:");
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GridBagLayout());
			jPanel5.add(jLabel2, gridBagConstraints3);
			jPanel5.add(getJTextField1(), gridBagConstraints4);
			jPanel5.add(getJButton5(), gridBagConstraints5);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setText("Name                      .");
			jTextField.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("Export");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(final java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO
					arfFileChooser2.showOpenDialog(null);
					// if (arfFileChooser2.getSelectedFile() != null) {
					// arfFileTxt2.append(arfFileChooser2.getSelectedFile()
					// .getPath()
					// + "\n");
					// }
					final String fileName = arfFileChooser2.getSelectedFile()
							.getAbsolutePath();
					final String beach = getJTextField2().getText();
					try {
						controller.exportResultsByBeach(beach, fileName);
					} catch (final WriteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (final IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					final Desktop dt = Desktop.getDesktop();
					try {
						dt.open(new File(fileName + ".xls"));
					} catch (final IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("Export");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(final java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO
					arfFileChooser2.showOpenDialog(null);
					// if (arfFileChooser2.getSelectedFile() != null) {
					// arfFileTxt2.append(arfFileChooser2.getSelectedFile()
					// .getPath()
					// + "\n");
					// }
					final String fileName = arfFileChooser2.getSelectedFile()
							.getAbsolutePath();
					final String experimentName = getJTextField1().getText();
					try {
						controller.exportResultsByExperimentName(
								experimentName, fileName);
					} catch (final WriteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (final IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					final Desktop dt = Desktop.getDesktop();
					try {
						dt.open(new File(fileName + ".xls"));
					} catch (final IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}
			});
		}
		return jButton5;
	}

	/**
	 * Launches this application
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final Main application = new Main();
				application.getJFrame().setVisible(true);
			}
		});
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(final Observable arg0, final Object arg1) {
		System.out.println("Gui notified");
		dataSetTableModel.setData(controller.getDataSets());

	}

}
