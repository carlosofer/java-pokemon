package global;

import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Window extends JFrame implements KeyListener{
	
	int mspf = 100; // milliseconds per frame = 1000 / fps
					// For debugging purposes, fps = 10.
	
	// The Window class keeps track of the graphical output and user input (all keyboard for the time being).
	
	Insets insets = new Insets(0, 0, 0, 0);
	
	BufferStrategy bs = null;
	Graphics2D g2d = null;
	
	public Window(String caption){
		super(caption);
		
		addKeyListener(this);
		
		this.setSize(240, 180);
		this.setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
		
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(240, 180));

		this.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        this.pack();
        
		insets = this.getInsets();
		
        this.setVisible(true);
        
        // set up double buffering

		this.createBufferStrategy(2);
	    bs = this.getBufferStrategy();
	    g2d = (Graphics2D) bs.getDrawGraphics();
        
	}
	
	public void redraw_image(){
		g2d.drawImage(global.Managers.GraphMan.getRenderedPicture(), insets.left, insets.top, null);
		g2d.dispose();
		bs.show();
		g2d = (Graphics2D) bs.getDrawGraphics();
	}
	
	/* Key events */

	public void keyPressed(KeyEvent ke){
		global.Managers.InputMan.Form_KeyDown(ke.getKeyCode());
	}
	
	public void keyReleased(KeyEvent ke){
		global.Managers.InputMan.Form_KeyUp(ke.getKeyCode());
	}

	public void keyTyped(KeyEvent arg0) {
		
	}
	
}
