package org.twgae.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class Listing<T> {
  FlexTable table = new FlexTable();

  public Listing(String header) {
    table.setText(0, 0, header);
  }

  public Widget widget() {
    return table;
  }

  public void render(T[] list) {
    for (int i = 0; i < list.length; i++) {
      try {
        table.setText(i + 1, 0, list[i].toString());
      } catch (Throwable ex) {
        Log.debug("Problem rendering list " + i + " msg: " + ex.getMessage());
      }
    }
  }
}
