package openlogbook.jlogimpl.filehandler.xmlfilehandler;

import openlogbook.jlog.filehandler.SaveData;
import openlogbook.jlog.logbook.LogBook;
import openlogbook.jlog.logbook.jlogentry.ContactAddress;
import openlogbook.jlog.logbook.jlogentry.ContactStationInformation;
import openlogbook.jlog.logbook.jlogentry.Era;
import openlogbook.jlog.logbook.jlogentry.FrequencyInformation;
import openlogbook.jlog.logbook.jlogentry.LogEntry;
import openlogbook.jlog.logbook.jlogentry.Misc;
import openlogbook.jlog.logbook.jlogentry.Qsl;
import openlogbook.jlog.logbook.jlogentry.Rst;
import openlogbook.jlog.util.Band;
import openlogbook.jlog.util.IotaEnum;

import openlogbook.jlog.util.QslRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.ecs.xml.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * Saves a LogBook to an XML file.
 * 
 * @author KC0ZPS
 * 
 * @deprecated Use ADIF Instead.
 */
public class SaveXmlFile implements SaveData {
  
   private File    _file ;
   private LogBook _logBook ;

   
   /**
    * Creates a new instance of a SaveXmlFile.
    * 
    * @param file The file to save to.
    * @param logBook The log book object that will be saved to the file.
    */
   public SaveXmlFile(File file, LogBook logBook) {
      _file = file ;
      _logBook = logBook ;
   }
   
   //**********
   // Methods inherited from SaveData
   //**********

   /**
    * Executes the save command.
    * 
    * @throws FileNotFoundException if the file cannot be found.  This is needed because this
    * method uses FileOutputStream.
        * @throws ParserConfigurationException 
            * @throws TransformerException 
                */
               public void execute() throws FileNotFoundException, ParserConfigurationException, TransformerException {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();
      Element rootElement = doc.createElement("Logbook");
      doc.appendChild(rootElement);
      
      XML logBookXml = new XML("LogBook") ; 

      logBookXml.addAttribute(XmlConstants.Version.toString(), _logBook.getVersion()) ;
      
      ArrayList<LogEntry> list = _logBook.getLogEntries() ;
      for (int index = 0; index < list.size(); index++) {
         LogEntry entry = list.get(index) ;
         Element log=createLogEntry(entry);
         
         doc.appendChild(log);
         
      }
      
      Element logBook = doc.createElement("LogBook");  // Equivalent to logBookXml
      rootElement.appendChild(logBook);    
             
             
      
      FileOutputStream stream = new FileOutputStream(_file) ;
      
      System.out.println("Saving to " + _file.getAbsolutePath()) ;
      
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      transformer.transform(new DOMSource(doc), new StreamResult(System.out));
      
      // document.output(System.out) ;
   }

   //**********
   // Private methods
   //**********

   /**
    * Creates an XML object from a LogEntry.
    * 
    * @param entry The log entry to create the XML object from.
    * 
    * @return An XML object representing the log entry.
        * @throws ParserConfigurationException 
        */
       private Element createLogEntry(LogEntry entry) throws ParserConfigurationException {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();

      // Root element <LogEntry>
      Element logEntry = doc.createElement("LogEntry");
      doc.appendChild(logEntry);
      
      // Station
      Element station = doc.createElement("Station");
      station.appendChild(doc.createTextNode(entry.getCallsign().getContactStation()));
      logEntry.appendChild(station);
     
      
      // Date
      Era era = entry.getEra() ;
      Element StartDate = doc.createElement(XmlConstants.StartDate.toString());
      StartDate.appendChild(doc.createTextNode(String.valueOf(era.getStartDate().getTime())));

      logEntry.appendChild(StartDate);

    //
     
    Element endDate = doc.createElement(XmlConstants.EndDate.toString());
    endDate.appendChild(doc.createTextNode(String.valueOf(era.getEndDate().getTime())));

    logEntry.appendChild(endDate);
    
     
    
    Element sameAsStart = doc.createElement("SameAsStart");
    boolean isSameAsStart = era.getStartDate().getTime() == era.getEndDate().getTime();
    sameAsStart.appendChild(doc.createTextNode(String.valueOf(isSameAsStart)));
    logEntry.appendChild(sameAsStart);
    
      
   //
     
      // Frequency
      FrequencyInformation freqInfo = entry.getFrequencyInformation();
    Element frequencyElement = doc.createElement(XmlConstants.Frequency.toString());
    frequencyElement.appendChild(doc.createTextNode(String.valueOf(freqInfo.getFrequency()))); 
    logEntry.appendChild(frequencyElement);
   

    // Mode
    Element mode = doc.createElement(XmlConstants.Mode.toString());
    mode.appendChild(doc.createTextNode(""+freqInfo.getMode().toString())); 
   logEntry.appendChild(mode);
    //Band

    // Create <Band> element
    Element bandElement = doc.createElement(XmlConstants.Band.toString());

   // Convert Band type to integer (same logic as old code)
   int value = Band.getIntToBandType().getIntValue(freqInfo.getBand());

   // Append value as text inside <Band>
   bandElement.appendChild(doc.createTextNode(String.valueOf(value)));

   // Attach <Band> to <LogEntry>
   logEntry.appendChild(bandElement);

  //Power 
  Element TXpower =doc.createElement(XmlConstants.Power.toString());

  int tx = freqInfo.getTxPower();
  TXpower.appendChild(doc.createTextNode(String.valueOf(tx)));
  logEntry.appendChild(TXpower);

  //misc 
  Misc misc = entry.getMisc();
  Element locatorElement=doc.createElement(XmlConstants.Locator.toString());
  // could be added misc.getLocator()
  //locatorElement.appendChild(doc.createTextNode(misc.getLocator()));

  //logEntry.appendChild(locatorElement);



  // Comments 
  Element Comment = doc.createElement(XmlConstants.Comment.toString());
  Comment.appendChild(doc.createTextNode(misc.getComment()));
  Comment.appendChild(Comment);
 // RST Recieved
 Rst rst = entry.getRst();
 Element RstReceived = doc.createElement(XmlConstants.RstReceived.toString());
 RstReceived.appendChild(doc.createTextNode(rst.getRstReceived()));
 RstReceived.appendChild(RstReceived);

 // RST Sent
 Element RstSent = doc.createElement(XmlConstants.RstSent.toString());
 RstSent.appendChild(doc.createTextNode(rst.getRstSent()));
 RstSent.appendChild(RstSent);

// Address data 
ContactAddress contactAddress = entry.getContactAddress() ;
Element ContactCounty = doc.createElement(XmlConstants.County.toString());
ContactCounty.appendChild(doc.createTextNode(contactAddress.getCounty()));
ContactCounty.appendChild(ContactCounty);
//Contact name 
Element ContactName= doc.createElement(XmlConstants.Name.toString());
ContactName.appendChild(doc.createTextNode(contactAddress.getName()));
ContactName.appendChild(ContactName);
// Adress
Element Address = doc.createElement(XmlConstants.Address.toString());
Address.appendChild(doc.createTextNode(contactAddress.getAddress()));
Address.appendChild(Address);
// Basic Contact Station Information
ContactStationInformation contactStationInformation = entry.getContactStationInformation() ;
Element QTH =doc.createElement(XmlConstants.QTH.toString());
QTH.appendChild(doc.createTextNode(contactStationInformation.getQth()));
QTH.appendChild(QTH);
// IOTA1
Element iota1Element = doc.createElement(XmlConstants.IOTA1.toString());
IotaEnum iotaEnum = contactStationInformation.getIota().getIotaEnum();
int iota1Value = IotaEnum.getIntToIotaType().getIntValue(iotaEnum);
iota1Element.appendChild(doc.createTextNode(String.valueOf(iota1Value)));
logEntry.appendChild(iota1Element);

// IOTA2
Element iota2Element = doc.createElement(XmlConstants.IOTA2.toString());
iota2Element.appendChild(doc.createTextNode(String.valueOf(contactStationInformation.getIota().getValue())));
logEntry.appendChild(iota2Element);

// QSL
Qsl qsl = entry.getQsl();

// QslSent
Element qslSentElement = doc.createElement(XmlConstants.QslSent.toString());
if (qsl.isQslSent().equals(QslRequest.Yes) || qsl.isQslSent().equals(QslRequest.Requested)) {
    qslSentElement.appendChild(doc.createTextNode("true"));
} else {
    qslSentElement.appendChild(doc.createTextNode("false"));
}
logEntry.appendChild(qslSentElement);

// QslSentDate
Element qslSentDateElement = doc.createElement(XmlConstants.QslSentDate.toString());
qslSentDateElement.appendChild(doc.createTextNode(String.valueOf(qsl.getQslSentDate().getTime())));
logEntry.appendChild(qslSentDateElement);

// QslReceived
Element qslReceivedElement = doc.createElement(XmlConstants.QslReceived.toString());
if (qsl.isQslReceived().equals(QslRequest.Yes) || qsl.isQslReceived().equals(QslRequest.Requested)) {
    qslReceivedElement.appendChild(doc.createTextNode("true"));
} else {
    qslReceivedElement.appendChild(doc.createTextNode("false"));
}
logEntry.appendChild(qslReceivedElement);

// QslReceivedDate
Element qslReceivedDateElement = doc.createElement(XmlConstants.QslReceivedDate.toString());
qslReceivedDateElement.appendChild(doc.createTextNode(String.valueOf(qsl.getQslReceivedDate().getTime())));
logEntry.appendChild(qslReceivedDateElement);
return logEntry;

     
      
   }
   
}
