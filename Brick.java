package sqare;
/**该类为砖块类 定义了游戏中可能出现的所有砖块的形状索引、角度索引、颜色索引、
  *以及其模型各种情况下的模型，规定了游戏中可能出现的所有颜色，定义了获取当前
  *砖块的形状的方法getCurrentShape()，获取角度的方法getCurrentAngle()，获取颜色
  *的方法getCurrentColor(),重置砖块各属性的方法resetBrick()和根据给定的坐标值绘
  *制当前砖块的方法drawCurrentBrick(Graphics g,int begin_x,int begin_y)
  */
import java.awt.*;
public class Brick{
	//定义砖块形状索引
	public static final int ZERO_SHAPE=0;
	public static final int I_SHAPE=1;
	public static final int T_SHAPE=2;
	public static final int Z_SHAPE=3;
	public static final int S_SHAPE=4;
	public static final int L_SHAPE=5;
	public static final int REVERSE_L_SHAPE=6;
	//定义砖块所处的角度(顺时针旋转)
	public static final int ANGLE_90=0;
	public static final int ANGLE_180=1;
	public static final int ANGLE_270=2;
	public static final int ANGLE_360=3;
	//定义每一个砖块的宽和高
	public static final int BRICK_WIDTH=10;//占10个像素
	public static final int BRICK_HIGTH=10;
	//初始化所有可能出现的砖块的模型
	public static final int BRICK_MODLE[][][][]={
		                      //ZERO_SHAPE型砖块处于不同角度时的形状模型
		                      {
		                        {
		                          {1,1,0,0},  
		                          {1,1,0,0},  //ANGLE_90时的模型
		                          {0,0,0,0},
		                          {0,0,0,0}
		                        },
		                        {
		                          {1,1,0,0},  
		                          {1,1,0,0},  
		                          {0,0,0,0},  //ANGLE_180时的模型
		                          {0,0,0,0}
		                        },
		                        {
		                          {1,1,0,0},  
		                          {1,1,0,0},
		                          {0,0,0,0},  //ANGLE_270时的模型
		                          {0,0,0,0}
		                        },
		                        {
		                          {1,1,0,0},  
		                          {1,1,0,0},
		                          {0,0,0,0},  //ANGLE_360时的模型
		                          {0,0,0,0}
		                        }		               
		                      },
		                      //I_SHAPE型的砖块处于不同角度时的形状模型
		                      {
		                      	{
		                      		{1,0,0,0},
		                      		{1,0,0,0},  //ANGLE_90时的模型
		                      		{1,0,0,0},
		                      		{1,0,0,0}		                      		
		                      	},
		                      	{
		                      		{1,1,1,1},
		                      		{0,0,0,0},  //ANGLE_180时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,0,0,0},  //ANGLE_270时的模型
		                      		{1,0,0,0},
		                      		{1,0,0,0}
		                      	},
		                      	{
		                      		{1,1,1,1},
		                      		{0,0,0,0},  //ANGLE_360时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	}		                    
		                      },
		                      //T_SHAPE型的砖块处于不同角度时的形状模型
		                      {
		                      	{
		                      		{1,1,1,0},
		                      		{0,1,0,0},  //ANGLE_90时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,0,0},
		                      		{1,1,0,0},  //ANGLE_180时的模型
		                      		{0,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,0,0},
		                      		{1,1,1,0},  //ANGLE_270时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,1,0,0},  //ANGLE_360时的模型
		                      		{1,0,0,0},
		                      		{0,0,0,0}
		                      	}		                      				                     
		                      },
		                      //Z_SHAPE型的砖块处于不同角度时的形状模型
		                      {
		                      	{
		                      		{1,1,0,0},
		                      		{0,1,1,0},  //ANGLE_90时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,0,0},
		                      		{1,1,0,0},  //ANGLE_180时的模型
		                      		{1,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,0,0},
		                      		{0,1,1,0}, //ANGLE_270时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,0,0},
		                      		{1,1,0,0},  //ANGLE_360时的模型
		                      		{1,0,0,0},
		                      		{0,0,0,0}
		                      	}
		                      },
		                      //S_SHAPE型的砖块处于不同角度时的形状模型
		                      {
		                      	{
		                      		{0,1,1,0},
		                      		{1,1,0,0},  //ANGLE_90时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,1,0,0}, //ANGLE_180时的模型
		                      		{0,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,1,1,0},
		                      		{1,1,0,0},  //ANGLE_270时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,1,0,0}, //ANGLE_360时的模型
		                      		{0,1,0,0},
		                      		{0,0,0,0}
		                      	}
		                      },
		                      //L_SHAPE型的砖块处于不同角度时的形状模型
		                      {
		                      	{
		                      		{1,0,0,0},
		                      		{1,0,0,0},  //ANGLE_90时的模型
		                      		{1,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,1,0},
		                      		{1,0,0,0},  //ANGLE_180时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,0,0},
		                      		{0,1,0,0},  //ANGLE_270时的模型
		                      		{0,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{0,0,1,0},
		                      		{1,1,1,0},  //ANGLE_360时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	}
		                      },
		                      //REVERSE_L_SHAPE型的砖块处于不同角度时的形状模型
		                      {
		                      	{
		                      		{0,1,0,0},
		                      		{0,1,0,0},  //ANGLE_90时的模型
		                      		{1,1,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,0,0,0},
		                      		{1,1,1,0},  //ANGLE_180时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,0,0},
		                      		{1,0,0,0},  //ANGLE_180时的模型
		                      		{1,0,0,0},
		                      		{0,0,0,0}
		                      	},
		                      	{
		                      		{1,1,1,0},
		                      		{0,0,1,0},  //ANGLE_360时的模型
		                      		{0,0,0,0},
		                      		{0,0,0,0}
		                      	}
		                      }
		                                  
		
    };
	//初始化可能出现的砖块的所有颜色
	public static final Color ALL_COLOR[]={
		new Color(21,170,122),new Color(138,87,53),new Color(187,18,4),new Color(103,57,134),
		new Color(17,174,45),new Color(158,33,139)
	};
	//当前砖块的形状
	public int current_shape;
	//当前砖块的颜色
	public Color current_color;
	//当前砖块颜色对应颜色数组中的索引
	public int current_color_index;
	//当前砖块的角度
	public int current_angle;
	//当前砖块所在的坐标值
	public int x,y;
	//构造函数 初始化砖块的有关属性
	public Brick(int shape,int angle,int color_index){
		current_shape = shape%7;
		current_angle = angle%4;
		current_color_index = color_index%6;
		x = -1;
		y = -1;
	}
	//返回当前砖块的形状
	public int getCurrentBrickShape(){
	 	return current_shape;
	}
	//返回当前砖块的角度
	public int getCurrentBrickAngle(){
		return current_angle;
	}
	//返回当前砖块的颜色的索引值
	public int getCurrentBrickColor(){
		return current_color_index;
	}
	//返回当前砖块的模型
	public int[][] getCurrentBrickModle(int shape,int angle){
		return BRICK_MODLE[shape][angle];
	}
	//重新设置一个砖块
	public void resetBrick(int shape,int angle,int color_index){
		current_shape = shape%7;
		current_angle = angle%4;
		current_color_index = color_index%6;
	}
	//根据给定的角度旋转砖块
	public void turnAngle(int angle){
		current_angle += angle;
		current_angle = (current_angle>=4?current_angle%4:current_angle);
	}
	
	
}