package openlogbook.util;

import openlogbook.debug.Debug;
import openlogbook.factory.DefaultLogBookFactory;
import openlogbook.jlog.gui.optiondialog.AbstractOptionsDialogBox;
import openlogbook.jlog.logbook.jlogentry.LogEntry;
import openlogbook.jlog.util.Band;
import openlogbook.jlog.util.Mode;
import openlogbook.jlogimpl.gui.TableColumnEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * This is a singleton, utility class.  It contains information that is needed
 * throughout the lifetime of the application, that is global throughout.
 * 
 * @author KC0ZPS
 */
public class Utility {
   static private final TimeZone _utcTimeZone = TimeZone.getTimeZone("UTC");
   static private final SimpleDateFormat _dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
   static private final SimpleDateFormat _timeFormat = new SimpleDateFormat("H:mm:ss") ;
   static private final SimpleDateFormat _adifDateFormat = new SimpleDateFormat("yyyyMMdd") ;
   static private final SimpleDateFormat _adifTimeFormat = new SimpleDateFormat("HHmmss") ;

   
   static private String             _lastFilePath = new String("./") ;
   static private ArrayList<String>  _fileHistory = new ArrayList<String>() ;
   
   static final private String KEY_FILE            = "File=" ;
   static final private String KEY_OPERATOR        = "Operator=" ;   
   static final private String KEY_BAND            = "Band=" ;
   static final private String KEY_MODE            = "Mode=" ;
   static final private String KEY_TX_POWER        = "TxPower=" ;
   static final private String KEY_RX_POWER        = "RxPower=" ;
   static final private String KEY_RST_RECEIVED    = "RstReceived=" ;
   static final private String KEY_RST_SENT        = "RstSent=" ;
   static final private String KEY_IS_CUSTOM_THEME = "IsCustomTheme=" ;
   static final private String KEY_CUSTOM_THEME    = "Theme=" ;
   static final private String KEY_XTRA_SCROLL     = "XtraScrollBar=" ;
   static final private String KEY_BACKGROUND      = "Background=" ;
   static final private String KEY_COLUMN          = "Column=" ;

   /**
    * Creates a new Utility.  This constructor is private since it should not be used.
    */
   private Utility() {
   }
   
   /**
    * Returns the last file path opened.
    * 
    * @return the last file path opened.
    */
   static public String getLastFilePath() {
      return _lastFilePath ;
   }

   /**
    * Sets the last file path opened.
    * 
    * @param lastFilePath The path of the last file.
    */
   static public void setLastFilePath(String lastFilePath) {
      _lastFilePath = lastFilePath ;
   }

   /**
    * Adds a file to the history queue.
    *
    * @param file The file to add.
    */
   static public void addFileHistory(File file) {
      String fileName = file.getAbsolutePath() ;
      for (int count = 0; count < _fileHistory.size(); count++) {
         String tmpFileName = (String)_fileHistory.get(count) ;
         if (fileName.equals(tmpFileName)) {
            // File exists.  Skip.
            return ;
         }
      }
      
      if (_fileHistory.size() >= 5) {
         String file1 = (String)_fileHistory.get(1) ;
         String file2 = (String)_fileHistory.get(2) ;
         String file3 = (String)_fileHistory.get(3) ;
         String file4 = (String)_fileHistory.get(4) ;
         String file5 = file.getAbsolutePath() ;
         _fileHistory = new ArrayList<String>() ;
         _fileHistory.add(file1) ;
         _fileHistory.add(file2) ;
         _fileHistory.add(file3) ;
         _fileHistory.add(file4) ;
         _fileHistory.add(file5) ;
      } else {
         _fileHistory.add(file.getAbsolutePath()) ;
      }
   }

   /**
    * Removes a file from the history.
    * 
    * @param file The file to remove from the history list.
    */
   static public void removeFileHistory(File file) {
      String fileName = file.getAbsolutePath() ;
      _fileHistory.remove(fileName) ;
   }
   
   /**
    * Returns a history file at the specified index.
    *
    * @param index The index.
    *
    * @return the file name at the specified index.
    */
   static public String getFileHistory(int index) {
      return (String)_fileHistory.get(index) ;
   }

   /**
    * Gets the file history arraylist.
    *
    * @return the file history arraylist.
    */
   static public ArrayList<String> getFileHistory() {
      return _fileHistory ;
   }

   /**
    * Loads properties from a given file
    *
    * @param filename name of properties file to load.
    * @param optionsDialogBox The options dialog box whose parameters will be saved to the file.
    *
    * @exception FileNotFoundException if the properties file does not exist
    * @exception IOException if an IO error occurs reading the file
    */
   static public void load(String filename, AbstractOptionsDialogBox optionsDialogBox) 
   throws IOException, FileNotFoundException {
      File inputFile = new File(filename);
      FileReader fileReader = new FileReader(inputFile);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String inputString;
      LogEntry logEntry = DefaultLogBookFactory.createLogEntry();
      while ((inputString = bufferedReader.readLine()) != null) {
         StringBuilder buffer = new StringBuilder(inputString);

         // File History
         int length = KEY_FILE.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_FILE)) {
            String file = buffer.substring(length, buffer.length());
            addFileHistory(new File(file));
         }

         // Column
         length = KEY_COLUMN.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_COLUMN)) {
            String file = buffer.substring(length, buffer.length());
            Integer intValue = Integer.valueOf(file);
            TableColumnEnum tableColumnEnum =
               (TableColumnEnum) TableColumnEnum.getIntToTableColumnType().getObjectValue(intValue);
            optionsDialogBox.addColumn(tableColumnEnum);
         }

         // Default Values
         length = KEY_OPERATOR.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_OPERATOR)) {
            String file = buffer.substring(length, buffer.length());
            logEntry.getCallsign().setOperatingStation(file);
         }

         length = KEY_BAND.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_BAND)) {
            String file = buffer.substring(length, buffer.length());
            Integer value = Integer.valueOf(file);
            Band band = (Band) Band.getIntToBandType().getObjectValue(value);
            logEntry.getFrequencyInformation().setBand(band);
         }

         length = KEY_MODE.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_MODE)) {
            String file = buffer.substring(length, buffer.length());
            Integer value = Integer.valueOf(file);
            Mode mode = (Mode) Mode.getIntToModeType().getObjectValue(value);
            logEntry.getFrequencyInformation().setMode(mode);
         }

         length = KEY_TX_POWER.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_TX_POWER)) {
            String file = buffer.substring(length, buffer.length());
            Integer value = Integer.valueOf(file);
            logEntry.getFrequencyInformation().setTxPower(value);
         }

         length = KEY_RX_POWER.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_RX_POWER)) {
            String file = buffer.substring(length, buffer.length());
            Integer value = Integer.valueOf(file);
            logEntry.getFrequencyInformation().setRxPower(value);
         }

         length = KEY_RST_RECEIVED.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_RST_RECEIVED)) {
            String file = buffer.substring(length, buffer.length());
            logEntry.getRst().setRstReceived(file);
         }

         length = KEY_RST_SENT.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_RST_SENT)) {
            String file = buffer.substring(length, buffer.length());
            logEntry.getRst().setRstSent(file);
         }

         // Theme
         length = KEY_IS_CUSTOM_THEME.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_IS_CUSTOM_THEME)) {
            String file = buffer.substring(length, buffer.length());
            Boolean isCustomTheme = Boolean.parseBoolean(file);
            optionsDialogBox.setIsCustomTheme(isCustomTheme);
         }

         length = KEY_CUSTOM_THEME.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_CUSTOM_THEME)) {
            String file = buffer.substring(length, buffer.length());
            optionsDialogBox.setSelectedTheme(file);
         }

         length = KEY_XTRA_SCROLL.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_XTRA_SCROLL)) {
            String file = buffer.substring(length, buffer.length());
            Boolean isXtraScrollbars = Boolean.parseBoolean(file);
            optionsDialogBox.setIsXtraScrollbars(isXtraScrollbars);
         }

         length = KEY_BACKGROUND.length();
         if (buffer.length() > length && buffer.substring(0, length).equals(KEY_BACKGROUND)) {
            String file = buffer.substring(length, buffer.length());
            Boolean isBackground = Boolean.parseBoolean(file);
            optionsDialogBox.setIsDesktopBackground(isBackground);
         }
      }

      optionsDialogBox.setDefaultLogEntry(logEntry);
   }

   /**
    * Writes to the file.
    *
    * @param filename The filename.
    * @param optionsDialogBox The options dialog box to populate the data read from the file.
    */
   static public void write(String filename, AbstractOptionsDialogBox optionsDialogBox) {
      try {
         FileOutputStream fOutStream = new FileOutputStream(new File(filename));
         PrintWriter printWriter = new PrintWriter(fOutStream, true);

         // File History
         for (int count = 0; count < _fileHistory.size(); count++) {
            String str = (String)_fileHistory.get(count) ;
            printWriter.println(KEY_FILE + str) ;
         }

         // Column
         ArrayList<TableColumnEnum> list = optionsDialogBox.getColumnList();
         int size = list.size() ;
         for (int index = 0; index < size; index++) {
            TableColumnEnum tableColumnEnum = (TableColumnEnum)list.get(index) ;
            int value = TableColumnEnum.getIntToTableColumnType().getIntValue(tableColumnEnum) ;
            printWriter.println(KEY_COLUMN + value) ;
         }
         
         // Default Values
         LogEntry entry = optionsDialogBox.getDefaultLogEntry() ;
         printWriter.println(KEY_OPERATOR + entry.getCallsign().getOperatingStation()) ;
         int band = Band.getIntToBandType().getIntValue(entry.getFrequencyInformation().getBand()) ;
         printWriter.println(KEY_BAND + band) ;
         int mode = Mode.getIntToModeType().getIntValue(entry.getFrequencyInformation().getMode()) ;
         printWriter.println(KEY_MODE + mode) ;
         printWriter.println(KEY_RST_SENT + entry.getRst().getRstSent()) ;
         printWriter.println(KEY_RST_RECEIVED + entry.getRst().getRstReceived()) ;
         Integer txPower = entry.getFrequencyInformation().getTxPower() ;
         if (txPower != null) {
            printWriter.println(KEY_TX_POWER + entry.getFrequencyInformation().getTxPower()) ;            
         }
         Integer rxPower = entry.getFrequencyInformation().getRxPower() ;
         if (rxPower != null) {
            printWriter.println(KEY_RX_POWER + entry.getFrequencyInformation().getRxPower()) ;            
         }
         
         // Theme
         boolean isCustomTheme = optionsDialogBox.isCustomTheme() ;
         printWriter.println(KEY_IS_CUSTOM_THEME + isCustomTheme) ;
         if (isCustomTheme) {
            printWriter.println(KEY_CUSTOM_THEME + optionsDialogBox.getSelectedTheme()) ;
            printWriter.println(KEY_XTRA_SCROLL + optionsDialogBox.isXtraScrollbars()) ;
            printWriter.println(KEY_BACKGROUND + optionsDialogBox.isDesktopBackground()) ;
         }
         
         printWriter.flush();
         printWriter.close();
      } catch (Exception e) {
         Debug.logException(e) ;
      }

   }
   
   /**
    * Gets a UTC SimpleTimeZone.
    * 
    * @return a UTC SimpleTimeZone.
    */
    static public TimeZone getUtcTimeZone() {
      return TimeZone.getTimeZone("UTC"); 
  }
  
   
   /**
    * Gets a SimpleDateFormat for displaying dates.
    * 
    * @return a SimpleDateFormat. 
    */
   static public SimpleDateFormat getDateFormat() {
      _dateFormat.setTimeZone(_utcTimeZone) ;
      return _dateFormat ;
   }
   
   /**
    * Gets a SimpleDateFormat for dislaying time.
    * 
    * @return a SimpleDateFormat. 
    */
   static public SimpleDateFormat getTimeFormat() {
      _timeFormat.setTimeZone(_utcTimeZone) ;   
      return _timeFormat ;
   }
 
   /**
    * Gets the adif date format.
    * 
    * @return the adif date format.
    */
   static public SimpleDateFormat getAdifDateFormat() {
      _adifDateFormat.setTimeZone(_utcTimeZone) ;
      return _adifDateFormat ;      
   }
   
   /**
    * Gets the adif time format.
    * 
    * @return the adif time format.
    */
   static public SimpleDateFormat getAdifTimeFormat() {
      _adifTimeFormat.setTimeZone(_utcTimeZone) ;   
      return _adifTimeFormat ;      
   }
   
}