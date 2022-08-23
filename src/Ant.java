
public class Ant {

	public Ant()
	{
		ID = (int)(Math.random()*32678);
	}
	public Ant(double x, double y, double d, int com)
	{
		this.x = x;
		this.y = y;
		this.d = d;
		this.com = com;
		ID = (int)(Math.random()*32678);
	}
	double x=0,y=0,d=0,speed=0.5;
	int com = 0,ID=0,trustID=0, trustTime=0;
			double HP=100,ATK=0.5;
	double trustX=0,trustY=0;
	public int time=0;
	public String inv = "none";
	public void move()
	{ //
		
		double dd = Math.PI/180*d;
		
		double x,y;
		
		x=(Math.cos(dd)*(speed)+Math.sin(dd)*(0)+this.x);
		y=(Math.cos(dd)*(0)+Math.sin(dd)*(speed))+this.y;
		
		boolean move = true;
		for(int i = 0; i<Graph.walls.size();i++)
		{
			int[] w = Graph.walls.get(i);
			if(x>=w[0]&&x<=w[0]+w[2])
				if(y>=w[1]&&y<=w[1]+w[3])
					move=false;
		}
		if(move) {this.x=x;this.y=y;}
		else
		{
			d+=rand(-40,40);	
		}
		
	}
	
	public void countThings()
	{	
		time++;
		//if(trustID==0)if(x<=0||x>=800||y<=0||y>=600)d-=180;
		if(trustID!=0)trustTime++;
		if(trustTime==100) {trustID=0;trustTime=0;}
		
		if(x<-20||x>820||y<-20||y>620) {Graph.removeByID(ID);x=Graph.homes.get(com).X;y=Graph.homes.get(com).Y; inv="none";}
		
		if(Math.random()*1000<100&&trustID==0)d+=rand(-40,40);//Math.random()*180-90;
	}
	
	double rand(double min, double max)
	{
		return min+(Math.random()*(max-min));
	}
	
}
