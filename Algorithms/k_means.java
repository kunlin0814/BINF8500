import java.util.ArrayList;

/**
 * K-Means演算法
 */
public class k_means {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	//1.建立二維陣列 10x10的陣列
		int num_1[]=new int[100];
		int num_2[]=new int[100];
        //隨機賦值

			for(int i=0;i<100;i++){
				num_1[i]=(int)( Math.random()*100);
			}
		
			for(int i=0;i<100;i++){
				num_2[i]=(int)( Math.random()*100);
			}
		// 2.建立點座標
			ArrayList<pointBean> list=new ArrayList<pointBean>();
			pointBean bean;
	        for(int i=0;i<100;i++){
	        	bean=new pointBean();
	        	bean.point_x=num_1[i];
	        	bean.point_y=num_2[i];
	        	list.add(bean);
	        }
	 // 執行k-means演算法
	     getDataKMeans gg=new getDataKMeans();
	     gg.setData(list);
	        //for (int i=0; i<list.size();i++) {
	        //System.out.println(list.get(i));
	        //}
	}
}