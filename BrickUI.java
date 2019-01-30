package sqare;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BrickUI extends KeyAdapter{
	
	private JFrame frame;
	private JButton startButton;	
	private Map m;
	
	public void init(){
		m = new Map();          //�߳�ʵ����
		frame = new JFrame("����˹����");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);   //������Ӧ�����¼�
		frame.setSize(400,400);
		frame.setLocation(150,200);   //�����Сλ��
		frame.add(m);			      //�����Ϸ����Panel
		frame.setVisible(true);
		m.startGame();                //�߳̿�ʼ
	}
	public void keyPressed(KeyEvent e){        //�����¼�
			int keyNum = e.getKeyCode();       //��ȡ���̴���
			switch(keyNum){
				case KeyEvent.VK_LEFT:         //���ͷ������ƽ��
				  m.moveLeftOrRight(-1);
				  break;
				case KeyEvent.VK_RIGHT:         //�Ҽ�ͷ������ƽ��
				  m.moveLeftOrRight(1);
				  break;
				case KeyEvent.VK_UP:            //�ϼ�ͷ����ת
				  m.turnAngle();
				  break;
				case KeyEvent.VK_DOWN:          //�¼�ͷ������
				  m.drop();
				  break;
			}
	}
	
	public static void main(String args[]){
		BrickUI bu = new BrickUI();//��������
		bu.init();    //��ʼ����
	}
}