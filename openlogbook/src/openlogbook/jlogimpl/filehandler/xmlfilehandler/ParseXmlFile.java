package openlogbook.jlogimpl.filehandler.xmlfilehandler ;

import openlogbook.factory.DefaultLogBookFactory;
import openlogbook.jlog.filehandler.ReadData;
import openlogbook.jlog.logbook.LogBook;
import openlogbook.jlog.logbook.jlogentry.LogEntry;
import openlogbook.jlog.util.Band;
import openlogbook.jlog.util.IotaEnum;
import openlogbook.jlog.util.Mode;
import openlogbook.jlog.util.QslRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import openlogbook.jlogimpl.constants.XmlConstants;

/** 
 * This main function of this class is to save LogBook to an XML file.  However, it has
 * various other functions for handling XML file which are currently not used.  e.g., saving
 * to an XML file and various other XML utilities.  These methods are set private, but can
 * be public if needed.  
 * 
 * @author KC0ZPS
 * 
 * @deprecated Use ADIF Instead.
 */
public class ParseXmlFile implements ReadData {
    
    private String _xmlFileName ;
    
    /**
     * Creates a new instance of ParseXmlFile.
     * 
     * @param fileName The name of the file we will be reading from.
     */
    public ParseXmlFile(String fileName) {
       _xmlFileName = fileName ;
       // parse XML file -> XML document will be build
       // Document doc = parseFile(_xmlFileName);
       // get root node of xml tree structure
       // Node root = doc.getDocumentElement();
       // write node and its child nodes into System.out
       // System.out.println("Statemend of XML document...");
       // writeDocumentToOutput(root,0);
       // System.out.println("... end of statement");
       // write Document into XML file
       // saveXMLDocument(_targetFileName, doc);
    }
    
    //**********
    // Methods inherited from ReadData
    //**********

    /**
     * Reads the XML Document.
     * 
     * @param logBook The log book that will hold that data from this file.
     * 
     * @throws SAXException is thrown if the file contains an invalid XML structure.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @throws ParserConfigurationException if the DocumentBuilder cannot be created which satisfies the configuration 
     *      requested.
     * @throws NumberFormatException if a string is parsed when a number is expected.
     */
    public void execute(LogBook logBook) 
    throws SAXException, IOException, ParserConfigurationException, NumberFormatException {
       Document doc = parseFile(_xmlFileName);
       Node root = doc.getDocumentElement();
       writeDocumentToLogBook(root, logBook, null);
    }

    //**********
    // Private methods
    //**********
    
    /** 
     * Returns element value
     * 
     * @param elem element (it is XML tag)
     * 
     * @return Element value otherwise empty String
     */
    private final static String getElementValue( Node elem ) {
        Node kid;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
                    if( kid.getNodeType() == Node.TEXT_NODE  ){
                        return kid.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    
    /**
     * This method is used for basic output.  
     * It will return a String object containing the number of spaces specified in indent.
     * 
     * @param indent The number of spaces to indent.
     * @return a String object containing the number of spaces specified in indent.
     */
    /* private String getIndentSpaces(int indent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    } */
    
    /** Writes node and all child nodes into System.out
     * 
     * @param node XML node from from XML tree wrom which will output statement start
     * @param indent number of spaces used to indent output
     */
    /* private void writeDocumentToOutput(Node node,int indent) {
        // get element name
        String nodeName = node.getNodeName();
        // get element value
        String nodeValue = getElementValue(node);
        // get attributes of element
        NamedNodeMap attributes = node.getAttributes();
        System.out.println(getIndentSpaces(indent) + "NodeName: " + nodeName + ", NodeValue: " + nodeValue);
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            System.out.println(getIndentSpaces(indent + 2) + "AttributeName: " + attribute.getNodeName() 
            + ", attributeValue: " + attribute.getNodeValue());
        }
        // write all child nodes recursively
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                writeDocumentToOutput(child,indent + 2);
            }
        }
    } */
        
    /** Saves XML Document into XML file.
     * 
     * @param fileName XML file name
     * @param doc XML document to save
     * @return <B>true</B> if method success <B>false</B> otherwise
     */    
    /* private boolean saveXMLDocument(String fileName, Document doc) {
        System.out.println("Saving XML file... " + fileName);
        // open output stream where XML Document will be saved
        File xmlOutputFile = new File(fileName);
        FileOutputStream fos;
        Transformer transformer;
        try {
            fos = new FileOutputStream(xmlOutputFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error occured: " + e.getMessage());
            return false;
        }
        // Use a Transformer for output
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        }
        catch (TransformerConfigurationException e) {
            System.out.println("Transformer configuration error: " + e.getMessage());
            return false;
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(fos);
        // transform source into result will do save
        try {
            transformer.transform(source, result);
        }
        catch (TransformerException e) {
            System.out.println("Error transform: " + e.getMessage());
        }
        System.out.println("XML file saved.");
        return true;
    } */
    
    /** Parses XML file and returns XML document.
     * 
     * @param fileName XML file to parse
     * 
     * @return XML document or <B>null</B> if error occured
     * 
     * @throws SAXException is thrown if the file contains an invalid XML structure.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @throws ParserConfigurationException if the DocumentBuilder cannot be created which satisfies 
     *         the configuration requested.
     */
    private Document parseFile(String fileName) throws SAXException, IOException, ParserConfigurationException {
        System.out.println("Parsing XML file... " + fileName);
        DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        docBuilder = docBuilderFactory.newDocumentBuilder();
        File sourceFile = new File(fileName);
        doc = docBuilder.parse(sourceFile);
        System.out.println("XML file parsed");
        return doc;
    }
    
    /**
     * Reads the Node attributes and puts the correct values into the log book object.
     * This is an iterative method.
     * 
     * @param node The Node in question.
     * @param logBook The users log book.
     * @param logEntry The log entry that will be placed into the log book.
     * 
     * @throws NumberFormatException If the node entry contains a String format instead of a number.
     */
    private void writeDocumentToLogBook(Node node, LogBook logBook, LogEntry logEntry) 
    throws NumberFormatException {
       String nodeName = node.getNodeName();
       String nodeValue = getElementValue(node);
       
       handleAttributes(node, logBook) ;
       
       // System.out.println("NodeName: " + nodeName + ", NodeValue: " + nodeValue);
       
       if (nodeName.equals("LogEntry")) {
          // System.out.println("Start LogEntry") ;
          logEntry = DefaultLogBookFactory.createLogEntry() ;
       }
       createLogEntry(nodeName, nodeValue, logEntry) ;
       
       NodeList children = node.getChildNodes();
       for (int i = 0; i < children.getLength(); i++) {
           Node child = children.item(i);
           if (child.getNodeType() == Node.ELEMENT_NODE) {
              writeDocumentToLogBook(child, logBook, logEntry);
           }
       }
       
       if (nodeName.equals("LogEntry")) {
          // System.out.println("Done LogEntry... adding.") ;
          logBook.addEntry(logEntry) ;
       }
   }

    /**
     * Handle the attributes of an XML Node.
     * 
     * @param node The node in question.
     * @param logBook The users log book to assign any defined attributes.
     */
   private void handleAttributes(Node node, LogBook logBook) {
      NamedNodeMap attributes = node.getAttributes();
      for (int i = 0; i < attributes.getLength(); i++) {
         Node attribute = attributes.item(i);
         if (attribute.getNodeName().equals(XmlConstants.Version.toString())) {
            logBook.setVersion(attribute.getNodeValue()) ;
         }
         // System.out.println("AttributeName: " + attribute.getNodeName() + ", attributeValue: " + attribute.getNodeValue());
      }

   }
    
   /**
    * Create the appropriate log entry from the node name and node value.
    * 
    * @param nodeName The keyword of the node.
    * @param nodeValue The value of the node.
    * @param logEntry The entry to create.
    * 
    * @throws NumberFormatException If the node entry contains a String format instead of a number.
    */
   private void createLogEntry(String nodeName, String nodeValue, LogEntry logEntry) 
   throws NumberFormatException {
      if (nodeName.equals("Station")) {
         logEntry.getCallsign().setContactStation(nodeValue) ;
      } else if (nodeName.equals("StartDate")) {
         Long value = new Long(nodeValue) ;
         Date date = new Date(value) ;
         logEntry.getEra().setStartDate(date) ;
      } else if (nodeName.equals("EndDate")) {
         Long value = new Long(nodeValue) ;
         Date date = new Date(value) ;
         logEntry.getEra().setEndDate(date) ;
      } else if (nodeName.equals("SameAsStart")) {
         // logEntry.getEra().setDateSameAsStart(new Boolean(nodeValue)) ; 
      } else if (nodeName.equals("Mode")) {
         Integer value = new Integer(nodeValue) ;
         Mode mode = (Mode)Mode.getIntToModeType().getObjectValue(value) ;
         logEntry.getFrequencyInformation().setMode(mode) ; 
      } else if (nodeName.equals("Band")) {
         Integer value = new Integer(nodeValue) ;
         Band band = (Band)Band.getIntToBandType().getObjectValue(value) ;
         logEntry.getFrequencyInformation().setBand(band) ;
      } else if (nodeName.equals("Frequency")) {
         logEntry.getFrequencyInformation().setFrequency(nodeValue) ; 
      } else if (nodeName.equals("Power")) {
         Integer value = new Integer(nodeValue) ;
         logEntry.getFrequencyInformation().setTxPower(value) ;
      } else if (nodeName.equals("Locator")) {
         // logEntry.getMisc().setLocator(nodeValue) ;
      } else if (nodeName.equals("Comment")) {
         logEntry.getMisc().setComment(nodeValue) ;
      } else if (nodeName.equals("RstReceived")) {
         logEntry.getRst().setRstReceived(nodeValue) ;
      } else if (nodeName.equals("RstSent")) {
         logEntry.getRst().setRstSent(nodeValue) ;
      } else if (nodeName.equals("County")) {
         logEntry.getContactAddress().setCounty(nodeValue) ;
      } else if (nodeName.equals("Name")) {
         logEntry.getContactAddress().setName(nodeValue) ;
      } else if (nodeName.equals("Address")) {
         logEntry.getContactAddress().setAddress(nodeValue) ;
      } else if (nodeName.equals("QTH")) {
         logEntry.getContactStationInformation().setQth(nodeValue) ;
      } else if (nodeName.equals("IOTA1")) {
         Integer value = new Integer(nodeValue) ;
         IotaEnum iotaEnum = (IotaEnum)IotaEnum.getIntToIotaType().getObjectValue(value) ;
         logEntry.getContactStationInformation().getIota().setIotaEnum(iotaEnum) ;
      } else if (nodeName.equals("IOTA2")) {
         Integer value = new Integer(nodeValue) ;
         logEntry.getContactStationInformation().getIota().setValue(value) ;
      } else if (nodeName.equals("QslReceived")) {
         Boolean value = Boolean.parseBoolean(nodeValue) ;
         QslRequest qslRequest ;
         if (value.equals(Boolean.TRUE)) {
            qslRequest = QslRequest.Yes ;
         } else {
            qslRequest = QslRequest.No ;            
         }
         logEntry.getQsl().setQslReceived(qslRequest) ;
      } else if (nodeName.equals("QslReceivedDate")) {
         Long value = new Long(nodeValue) ;
         Date date = new Date(value) ;
         logEntry.getQsl().setQslReceivedDate(date) ;
      } else if (nodeName.equals("QslSent")) {
         Boolean value = Boolean.parseBoolean(nodeValue) ;
         QslRequest qslRequest ;
         if (value.equals(Boolean.TRUE)) {
            qslRequest = QslRequest.Yes ;
         } else {
            qslRequest = QslRequest.No ;            
         }
         logEntry.getQsl().setQslSent(qslRequest) ;
      } else if (nodeName.equals("QslSentDate")) {
         Long value = new Long(nodeValue) ;
         Date date = new Date(value) ;
         logEntry.getQsl().setQslSentDate(date) ;
      }
   }    
}
