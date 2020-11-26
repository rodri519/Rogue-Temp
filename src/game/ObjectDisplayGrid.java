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
    private Char[] topGrid = null;
    private int[] playerCoords = null;
    private List<InputObserver> inputObservers = null;
    public List<Room> rooms = new ArrayList<Room>();
    public List<Char> monsterChars = new ArrayList<Char>();
    public List<Passage> passages = new ArrayList<Passage>();
    public List<Item> droppedItems = new ArrayList<Item>();
    private Player player;
    private static int height;
    private static int width;
    private static int topHeight;
    private static int gameHeight;
    private static int bottomHeight;
    private char lastChar;

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
        passages = handler.passages;
        objectGrid = new Char[width][gameHeight];
        bottomGrid = new Char[width][bottomHeight];
        finalGrid = new Char[width][height];
        playerCoords = new int[2];
        player = handler.player;
        topGrid = new Char[width];
        setTopGrid();

        //find player location
        for (Room r : rooms) {
            if (r.player != null) {
                playerCoords[0] = r.posX + r.player.posX;
                playerCoords[1] = r.posY + r.player.posY;
                r.player = null;
            }
        }
        resetBottom();
        setObjectGrid();
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

    private void setObjectGrid() {
        //initialize dungeon display
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < gameHeight; j++) {
                objectGrid[i][j] = new Char(' ');
            }
        }
        //displays rooms - walls, interior, then monsters and items
        for (Room r : rooms) {
            for (int i = r.posX; i < r.posX + r.width; i++) {
                for (int j = r.posY; j < r.posY + r.height; j++) {
                    if (j == r.posY || j == r.posY + r.height - 1 || i == r.posX || i == r.posX + r.width - 1) {
                        objectGrid[i][j] = new Char('X');
                    }
                    else {
                        objectGrid[i][j] = new Char('.');
                    }
                }
            }
            for (Monster monster : r.monsters) {
                objectGrid[r.posX + monster.posX][r.posY + monster.posY] = monster.aChar;
                if (monsterChars.contains(monster.aChar) != true) {
                    monsterChars.add(monster.aChar);
                }
            }
            for (Item item : r.items) {
                objectGrid[r.posX + item.posX][r.posY + item.posY] = item.aChar;
            }
        }
        //displays passages, determining whether to go up, down, left, or right, and whether door or passage
        for (Passage p : passages) {
            for (int index = 0; index < p.xCoords.size() - 1; index++) {
                if (p.xCoords.get(index) == p.xCoords.get(index + 1)) {
                    if (p.yCoords.get(index) < p.yCoords.get(index + 1)) {
                        for (int y = p.yCoords.get(index); y <= p.yCoords.get(index + 1); y++) {
                            if (objectGrid[p.xCoords.get(index)][y].getChar() == 'X' || objectGrid[p.xCoords.get(index)][y].getChar() == '+') {
                                objectGrid[p.xCoords.get(index)][y] = new Char('+');
                            } else {
                                objectGrid[p.xCoords.get(index)][y] = new Char('#');
                            }
                        }
                    } else {
                        for (int y = p.yCoords.get(index); y >= p.yCoords.get(index + 1); y--) {
                            if (objectGrid[p.xCoords.get(index)][y].getChar() == 'X' || objectGrid[p.xCoords.get(index)][y].getChar() == '+') {
                                objectGrid[p.xCoords.get(index)][y] = new Char('+');
                            } else {
                                objectGrid[p.xCoords.get(index)][y] = new Char('#');
                            }
                        }
                    }
                } else {
                    if (p.xCoords.get(index) < p.xCoords.get(index + 1)) {
                        for (int x = p.xCoords.get(index); x <= p.xCoords.get(index + 1); x++) {
                            if (objectGrid[x][p.yCoords.get(index)].getChar() == 'X' || objectGrid[x][p.yCoords.get(index)].getChar() == '+') {
                                objectGrid[x][p.yCoords.get(index)] = new Char('+');
                            } else {
                                objectGrid[x][p.yCoords.get(index)] = new Char('#');
                            }
                        }
                    } else {
                        for (int x = p.xCoords.get(index); x >= p.xCoords.get(index + 1); x--) {
                            if (objectGrid[x][p.yCoords.get(index)].getChar() == 'X' || objectGrid[x][p.yCoords.get(index)].getChar() == '+') {
                                objectGrid[x][p.yCoords.get(index)] = new Char('+');
                            } else {
                                objectGrid[x][p.yCoords.get(index)] = new Char('#');
                            }
                        }
                    }
                }
            }
        }
        //display all dropped items wherever they were dropped
        for (Item item: droppedItems) {
            objectGrid[item.posX][item.posY] = item.aChar;
        }
        //display player icon
        objectGrid[playerCoords[0]][playerCoords[1]] = player.aChar;
    }

    private void setTopGrid(){
        String hpRemaining = "GAME OVER";
        if (player.hp > 0) {
            hpRemaining = "Player HP: " + player.hp;
        }
        for (int i = 0; i < width; i++){
            if (i < hpRemaining.length()) {
                topGrid[i] = new Char(hpRemaining.charAt(i));
            }
            else {
                topGrid[i] = new Char(' ');
            }
        }
    }

    private void checkForMonster() {
        //check if moved to monster
        Monster temp = null;
        for (Room r : rooms) {
            for (Monster m : r.monsters) {
                if (playerCoords[0] == m.posX + r.posX && playerCoords[1] == m.posY + r.posY) {
                    temp = m;
                }
            }
            if (temp != null) {
                attackMonster(r, temp);
                temp = null;
            }
        }
    }

    private void attackMonster(Room room, Monster monster) {
        Random rand = new Random();
        int damage = player.maxHit;
        if (player.sword != null) {
            damage = damage + player.sword.intValue;
        }
        int attack = rand.nextInt(damage + 1);
        /*
        call function that updates bottom display with attack, defend
         */
        resetBottom();
        monster.setHp(monster.hp - attack);
        if (monster.hp <= 0) {
            room.monsters.remove(monster);
            displayAttack(attack, -1);
        }
        else {
            int defend = rand.nextInt(monster.maxHit + 1);
            displayAttack(attack, defend);
            if (player.armor != null) {
                if (defend >= player.armor.hp) {
                    defend -= player.armor.hp;
                    player.armor = null;
                }
                else {
                    player.armor.hp -= damage;
                }
            }
            player.setHp(player.hp - defend);
            setTopGrid();
            if (player.hp <= 0) {
                //following according to description, but could be <=
                gameOver(0);
            }
        }
    }

    private void displayAttack(int attack, int defend) {
        //attack is the damage the player does to monster, defend is damage monster does to player
        String monHit = "";
        if (defend != -1) {
            monHit = "The monster hit you for " + defend + " hitpoints!";
        }
        String playHit = "You hit the monster for " + attack + " hitpoints!";

        for (int i = 0; i < monHit.length(); i++){
            bottomGrid[i][1] = new Char(monHit.charAt(i));
        }
        for (int i = 0; i < playHit.length(); i++){
            bottomGrid[i][0] = new Char(playHit.charAt(i));
        }
    }

    private void resetBottom() {
        //set all Chars of bottomGrid to ' '
        for (int i = 0; i < width; i++) {
            for(int j = 0; j < bottomHeight; j++)
            bottomGrid[i][j] = new Char(' ');
        }
    }

    private void displayPack() {
        //display contents of pack in bottom grid
        for (Item item : player.pack) {
            String str = item.serial + " - " + item.name + " ";
            for (int i = 0; i < str.length(); i++) {
                bottomGrid[i][player.pack.indexOf(item)] = new Char(str.charAt(i));
            }
        }
    }

    private void displayCommands() {
        //display list of possible commands
        resetBottom();
        String str = "h,l,k,j,i,?,H,c,d,p,r,T,w,E,0-9. H <cmd> for more info";
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < 1; j++) {
                bottomGrid[i][j] = new Char(str.charAt(i));
            }
        }
    }

    private void displayCommandInfo(String s) {
        resetBottom();
        String str = " ";
        if (s == "h"){
            str = "Command h: Moves the player one spot to the left.";
        }
        else if (s == "l"){
            str = "Command l: Moves the player one spot to the right.";
        }
        else if (s == "k"){
            str = "Command k: Moves the player one spot down.";
        }
        else if (s == "j"){
            str = "Command j: Moves the player one spot up.";
        }
        else if (s == "i"){
            str = "Command i: Displays the contents of the pack.";
        }
        else if (s == "?"){
            str = "Command ?: Shows a list of all the possible commands.";
        }
        else if (s == "H"){
            str = "Command H <command>: Gives more detailed information on each command.";
        }
        else if (s == "c"){
            str = "Command c: Armor that is being worn is taken off and placed in the pack.";
        }
        else if (s == "d"){
            str = "Command d <integer>: drops item <integer> from the pack.";
        }
        else if (s == "p"){
            str = "Command p: Picks up an item off the dungeon floor and adds it to the pack.";
        }
        else if (s == "r"){
            str = "Command r <integer>: Reads a scroll and in turn uses that scroll.";
        }
        else if (s == "T"){
            str = "Command T <integer>: Takes out the sword of <integer> and wields it.";
        }
        else if (s == "w"){
            str = "Command w <integer>: Wears the armor of <integer>";
        }
        else if (s == "E"){
            str = "Command E: Ends the game.";
        }
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < 1; j++) {
                bottomGrid[i][j] = new Char(str.charAt(i));
            }
        }
    }

    private void gameOver(int i) {
        resetBottom();
        String str = " ";
        if (i == 1) {
            str = "The game was ended manually";
        }
        else{
            str = "You died. Game Over.";
        }
        for (int k = 0; k < str.length(); k++) {
            for (int j = 0; j < 1; j++) {
                bottomGrid[k][j] = new Char(str.charAt(k));
            }
        }
    }

    private void checkForItem() {
        Item tempItem = null;
        for (Item i : droppedItems) {
            if (playerCoords[0] == i.posX && playerCoords[1] == i.posY) {
                tempItem = i;
            }
        }
        if (tempItem != null) {
            player.pack.add(tempItem);
            tempItem.setOwner(player);
            droppedItems.remove(tempItem);
        }
        else {
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
    }

    private void pickUpItem(Room r, Item i) {
        //remove item from objectGrid, room
        r.items.remove(i);
        //objectGrid[r.posX + i.posX][r.posY + i.posY] = new Char('.');
        /*for (Item item : r.items) {
            if (item.posX == i.posX && item.posY == i.posY) {
                objectGrid[r.posX + item.posX][r.posY + item.posY] = item.aChar;
            }
        }*/
        //add item to pack, set owner to player
        i.setOwner(player);
        player.addItem(i);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        if (keypress.getKeyChar() == 'h'){
            if (lastChar == 'H'){
                displayCommandInfo("h");
            }
            else{
                playerCoords[0]--;
                checkForMonster();
                if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ' || monsterChars.contains(objectGrid[playerCoords[0]][playerCoords[1]])) {
                    playerCoords[0]++;
                }
            }
        }
        else if (keypress.getKeyChar() == 'j'){
            if (lastChar == 'H'){
                displayCommandInfo("j");
            }
            else {
                playerCoords[1]++;
                checkForMonster();
                if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ' || monsterChars.contains(objectGrid[playerCoords[0]][playerCoords[1]])) {
                    playerCoords[1]--;
                }
            }
        }
        else if (keypress.getKeyChar() == 'k'){
            if (lastChar == 'H'){
                displayCommandInfo("k");
            }
            else {
                playerCoords[1]--;
                checkForMonster();
                if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ' || monsterChars.contains(objectGrid[playerCoords[0]][playerCoords[1]])) {
                    playerCoords[1]++;
                }
            }
        }
        else if (keypress.getKeyChar() == 'l'){
            if (lastChar == 'H'){
                displayCommandInfo("l");
            }
            else {
                playerCoords[0]++;
                checkForMonster();
                if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ' || monsterChars.contains(objectGrid[playerCoords[0]][playerCoords[1]])) {
                    playerCoords[0]--;
                }
            }
        }
        else if (keypress.getKeyChar() == 'p'){
            if (lastChar == 'H'){
                displayCommandInfo("p");
            }
            else {
                checkForItem();
            }
        }
        else if (keypress.getKeyChar() == 'd'){
            //removes LAST item from pack, updates location and adds to dropped items
            //can drop anywhere player can go - including on monsters, which will probably need to be changed
            if (lastChar == 'H'){
                displayCommandInfo("d");
            }
            else {
                if (player.pack.size() > 0) {
                    Item dropped = player.pack.get(player.pack.size() - 1);
                    dropped.posX = playerCoords[0];
                    dropped.posY = playerCoords[1];
                    droppedItems.add(dropped);
                    dropped.setOwner(null);
                    player.pack.remove(dropped);
                }
            }
        }
        else if (keypress.getKeyChar() == 'i'){
            //displays pack in bottom display
            if (lastChar == 'H'){
                displayCommandInfo("i");
            }
            else {
                resetBottom();
                displayPack();
            }
        }
        else if (keypress.getKeyChar() == 'r'){
            //item actions of scroll execute
        }
        else if (keypress.getKeyChar() == 'T'){
            //modify player damage based on whether they are wielding sword
        }
        else if (keypress.getKeyChar() == 'w'){
            //modify player hp based on whether they are wearing armor
        }
        else if (keypress.getKeyChar() == 'c'){
            if (player.armor == null) {
                resetBottom();
                String errorMessage = "Player is not currently wearing armor.";
                for (int i = 0; i < errorMessage.length(); i++){
                    bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                }
            }
            else {
                player.pack.add(player.armor);
                player.armor = null;
            }
            //if no armor on then display message
        }
        else if (keypress.getKeyChar() == '1' || keypress.getKeyChar() == '2' || keypress.getKeyChar() == '3' || keypress.getKeyChar() == '4' || keypress.getKeyChar() == '5' || keypress.getKeyChar() == '6' || keypress.getKeyChar() == '7' || keypress.getKeyChar() == '8' || keypress.getKeyChar() == '9') {
            int index = Character.getNumericValue(keypress.getKeyChar()) - 1;
            Item packItem = null;
            if (lastChar == 'H') {
                //ADD HELP COMMAND
            }
            else if (lastChar == 'w') {
                try {
                    packItem = player.pack.get(index);
                }
                catch(Exception ex) {
                    resetBottom();
                    String errorMessage = "Item does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                if (packItem == null || !(packItem instanceof Armor)) {
                    resetBottom();
                    String errorMessage = "Item is not wearable or does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else if (player.armor != null) {
                    resetBottom();
                    String errorMessage = "Player is already wearing armor. Press 'c' to change.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else {
                    player.pack.remove(packItem);
                    player.setArmor(packItem);
                }
            }
            else if (lastChar == 'r') {
                try {
                    packItem = player.pack.get(index);
                }
                catch(Exception ex) {
                    resetBottom();
                    String errorMessage = "Item does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                if (packItem == null || !(packItem instanceof Scroll)) {
                    resetBottom();
                    String errorMessage = "Item is not readable or does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else {
                    //EXECUTE ACTIONS OF SCROLL
                    player.pack.remove(packItem);
                }
            }
            else if (lastChar == 'T') {
                try {
                    packItem = player.pack.get(index);
                }
                catch(Exception ex) {
                    resetBottom();
                    String errorMessage = "Item does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                if (packItem == null || !(packItem instanceof Sword)) {
                    resetBottom();
                    String errorMessage = "Item is not a weapon or does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else if (player.sword != null) {
                    resetBottom();
                    String errorMessage = "Player is already wielding a sword. Drop it to wield a different one.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else {
                    player.setWeapon(packItem);
                }
            }
        }
<<<<<<< HEAD
        else if (keypress.getKeyChar() == '?'){
            //displays list of possible commands
            if (lastChar == 'H'){
                displayCommandInfo("?");
            }
            else {
                displayCommands();
            }
        }
        else if (keypress.getKeyChar() == 'E'){
            //ends the game
            if (lastChar == 'H'){
                displayCommandInfo("h");
            }
            else {
                gameOver(1);
            }

        }
=======

>>>>>>> 2cd02de9dd375cc106b6d33c68a7898515a084d9
        notifyInputObservers(keypress.getKeyChar());
        setObjectGrid();
        initializeDisplay();
        lastChar = keypress.getKeyChar();
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
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
        for (int i = 0; i < width; i++){
            addObjectToDisplay(topGrid[i], i, 0);
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < gameHeight; j++) {
                addObjectToDisplay(objectGrid[i][j], i, j + topHeight);
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < bottomHeight; j++){
                addObjectToDisplay(bottomGrid[i][j], i, j + topHeight + gameHeight);
            }
        }
        terminal.repaint();
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
