package sqare;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class Map extends JPanel implements Runnable{
	private  boolean  flag=false;
	private  boolean  flagother=false;
private  boolean  flagtwo=false;
	//定义游戏中最多出现多少行和列
	private static final int MAX_ROWS = 24;
	private static final int MAX_COLS = 16;
	//定义延迟时间 决定游戏的速度
	private  int delay = 1000;
	//该值表示此处为空 没有任何砖块
	private static final int NULL_BRICK = -1;
	//定义边界
	private static final int BORDER = 20;
	/**此数组表示游戏进行时的整个面板，当某位置无砖块时数组的值为NULL_BRICK，当有砖块
	  *时数组的值为此处砖块颜色的索引值
	  */
	private int[][] brick_info;
	//记录游戏得分
	private static int score;
	//标识游戏是否结束，true为结束false为未结束
	private boolean gameOver = true;
	//标识砖块是否已经到达底部，投入额为达到false为未到达
	private boolean reachBottom = true;
	//显示下一个砖块的横坐标和纵坐标
	private int msgX,msgY;
	//当前砖块和预览砖块
	private Brick brick,next_brick;
	private Random ran;
	//构造方法，初始化游戏中的一些参数
	public Map(){
		brick_info = new int[MAX_ROWS][MAX_COLS];//游戏区域
		resetBrick_info();        //清空二维数组中的砖块信息
		brick = new Brick(0,0,0);   //产生当前砖块和下一个提示的砖块
		next_brick = new Brick(0,0,0);		
		msgX = BORDER+(MAX_COLS+1)*Brick.BRICK_WIDTH+BORDER;
		msgY = BORDER*3;           //提示的下一个砖块的显示位置
		ran = new Random();         
		score = 0;
	}
	/**
	  *重置brick_info数组
	  */
	private void resetBrick_info(){     //清空二维数组中的砖块信息
		for(int i=0;i<MAX_ROWS;i++){
			for(int j=0;j<MAX_COLS;j++){
				brick_info[i][j]=NULL_BRICK;
			}
		}
	}  
	
	/**
	  *游戏开始时调用此方法，初始化一些有戏中必须的参数
	  */
	public void startGame(){
		gameOver = false;
		reachBottom = false;
		score = 0;        //变量赋初值
		(new Thread(this)).start();
		repaint();
	}
	/**
	  *此方法用来结束游戏
	  */
	
	/**
	  *进行游戏的线程，游戏开始时会被调用。当有戏结束标志为false时则继续游戏，若砖块的
	  *x坐标为-1或到达底部标志为true则重新创建一个砖块并将reachBottom置为false，清空brick_info
	  *数组中当前位置，将当前砖块的信息填充到brick_info数组的当前位置。若x坐标不为-1且reachBottom
	  *不为true则向下移动砖块，若到达底部则继续下一次循环，否则延时delay毫秒继续循环
	  */
	public void run(){
		while(!gameOver){                   //只要游戏没有结束，继续
			if(brick.x==-1||reachBottom){   //只要当前砖块到达界面底部，
				createNewBrick();              //产生一个新砖块
				reachBottom = false;           //将到达底部的变量重置为假		
				//updataBrick_info(true);       //将当前砖块区域清空
				//updataBrick_info(false);      //根据当前砖块位置重画
			}else{
				drop();                        //砖块下落					
			}
			try{
				Thread.sleep(delay);           //线程休眠一会
			}catch(InterruptedException e){
				
			}			  			
		}
	}
	/**
	  *创建砖块函数，利用下一个砖块的信息重置当前砖块的信息，同时重新生成下一个砖块的信息
	  *以备下一次创建砖块使用
	  */
	synchronized private void  createNewBrick(){
		if(next_brick.x==-1){
			int shape = ran.nextInt(200);
		  int angle = ran.nextInt(200);
		  int color_index = ran.nextInt(200);
		  next_brick.resetBrick(shape,angle,color_index);
		  next_brick.x=0;
		}               //将下一个砖块的信息赋给当前信息，将下一个砖块变成正在进行的砖块
		brick.resetBrick(next_brick.current_shape,next_brick.current_angle,next_brick.current_color_index);
		brick.x = 8;
		brick.y = 0;
		flagother=true;
		brick.current_color = Brick.ALL_COLOR[next_brick.current_color_index];//下一砖块颜色赋给当前砖块
		int shape1 = ran.nextInt(200);
		int angle1 = ran.nextInt(200);
		int color_index1 = ran.nextInt(200);//使用随机数，生成形状，角度，颜色
		next_brick.resetBrick(shape1,angle1,color_index1); //下一个砖块生成
	}
	/**
	  *清空或填充brick_info数组中当前砖块位置的信息，当清空时，将该处信息全部至为NULL_BRICK
	  *当时填充时将当前砖块的颜色索引值填入相应的位置
	  */
	private void updataBrick_info(boolean clear){
		//clear为真，将临时区域全部清空；
		//clear为假，将当前砖块填充到临时区域
		int[][] tempBrick = brick.getCurrentBrickModle(brick.current_shape,brick.current_angle);
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(tempBrick[i][j]!=0){
                          brick_info[brick.y+i][brick.x+j]=clear?NULL_BRICK:brick.current_color_index;
				}
			}
		}
		if(!clear){			repaint();		}
		
		
	}
	/**
	  *定义砖块向下移动的方法，先清空当前砖块在brick_info数组中的信息，然后判断是否可以
	  *移动到brick.y+1的位置，如果可以则将brick.y+1，brick.x不变，
	  *如果不可以移动，判断brick.y的值是否为0，为0说明游戏结束，将gameOver置为true否则是
	  *到达底部，将reachBottom置为true。最后填充brick_info数组检查是否可以消行
	  */
	public void drop(){
		if(brick.x==-1){
			return;
		}
		updataBrick_info(true);
		
		if(canMove(brick.x,brick.y+1)){
			brick.y += 1;
		}else{
			if(brick.y==0){
				gameOver = true;
			}else{
				reachBottom = true;
			}
		}
		updataBrick_info(false);
		if(reachBottom){
			checkRowAndGetScore();
		}
	}
	/**
	  *检查砖块是否可以移动，当x<0时超过左边界返回false。当重叠或者超过右边界或底边界
	  *时返回false。不超出任何边界也不重叠返回true
	  */
	private boolean canMove(int x,int y){
		//超过左边界返回false
		if((x<0)||(y>=MAX_ROWS)){
			return false;
		}
		//不能重叠和超过下边界和右边界否则返回false				
		int[][] tempBrick = brick.getCurrentBrickModle(brick.current_shape,brick.current_angle);						
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(tempBrick[i][j]!=0){
					if(x+j>=MAX_COLS||y+i>=MAX_ROWS||brick_info[y+i][x+j]!=NULL_BRICK){
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	  *实现左右移动，当传递参数为-1时代表左移一个单位，参数为1时代表右移一个单位
	  */
	public void moveLeftOrRight(int left){
		if(brick.x!=-1){
			updataBrick_info(true);

			
			if(canMove(brick.x+left,brick.y)){
			  updataBrick_info(true);
				  
		    brick.x += left;
		  }
		}
		updataBrick_info(false);
		
	}
	/**
	  *检查是否有满行，并记录有几行满行，跟据一次削去行数的不同相应的得分也不同，并记录
	  *相同颜色的行数，颜色相同的行数不同相应的得分也不同。最后绘制得分削去符合条件的行
	  */
	public void checkRowAndGetScore(){
		int s = 0;    		
		int full_lines_score = 50;  //满一行的基础得分
		int color_score = 200;       //一行颜色相同的基础得分
		int full_rows_lines = 0;     //满的行数
		int same_colors_lines = 0;   //颜色相同的行数
		boolean full_rows;           //标记是否满行
		boolean same_colors;         //标记是否颜色相同
		for(int i=MAX_ROWS-1;i>=0;i--){
			full_rows = true;
			same_colors = true;
			//如果此行内brick_info数组中有NULL_BRICK说明该行不可消除则跳出本行进行下一行检验
			for(int j=0;j<MAX_COLS;j++){
				if(brick_info[i][j]==NULL_BRICK){
					full_rows = false;
					break;
				}
				if(j+1<=MAX_COLS-1&&brick_info[i][j]!=brick_info[i][j+1]){
					same_colors = false;
				}
			}
			if(full_rows==true){                //如果满行,满行数加一,如果颜色相同，颜色数加一并削去该行
				full_rows_lines++;
				if(same_colors==true){
				  same_colors_lines++;				
			  }
			  for(int z=i;z>0;z--){              //将上面的砖块全体下移，并将最上面一行全部置空
			  	//brick_info[z]= brick_info[z-1];
				System.arraycopy(brick_info[z-1],0,brick_info[z],0,MAX_COLS);

			  }			  							
				for(int n=0;n<MAX_COLS;n++){
					brick_info[0][n]=NULL_BRICK;
					flag=true;
				}
				i++;				
			}			
		}


		//记算得分并根据得分更改游戏速度
		s += (full_lines_score + full_rows_lines%4*50)*full_rows_lines;
		s += (color_score + same_colors_lines%4*100)*same_colors_lines;
		this.score += s;
		delay -= s/50;
	}
	/**
	  *绘制游戏边界
	  */
	private void drawBorder(Graphics g){
		int rightX = BORDER+(MAX_COLS+1)*Brick.BRICK_WIDTH;//右边界横坐标
		int bottomY = BORDER+MAX_ROWS*Brick.BRICK_HIGTH;  //底部边界的纵坐标
		int x,y;
		g.setColor(Color.BLACK);  //设定边界颜色		
		//绘制左右边界
		for(int i=0;i<MAX_ROWS;i++){
			y = BORDER+i*Brick.BRICK_WIDTH; //一个边界砖块的纵坐标
			g.fill3DRect(BORDER,y,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
			g.fill3DRect(rightX,y,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
		}
		//绘制底部边界
		for(int j=0;j<=MAX_COLS+1;j++){
			x = BORDER+j*Brick.BRICK_WIDTH;  //每一个底部边界砖块的横坐标
			g.fill3DRect(x,bottomY,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
		}
	}
	/**
	  *根据brick_info数组绘制游戏面板中的所有砖块
	  */
	private void drawBrick(Graphics g){
		int index,x,y;
		for(int i=0;i<MAX_ROWS;i++){
			for(int j=0;j<MAX_COLS;j++){
				index = brick_info[i][j];
				if(index!=NULL_BRICK){
					x = BORDER+(j+1)*Brick.BRICK_WIDTH;  //该砖块的横坐标
					y = BORDER+i*Brick.BRICK_HIGTH;  //该砖块的纵坐标
					g.setColor(Brick.ALL_COLOR[index]);
					g.fill3DRect(x,y,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
				}
			}
		}		
	}
	/**
	  *根据next_brick中的信息绘制出下一个砖块
	  */
	private void drawNextBrick(Graphics g){				
		int[][] msgBrick = next_brick.getCurrentBrickModle(next_brick.current_shape,next_brick.current_angle);
		int index = next_brick.current_color_index;
		int x,y;
		g.setColor(Brick.ALL_COLOR[index]);
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(msgBrick[i][j]!=0){
					x = msgX+j*Brick.BRICK_WIDTH;
					y = msgY+i*Brick.BRICK_HIGTH;
					g.fill3DRect(x,y,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
				}
			}
		}
		repaint();
	}
	/**
	  *用来旋转砖块，每次旋转90度，
	  */
	public void turnAngle(){
		if(brick.x!=-1){			
			updataBrick_info(true);
			brick.turnAngle(1);
			if(!canMove(brick.x,brick.y)){
				brick.turnAngle(3);
			}
		}
		updataBrick_info(false);
	}
	/**
	  *绘制得分
	  */
	private void drawScore(Graphics g,int score){
		String str = "得分："+score;
		int x = BORDER+(MAX_COLS+1)*Brick.BRICK_WIDTH+BORDER;
		int y = BORDER+BORDER/2;
		g.setColor(Color.RED);
		g.drawString(str,x,y);
	}
	/**
	  *重载JPanel的paint()方法
	  */
	public void paint(Graphics g){
		super.paint(g);
		drawBorder(g);
		if(brick.x==-1){
			return;
		}
		drawBrick(g);
		drawNextBrick(g);		
		drawScore(g,this.score);
	}
		
}