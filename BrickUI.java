package sqare;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BrickUI extends KeyAdapter{
	
	private JFrame frame;
	private JButton startButton;	
	private Map m;
	
	public void init(){
		m = new Map();          //线程实现类
		frame = new JFrame("俄罗斯方块");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);   //界面相应键盘事件
		frame.setSize(400,400);
		frame.setLocation(150,200);   //界面大小位置
		frame.add(m);			      //添加游戏界面Panel
		frame.setVisible(true);
		m.startGame();                //线程开始
	}
	public void keyPressed(KeyEvent e){        //键盘事件
			int keyNum = e.getKeyCode();       //获取键盘代码
			switch(keyNum){
				case KeyEvent.VK_LEFT:         //左箭头，向左平移
				  m.moveLeftOrRight(-1);
				  break;
				case KeyEvent.VK_RIGHT:         //右箭头，向右平移
				  m.moveLeftOrRight(1);
				  break;
				case KeyEvent.VK_UP:            //上箭头，旋转
				  m.turnAngle();
				  break;
				case KeyEvent.VK_DOWN:          //下箭头，下落
				  m.drop();
				  break;
			}
	}
	
	public static void main(String args[]){
		BrickUI bu = new BrickUI();//创建对象
		bu.init();    //初始化；
	}
}