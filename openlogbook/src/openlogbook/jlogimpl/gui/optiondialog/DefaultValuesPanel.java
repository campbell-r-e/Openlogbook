package openlogbook.jlogimpl.gui.optiondialog;

import openlogbook.jlog.logbook.jlogentry.LogEntry;
import openlogbook.jlog.logbook.jlogentry.Rst;
import openlogbook.jlog.util.Band;
import openlogbook.jlog.util.Mode;
import openlogbook.jlogimpl.logbook.logentry.LogEntryImpl;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * This panel will handle all user input for default values.
 * 
 * @author KC0ZPS
 */
public class DefaultValuesPanel extends JPanel {

   private JLabel     _bandLabel = new JLabel("Band: ") ;
   private JLabel     _modeLabel = new JLabel("Mode: ") ;
   private JLabel     _txPowerLabel = new JLabel("TX Power: ") ;
   private JLabel     _rxPowerLabel = new JLabel("RX Power: ") ;
   private JLabel     _receivedLabel = new JLabel("RST Received: ") ;
   private JLabel     _sentLabel = new JLabel("RST Sent: ") ;
   private JLabel     _operatorLabel = new JLabel("Operator: ") ;
   private JTextField _txPowerTextField = new JTextField() ;
   private JTextField _rxPowerTextField = new JTextField() ;
   private JComboBox  _bandComboBox = new JComboBox(Band.getValues()) ;
   private JComboBox  _modeComboBox = new JComboBox(Mode.getValues()) ;
   private JTextField _receivedTextField = new JTextField() ;
   private JTextField _sentTextField = new JTextField() ;
   private JTextField _operatorTextField = new JTextField() ;

   private Band    _band ;
   private Mode    _mode ;
   private Integer _txPower ;
   private Integer _rxPower ;
   private Rst     _rst ;
   private String  _operator ;
   
   private static final Dimension OPERATOR_TEXT_FIELD_DIMENSION = new Dimension(100, 20) ;
   private static final Dimension POWER_TEXT_FIELD_DIMENSION = new Dimension(50, 20) ;
   private static final Dimension RST_FIELD_DIMENSION = new Dimension(40, 20) ;
   
   static final long serialVersionUID = 131951320278513675L;

   /**
    * Creates a new DefaultValuesPanel.
    */
   public DefaultValuesPanel() {
      super() ;
      
      initialize() ;
      addListeners() ;
   }

   //**********
   // Public methods
   //**********

   /**
    * Creates a LogEntry with the values defined in this panel.
    * 
    * @return a LogEntry with the values defined in this panel.
    */
   public LogEntry getDefaultLogEntry() {
      LogEntry logEntry = new LogEntryImpl() ;
      logEntry.setRst(_rst) ;
      logEntry.getFrequencyInformation().setBand(_band) ;
      logEntry.getFrequencyInformation().setMode(_mode) ;
      logEntry.getFrequencyInformation().setTxPower(_txPower) ;
      logEntry.getFrequencyInformation().setRxPower(_rxPower) ;
      logEntry.getCallsign().setOperatingStation(_operator) ;
      
      return logEntry ;
   }
   
   /**
    * Sets the default log entry.
    * 
    * @param entry the logentry to set as the default.
    */
   public void setDefaultLogEntry(LogEntry entry) {
      _band = entry.getFrequencyInformation().getBand() ;
      _mode = entry.getFrequencyInformation().getMode() ;
      _txPower = entry.getFrequencyInformation().getTxPower() ;
      _rxPower = entry.getFrequencyInformation().getRxPower() ;
      _rst = entry.getRst() ;
      _operator = entry.getCallsign().getOperatingStation() ;
      restoreDefaultValues() ;
   }
   
   /**
    * Restores the default values of this panel.
    */
   public void restoreDefaultValues() {
      _bandComboBox.setSelectedItem(_band) ;
      _modeComboBox.setSelectedItem(_mode) ;
      _receivedTextField.setText(_rst.getRstReceived()) ;
      _sentTextField.setText(_rst.getRstSent()) ;
      _operatorTextField.setText(_operator) ;
      if (_txPower == null) {
         _txPowerTextField.setText("") ;
      } else {
         _txPowerTextField.setText("" + _txPower) ;         
      }
      if (_rxPower == null) {
         _rxPowerTextField.setText("") ;
      } else {
         _rxPowerTextField.setText("" + _rxPower) ;         
      }

   }
   
   /**
    * Sets the new default values to the values contained in the components.
    * 
    * @throws IllegalArgumentException If one of the fields containts invalid data.
    */
   public void setDefaultValues() throws IllegalArgumentException {
      verifyData() ;
      _band = (Band)_bandComboBox.getSelectedItem() ;
      _mode = (Mode)_modeComboBox.getSelectedItem() ;
      _rst.setRstReceived(_receivedTextField.getText()) ;
      _rst.setRstSent(_sentTextField.getText()) ;
      _operator = _operatorTextField.getText() ;
      if (_txPowerTextField.getText().equals("")) {
         _txPower = null ;
      } else {
         _txPower = new Integer(_txPowerTextField.getText()).intValue() ;         
      }
      if (_rxPowerTextField.getText().equals("")) {
         _rxPower = null ;
      } else {
         _rxPower = new Integer(_rxPowerTextField.getText()).intValue() ;         
      }
   }
   
   /**
    * Cleans the panel up.  Should be called when this object is no longer needed.
    */
   public void cleanup() {
      removeListeners() ;
   }
   
   //**********
   // Private methods
   //**********

   /**
    * Validates the data in this panel.
    * 
    * @throws IllegalArgumentException If one of the fields containts invalid data.
    */
   private void verifyData() throws IllegalArgumentException {
      try {
         if (!_txPowerTextField.getText().equals("")) {
            new Integer(_txPowerTextField.getText()) ;
         }

         if (!_rxPowerTextField.getText().equals("")) {
            new Integer(_rxPowerTextField.getText()) ;
         }

      } catch (NumberFormatException e) {
         _txPowerTextField.selectAll() ;
         _txPowerTextField.requestFocus() ;
         throw new IllegalArgumentException("Invalid power field.  Expecting numbers only.") ;
      }
      return ;
   }
   
   /**
    * Initializes the components of this panel.
    */
   private void initialize() {
      _operatorTextField.setPreferredSize(OPERATOR_TEXT_FIELD_DIMENSION);
      _operatorTextField.setMaximumSize(OPERATOR_TEXT_FIELD_DIMENSION);
      _operatorTextField.setMinimumSize(OPERATOR_TEXT_FIELD_DIMENSION);

      _txPowerTextField.setPreferredSize(POWER_TEXT_FIELD_DIMENSION);
      _txPowerTextField.setMaximumSize(POWER_TEXT_FIELD_DIMENSION);
      _txPowerTextField.setMinimumSize(POWER_TEXT_FIELD_DIMENSION);

      _rxPowerTextField.setPreferredSize(POWER_TEXT_FIELD_DIMENSION);
      _rxPowerTextField.setMaximumSize(POWER_TEXT_FIELD_DIMENSION);
      _rxPowerTextField.setMinimumSize(POWER_TEXT_FIELD_DIMENSION);

      _receivedTextField.setPreferredSize(RST_FIELD_DIMENSION);
      _receivedTextField.setMaximumSize(RST_FIELD_DIMENSION);
      _receivedTextField.setMinimumSize(RST_FIELD_DIMENSION);

      _sentTextField.setPreferredSize(RST_FIELD_DIMENSION);
      _sentTextField.setMaximumSize(RST_FIELD_DIMENSION);
      _sentTextField.setMinimumSize(RST_FIELD_DIMENSION);

      setLayout(new GridBagLayout()) ;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();      
      gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
      gridBagConstraints1.anchor = GridBagConstraints.EAST;

      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.gridy = 0;
      add(_operatorLabel, gridBagConstraints1);

      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.gridy = 1;
      add(_bandLabel, gridBagConstraints1);

      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.gridy = 2;
      add(_modeLabel, gridBagConstraints1);

      gridBagConstraints1.gridx = 2;
      gridBagConstraints1.gridy = 0;
      add(_txPowerLabel, gridBagConstraints1);

      gridBagConstraints1.gridx = 2;
      gridBagConstraints1.gridy = 1;
      add(_rxPowerLabel, gridBagConstraints1);

      gridBagConstraints1.gridx = 2;
      gridBagConstraints1.gridy = 2;
      add(_receivedLabel, gridBagConstraints1);

      gridBagConstraints1.gridx = 2;
      gridBagConstraints1.gridy = 3;
      add(_sentLabel, gridBagConstraints1);

      gridBagConstraints1.gridx = 1;
      gridBagConstraints1.gridy = 0;
      gridBagConstraints1.anchor = GridBagConstraints.WEST;
      add(_operatorTextField, gridBagConstraints1);

      gridBagConstraints1.gridx = 1;
      gridBagConstraints1.gridy = 1;
      add(_bandComboBox, gridBagConstraints1);

      gridBagConstraints1.gridx = 1;
      gridBagConstraints1.gridy = 2;
      add(_modeComboBox, gridBagConstraints1);

      gridBagConstraints1.gridx = 3;
      gridBagConstraints1.gridy = 0;
      add(_txPowerTextField, gridBagConstraints1);

      gridBagConstraints1.gridx = 3;
      gridBagConstraints1.gridy = 1;
      add(_rxPowerTextField, gridBagConstraints1);

      gridBagConstraints1.gridx = 3;
      gridBagConstraints1.gridy = 2;
      add(_receivedTextField, gridBagConstraints1);

      gridBagConstraints1.gridx = 3;
      gridBagConstraints1.gridy = 3;
      add(_sentTextField, gridBagConstraints1);

   }

   /**
    * Initializes all listeners in this panel.
    */
   private void addListeners() {
   }
   
   /**
    * Removes all listeners in this panel.
    */
   private void removeListeners() {
   }

}
