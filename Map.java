package sqare;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class Map extends JPanel implements Runnable{
	private  boolean  flag=false;
	private  boolean  flagother=false;
private  boolean  flagtwo=false;
	//������Ϸ�������ֶ����к���
	private static final int MAX_ROWS = 24;
	private static final int MAX_COLS = 16;
	//�����ӳ�ʱ�� ������Ϸ���ٶ�
	private  int delay = 1000;
	//��ֵ��ʾ�˴�Ϊ�� û���κ�ש��
	private static final int NULL_BRICK = -1;
	//����߽�
	private static final int BORDER = 20;
	/**�������ʾ��Ϸ����ʱ��������壬��ĳλ����ש��ʱ�����ֵΪNULL_BRICK������ש��
	  *ʱ�����ֵΪ�˴�ש����ɫ������ֵ
	  */
	private int[][] brick_info;
	//��¼��Ϸ�÷�
	private static int score;
	//��ʶ��Ϸ�Ƿ������trueΪ����falseΪδ����
	private boolean gameOver = true;
	//��ʶש���Ƿ��Ѿ�����ײ���Ͷ���Ϊ�ﵽfalseΪδ����
	private boolean reachBottom = true;
	//��ʾ��һ��ש��ĺ������������
	private int msgX,msgY;
	//��ǰש���Ԥ��ש��
	private Brick brick,next_brick;
	private Random ran;
	//���췽������ʼ����Ϸ�е�һЩ����
	public Map(){
		brick_info = new int[MAX_ROWS][MAX_COLS];//��Ϸ����
		resetBrick_info();        //��ն�ά�����е�ש����Ϣ
		brick = new Brick(0,0,0);   //������ǰש�����һ����ʾ��ש��
		next_brick = new Brick(0,0,0);		
		msgX = BORDER+(MAX_COLS+1)*Brick.BRICK_WIDTH+BORDER;
		msgY = BORDER*3;           //��ʾ����һ��ש�����ʾλ��
		ran = new Random();         
		score = 0;
	}
	/**
	  *����brick_info����
	  */
	private void resetBrick_info(){     //��ն�ά�����е�ש����Ϣ
		for(int i=0;i<MAX_ROWS;i++){
			for(int j=0;j<MAX_COLS;j++){
				brick_info[i][j]=NULL_BRICK;
			}
		}
	}  
	
	/**
	  *��Ϸ��ʼʱ���ô˷�������ʼ��һЩ��Ϸ�б���Ĳ���
	  */
	public void startGame(){
		gameOver = false;
		reachBottom = false;
		score = 0;        //��������ֵ
		(new Thread(this)).start();
		repaint();
	}
	/**
	  *�˷�������������Ϸ
	  */
	
	/**
	  *������Ϸ���̣߳���Ϸ��ʼʱ�ᱻ���á�����Ϸ������־Ϊfalseʱ�������Ϸ����ש���
	  *x����Ϊ-1�򵽴�ײ���־Ϊtrue�����´���һ��ש�鲢��reachBottom��Ϊfalse�����brick_info
	  *�����е�ǰλ�ã�����ǰש�����Ϣ��䵽brick_info����ĵ�ǰλ�á���x���겻Ϊ-1��reachBottom
	  *��Ϊtrue�������ƶ�ש�飬������ײ��������һ��ѭ����������ʱdelay�������ѭ��
	  */
	public void run(){
		while(!gameOver){                   //ֻҪ��Ϸû�н���������
			if(brick.x==-1||reachBottom){   //ֻҪ��ǰש�鵽�����ײ���
				createNewBrick();              //����һ����ש��
				reachBottom = false;           //������ײ��ı�������Ϊ��		
				//updataBrick_info(true);       //����ǰש���������
				//updataBrick_info(false);      //���ݵ�ǰש��λ���ػ�
			}else{
				drop();                        //ש������					
			}
			try{
				Thread.sleep(delay);           //�߳�����һ��
			}catch(InterruptedException e){
				
			}			  			
		}
	}
	/**
	  *����ש�麯����������һ��ש�����Ϣ���õ�ǰש�����Ϣ��ͬʱ����������һ��ש�����Ϣ
	  *�Ա���һ�δ���ש��ʹ��
	  */
	synchronized private void  createNewBrick(){
		if(next_brick.x==-1){
			int shape = ran.nextInt(200);
		  int angle = ran.nextInt(200);
		  int color_index = ran.nextInt(200);
		  next_brick.resetBrick(shape,angle,color_index);
		  next_brick.x=0;
		}               //����һ��ש�����Ϣ������ǰ��Ϣ������һ��ש�������ڽ��е�ש��
		brick.resetBrick(next_brick.current_shape,next_brick.current_angle,next_brick.current_color_index);
		brick.x = 8;
		brick.y = 0;
		flagother=true;
		brick.current_color = Brick.ALL_COLOR[next_brick.current_color_index];//��һש����ɫ������ǰש��
		int shape1 = ran.nextInt(200);
		int angle1 = ran.nextInt(200);
		int color_index1 = ran.nextInt(200);//ʹ���������������״���Ƕȣ���ɫ
		next_brick.resetBrick(shape1,angle1,color_index1); //��һ��ש������
	}
	/**
	  *��ջ����brick_info�����е�ǰש��λ�õ���Ϣ�������ʱ�����ô���Ϣȫ����ΪNULL_BRICK
	  *��ʱ���ʱ����ǰש�����ɫ����ֵ������Ӧ��λ��
	  */
	private void updataBrick_info(boolean clear){
		//clearΪ�棬����ʱ����ȫ����գ�
		//clearΪ�٣�����ǰש����䵽��ʱ����
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
	  *����ש�������ƶ��ķ���������յ�ǰש����brick_info�����е���Ϣ��Ȼ���ж��Ƿ����
	  *�ƶ���brick.y+1��λ�ã����������brick.y+1��brick.x���䣬
	  *����������ƶ����ж�brick.y��ֵ�Ƿ�Ϊ0��Ϊ0˵����Ϸ��������gameOver��Ϊtrue������
	  *����ײ�����reachBottom��Ϊtrue��������brick_info�������Ƿ��������
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
	  *���ש���Ƿ�����ƶ�����x<0ʱ������߽緵��false�����ص����߳����ұ߽��ױ߽�
	  *ʱ����false���������κα߽�Ҳ���ص�����true
	  */
	private boolean canMove(int x,int y){
		//������߽緵��false
		if((x<0)||(y>=MAX_ROWS)){
			return false;
		}
		//�����ص��ͳ����±߽���ұ߽���򷵻�false				
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
	  *ʵ�������ƶ��������ݲ���Ϊ-1ʱ��������һ����λ������Ϊ1ʱ��������һ����λ
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
	  *����Ƿ������У�����¼�м������У�����һ����ȥ�����Ĳ�ͬ��Ӧ�ĵ÷�Ҳ��ͬ������¼
	  *��ͬ��ɫ����������ɫ��ͬ��������ͬ��Ӧ�ĵ÷�Ҳ��ͬ�������Ƶ÷���ȥ������������
	  */
	public void checkRowAndGetScore(){
		int s = 0;    		
		int full_lines_score = 50;  //��һ�еĻ����÷�
		int color_score = 200;       //һ����ɫ��ͬ�Ļ����÷�
		int full_rows_lines = 0;     //��������
		int same_colors_lines = 0;   //��ɫ��ͬ������
		boolean full_rows;           //����Ƿ�����
		boolean same_colors;         //����Ƿ���ɫ��ͬ
		for(int i=MAX_ROWS-1;i>=0;i--){
			full_rows = true;
			same_colors = true;
			//���������brick_info��������NULL_BRICK˵�����в����������������н�����һ�м���
			for(int j=0;j<MAX_COLS;j++){
				if(brick_info[i][j]==NULL_BRICK){
					full_rows = false;
					break;
				}
				if(j+1<=MAX_COLS-1&&brick_info[i][j]!=brick_info[i][j+1]){
					same_colors = false;
				}
			}
			if(full_rows==true){                //�������,��������һ,�����ɫ��ͬ����ɫ����һ����ȥ����
				full_rows_lines++;
				if(same_colors==true){
				  same_colors_lines++;				
			  }
			  for(int z=i;z>0;z--){              //�������ש��ȫ�����ƣ�����������һ��ȫ���ÿ�
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


		//����÷ֲ����ݵ÷ָ�����Ϸ�ٶ�
		s += (full_lines_score + full_rows_lines%4*50)*full_rows_lines;
		s += (color_score + same_colors_lines%4*100)*same_colors_lines;
		this.score += s;
		delay -= s/50;
	}
	/**
	  *������Ϸ�߽�
	  */
	private void drawBorder(Graphics g){
		int rightX = BORDER+(MAX_COLS+1)*Brick.BRICK_WIDTH;//�ұ߽������
		int bottomY = BORDER+MAX_ROWS*Brick.BRICK_HIGTH;  //�ײ��߽��������
		int x,y;
		g.setColor(Color.BLACK);  //�趨�߽���ɫ		
		//�������ұ߽�
		for(int i=0;i<MAX_ROWS;i++){
			y = BORDER+i*Brick.BRICK_WIDTH; //һ���߽�ש���������
			g.fill3DRect(BORDER,y,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
			g.fill3DRect(rightX,y,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
		}
		//���Ƶײ��߽�
		for(int j=0;j<=MAX_COLS+1;j++){
			x = BORDER+j*Brick.BRICK_WIDTH;  //ÿһ���ײ��߽�ש��ĺ�����
			g.fill3DRect(x,bottomY,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
		}
	}
	/**
	  *����brick_info���������Ϸ����е�����ש��
	  */
	private void drawBrick(Graphics g){
		int index,x,y;
		for(int i=0;i<MAX_ROWS;i++){
			for(int j=0;j<MAX_COLS;j++){
				index = brick_info[i][j];
				if(index!=NULL_BRICK){
					x = BORDER+(j+1)*Brick.BRICK_WIDTH;  //��ש��ĺ�����
					y = BORDER+i*Brick.BRICK_HIGTH;  //��ש���������
					g.setColor(Brick.ALL_COLOR[index]);
					g.fill3DRect(x,y,Brick.BRICK_WIDTH,Brick.BRICK_HIGTH,true);
				}
			}
		}		
	}
	/**
	  *����next_brick�е���Ϣ���Ƴ���һ��ש��
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
	  *������תש�飬ÿ����ת90�ȣ�
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
	  *���Ƶ÷�
	  */
	private void drawScore(Graphics g,int score){
		String str = "�÷֣�"+score;
		int x = BORDER+(MAX_COLS+1)*Brick.BRICK_WIDTH+BORDER;
		int y = BORDER+BORDER/2;
		g.setColor(Color.RED);
		g.drawString(str,x,y);
	}
	/**
	  *����JPanel��paint()����
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