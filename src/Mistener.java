import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mistener implements MouseMotionListener, MouseListener, KeyListener{
	static int onAnt=-1, brush=1;
	
	static int tx=0,ty=0,w=0,h=0;
	static int wx=0,wy=0,wh=0,ww=0;
	static int ax=0,ay=0,ac=0;
	static boolean antscreate=false,foodmin=false,foodplu=false,minspeed=false,addspeed=false,mintt=false,addtt=false,hider=false,esc=false;

	
	@Override
	public void mouseDragged(MouseEvent e) {
		w=e.getX()-tx;
		h=e.getY()-ty;
		if(foodcreate)
			for(int i = 0; i<brush;i++)
			for(int j = 0; j<brush;j++)
			{
				Graph.food.add(new double[] {(double)e.getX()+i*4-((brush-1)*4/2),(double)e.getY()+j*4-((brush-1)*4/2)});
			}
		
		
		if(wallcreate)
		{
			ww=e.getX()-wx;
			wh=e.getY()-wy;
		}
		
		if(antscreate)
		{
			ac=Math.abs(e.getX()-ax);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		onAnt=-1;
		for(int i = 0; i<Graph.ants.size();i++)
		{
			Ant a = Graph.ants.get(i);
			if(dist(a.x,a.y,e.getX(),e.getY())<3)onAnt=i;
		}
		//log(e.getX()+":"+e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i = 0; i<Graph.ants.size();i++)
		{
			Ant a = Graph.ants.get(i);
			if(dist(a.x,a.y,e.getX(),e.getY())<3)Graph.ants.remove(i);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		tx=e.getX();ty=e.getY();
		
		if(foodcreate)
		for(int i = 0; i<brush;i++)
			for(int j = 0; j<brush;j++)
			{
				Graph.food.add(new double[] {(double)e.getX()+i*4-((brush-1)*4/2),(double)e.getY()+j*4-((brush-1)*4/2)});
			}
		
		if(wallcreate)
		{
			wx=e.getX();
			wy=e.getY();
		}
		
		if(antscreate)
		{
			ax=e.getX();
			ay=e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		log(tx+","+ty+","+w+","+h);
		
		
		if(wallcreate)
		{
			Graph.walls.add(new int[] {wx,wy,ww,wh});
		}
		
		if(antscreate)
		{
			
			for(int i = 0; i<ac;i++)
			{
				Graph.ants.add(new Ant(ax,ay,Math.random()*360,Graph.homes.size()));
			}
			Graph.homes.add(new Home(ax,ay));
		}
		
		wx=0;wy=0;wh=0;ww=0;ax=0;ay=0;ac=0;
		
	}

	
	static boolean foodcreate = false,wallcreate=false;
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode())
		{
		case 72:
			Graph.hidePanel=!Graph.hidePanel;
			hider=true;
			break;
		case 27:;
			if(Graph.closetime<75)Graph.closetime++;
			esc=true;
			break;
		case 61:
			Graph.workspeed++;
			addspeed=true;
			break;
		case 45:
			if(Graph.workspeed>0)Graph.workspeed--;
			minspeed = true;
			break;
		case 50:
			Graph.deleteDots+=5;addtt=true;
			//log(Graph.deleteDots);
			break;
		case 49:
			if(Graph.deleteDots>0)Graph.deleteDots-=5;mintt=true;
			//log(Graph.deleteDots);
			break;
			
		case 48:
			brush++;
			foodplu=true;
			break;
		case 57:
			if(brush>1)brush--;
			foodmin=true;
			break;
		case 81:
			Graph.pointsIsOn=!Graph.pointsIsOn;
			break;
		case 90:
			Graph.foodIsOn=!Graph.foodIsOn;
			break;
		case 70:
			foodcreate=true;
			break;
		case 87:
			wallcreate=true;
			break;
		case 65:
			antscreate=true;
			break;
		default:
			System.out.println(e.getKeyChar()+": "+e.getKeyCode());
			break;
		}
		
	}

	
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case 72:
			hider=false;
			break;
		case 27:

			if(Graph.closetime>=74)System.exit(0);

			if(Graph.closetime<2)Graph.clear();
			Graph.closetime=0;
			esc=false;
			break;
		case 50:
			addtt=false;
			//log(Graph.deleteDots);
			break;
		case 49:
			mintt=false;
			//log(Graph.deleteDots);
			break;
		case 70:
			foodcreate=false;
			break;
		case 87:
			wallcreate=false;
			break;
		case 65:
			antscreate=false;
			break;
		case 48:
			foodplu=false;
			break;
		case 57:
			foodmin=false;
			break;
		case 61:
			
			addspeed=false;
			break;
		case 45:
			
			 minspeed = false;
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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
	
}
