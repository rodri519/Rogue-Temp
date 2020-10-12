public class ObjectDisplayGrid {
    //static variable declarations?
    public ObjectDisplayGrid(int gameHeight, int width, int topHeight, int bottomHeight){
        System.out.println("ObjectDisplayGrid (ObjectDisplayGrid), gameHeight = " + gameHeight + ", width = " + width + ", topHeight = " + topHeight + ", bottomHeight = " + bottomHeight);
    }
    public void getObjectDisplayGrid(int gameHeight, int width, int topHeight){
        System.out.println("ObjectDisplayGrid (getObjectDisplayGrid)");
    }

    public void setTopMessageHeight(int topHeight){
        System.out.println("ObjectDisplayGrid (setTopMessageHeight)");
    }
}
