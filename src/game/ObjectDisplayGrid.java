package game;

import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Char[][] objectGrid = null;
    private Char[][] finalGrid = null;
    private Char[][] bottomGrid = null;
    private int[] playerCoords = null;
    private List<InputObserver> inputObservers = null;
    public List<Room> rooms = new ArrayList<Room>();
    private Player player;
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
        rooms = handler.rooms;
        objectGrid = new Char[width][gameHeight];
        bottomGrid = new Char[width][bottomHeight];
        finalGrid = new Char[width][height];
        playerCoords = new int[2];
        player = handler.player;
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
        for (Room r : rooms) {
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
            if (r.player != null) {
                playerChar = r.player.aChar;
                objectGrid[r.posX + r.player.posX][r.posY + r.player.posY] = n;
                playerCoords[0] = r.posX + r.player.posX;
                playerCoords[1] = r.posY + r.player.posY;
            }
            for (Monster monster : r.monsters) {
                objectGrid[r.posX + monster.posX][r.posY + monster.posY] = monster.aChar;
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

    private void checkForMonster() {
        //check if moved to monster
        for (Room r : rooms) {
            for (Monster m : r.monsters) {
                if (playerCoords[0] == m.posX + r.posX && playerCoords[1] == m.posY + r.posY) {
                    attackMonster(m);
                }
            }
        }
    }

    private void attackMonster(Monster monster) {
        Random rand = new Random();
        int attack = rand.nextInt(player.maxHit + 1);
        int defend = rand.nextInt(monster.maxHit + 1);
        /*
        call function that updates bottom display with attack, defend
         */
        resetBottom();
        displayAttack(attack, defend);
        monster.setHp(monster.hp - attack);
        player.setHp(player.hp - defend);
        if (player.hp < 0) {
            //following according to description, but could be <=
            gameOver();
        }
        //possible function that involves killing off monster? not in docs
    }

    private void displayAttack(int attack, int defend) {
        //attack is the damage the player does to monster, defend is damage monster does to player
        //temporary: (these messages should be displayed on the grid using bottomGrid
        System.out.println("Damage by monster on player: " + defend);
        System.out.println("Damage by player on monster: " + attack);
    }

    private void resetBottom() {
        //set all Chars of bottomGrid to ' '
    }

    private void displayPack() {
        //display contents of pack in bottom grid
    }

    private void gameOver() {
        System.out.println("GAME OVER");
    }

    private void checkForItem() {
        Item tempItem = null;
        for (Room r : rooms) {
            for (Item i : r.items) {
                if (playerCoords[0] == i.posX + r.posX && playerCoords[1] == i.posY + r.posY) {
                    tempItem = i;
                }
            }
            if (tempItem != null) {
                pickUpItem(r, tempItem);
                tempItem = null;
            }
        }
    }

    private void pickUpItem(Room r, Item i) {
        //remove item from objectGrid, room
        r.items.remove(i);
        objectGrid[r.posX + i.posX][r.posY + i.posY] = new Char('.');
        /*
        This assumes object picked up is in room - can be door or passage which needs to be added
         */
        for (Item item : r.items) {
            if (item.posX == i.posX && item.posY == i.posY) {
                objectGrid[r.posX + item.posX][r.posY + item.posY] = item.aChar;
            }
        }
        //add item to pack, set owner to player
        i.setOwner(player);
        player.addItem(i);
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
            checkForMonster();
            if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ') {
                playerCoords[0]++;
            }
        }
        else if (keypress.getKeyChar() == 'j'){
            playerCoords[1]++;
            checkForMonster();
            if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ') {
                playerCoords[1]--;
            }
        }
        else if (keypress.getKeyChar() == 'k'){
            playerCoords[1]--;
            checkForMonster();
            if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ') {
                playerCoords[1]++;
            }
        }
        else if (keypress.getKeyChar() == 'l'){
            playerCoords[0]++;
            checkForMonster();
            if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ') {
                playerCoords[0]--;
            }
        }
        else if (keypress.getKeyChar() == 'p'){
            checkForItem();
        }
        else if (keypress.getKeyChar() == 'd'){
            //Ethan
            //check if in room or passageway
        }
        else if (keypress.getKeyChar() == 'i'){
            //displays pack in bottom display
            resetBottom();
            displayPack();
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
        /*
        similar nested for loop for bottomGrid
         */
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
