package sqare;
/**����Ϊש���� ��������Ϸ�п��ܳ��ֵ�����ש�����״�������Ƕ���������ɫ������
  *�Լ���ģ�͸�������µ�ģ�ͣ��涨����Ϸ�п��ܳ��ֵ�������ɫ�������˻�ȡ��ǰ
  *ש�����״�ķ���getCurrentShape()����ȡ�Ƕȵķ���getCurrentAngle()����ȡ��ɫ
  *�ķ���getCurrentColor(),����ש������Եķ���resetBrick()�͸��ݸ���������ֵ��
  *�Ƶ�ǰש��ķ���drawCurrentBrick(Graphics g,int begin_x,int begin_y)
  */
import java.awt.*;
public class Brick{
	//����ש����״����
	public static final int ZERO_SHAPE=0;
	public static final int I_SHAPE=1;
	public static final int T_SHAPE=2;
	public static final int Z_SHAPE=3;
	public static final int S_SHAPE=4;
	public static final int L_SHAPE=5;
	public static final int REVERSE_L_SHAPE=6;
	//����ש�������ĽǶ�(˳ʱ����ת)
	public static final int ANGLE_90=0;
	public static final int ANGLE_180=1;
	public static final int ANGLE_270=2;
	public static final int ANGLE_360=3;
	//����ÿһ��ש��Ŀ�͸�
	public static final int BRICK_WIDTH=10;//ռ10������
	public static final int BRICK_HIGTH=10;
	//��ʼ�����п��ܳ��ֵ�ש���ģ��
	public static final int BRICK_MODLE[][][][]={
		                      //ZERO_SHAPE��ש�鴦�ڲ�ͬ�Ƕ�ʱ����״ģ��
		                      {
		                        {
		                          {1,1,0,0},  
		                          {1,1,0,0},  //ANGLE_90ʱ��ģ��
		                          {0,0,0,0},
		                          {0,0,0,0}
		                        },
		                        {
		                          {1,1,0,0},  
		                          {1,1,0,0},  
		                          {0,0,0,0},  //ANGLE_180ʱ��ģ��
		                          {0,0,0,0}
		                        },
		                        {
		                          {1,1,0,0},  
		                          {1,1,0,0},
		                          {0,0,0,0},  //ANGLE_270ʱ��ģ��
		                          {0,0,0,0}
		                        },
		                        {
		                          {1,1,0,0},  
		                          {1,1,0,0},
		                          {0,0,0,0},  //ANGLE_360ʱ��ģ��
		                          {0,0,0,0}
		                        }		               
		                      },
		                      //I_SHAPE�͵�ש�鴦�ڲ�ͬ�Ƕ�ʱ����״ģ��
		                      {
		                      	{
		                      		{1,0,0,0},
		                      		{1,0,0,0},  //ANGLE_90ʱ��ģ��
		                      		{1,0,0,0},
		                      		{1,0,0,0}		                      		
		                      	},
		                      	{
		                      		{1,1,1,1},
		                      		{0,0,0,0},  //ANGLE_180ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,0,0,0},  //ANGLE_270ʱ��ģ��
		                      		{1,0,0,0},
		                      		{1,0,0,0}
		                      	},
		                      	{
		                      		{1,1,1,1},
		                      		{0,0,0,0},  //ANGLE_360ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	}		                    
		                      },
		                      //T_SHAPE�͵�ש�鴦�ڲ�ͬ�Ƕ�ʱ����״ģ��
		                      {
		                      	{
		                      		{1,1,1,0},
		                      		{0,1,0,0},  //ANGLE_90ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,0,0},
		                      		{1,1,0,0},  //ANGLE_180ʱ��ģ��
		                      		{0,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,0,0},
		                      		{1,1,1,0},  //ANGLE_270ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,1,0,0},  //ANGLE_360ʱ��ģ��
		                      		{1,0,0,0},
		                      		{0,0,0,0}
		                      	}		                      				                     
		                      },
		                      //Z_SHAPE�͵�ש�鴦�ڲ�ͬ�Ƕ�ʱ����״ģ��
		                      {
		                      	{
		                      		{1,1,0,0},
		                      		{0,1,1,0},  //ANGLE_90ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,0,0},
		                      		{1,1,0,0},  //ANGLE_180ʱ��ģ��
		                      		{1,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,0,0},
		                      		{0,1,1,0}, //ANGLE_270ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,0,0},
		                      		{1,1,0,0},  //ANGLE_360ʱ��ģ��
		                      		{1,0,0,0},
		                      		{0,0,0,0}
		                      	}
		                      },
		                      //S_SHAPE�͵�ש�鴦�ڲ�ͬ�Ƕ�ʱ����״ģ��
		                      {
		                      	{
		                      		{0,1,1,0},
		                      		{1,1,0,0},  //ANGLE_90ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,1,0,0}, //ANGLE_180ʱ��ģ��
		                      		{0,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,1,0},
		                      		{1,1,0,0},  //ANGLE_270ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,1,0,0}, //ANGLE_360ʱ��ģ��
		                      		{0,1,0,0},
		                      		{0,0,0,0}
		                      	}
		                      },
		                      //L_SHAPE�͵�ש�鴦�ڲ�ͬ�Ƕ�ʱ����״ģ��
		                      {
		                      	{
		                      		{1,0,0,0},
		                      		{1,0,0,0},  //ANGLE_90ʱ��ģ��
		                      		{1,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,1,0},
		                      		{1,0,0,0},  //ANGLE_180ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,0,0},
		                      		{0,1,0,0},  //ANGLE_270ʱ��ģ��
		                      		{0,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,0,1,0},
		                      		{1,1,1,0},  //ANGLE_360ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	}
		                      },
		                      //REVERSE_L_SHAPE�͵�ש�鴦�ڲ�ͬ�Ƕ�ʱ����״ģ��
		                      {
		                      	{
		                      		{0,1,0,0},
		                      		{0,1,0,0},  //ANGLE_90ʱ��ģ��
		                      		{1,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,1,1,0},  //ANGLE_180ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,0,0},
		                      		{1,0,0,0},  //ANGLE_180ʱ��ģ��
		                      		{1,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,1,0},
		                      		{0,0,1,0},  //ANGLE_360ʱ��ģ��
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	}
		                      }
		                                  
		
    };
	//��ʼ�����ܳ��ֵ�ש���������ɫ
	public static final Color ALL_COLOR[]={
		new Color(21,170,122),new Color(138,87,53),new Color(187,18,4),new Color(103,57,134),
		new Color(17,174,45),new Color(158,33,139)
	};
	//��ǰש�����״
	public int current_shape;
	//��ǰש�����ɫ
	public Color current_color;
	//��ǰש����ɫ��Ӧ��ɫ�����е�����
	public int current_color_index;
	//��ǰש��ĽǶ�
	public int current_angle;
	//��ǰש�����ڵ�����ֵ
	public int x,y;
	//���캯�� ��ʼ��ש����й�����
	public Brick(int shape,int angle,int color_index){
		current_shape = shape%7;
		current_angle = angle%4;
		current_color_index = color_index%6;
		x = -1;
		y = -1;
	}
	//���ص�ǰש�����״
	public int getCurrentBrickShape(){
	 	return current_shape;
	}
	//���ص�ǰש��ĽǶ�
	public int getCurrentBrickAngle(){
		return current_angle;
	}
	//���ص�ǰש�����ɫ������ֵ
	public int getCurrentBrickColor(){
		return current_color_index;
	}
	//���ص�ǰש���ģ��
	public int[][] getCurrentBrickModle(int shape,int angle){
		return BRICK_MODLE[shape][angle];
	}
	//��������һ��ש��
	public void resetBrick(int shape,int angle,int color_index){
		current_shape = shape%7;
		current_angle = angle%4;
		current_color_index = color_index%6;
	}
	//���ݸ����ĽǶ���תש��
	public void turnAngle(int angle){
		current_angle += angle;
		current_angle = (current_angle>=4?current_angle%4:current_angle);
	}
	
	
}