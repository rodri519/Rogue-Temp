package game;

import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Char[][] objectGrid = null;
    private Char[][] finalGrid = null;
    private int[] playerCoords = null;
    private List<InputObserver> inputObservers = null;

    private Char playerChar;
    private static int height;
    private static int width;
    private static int topHeight;
    private static int gameHeight;
    private static int bottomHeight;

    public void getObjectDisplayGrid(int gameHeight, int width, int topHeight){
        System.out.println("ObjectDisplayGrid (getObjectDisplayGrid)");
    }

    public void setTopMessageHeight(int topHeight){
        System.out.println("ObjectDisplayGrid (setTopMessageHeight)");
    }

    public ObjectDisplayGrid(RogueXMLHandler handler) {
        width = handler.width;
        topHeight = handler.topHeight;
        bottomHeight = handler.bottomHeight;
        height = handler.gameHeight + handler.topHeight + handler.bottomHeight;
        gameHeight = handler.gameHeight;
        terminal = new AsciiPanel(width, height);

        objectGrid = new Char[width][gameHeight];
        finalGrid = new Char[width][height];
        playerCoords = new int[2];
        Char n = new Char('.');
        Char rr = new Char('X');
        Char door = new Char('+');
        Char psg = new Char('#');
        Char sp = new Char(' ');


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < handler.gameHeight; j++) {
                objectGrid[i][j] = sp;
            }
        }
        for (Room r : handler.rooms) {
            for (int i = r.posX; i < r.posX + r.width; i++) {
                for (int j = r.posY; j < r.posY + r.height; j++) {
                    if (j == r.posY || j == r.posY + r.height - 1 || i == r.posX || i == r.posX + r.width - 1) {
                        objectGrid[i][j] = rr;
                    }
                    else {
                        objectGrid[i][j] = n;
                    }
                }
            }
            for (Creature creature : r.creatures) {
                if (creature.aChar.getChar() == '@') {
                    playerChar = creature.aChar;
                    objectGrid[r.posX + creature.posX][r.posY + creature.posY] = n;
                    playerCoords[0] = r.posX + creature.posX;
                    playerCoords[1] = r.posY + creature.posY;
                }
                else{
                    objectGrid[r.posX + creature.posX][r.posY + creature.posY] = creature.aChar;
                }
            }
            for (Item item : r.items) {
                objectGrid[r.posX + item.posX][r.posY + item.posY] = item.aChar;
            }
        }
        for (Passage p : handler.passages) {
            for (int index = 0; index < p.xCoords.size() - 1; index++) {
                if (p.xCoords.get(index) == p.xCoords.get(index + 1)) {
                    if (p.yCoords.get(index) < p.yCoords.get(index + 1)) {
                        for (int y = p.yCoords.get(index); y <= p.yCoords.get(index + 1); y ++){
                            if (objectGrid[p.xCoords.get(index)][y].getChar() == 'X' || objectGrid[p.xCoords.get(index)][y].getChar() == '+') {
                                objectGrid[p.xCoords.get(index)][y] = door;
                            }
                            else{
                                objectGrid[p.xCoords.get(index)][y] = psg;
                            }
                        }
                    }
                    else {
                        for (int y = p.yCoords.get(index); y >= p.yCoords.get(index + 1); y --){
                            if (objectGrid[p.xCoords.get(index)][y].getChar() == 'X' || objectGrid[p.xCoords.get(index)][y].getChar() == '+') {
                                objectGrid[p.xCoords.get(index)][y] = door;
                            }
                            else{
                                objectGrid[p.xCoords.get(index)][y] = psg;
                            }
                        }
                    }
                }
                else {
                    if (p.xCoords.get(index) < p.xCoords.get(index + 1)) {
                        for (int x = p.xCoords.get(index); x <= p.xCoords.get(index + 1); x ++){
                            if (objectGrid[x][p.yCoords.get(index)].getChar() == 'X' || objectGrid[x][p.yCoords.get(index)].getChar() == '+') {
                                objectGrid[x][p.yCoords.get(index)] = door;
                            }
                            else{
                                objectGrid[x][p.yCoords.get(index)] = psg;
                            }
                        }
                    }
                    else {
                        for (int x = p.xCoords.get(index); x >= p.xCoords.get(index + 1); x --){
                            if (objectGrid[x][p.yCoords.get(index)].getChar() == 'X' || objectGrid[x][p.yCoords.get(index)].getChar() == '+') {
                                objectGrid[x][p.yCoords.get(index)] = door;
                            }
                            else{
                                objectGrid[x][p.yCoords.get(index)] = psg;
                            }
                        }
                    }
                }
            }
        }

        initializeDisplay();

        super.add(terminal);
        super.setSize(width * 9, height * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // super.repaint();
        // terminal.repaint( );
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        if (keypress.getKeyChar() == 'h'){
            playerCoords[0]--;
            if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ') {
                playerCoords[0]++;
            }
        }
        else if (keypress.getKeyChar() == 'j'){
            playerCoords[1]++;
            if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ') {
                playerCoords[1]--;
            }
        }
        else if (keypress.getKeyChar() == 'k'){
            playerCoords[1]--;
            if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ') {
                playerCoords[1]++;
            }
        }
        else if (keypress.getKeyChar() == 'l'){
            playerCoords[0]++;
            if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ') {
                playerCoords[0]--;
            }
        }
        notifyInputObservers(keypress.getKeyChar());
        initializeDisplay();
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void initializeDisplay() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < gameHeight; j++) {
                addObjectToDisplay(objectGrid[i][j], i, j + topHeight);
            }
        }
        addObjectToDisplay(playerChar, playerCoords[0], playerCoords[1] + topHeight);
        terminal.repaint();
    }

    public void fireUp() {
        if (terminal.requestFocusInWindow()) {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow Succeeded");
        } else {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow FAILED");
        }
    }

    public void addObjectToDisplay(Char ch, int x, int y) {
        if ((0 <= x) && (x < finalGrid.length)) {
            if ((0 <= y) && (y < finalGrid[0].length)) {
                finalGrid[x][y] = ch;
                writeToTerminal(x, y);
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        char ch = finalGrid[x][y].getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
    }
}
