public class pointBean {
int point_x;
int point_y;

public pointBean() {
	
}
public pointBean(int point_x, int point_y) {
	this.point_x = point_x;
	this.point_y = point_y;
}

public int getPoint_x() {
	return point_x;
}
public void setPoint_x(int point_x) {
	this.point_x = point_x;
}
public int getPoint_y() {
	return point_y;
}
public void setPoint_y(int point_y) {
	this.point_y = point_y;
}
@Override
public String toString() {
	return "pointBean [point_x=" + point_x + ", point_y=" + point_y + "]";
}

}