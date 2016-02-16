import javax.swing.JFrame;
import java.lang.Thread;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
public class LifeFrame extends JFrame implements Runnable
{
	private static boolean getRandomBool()
	{
		return Math.random() >.5;
	}
	int width;
	int height;
	int cw, ch;
	int cx = 50, cy= 50;
	boolean running = true;
	boolean[][] living = new boolean[cx][cy];
	
	public LifeFrame()
	{
		super("Conway's Game of Life");	
		
		width = 750;
		cw = width/cx;
		height = 750;
		ch = height/cy;
		this.setSize(width,height);
		this.setResizable(false);
		this.setVisible(true);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(new KeyListener()
					{
					public void keyPressed(KeyEvent e)
					{
					}
					public void keyReleased(KeyEvent e)
					{
					if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE)
					{
						if(running){running = false;}
						else{running = true;}
					}
					}
					public void keyTyped(KeyEvent e){}
					});
		this.addMouseListener(new MouseListener()
				{
					boolean clicked = false;
					public void mousePressed(MouseEvent e)
					{
						clicked = true;
						int xpos = e.getX();
						int ypos = e.getY();
						xpos = xpos/cw;
						ypos = ypos/ch;
						if(xpos > 0 && xpos < cx &&ypos > 0 &&ypos <cy)
						{
							if(living[xpos][ypos]) 
		       					{
								living[xpos][ypos]= false;
							}
							else
							{
								living[xpos][ypos] = true;
							}
							repaint();
						}
					}
					public void mouseReleased(MouseEvent e)
					{
						clicked = false;
					}		
				
					public void mouseEntered(MouseEvent e){}
					public void mouseExited(MouseEvent e){}
					public void mouseClicked(MouseEvent e){}
				});
			init();
	}

	private void init()
	{
		for(int i = 0; i < cx; i++)
		{
			for(int j = 0; j < cy; j++)
			{
				living[i][j] = getRandomBool();
			}
		}
		Thread t = new Thread(this);
		t.start();
	}
	public void run()
	{	
		while(true)
		{
			if(running)
			{
				tick();
				repaint();
				try
				{
					Thread.sleep(1000);
				}
				catch(Exception e){}
			}
		}
	}

	private void tick()
	{
		for(int i = 0; i < cx; i++)
		{
			for(int j = 0; j < cy; j++)
			{
				int sum=countcell(i,j);
				if(living[i][j])
				{
					if(sum < 2 || sum ==4)
					{
						living[i][j] = false;
					}		
					else if(sum ==2 || sum==3)
					{
						living[i][j] = true;
					}
				}
				else
				{
					if(sum == 3)
					{
						living[i][j] = true;
					}
					else
					{
						living[i][j] = false;
					}
				}
			}
		}
	}

	private void stop()
	{
		running = false;
	}

	private int countcell(int x, int y)
	{
		int dx = -1;
		int dy = 0;
		int sum = 0;
		
		for(int i = 0; i < 4; i++)
		{
			if(checkcell(x+dx,y+dy))
			{
				sum++;
			}
			if(i ==0)
			{
				dx = 1;
			}
			if(i ==1)
			{
				dx = 0;
				dy = 1;
			}
			if(i==2)
			{
				dy = -1;
			}
		}
		return sum;
	}
	
	private boolean checkcell(int xc, int yc)
	{
		if(xc < 0 || xc>=cx || yc <0||yc >= cy)
		{
			return false;
		}
		else
		{
			return living[xc][yc];
		}
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		for(int i = 0; i < cx; i++)
		{
			for(int j = 0; j < cy; j++)
			{
				if(living[i][j])
				{
					g.setColor(Color.black);
					g.fillRect(i*cw,j*ch,cw,ch);
				}
				else
				{
					g.setColor(Color.white);
					g.fillRect(i*cw,j*ch,cw,ch);
				}
			}
		}
		g.setColor(Color.black);
		for(int i = cw; i < 750; i+=cw)
		{
				g.drawLine(i,0,i,750);
				g.drawLine(0,i,750,i);
		}

	}
}
