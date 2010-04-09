/**
 * 
 */
package edu.unicen.experimenter.gui;

import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * @author esteban
 * 
 */
public class TextAreaOutputStream extends OutputStream {

	private final JTextArea textArea;

	public TextAreaOutputStream(final JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void flush() {
		textArea.repaint();
	}

	@Override
	public void write(final int b) {
		textArea.append(new String(new byte[] { (byte) b }));
	}
}