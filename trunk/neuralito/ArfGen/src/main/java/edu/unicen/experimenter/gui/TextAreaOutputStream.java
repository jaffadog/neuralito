/**
 * 
 */
package edu.unicen.experimenter.gui;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;

import javax.swing.JTextArea;

/**
 * @author esteban
 * 
 */
public class TextAreaOutputStream extends OutputStream {

	private final Collection<JTextArea> textAreas;
	private final PrintStream originalStream;

	public TextAreaOutputStream(final Collection<JTextArea> textArea,
			final PrintStream originalStream) {
		textAreas = textArea;
		this.originalStream = originalStream;
	}

	@Override
	public void flush() {
		for (final JTextArea textArea : textAreas) {
			textArea.repaint();
		}
		originalStream.flush();

	}

	@Override
	public void write(final int b) {
		for (final JTextArea textArea : textAreas) {
			textArea.append(new String(new byte[] { (byte) b }));
		}
		originalStream.write(b);

	}
}