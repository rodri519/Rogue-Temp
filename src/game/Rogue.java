package game;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Rogue implements Runnable {

    private static final int DEBUG = 0;
    private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid = null;
    private Thread keyStrokePrinter;

    public Rogue(RogueXMLHandler handler) {
        displayGrid = new ObjectDisplayGrid(handler);
    }

    @Override
    public void run() {
        displayGrid.initializeDisplay();
    }
    public static void main(String[] args) throws Exception {
        String fileName = null;
        switch (args.length) {
            case 1:
                fileName = "xmlFiles/" + args[0];
                break;
            default:
                System.out.println("java Test <xmlfilename>");
                return;
        }

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        //try {
        SAXParser saxParser = saxParserFactory.newSAXParser();
        RogueXMLHandler handler = new RogueXMLHandler();
        saxParser.parse(new File(fileName), handler);

        /*} catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }*/
        Rogue test = new Rogue(handler);
        Thread testThread = new Thread(test);
        testThread.start();

        test.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid));
        test.keyStrokePrinter.start();

        testThread.join();
        test.keyStrokePrinter.join();
    }

}
