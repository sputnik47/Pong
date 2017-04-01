import java.awt.*;
import javax.swing.*;

public class Display extends Canvas{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int HEIGHT = 500;
	static final int WIDTH = 500;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame();
		Board anim = new Board();
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setTitle("Pong");
		frame.add(anim);
		anim.init();
		
		frame.setVisible(true);

	}

}
