import javax.swing.JFrame;
import java.lang.Thread;
import java.awt.Graphics;
import java.awt.Color;
public class LifeFrame extends JFrame
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
	boolean[][] living;
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
		//Add mouse listener
		init();
	}

	private void init()
	{
		living = new boolean[cx][cy];
		for(int i = 0; i < cx; i++)
		{
			for(int j = 0; j < cy; j++)
			{
				living[i][j] = getRandomBool();
			}
		}
		while(running)
		{
			tick();
			repaint();
			try
			{
			Thread.sleep(1000);
			}
			catch(Exception e)
			{
			
			}
		}
	}

	private void tick()
	{
		boolean[][] temp = new boolean[cx][cy];
		for(int i = 0; i < cx; i++)
		{
			for(int j = 0; j < cy; j++)
			{
				int sum=countcell(i,j);
				if(living[i][j])
				{
					if(sum < 2 || sum ==4)
					{
						temp[i][j] = false;
					}		
					else if(sum ==2 || sum==3)
					{
						temp[i][j] = true;
					}
				}
				else
				{
					if(sum == 3)
					{
						temp[i][j] = true;
					}
					else
					{
						temp[i][j] = false;
					}
				}
			}
		}
		living = temp;
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
		for(int i = cw; i < 750; i+=cw)
		{
				g.drawLine(i,0,i,750);
				g.drawLine(0,i,750,i);
		}
		for(int i = 0; i < cx; i++)
		{
			for(int j = 0; j < cy; j++)
			{
				if(living[i][j])
				{
					g.fillRect(i*cw,j*ch,cw,ch);
				}
			}
		}
	}
}
