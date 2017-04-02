import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements ActionListener,KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Sound noise = new Sound();
    String death = ("audio/death.wav");
    String ball_bounce = ("audio/bounce.wav");
    
	Timer tick = new Timer(15, this); //tick speed
	
	int p1_x = 5, p1_y = 215, p2_x = 480, p2_y = 215;
	int p1_score = 0, p2_score = 0;
	Boolean p1_up = false, p1_down = false;
	Boolean p2_up = false, p2_down = false;
	final int Y_VEL = 5; //player speed
	
	Boolean new_game = true;
	Boolean new_round = true;
	Boolean ball_move = false; //when true moves in normal bouncing behavior
	Boolean ball_start = false; // when true it moves in straight line
	Boolean ball_side = null; //left is true, right is false
	int ball_x = 245, ball_y = 245;
	int ball_y_cent = ball_y + 5;
	double ball_vel = 1;
	
	Boolean ball_p1down = false, ball_p1up = false; // controls sideways movement from ball
	Boolean ball_p2down = false, ball_p2up = false;
	
	public void init(){
		tick.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		g.fillRect(p1_x, p1_y, 10, 80);
		g.fillRect(p2_x, p2_y, 10, 80);
		g.fillRect(245, 0, 10, 500);
		
		g.fillOval(ball_x, ball_y, 10, 10);
		g.setColor(Color.BLACK);
		g.drawOval(ball_x, ball_y, 10, 10);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("System", Font.PLAIN, 40)); 
		g.drawString(String.valueOf(p1_score), 200, 50);
		g.drawString(String.valueOf(p2_score), 275, 50);
		g.setFont(new Font("System", Font.PLAIN, 10));
		g.drawString("Ball Speed: " + ball_vel, 300, 440);
		g.setFont(new Font("System", Font.ITALIC, 10));
		g.drawString("Chandler Bone v1.3", 20, 470);
		
		g.setFont(new Font("System", Font.PLAIN, 10));
		
		if(new_game == true)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.BLACK);
		
		g.drawString("Controls:", 300, 100);
		g.drawString("R = Restart Game", 300, 120);
		g.drawString("Space = Start Game", 300, 130);
		g.drawString("W & S = P1 Controls", 300, 140);
		g.drawString("Up & Down = P2 Controls", 300, 150);
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
		
		ball_y_cent = ball_y + 5;
		
		if(p1_up == true)
			p1_goup();
		if(p1_down == true)
			p1_godown();
		if(p2_up == true)
			p2_goup();
		if(p2_down == true)
			p2_godown();
		if(ball_start == true)
			ball_startmove();
		if(ball_x > 468 && ball_x < 480)
			p2_bounce();
		if(ball_x > 4 && ball_x < 16)
			p1_bounce();
		if(ball_p1up == true)
			ball_p1goup();
		if(ball_p1down == true)
			ball_p1godown();
		if(ball_p2up == true)
			ball_p2goup();
		if(ball_p2down == true)
			ball_p2godown();
		if(ball_y_cent - 5 <= 0 || ball_y_cent + 5 >= 470)
			wall_bounce();
		if(ball_x <= 0 || ball_x >= 500)
			next_round();
			
	}
	
	public void keyPressed(KeyEvent e){
		int code = e.getKeyCode();
		//System.out.println(code);
		
		if(code == KeyEvent.VK_W)
			p1_up = true;
		if(code == KeyEvent.VK_S)
			p1_down = true;
		if(code == KeyEvent.VK_UP)
			p2_up = true;
		if(code == KeyEvent.VK_DOWN)
			p2_down = true;
		if(code == KeyEvent.VK_SPACE && new_round == true){
			ball_start = true;
			new_round = false;
			new_game = false;}
		if(code == KeyEvent.VK_R)
			restart();
	}

	public void keyReleased(KeyEvent e){
		int code = e.getKeyCode();
		//System.out.println(code);
		
		if(code == KeyEvent.VK_W)
			p1_up = false;
		if(code == KeyEvent.VK_S)
			p1_down = false;
		if(code == KeyEvent.VK_UP)
			p2_up = false;
		if(code == KeyEvent.VK_DOWN)
			p2_down = false;
	}
	
	public void keyTyped(KeyEvent e){}
	
	public void p1_goup(){
		if(p1_y >= 5)
			p1_y -= Y_VEL;
	}
	public void p1_godown(){
		if(p1_y <= 390)
			p1_y += Y_VEL;
	}
	public void p2_goup(){
		if(p2_y >= 5)
			p2_y -= Y_VEL;
	}
	public void p2_godown(){
		if(p2_y <= 390)
			p2_y += Y_VEL;
	}
	
	public void ball_startmove(){
		if(ball_side == null)
			ball_side = setRandomSide();
		else if(ball_side == false)
			ball_x += ball_vel;
		else
			ball_x -= ball_vel;
			
			
	}
	public void ball_p1goup(){
		ball_x += ball_vel;
		ball_y -= ball_vel * .5;
	}
	public void ball_p1godown(){
		ball_x += ball_vel;
		ball_y += ball_vel * .5;
	}
	public void ball_p2goup(){
		ball_x -= ball_vel;
		ball_y -= ball_vel * .5;
	}
	public void ball_p2godown(){
		ball_x -= ball_vel;
		ball_y += ball_vel * .5;
	}
	
	public void p1_bounce(){
		//System.out.println(ball_y + " || " + p1_y);
		if (ball_y_cent >= (p1_y - 4) && ball_y_cent <= (p1_y + 40)){
			//System.out.print("p1top ");
			ball_start = false;
			ball_p1up = true;
			ball_p2up = false;
			ball_p2down = false;
			noise.playSound(ball_bounce);}
			
		if (ball_y_cent >= (p1_y + 41) && ball_y_cent <= (p1_y + 84)){
			//System.out.print("p2bottom ");
			ball_start = false;
			ball_p1down = true;
			ball_p2up = false;
			ball_p2down = false;
			noise.playSound(ball_bounce);}
		
		
		if (ball_vel == 1)
			ball_vel = 2.25;
		
		
		ball_vel += .25;
	}
	public void p2_bounce(){
		//System.out.println(ball_y + " || " + p2_y);
		if (ball_y_cent >= p2_y && ball_y_cent <= (p2_y + 40)){
			//System.out.print("p2top ");
			ball_start = false;
			ball_p2up = true;
			ball_p1up = false;
			ball_p1down = false;
			noise.playSound(ball_bounce);}
		
		if (ball_y_cent >= (p2_y + 41) && ball_y_cent <= (p2_y + 84)){
			//System.out.print("p2bottom ");
			ball_start = false;
			ball_p2down = true;
			ball_p1up = false;
			ball_p1down = false;
			noise.playSound(ball_bounce);}
		
		if (ball_vel == 1)
			ball_vel = 2.25;
		
		
		ball_vel += .25;
	}
	
	public void wall_bounce(){
		if(ball_p1up == true){
			ball_p1up = false;
			ball_p1down = true;
			ball_y += ball_vel * 2;}
		
		else if(ball_p1down == true){
			ball_p1down = false;
			ball_p1up = true;
			ball_y -= ball_vel * 2;}
		
		else if(ball_p2up == true){
			ball_p2up = false;
			ball_p2down = true;
			ball_y += ball_vel * 2;}
		
		else if(ball_p2down == true){
			ball_p2down = false;
			ball_p2up = true;
			ball_y -= ball_vel * 2;}
		
		
	}
	
	public void next_round(){
		
		noise.playSound(death);
		
		if(ball_x <= 0)
			p2_score += 1;
		if(ball_x >= 500)
			p1_score += 1;
		
		new_round = true;
		ball_start = false;
		ball_p1up = false;
		ball_p1down = false;
		ball_p2up = false;
		ball_p2down = false;
		
		if (ball_side == true)
			ball_side = false;
		else if (ball_side == false)
			ball_side = true;
		
		ball_x = 245;
		ball_y = 245;
		ball_vel = 1;
	}
	public void restart(){
		next_round();
		new_game = true;
		p1_score = 0;
		p2_score = 0;
		p1_y = 215;
		p2_y = 215;
		
		ball_side = setRandomSide();
	}
	
	public Boolean setRandomSide(){
			return Math.random() > .5;
	}
	

}