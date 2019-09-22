import java.util.ArrayList;

public class getDataKMeans {

	int k=5;//k值
	  //第一個中心點x,y
	static double con1_x;
	static double con1_y;
	//第一個中心點x,y
	static double con2_x;
	static double con2_y;
	//第一個中心點x,y
	static double con3_x;
	static double con3_y;
	//第一個中心點x,y
	static double con4_x;
	static double con4_y;
	//第一個中心點x,y
	static double con5_x;
	static double con5_y;		
	//創建5個list裝各個點
	ArrayList<pointBean> list1=new ArrayList<pointBean>();
	ArrayList<pointBean> list2=new ArrayList<pointBean>();
	ArrayList<pointBean> list3=new ArrayList<pointBean>();
	ArrayList<pointBean> list4=new ArrayList<pointBean>();
	ArrayList<pointBean> list5=new ArrayList<pointBean>();
	
	public  void setData(ArrayList<pointBean> list){
		con1_x=list.get(0).point_x;
		con1_y=list.get(0).point_y;
		con2_x=list.get(1).point_x;
		con2_y=list.get(1).point_y;
		con3_x=list.get(2).point_x;
		con3_y=list.get(2).point_y;
		con4_x=list.get(3).point_x;
		con4_y=list.get(3).point_y;
		con5_x=list.get(4).point_x;
		con5_y=list.get(4).point_y;
		//分別加入list中
		list1.add(list.get(0));
		list2.add(list.get(1));
		list3.add(list.get(2));
		list4.add(list.get(3));
		list5.add(list.get(4));
        //循環操作
		for(int i=5;i<list.size();i++){
			getLength(list.get(i));
		}		
	    // 打印出對應的中心點 、聚類的值
		System.out.println("-------1-------");
		System.out.println("1的中心點:"+con1_x+" "+con1_y);
		for(int i=0;i<list1.size();i++){
			System.out.println(list1.get(i).point_x+" "+list1.get(i).point_y);
		}
		System.out.println("-------2-------");
		System.out.println("2的中心點:"+con2_x+" "+con2_y);
		for(int i=0;i<list2.size();i++){
			System.out.println(list2.get(i).point_x+" "+list2.get(i).point_y);
		}
		System.out.println("-------3-------");
		System.out.println("3的中心點:"+con3_x+" "+con3_y);
		for(int i=0;i<list3.size();i++){
			System.out.println(list3.get(i).point_x+" "+list3.get(i).point_y);
		}
		System.out.println("-------4-------");
		System.out.println("4的中心點:"+con4_x+" "+con4_y);
		for(int i=0;i<list4.size();i++){
			System.out.println(list4.get(i).point_x+" "+list4.get(i).point_y);
		}
		System.out.println("-------5-------");
		System.out.println("5的中心點:"+con5_x+" "+con5_y);
		for(int i=0;i<list5.size();i++){
			System.out.println(list5.get(i).point_x+" "+list5.get(i).point_y);
		}	
	}
    /**
     * 求出每個點到中心點距離
     * @param point
     */
	public  void getLength(pointBean point) {
		int x=point.point_x;
		int y=point.point_y;
		
		
		double s1=(x-con1_x)*(x-con1_x)+(y-con1_y)*(y-con1_y); //a^2+b2
		double s2=(x-con2_x)*(x-con2_x)+(y-con2_y)*(y-con2_y);
		double s3=(x-con3_x)*(x-con3_x)+(y-con3_y)*(y-con3_y);
		double s4=(x-con4_x)*(x-con4_x)+(y-con4_y)*(y-con4_y);
		double s5=(x-con5_x)*(x-con5_x)+(y-con5_y)*(y-con5_y);
		
		double nn[]={s1,s2,s3,s4,s5};
		// 找出最小的一個
		double temp=nn[0];
		for(int i=1;i<nn.length;i++){
			if(nn[i]<=temp)
				temp=nn[i];
		}
		// 添加點
        if(temp==s1){
        	list1.add(point);
        	upDataPoint(list1,con1_x,con1_y);
        }
        if(temp==s2){
        	list2.add(point);
        	upDataPoint(list2,con2_x,con2_y);
        }
        if(temp==s3){
        	list3.add(point);
        	upDataPoint(list3,con3_x,con3_y);
        }
        if(temp==s4){
        	list4.add(point);
        	upDataPoint(list4,con4_x,con4_y);
        }
        if(temp==s5){
        	list5.add(point);
        	upDataPoint(list5,con5_x,con5_y);
        }
 
	}
	/**
	 * 更新中心點座標
	 * @param list
	 */
	private void upDataPoint(ArrayList<pointBean> list,double x,double y) {
		double up_x=0;
		double up_y=0;
		for(int i=0;i<list.size();i++){
			up_x+=list.get(i).point_x;
			up_y+=list.get(i).point_y;
		}
		x=up_x/(list.size());
		y=up_y/(list.size());
	}

}