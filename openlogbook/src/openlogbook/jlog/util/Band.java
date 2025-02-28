package openlogbook.jlog.util;

/**
 * An enumeration of Band types.
 * 
 * @author KC0ZPS
 */
public class Band {

   /**
    * Creates a new Band.  The constructor is private.  The only instances are the constants
    * instantiated in this module.
    * 
    * @param description   Description of this meeting type.
    */
   private Band(String description) {
       _description = description ;
   }

   //**********
   // Public methods
   //**********

   /**
    * Returns the IntToObject object.
    * 
    * @return the IntToObject object.
    */
   public static IntToObject getIntToBandType() {
       return _intToBandType ;
   }

   /**
    * Returns the StringTObject object.
    * 
    * @return the StringTObject object.
    */
   public static StringToObject getStrToBandType() {
      return _strToBandType ;
   }
   
   /**
    * Returns all objects represented by this class as an array.
    * 
    * @return all objects represented by this class as an array.
    */
   public static Band[] getValues() {
      return _values ;
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
       return _description ;
   }

   //**********
   // Class attributes and constants
   //**********
   
   private String                    _description ;

   public static final Band Blank    = new Band("") ;
   public static final Band Unknown  = new Band("Unknown") ;
   public static final Band _2190m   = new Band("2190m") ;
   public static final Band _160m    = new Band("160m") ;
   public static final Band _80m     = new Band("80m") ;
   public static final Band _60m     = new Band("60m") ;
   public static final Band _40m     = new Band("40m") ;
   public static final Band _30m     = new Band("30m") ;
   public static final Band _20m     = new Band("20m") ;
   public static final Band _17m     = new Band("17m") ;
   public static final Band _15m     = new Band("15m") ;
   public static final Band _12m     = new Band("12m") ;
   public static final Band _10m     = new Band("10m") ;
   public static final Band _6m      = new Band("6m") ;
   public static final Band _4m      = new Band("4m") ;
   public static final Band _2m      = new Band("2m") ;
   public static final Band _1_25m   = new Band("1.25m") ;
   public static final Band _70cm    = new Band("70cm") ;
   public static final Band _35cm    = new Band("35cm") ;
   public static final Band _23cm    = new Band("23cm") ;
   public static final Band _13cm    = new Band("13cm") ;
   public static final Band _9cm     = new Band("9cm") ;
   public static final Band _6cm     = new Band("6cm") ;
   public static final Band _3cm     = new Band("3cm") ;
   public static final Band _1_25cm  = new Band("1.25cm") ;
   public static final Band _6mm     = new Band("6mm") ;
   public static final Band _4mm     = new Band("4mm") ;
   public static final Band _2_5mm   = new Band("2.5mm") ;
   public static final Band _2mm     = new Band("2mm") ;
   public static final Band _1mm     = new Band("1mm") ;
   
   /**
    * These values MUST match _intlookupData.
    */
   private static final Band _values[] = {
      Blank,
      // _2190m,
      _160m,
      _80m,
      // _60m,
      _40m,
      _30m,
      _20m,
      _17m,
      _15m,
      _12m,
      _10m,
      _6m,
      // _4m,
      _2m,
      _1_25m,
      _70cm,
      _35cm,
      _23cm,
      _13cm,
      _9cm,
      _6cm,
      _3cm,
      _1_25cm,
      _6mm,
      _4mm,
      _2_5mm,
      _2mm,
      _1mm
   } ;
   
   /**
    * A mapping of the objects to the constants as Integers.  These valus MUST mimic _values.  ADIF 1.0 only uses a subset
    * of the total enumerations I've defined.  The ones that aren't use are commented out.
    */
   private static final Object[][] _intlookupData = {
      {new Integer(0), Band.Blank},
      // {new Integer(), Band._2190m},
      {new Integer(1), Band._160m},
      {new Integer(2), Band._80m},
      // {new Integer(), Band._60m},
      {new Integer(3), Band._40m},
      {new Integer(4), Band._30m},
      {new Integer(5), Band._20m},
      {new Integer(6), Band._17m},
      {new Integer(7), Band._15m},
      {new Integer(8), Band._12m},
      {new Integer(9), Band._10m},
      {new Integer(10), Band._6m},
      // {new Integer(), Band._4m},
      {new Integer(11), Band._2m},
      {new Integer(12), Band._1_25m},
      {new Integer(13), Band._70cm},
      {new Integer(14), Band._35cm},
      {new Integer(15), Band._23cm},
      {new Integer(16), Band._13cm},
      {new Integer(17), Band._9cm},
      {new Integer(18), Band._6cm},
      {new Integer(19), Band._3cm},
      {new Integer(20), Band._1_25cm},
      {new Integer(21), Band._6mm},
      {new Integer(22), Band._4mm},
      {new Integer(23), Band._2_5mm},
      {new Integer(24), Band._2mm},
      {new Integer(25), Band._1mm}
   } ;

   /**
    * A mapping of the objects to the constants as Strings.
    */
   private static final Object[][] _strlookupData = {
      {new String(""), Band.Blank},
      {new String("Unknown"), Band.Unknown},
      // {new String("2190m"), Band._2190m},
      {new String("160m"), Band._160m},      
      {new String("80m"), Band._80m},
      // {new String("60m"), Band._60m},
      {new String("40m"), Band._40m},
      {new String("30m"), Band._30m},
      {new String("20m"), Band._20m},
      {new String("17m"), Band._17m},
      {new String("15m"), Band._15m},
      {new String("12m"), Band._12m},
      {new String("10m"), Band._10m},
      {new String("6m"), Band._6m},
      // {new String("4m"), Band._4m},
      {new String("2m"), Band._2m},
      {new String("1.25m"), Band._1_25m},
      {new String("70cm"), Band._70cm},
      {new String("35cm"), Band._35cm},
      {new String("23cm"), Band._23cm},
      {new String("13cm"), Band._13cm},
      {new String("9cm"), Band._9cm},
      {new String("6cm"), Band._6cm},
      {new String("3cm"), Band._3cm},
      {new String("1.25cm"), Band._1_25cm},
      {new String("6mm"), Band._6mm},
      {new String("4mm"), Band._4mm},
      {new String("2.5mm"), Band._2_5mm},
      {new String("2mm"), Band._2mm},
      {new String("1mm"), Band._1mm}
   } ; 
   
   private static final IntToObject    _intToBandType = new IntToObject(_intlookupData, Band.Unknown) ;
   private static final StringToObject _strToBandType = new StringToObject(_strlookupData, Band.Unknown) ;
   
}
