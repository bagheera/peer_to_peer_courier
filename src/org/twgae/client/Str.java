package org.twgae.client;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.DateTimeFormat;

public class Str {
  private static final String EMPTY_STRING = "";
  public static String s(Object obj_to_be_stringed) {
    if (null == obj_to_be_stringed)
      return EMPTY_STRING;
    if (obj_to_be_stringed instanceof String)
      return (String) obj_to_be_stringed;
    String to_s = obj_to_be_stringed.toString();
    if (null != to_s)
      return to_s;
    return EMPTY_STRING;
  }

  public static String s(String label, Object obj_to_be_stringed) {
    if (null == obj_to_be_stringed)
      return EMPTY_STRING;
    if (null == label)
      label = EMPTY_STRING;
    if (obj_to_be_stringed instanceof String) {
      if (EMPTY_STRING.equals(((String) obj_to_be_stringed).trim()))
        return EMPTY_STRING;
      return label + (String) obj_to_be_stringed;
    }
    if (obj_to_be_stringed instanceof Date) {
      try {
        return label + DateTimeFormat.getFormat("EEE, MMM d, ''yy").format((Date) obj_to_be_stringed);
      } catch (Throwable ex) {
        Log.debug("Error formatting date for " + obj_to_be_stringed);
        return EMPTY_STRING;
      }
    }
    String to_s = obj_to_be_stringed.toString();
    if (null != to_s)
      return label + to_s;
    return EMPTY_STRING;
  }

  public static boolean isEmpty(String string) {
    return null == string || string.length() == 0;
  }

  
}
