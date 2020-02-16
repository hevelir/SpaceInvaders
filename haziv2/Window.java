package haziv2;

import java.awt.*;

import javax.swing.*;

public class Window extends Canvas{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Window konstruktora, letrehozza az ablakot, megadott szelesseggel, magassaggal, cimmel, es adott Ingame peldannyal.
	 * @param width
	 * @param height
	 * @param title
	 * @param in
	 */

	public Window(int width, int height, String title, Ingame in) {
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(in);
		frame.setVisible(true);
		in.start();
		
	}
}
