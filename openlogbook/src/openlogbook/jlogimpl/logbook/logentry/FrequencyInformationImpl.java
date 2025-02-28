package openlogbook.jlogimpl.logbook.logentry;

import openlogbook.jlog.logbook.jlogentry.FrequencyInformation;
import openlogbook.jlog.util.Band;
import openlogbook.jlog.util.Mode;
import openlogbook.jlogimpl.filehandler.adif.AdifConstants;

/**
 * An implementation of the FrequencyInformation interface.
 * 
 * @author KC0ZPS
 */
public class FrequencyInformationImpl implements FrequencyInformation {

   private Mode    _mode = Mode.Blank ;
   private String  _unknownMode = new String() ;
   private Band    _band = Band.Blank ;
   private String  _frequency = new String() ;
   private Integer _txPower ;
   private Integer _rxPower ;
   
   /**
    * Creates a new FrequencyInformationImpl.
    */
   public FrequencyInformationImpl() {
   }
   
   //**********
   // Methods that are inherited from FrequencyInformation
   //**********

   /**
    * Gets the mode.  Mode.Blank if not used.
    * 
    * @return the mode.
    */
   public Mode getMode() {
      return _mode ;
   }
   
   /**
    * Sets the mode.  Mode.Blank if not used.
    * 
    * @param mode the mode.
    */
   public void setMode(Mode mode) {
      _mode = mode ;
   }
   
   /**
    * Gets the unknown mode.  This is used if the mode read from the file doesn't match the enumeration.
    * 
    * @return the unknown mode.
    */
   public String getUnknownMode() {
      return _unknownMode ;
   }
   
   /**
    * Sets the unknown mode.  This is used if the mode read from the file doesn't match the enumeration.
    * 
    * @param unknownMode the unknown mode.
    */
   public void setUnknownMode(String unknownMode) {
      _unknownMode = unknownMode ;
   }

   /**
    * Gets the Band.Blank.  null if not used.
    * 
    * @return the band.
    */
   public Band getBand() {
      return _band ;
   }
   
   /**
    * Sets the band.  Band.Blank if not used.
    * 
    * @param band the band
    */
   public void setBand(Band band) {
      _band = band ;
   }
   
   /**
    * Gets the frequency.  A "" indicates that this field is not in use.
    * 
    * @return the frequency.
    */
   public String getFrequency() {
      return _frequency ;
   }
   
   /**
    * Sets the frequency.  A "" indicates that this field is not in use.
    * 
    * @param frequency the frequency.
    */
   public void setFrequency(String frequency) {
      _frequency = frequency ;
   }
   
   /**
    * Gets the transmitting power.  null if not used.
    * 
    * @return the transmitting power.
    */
   public Integer getTxPower() {
      return _txPower ;
   }
   
   /**
    * Sets the transmitting power.  null if not used.
    * 
    * @param power the transmitting power.
    */
   public void setTxPower(Integer power) {
      _txPower = power ;
   }

   /**
    * Gets the power of other station in Watts.  null if not used.
    * 
    * @return the power of other station in Watts.
    */
   public Integer getRxPower() {
      return _rxPower ;
   }
   
   /**
    * Sets the power of other station in Watts.  null if not used.
    * 
    * @param power the power of other station in Watts.
    */
   public void setRxPower(Integer power) {
      _rxPower = power ;
   }

   /**
    * Returns a string representation of this object in ADIF format.
    * 
    * @return a string representation of this object in ADIF format.
    */
   public String toAdifString() {
      StringBuffer buffer = new StringBuffer() ;

      if(!_unknownMode.equals("")) {
         buffer.append("<" + AdifConstants.MODE + ":" + _unknownMode.length() + ">" + _unknownMode) ;         
      } else {
         if (!_mode.equals(Mode.Blank)) {
            String mode = Mode.getStrToModeType().getStringValue(_mode) ;
            buffer.append("<" + AdifConstants.MODE + ":" + mode.length() + ">" + mode) ;
         }
      }

      if (!_band.equals(Band.Blank)) {
         String band = Band.getStrToBandType().getStringValue(_band) ;
         buffer.append("<" + AdifConstants.BAND + ":" + band.length() + ">" + band) ;
      }
      
      if(!_frequency.equals("")) {
         buffer.append("<" + AdifConstants.FREQ + ":" + _frequency.length() + ">" + _frequency) ;         
      }

      if(_txPower != null) {
         String txPower = _txPower.toString() ;
         buffer.append("<" + AdifConstants.TX_PWR + ":" + txPower.length() + ">" + txPower) ;         
      }

      if(_rxPower != null) {
         String rxPower = _rxPower.toString() ;
         buffer.append("<" + AdifConstants.RX_PWR + ":" + rxPower.length() + ">" + rxPower) ;         
      }
      
      return buffer.toString() ;
   }

   //**********
   // Methods overridden from Object
   //**********
   
   /**
    * Returns a string representation of the object.
    * 
    * @return string representation of the object.
    */
   public String toString() {
      StringBuffer buffer = new StringBuffer() ;
      
      buffer.append(getClass() + ":\n") ;
      buffer.append(" Mode: " + _mode + "\n") ;
      buffer.append(" Unknown Mode: " + _unknownMode + "\n") ;
      buffer.append(" Band: " + _band + "\n") ;
      buffer.append(" Frequency: " + _frequency + "\n") ;
      buffer.append(" TX Power: " + _txPower + "\n") ;
      buffer.append(" RX Power: " + _rxPower + "\n") ;
      
      return buffer.toString() ;
   }

   public void setBand(String bandString) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setBand'");
   }

}
