
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Test {

    public static void main(String[] args) {

	// check if a filename is passed in.  If not, print a usage message.
	// If it is, open the file
        String fileName = null;
        switch (args.length) {
        case 1:
           // note that the relative file path may depend on what IDE you are
	   // using.  This worked for NetBeans.
           fileName = "src/xmlFiles/" + args[0];
           break;
        default:
           System.out.println("java Test <xmlfilename>");
	   return;
        }

	// Create a saxParserFactory, that will allow use to create a parser
	// Use this line unchanged
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

	// We haven't covered exceptions, so just copy the try { } catch {...}
	// exactly, // filling in what needs to be changed between the open and 
	// closed braces.
        try {
	    // just copy this
            SAXParser saxParser = saxParserFactory.newSAXParser();
	    // just copy this
            StudentXMLHandler handler = new StudentXMLHandler();
	    // just copy this.  This will parse the xml file given by fileName
            saxParser.parse(new File(fileName), handler);
	    // This will change depending on what kind of XML we are parsing
            Student[ ] students = handler.getStudents();
	    // print out all of the students.  This will change depending on 
	    // what kind of XML we are parsing
            for (Student student : students) {
                System.out.println(student);
            }
            /*
             * the above is a different form of 
             for (int i = 0; i < students.length; i++) {
                System.out.println(students[i]);
            }
            */
	// these lines should be copied exactly.
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
