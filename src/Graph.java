import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Graph extends JPanel{
	//static int homeX=100,homeY=100,homeHP=1000,homeFood=0;
	//static double homeRad=10;
	
	static ArrayList <Home> homes = new ArrayList<Home>();
	static ArrayList <int[]> walls = new ArrayList<int[]>();
	static int deleteDots=2000;
	public Graph()
	{
		/*homes.add(new Home(100,100));
		homes.add(new Home(679,483));
		
		for(int i = 0; i<50;i++)
		{
			ants.add(new Ant(100,100,Math.random()*360,0));
		}
		
		for(int i = 50; i<100;i++)
		{
			ants.add(new Ant(679,483,Math.random()*360,1));
		}*/
		
		Timer t = new Timer(10,new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
				
			}
			
		});
		Timer t2 = new Timer(10,new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i<workspeed;i++)
				{
					countAnts();
					countPoints();
				}
			}
			
		});
		t2.start();
		t.restart();
	}

	
	
	
	public void countAnts()
	{
		for(int i= 0; i<ants.size();i++)
		{
			Ant sd = ants.get(i);
			sd.move();
			sd.countThings();
			if(sd.HP<=0)ants.remove(i);
			
			for(int j= 0; j<ants.size();j++)
			{
				Ant sd2 = ants.get(j);
				if(dist(sd2.x,sd2.y,sd.x,sd.y)<4)
				if(sd2.com!=sd.com) {sd2.HP-=sd.ATK;sd.HP-=sd2.ATK;}
			}
			
			
			int cc = 0;
			
			switch (sd.inv)
			{
			case "food":
				cc=1;
				
				break;
			}
			
			if(sd.time%10==0) {points.add(new double[] {sd.x,sd.y,sd.d,cc,sd.ID,0});}
			
			for(int h = 0; h<homes.size();h++)
				if(dist(homes.get(h).X,homes.get(h).Y,sd.x,sd.y)<homes.get(h).Rad&&h!=sd.com)homes.get(h).Rad-=0.01;
					
				if(dist(homes.get(sd.com).X,homes.get(sd.com).Y,sd.x,sd.y)<homes.get(sd.com).Rad)
			{
				
				if(sd.inv=="food") {homes.get(sd.com).Food++;homes.get(sd.com).Rad+=0.05;}
				
				sd.inv="none";
				sd.trustID=0;
			}
			
			for(int j= 0; j<food.size();j++)
			{
				double[] f = food.get(j);
				if(dist(sd.x,sd.y,f[0],f[1])<=2&&sd.inv=="none")
					{
						//changeByID(sd.ID,1);
						food.remove(j);
						sd.inv="food";
						sd.d-=180;
					}
			
			}
			
			for(int j= 0; j<points.size();j++)
			{
				double[] p = points.get(j);
				
				if(sd.inv=="food")
				{
					if(dist(sd.x,sd.y,p[0],p[1])<10&&p[3]==0)
					{
						if(p[4]==0||p[4]==sd.trustID)
						{sd.d = p[2]-180; sd.trustX=p[0];sd.trustY=p[1];}
						if(sd.trustID==0)sd.trustID=(int)p[4];
					}
				}
				
				if(sd.inv=="none")
				{
					if(dist(sd.x,sd.y,p[0],p[1])<10&&p[3]==1)
					{
						{sd.d=p[2]-180;}
					}
				}
				
			}
		
			
		}
	}
	
	
	public void drawAnts(Graphics g)
	{
		
		for(int i= 0; i<ants.size();i++)
		{g.setColor(Color.red);
			Ant sd = ants.get(i);
			g.drawOval((int)sd.x-2,(int)sd.y-2,4,4);
			
			//int cc = 0;
			switch (sd.inv)
			{
			case "food":
				//cc=1;
				g.setColor(Color.GREEN);
				g.fillOval((int)sd.x,(int)sd.y,3,3);
				break;
			}
			
		//	if(sd.time%10==0) {points.add(new double[] {sd.x,sd.y,sd.d,cc,sd.ID,0});}
			
			
			
			
		}
	}
	
	public void countPoints()
	{
		for(int i= 0; i<points.size();i++)
		{
			
			double[] p = points.get(i);
			p[5]++;
			if(p[5]>=deleteDots)points.remove(i);
		}
	}
	
	public void drawPoints(Graphics g)
	{
		for(int i= 0; i<points.size();i++)
		{
			
			double[] p = points.get(i);
			
			
			switch((int)p[3]) {
				case 0://home
					g.setColor(Color.BLUE);	
					break;
				case 1://food
					g.setColor(Color.GREEN);	
					break;
				case 2://enemy
					g.setColor(Color.red);	
					break;
				case 3://forHome
					g.setColor(Color.yellow);	
					break;
			}
			g.fillOval((int)p[0]-1, (int)p[1]-1, 2, 2);
		}
	}
	
	void drawHome(Graphics g)
	{
		g.setColor(new Color(100,0,0));
	for(int i = 0; i<homes.size();i++)
	g.fillOval(homes.get(i).X-(int)homes.get(i).Rad, homes.get(i).Y-(int)homes.get(i).Rad, (int)homes.get(i).Rad*2, (int)homes.get(i).Rad*2);
	}

	
	
	void drawFood(Graphics g)
	{
		g.setColor(Color.green);
		for(int i= 0; i<food.size();i++)
		{
			double[] sd = food.get(i);
			g.fillOval((int)sd[0]-2,(int)sd[1]-2,4,4);
		}
	}
	
	
	void drawWalls(Graphics g)
	{
		g.setColor(new Color(100,100,100));
		for(int i = 0; i<walls.size();i++)
		{	int[] a = walls.get(i);
			g.fillRect(a[0],a[1],a[2],a[3]);
		}
	}
	
	static ArrayList <Ant> ants  = new ArrayList<Ant>();
	static ArrayList <double[]> points = new ArrayList<double[]>();
	static ArrayList <double[]> food = new ArrayList<double[]>();
	static boolean pointsIsOn = false;
	static boolean foodIsOn = true;
	
	
	static int workspeed=1;
	public void paintComponent(Graphics g)
	{
		
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
	/*for(int i = 0; i<workspeed;i++)
	{
		countAnts();
		countPoints();
	}//*/
	if(pointsIsOn)drawPoints(g);
	drawWalls(g);
	drawAnts(g);
	
	drawHome(g);
	
	
	if(foodIsOn)drawFood(g);
	
	
	
	if(Mistener.onAnt!=-1)
		drawDataAbout(g,Mistener.onAnt);
	
	g.setColor(new Color(100,100,100,100));
	
	g.fillRect(Mistener.wx, Mistener.wy, Mistener.ww, Mistener.wh);
		if(Mistener.antscreate)
		{
			g.setColor(new Color(100,0,0,100));
		g.fillOval(Mistener.ax-10, Mistener.ay-10, 20, 20);
		g.setColor(Color.red);
		g.drawString("Ants: "+Mistener.ac, Mistener.ax, Mistener.ay);
		}
		
		if(!hidePanel)drawPanel(g);
		if(closetime>=74)
		{
			g.setColor(Color.WHITE);
			g.fillRect(51+565+(int)(51d/74d*(double)closetime), 548, 51, 51);
		
		
		if(closetime<74)
		g.setColor(Color.white);
		else
			g.setColor(Color.RED);
		g.drawRect(51+565+(int)(51d/74d*(double)closetime), 548, 51, 51);
		g.drawString("ESCAPE", 618+(int)(51d/74d*(double)closetime), 575);
		}
	}
	
	static boolean hidePanel=false; 
	static int closetime=0;
	void drawPanel(Graphics g)
	{
		
		//background
		g.setColor(new Color(0,255,255,100));
		if(Mistener.foodcreate)g.fillRect(0, 548, 51, 51);
		if(Mistener.foodmin)g.fillRect(1, 548, 51/2, 51);
		if(Mistener.foodplu)g.fillRect(51/2+1, 548, 51/2, 51);
		if(Mistener.antscreate)g.fillRect(51, 548, 51, 51);
		if(Mistener.wallcreate)g.fillRect(51*2, 548, 51, 51);
		
		if(Mistener.addspeed)g.fillRect(51*3+51/2+1, 548, 51/2, 51);
		if(Mistener.minspeed)g.fillRect(51*3+1, 548, 51/2, 51);
		
		if(Mistener.addtt)g.fillRect(51*4+51/2+1, 548, 51/2, 51);
		if(Mistener.mintt)g.fillRect(51*4+1, 548, 51/2, 51);
		if(Mistener.hider)g.fillRect(565, 548, 51, 51);
		
		
		
		
		if(pointsIsOn)g.fillRect(51*5, 548, 51, 51);

		if(foodIsOn)g.fillRect(51*6, 548, 51, 51);
		
		if(closetime>=74)
		{
			g.setColor(Color.WHITE);
			g.fillRect(51+565+(int)(51d/74d*(double)closetime), 548, 51, 51);
		}
		
		if(closetime<74)
		g.setColor(Color.white);
		else
			g.setColor(Color.RED);
		g.drawRect(51+565+(int)(51d/74d*(double)closetime), 548, 51, 51);
		g.drawString("ESCAPE", 618+(int)(51d/74d*(double)closetime), 575);
		
		g.setColor(Color.black);
		g.fillRect(616,548, 51, 51);
		
		if(Mistener.esc) {
			g.setColor(new Color(0,255,255,100));
			g.fillRect(616,548, 51, 51);
		}
		
		g.setColor(Color.white);
		//g.drawRect(0,548,798,51);//Mistener.tx,Mistener.ty,Mistener.w,Mistener.h
		
		if(workspeed<=0)
		{
			g.fillRect(167,562,8,20);
			g.fillRect(167+15,562,8,20);
		}
		else
		{
			g.fillPolygon(new int[] {167,167,167+8+15}, new int[] {562, 582,572},3);
		}
		
		
		
		
		
		//точки еда панель очистка 
		for(int i = 0; i<7;i++)
			g.drawRect(i*51, 548, 51, 51);
		
		for(int i = 0; i<2;i++)
			g.drawRect(i*51+565, 548, 51, 51);
		
		
		
		g.drawRect(357,548,208,51/3);
		g.drawRect(357,548+51/3,208,51/3);
		g.drawRect(357,548+51/3*2,208,51/3);
		
		g.drawRect(571,564,38,17);
		g.drawLine(565,599,51+565,-51+599);
		g.drawLine(566,549,51+566,51+549);
		
		
		g.setColor(Color.red);
		for(int i = 0; i<5;i++)
		{
			g.drawLine(620+i, 554, 620+40+i, 554+40);
			g.drawLine(620-i+44, 554, 620-i+4, 554+40);
		}
		
		
		g.setColor(new Color(100,0,0));
		
		g.fillOval(51+51/2-10,548+51/2-10,20,20);
		
		g.setColor(Color.GREEN);
		for(int i = 0; i<Mistener.brush;i++)
		for(int j = 0; j<Mistener.brush;j++)
		{
			g.drawOval(25+i*4-((Mistener.brush-1)*4/2)-2,573+j*4-((Mistener.brush-1)*4/2)-2,4,4);
		}
		
		g.fillOval(330-10,565-5,20,20);
		g.fillOval(319-10,578-5,20,20);
		g.fillOval(342-10,578-5,20,20);
		
		for(int i = 0; i<3;i++)
		{
			g.setColor(new Color(0,0,255/3*(i+1)));
			g.fillOval(212-5+i*12,570-5,20,20);
		}
		

		g.setColor(Color.BLUE);
		for(int i = 0; i<3;i++)
		{
			g.fillOval(212+51-5+i*12,570-5,20,20);
		}
		
		
		
		g.setColor(new Color(100,100,100));
		g.fillRect((int)(2.5*51)-14, 548+51/2-14, 30, 30);
		
		//g.drawRect(Mistener.tx,Mistener.ty,Mistener.w,Mistener.h);
		
		
		
		
		//texts
		g.setColor(Color.white);
		g.drawString("F",40, 562);
		g.drawString("A",40+51, 562);
		g.drawString("Q",40+51*5, 562);
		g.drawString("Z",40+51*6, 562);
		g.drawString("W",39+51*2, 562);
		g.drawString("H",586,562);
		g.drawString("<[9]   [0]>", 2, 595);
		g.drawString("<[-]   [+]>", 3+51*3, 595);
		g.drawString("<[1]   [2]>", 3+51*4, 595);
		g.drawString(""+workspeed, 155,560);
		g.drawString(""+deleteDots, 155+51,560);
		
		g.drawString("Total ants: "+ants.size(), 359,562);
		g.drawString("Total foods: "+food.size(), 359,562+51/3);
		g.drawString("Total way marks: "+points.size(), 359,562+51/3*2);
	}

	void drawDataAbout(Graphics g,int n)
	{g.setColor(Color.WHITE);
		try
		{
	Ant ant = ants.get(n);
	int ys=0;
	String[] str = {"ID: "+ant.ID,"HP: "+ant.HP,"team: "+ant.com,"trustID: "+ant.trustID,"trustTime: "+ant.trustTime};
	
	for(int i = 0; i<str.length;i++)
	{
		g.drawString(str[i], (int)ant.x, (int)ant.y+ys);
		ys+=10;
	}
		}catch(IndexOutOfBoundsException e)
		{
			
		}
	
	}
	
	void log()
	{
		System.out.println("DEBUG!");
	}
	
	void log(int n)
	{
		System.out.println(n);
	}
	
	void log(String str)
	{
		System.out.println(str);
	}
	
	double dist(double x,double y,double x1, double y1)
	{
		return Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
	}
	
	static void removeByID(int ID)
	{
		for(int j= 0; j<points.size();j++)
		{
			double[] f = points.get(j);
			if((int)f[4]==ID) {points.remove(j);}
		}
	}
	
	static void clear()
	{
		ants.clear();
		walls.clear();
		food.clear();
		points.clear();
		homes.clear();
	}
	
}