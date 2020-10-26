public class ObjectDisplayGrid {
    //static variable declarations?
    private int gameHeight;
    private int width;
    private int topHeight;
    private int bottomHeight;
    public ObjectDisplayGrid(int _gameHeight, int _width, int _topHeight, int _bottomHeight){
        gameHeight = _gameHeight;
        width = _width;
        topHeight = _topHeight;
        bottomHeight = _bottomHeight;
        System.out.println("ObjectDisplayGrid (ObjectDisplayGrid), gameHeight = " + gameHeight + ", width = " + width + ", topHeight = " + topHeight + ", bottomHeight = " + bottomHeight);
    }
    public void getObjectDisplayGrid(int gameHeight, int width, int topHeight){
        System.out.println("ObjectDisplayGrid (getObjectDisplayGrid)");
    }

    public void setTopMessageHeight(int topHeight){
        System.out.println("ObjectDisplayGrid (setTopMessageHeight)");
    }
}
