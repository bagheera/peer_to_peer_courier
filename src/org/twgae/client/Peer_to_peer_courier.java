package org.twgae.client;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Peer_to_peer_courier implements EntryPoint {
  private final class TravelNoteCreator implements ClickHandler, AsyncCallback<TravelNote> {
    public void onClick(ClickEvent arg0) {
      messageBox.clear();
      try {
        final Origin travelFrom = new Origin(travelFromText.getText());
        final Destination travelTo = new Destination(travelToText.getText());
        final Date departureDate = departureDatePicker.getValue();
        final Date arrivalDate = arrivalDatePicker.getValue();
        final TravelNote travelNote = new TravelNote(currentUser, travelFrom, departureDate, travelTo, arrivalDate);
        Log.debug("Creating travel note with " + departureDatePicker.getValue() + " and "
            + arrivalDatePicker.getValue());
        carryService.createTravelNote(travelNote, this);
      } catch (ValidationException ex) {
        messageBox.flash(ex.getMessage());
      }
    }

    public void onFailure(Throwable caught) {
      Log.debug("Travel Note creation failed because " + caught.getMessage());
    }

    public void onSuccess(TravelNote arg0) {
      new RefreshTravelNoteListing().execute();
    }
  }

  final class RefreshTravelNoteListing implements AsyncCallback<TravelNote[]> {
    public void onFailure(Throwable caught) {
      Log.debug("Travel note listing refresh failed because " + caught.getMessage());
    }

    public void execute() {
      carryService.listTravelNotes(this);
    }

    public void onSuccess(TravelNote[] travelNotes) {
      travelsListing.render(travelNotes);
    }
  }

  private final class PacketCreator implements ClickHandler, AsyncCallback<Void> {
    public void onClick(ClickEvent arg0) {
      messageBox.clear();
      final Origin from = new Origin(originText.getText());
      final Destination to = new Destination(destText.getText());
      final Packet p = new Packet(currentUser, packetDescText.getText(), packetDimText.getText(), packetWeightText.getText());
      carryService.create(new CarryRequest(from, to, p), this);
    }

    public void onFailure(Throwable caught) {
      Log.debug("Packet creation failed because " + caught.getMessage());
    }

    public void onSuccess(Void request) {
      new RefreshPacketListing().execute();
    }
  }

  final class RefreshPacketListing implements AsyncCallback<CarryRequest[]> {
    public void onFailure(Throwable caught) {
      Log.debug("Packet listing refresh failed because " + caught.getMessage());
    }

    public void execute() {
      carryService.listRequests(this);
    }

    public void onSuccess(CarryRequest[] requests) {
      packetsListing.render(requests);
    }
  }

  public Listing<CarryRequest> packetsListing = new Listing<CarryRequest>("Listing Carry Requests");
  public TextBox packetDescText = new TextBox();
  public TextBox packetDimText = new TextBox();
  public TextBox packetWeightText = new TextBox();
  public TextBox originText = new TextBox();
  public TextBox destText = new TextBox();
  public Button addPacketButton = new Button("add packet");
  public Listing<TravelNote> travelsListing = new Listing<TravelNote>("Listing Travel Notes");
  public TextBox travelFromText = new TextBox();
  public DatePicker departureDatePicker = new DatePicker();
  public DatePicker arrivalDatePicker = new DatePicker();
  public TextBox travelToText = new TextBox();
  public Button addtravelButton = new Button("add travel");
  public Button logoutButton = new Button("Logout");
  private Uzer currentUser;
  public MessageBox messageBox = new MessageBox();
  final CarryServiceAsync carryService = (CarryServiceAsync) GWT.create(CarryService.class);

  public Peer_to_peer_courier() {
    final ServiceDefTarget endpoint = (ServiceDefTarget) carryService;
    endpoint.setServiceEntryPoint("/peer_to_peer_courier");
  }

  public void onModuleLoad() {
    Log.setUncaughtExceptionHandler();
    Log.debug("module base url is " + GWT.getModuleBaseURL());
    Log.debug("host page base url is " + GWT.getHostPageBaseURL());
    carryService.getCurrentUser(new AsyncCallback<Uzer>() {
      public void onFailure(Throwable caught) {
        Log.debug("Problem getting current user " + caught.getMessage());
        carryService.createLoginUrl(GWT.getHostPageBaseURL() + "Peer_to_peer_courier.html", new DoLogin());
      }

      public void onSuccess(Uzer currentUser) {
        Log.debug("got current user " + currentUser);
        Peer_to_peer_courier.this.currentUser = currentUser;
      }
    });
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        final VerticalPanel mainPanel = new VerticalPanel();
        final VerticalPanel packetPanel = new VerticalPanel();
        packetPanel.setStyleName("packetPanel");
        addPacketButton.addClickHandler(new PacketCreator());
        packetPanel.add(new InlineLabel("Someone please carry"));
        packetPanel.add(packetDescText);
        packetDescText.setText("a book");
        packetPanel.add(new InlineLabel("having dimensions"));
        packetPanel.add(packetDimText);
        packetDimText.setText("6\" x 6\" x 2\"");
        packetPanel.add(new InlineLabel("weighing"));
        packetPanel.add(packetWeightText);
        packetWeightText.setText("500g");
        packetPanel.add(new InlineLabel("from"));
        packetPanel.add(originText);
        originText.setText("chicago");
        packetPanel.add(new InlineLabel("to"));
        packetPanel.add(destText);
        destText.setText("bangalore");
        packetPanel.add(addPacketButton);
        final VerticalPanel travelPanel = new VerticalPanel();
        travelPanel.setStyleName("travelPanel");
        addtravelButton.addClickHandler(new TravelNoteCreator());
        travelPanel.add(new InlineLabel("I am going from "));
        travelPanel.add(travelFromText);
        travelFromText.setText("chicago");
        travelPanel.add(new InlineLabel("departing on "));
        travelPanel.add(departureDatePicker);
        travelPanel.add(new InlineLabel("to"));
        travelPanel.add(travelToText);
        travelToText.setText("bangalore");
        travelPanel.add(new InlineLabel("arriving on "));
        travelPanel.add(arrivalDatePicker);
        travelPanel.add(addtravelButton);
        mainPanel.add(messageBox.getPanel());
        final HorizontalPanel row1 = new HorizontalPanel();
        row1.add(packetPanel);
        row1.add(travelPanel);
        mainPanel.add(row1);
        final HorizontalPanel row2 = new HorizontalPanel();
        row2.add(packetsListing.widget());
        row2.add(travelsListing.widget());
        mainPanel.add(row2);
        //            Hyperlink logout = new Hyperlink("logout", "bye"); figure this out
        mainPanel.add(logoutButton);
        logoutButton.addClickHandler(new DoLogout());
        RootPanel.get().add(mainPanel);
        new RefreshTravelNoteListing().execute();
      }
    });
    //need to put this in a separate deferred command to avoid concurrent query problem - still not useful?
    //    DeferredCommand.addCommand(new Command() {
    //      public void execute() {
    //        new RefreshPacketListing().execute();
    //      }
    //    });
  }

  class DoLogin implements AsyncCallback<String> {
    public void onFailure(Throwable caught) {
      Log.debug("Problem getting login url " + caught.getMessage());
    }

    public void onSuccess(String loginUrl) {
      Log.debug("redirecting to " + loginUrl);
      Window.open(loginUrl, "_self", null);
    }
  }

  class DoLogout implements ClickHandler {
    public void onClick(ClickEvent arg0) {
      carryService.createLogoutUrl("http://sriramnarayan.com", new AsyncCallback<String>() {
        public void onFailure(Throwable caught) {
          Log.debug("Problem getting logout url " + caught.getMessage());
        }

        public void onSuccess(String logoutUrl) {
          Window.open(logoutUrl, "_self", null);
        }
      });
    }
  }

  class MessageBox {
    private Label message = new Label();

    Panel getPanel() {
      HorizontalPanel row0 = new HorizontalPanel();
      row0.setStyleName("messageBox");
      row0.add(message);
      return row0;
    }

    public void clear() {
      message.setText("");
    }

    public void flash(String messageText) {
      message.setText(messageText);
    }
  }
}
