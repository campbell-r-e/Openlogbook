package jalog.jlogimpl.remotecallsign.callookreader;

import jalog.factory.CallsignInformationFactory;
import jalog.jlog.remotecallsign.RemoteCallsignInformation;
import jalog.jlog.remotecallsign.ConnectionReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * An implementation of the ConnectionReader for reading data from the "calllook.info" web site.
 * 
 * @author KC0ZPS
 */
public class CallookReaderImpl implements ConnectionReader {
   private RemoteCallsignInformation _callsignInformation ;
   
   private static final String URL = "http://callook.info/index.php?callsign=" ;
   private static final String DISPLAY = "&display=xml" ;

   /**
    * Creates a new CallLookReaderImpl
    */
   public CallookReaderImpl() {
   }

   //**********
   // Methods that are inherited from ConnectionReader
   //**********

   public RemoteCallsignInformation get(String callsign)
   throws ParserConfigurationException, MalformedURLException, IOException, SAXException {
      _callsignInformation = CallsignInformationFactory.createCallsignInformation() ;
      Document doc = readXmlPage(callsign);
      Node root = doc.getDocumentElement();
      convertDocumentToObject(root) ;
      return _callsignInformation ;
   }

   public RemoteCallsignInformation getCachedCallsignInformation() {
      return _callsignInformation ;
   }
   
   //**********
   // Private methods
   //**********
   
   /**
    * Start reading data from the xml page.
    * 
    * @param callsign the callsign to query.
    */
   private Document readXmlPage(String callsign) 
   throws ParserConfigurationException, MalformedURLException, IOException, SAXException {
      DocumentBuilder docBuilder;
      Document doc = null;
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      docBuilderFactory.setIgnoringElementContentWhitespace(true);
      docBuilder = docBuilderFactory.newDocumentBuilder();
      
      URL url = new URL(URL + callsign + DISPLAY);
      URLConnection yc = url.openConnection();
      
      doc = docBuilder.parse(yc.getInputStream()) ;      
      return doc;
   }
   
   /**
    * Convert the node information to a RemoteCallsignInformation object.
    * 
    * @param node The node to get the data from.
    */
   private void convertDocumentToObject(Node node) {
      String nodeName = node.getNodeName();
      
      createEntry(node) ;
      // System.out.println("NodeName: " + nodeName + ", NodeValue: " + nodeValue);
      
      if (nodeName.equals(CallookXmlConstants.Root)) {
         System.out.println("Start CallLook") ;
      }

      NodeList children = node.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
          Node child = children.item(i);
          if (child.getNodeType() == Node.ELEMENT_NODE) {
             convertDocumentToObject(child);
          }
      }      
   }
   
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
    * Assign the correct data from a single entry.
    * 
    * @param node The node to get the data from.
    */
   private void createEntry(Node node) {
      String nodeName = node.getNodeName();
      String nodeValue = getElementValue(node);

      if (nodeName.equals(CallookXmlConstants.Status.toString())) {
         _callsignInformation.setStatus(nodeValue) ;
      } else if (nodeName.equals(CallookXmlConstants.Callsign.toString())) {
         if (checkCallsignStatus(node, CallookXmlConstants.CurrentCallsignValue)) {
            _callsignInformation.setCurrentCallsign(nodeValue) ;            
         }
         if (checkCallsignStatus(node, CallookXmlConstants.PreviousCallsignValue)) {
            _callsignInformation.setPreviousCallsign(nodeValue) ;            
         }         
      } else if (nodeName.equals(CallookXmlConstants.Name.toString())) {
         _callsignInformation.setName(nodeValue) ;
      } else if (nodeName.equals(CallookXmlConstants.AddressLine1.toString())) {
         _callsignInformation.setAddressLine1(nodeValue) ;
      } else if (nodeName.equals(CallookXmlConstants.AddressLine2.toString())) {
         _callsignInformation.setAddressLine2(nodeValue) ;
      } else if (nodeName.equals(CallookXmlConstants.Latitude.toString())) {
         _callsignInformation.setLatitude(nodeValue) ;
      } else if (nodeName.equals(CallookXmlConstants.Longitude.toString())) {
         _callsignInformation.setLongitude(nodeValue) ;
      } else if (nodeName.equals(CallookXmlConstants.GrantDate.toString())) {
         _callsignInformation.setGrantDate(nodeValue) ;
      } else if (nodeName.equals(CallookXmlConstants.ExpiryDate.toString())) {
         _callsignInformation.setExpiryDate(nodeValue) ;
      } else if (nodeName.equals(CallookXmlConstants.Frn.toString())) {
         _callsignInformation.setFrn(nodeValue) ;
      }
   }
 
   /**
    * Checks the type of callsign we are looking at.
    * 
    * @param node The node to get the data from.
    * @param value The callsign node status value we are interested in.
    * 
    * @return true if the callsign node contains the status that was passed in.
    */
   private boolean checkCallsignStatus(Node node, CallookXmlConstants callLookXmlConstants) {
      NamedNodeMap attributes = node.getAttributes();
      for (int i = 0; i < attributes.getLength(); i++) {
         Node attribute = attributes.item(i);
         if (attribute.getNodeName().equals(CallookXmlConstants.CallsignStatus.toString())) {
            if (attribute.getNodeValue().equals(callLookXmlConstants.toString())) {
               return true ;
            }
         }
      }
      return false ;
   }
   
}
