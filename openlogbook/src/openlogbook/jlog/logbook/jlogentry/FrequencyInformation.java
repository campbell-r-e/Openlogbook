package openlogbook.jlog.logbook.jlogentry;

import openlogbook.jlog.logbook.Adif;
import openlogbook.jlog.util.Band;
import openlogbook.jlog.util.Mode;

/**
 * An interface that defines the frequency related information API.  This interface defines :
 * 
 * MODE  
 * BAND  
 * FREQ  
 * TX_PWR
 * RX_PWR
 * 
 * @author KC0ZPS
 */
public interface FrequencyInformation extends Adif {

   /**
    * Gets the mode.  Mode.Blank if not used.
    * 
    * @return the mode.
    */
   public Mode getMode() ;
   
   /**
    * Sets the mode.  Mode.Blank if not used.
    * 
    * @param mode the mode.
    */
   public void setMode(Mode mode) ;
   
   /**
    * Gets the unknown mode.  This is used if the mode read from the file doesn't match the enumeration.
    * 
    * @return the unknown mode.
    */
   public String getUnknownMode() ;
   
   /**
    * Sets the unknown mode.  This is used if the mode read from the file doesn't match the enumeration.
    * 
    * @param unknownMode the unknown mode.
    */
   public void setUnknownMode(String unknownMode) ;
   
   /**
    * Gets the Band.Blank.  null if not used.
    * 
    * @return the band.
    */
   public Band getBand() ;
   
   /**
    * Sets the band.  Band.Blank if not used.
    * 
    * @param _band the band
    */
   public void setBand(Band _band) ;
   
   /**
    * Gets the frequency.  A "" indicates that this field is not in use.
    * 
    * @return the frequency.
    */
   public String getFrequency() ;
   
   /**
    * Sets the frequency.  A "" indicates that this field is not in use.
    * 
    * @param frequency the frequency.
    */
   public void setFrequency(String frequency) ;
   
   /**
    * Gets the transmitting power.  null if not used.
    * 
    * @return the transmitting power.
    */
   public Integer getTxPower() ;
   
   /**
    * Sets the transmitting power.  null if not used.
    * 
    * @param power the transmitting power.
    */
   public void setTxPower(Integer power) ;

   /**
    * Gets the power of other station in Watts.  null if not used.
    * 
    * @return the power of other station in Watts.
    */
   public Integer getRxPower() ;
   
   /**
    * Sets the power of other station in Watts.  null if not used.
    * 
    * @param power the power of other station in Watts.
    */
   public void setRxPower(Integer power) ;

}
