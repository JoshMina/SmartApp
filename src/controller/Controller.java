package controller;

import event.Event;
import gui.*;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
import javafx.stage.Stage;
import model.BusinessModel;
import model.Database;
import model.EventProcessor;
import model.Route;
import model.User;

import java.util.List;

public class Controller {
	public static final String MAIL = "MAIL";
	public static final String LOGIN = "LOGIN";
	public static final String EVENTGUI = "EVENTGUI";
	public static final String TRANSPORTDISC = "TRANSPORTDISC";
	public static final String TRANSCOSTUPDATE = "TRANSCOSTUPDATE";
	public static final String CUSTPRICEUPDATE = "CUSTPRICEUPDATE";
	public static final String ACCOUNTMANAGE = "ACCOUNTMANAGE";
	public static final String MAINSCREEN = "MAINSCREEN";
	public static final String BUSINESS = "BUSINESS";
	public static final String DECISIONSUPPORT = "DECISION";

	Stage primaryStage;
	DataEntryGUI currentView;


	// Global System Components
	private BusinessModel model;
	private UserDatabase userDatabase;
	private EventProcessor eventProcessor;
	private User loggedIn;

	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.userDatabase = new UserDatabase();
		this.eventProcessor = new EventProcessor();
		model = new BusinessModel(this);


	}

	public void handleEvent(Event entry, DataEntryGUI sourceView) {
		this.currentView = sourceView;

		// first check for accuracy
		if (validateEvent(entry)) {
			if(entry instanceof event.TransportCostUpdate){
				boolean b = model.processEvent(entry);
				Route r = model.getEventManager().getAverageRoute();
				if(r != null){
					sourceView.displayRoute(r);
				}
				sourceView.showMessage("Your event has been successfully processed.");
			}
			if(entry instanceof event.MailDelivery){
				//get route.
				Route r = model.getEventManager().getAverageRoute();
				if(r == null){
					AlertDialog.display("Route not found", "Route for this delivery is not available");
				}
				else{

					boolean b = this.model.processEvent(entry);
					sourceView.displayRoute(r);
					sourceView.showMessage("Message: Your event has been successfully processes.");
				}
			}
			if(entry instanceof event.TransportDiscontinued){
				boolean b = model.processEvent(entry);
				Route r = model.getEventManager().getAverageRoute();
				if(r != null){
					sourceView.displayRoute(r);
					sourceView.showMessage("Message: Your event has been successfully processes.");
				}
				else{
					System.out.println("Route not found");
				}
			}
			if(entry instanceof event.CustomerPriceUpdate){
				boolean b = model.processEvent(entry);
				Route r = model.getEventManager().getAverageRoute();
				if(r != null){
					sourceView.displayRoute(r);
					sourceView.showMessage("Message: Your event has been successfully processes.");
				}
				else{
					System.out.println("Route not found");
				}
			}

			// Then send it to model

			//notify the user all okay.
		}
		// if event is not validated, we must notify the user.
	}

	public void handleEvent(String nextScreen) {

		if (nextScreen.equals(MAIL)) {
			MailDelivery mailDeliveryGUI = new MailDelivery(this);
			primaryStage.setScene(mailDeliveryGUI.scene());
		}
		if (nextScreen.equals(EVENTGUI)) {
			EventGUI eventGUI = new EventGUI(this);
			primaryStage.setScene(eventGUI.scene());
		}
		if (nextScreen.equals(LOGIN)) {
			primaryStage.setScene(Login.scene());
		}
		if (nextScreen.equals(TRANSPORTDISC)) {
			TransportDiscontinued transdisc = new TransportDiscontinued(this);
			primaryStage.setScene(transdisc.scene());
		}
		if (nextScreen.equals(TRANSCOSTUPDATE)) {
			TransportCostUpdate trans = new TransportCostUpdate(this);
			primaryStage.setScene(trans.scene());
		}
		if (nextScreen.equals(CUSTPRICEUPDATE)) {
			CustomerPriceUpdate cust = new CustomerPriceUpdate(this);
			primaryStage.setScene(cust.scene());
		}if(nextScreen.equals(MAINSCREEN)){
			MainScreen mainScreen = new MainScreen(this);
			primaryStage.setScene(mainScreen.scene());
		}if(nextScreen.equals(ACCOUNTMANAGE)){
			UserAccountManagement  userManage = new UserAccountManagement(this);
			primaryStage.setScene(userManage.scene());
		}if(nextScreen.equals(DECISIONSUPPORT)){
			DecisionSupport ds = new DecisionSupport(this);
			primaryStage.setScene(ds.scene());
		}

	}



	private boolean validateEvent(Event entry) {
		// if entry not valid call the source GUI e.g.
		// currentView.setError(errormsg)
		return true;
	}

	public UserDatabase getUserDatabase() {
		return userDatabase;
	}

	public EventProcessor getEventProcessor() {
		return eventProcessor;
	}

	/**
	 * @return the {@link User} logged in.
	 */
	public User getLoggedInUser() {
		return loggedIn;
	}

	/**
	 * Set the current logged in user. To retrieve a user to set for this method, use the
	 * {@link UserDatabase#getUser(String)} or implicitly through {@link UserDatabase#getUsers()}
	 *
	 * @param loggedIn set the user that is logged in to the application
	 */
	public void setLoggedInUser(User loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean login(String inputUsername, String inputPassword) {
		List<User> users = userDatabase.getUsers();
		for(User user : users) {
			if(user.getUsername().equals(inputUsername)
					&& user.getPassword().equals(inputPassword)) {
				setLoggedInUser(user);
				handleEvent(Controller.MAINSCREEN);
				return true;
			}
		}
		return false;
	}

	public boolean logout(){
		setLoggedInUser(null);
		handleEvent(Controller.LOGIN);
		return true;
	}

	public Route getRoute(Event event){
		//pass event to model.
		return model.getRoute(event);


	}

	public BusinessModel getModel(){
		return this.model;
	}

	public Database getDatabase(){
		return model.getDatabase();
	}

	public Route getAverageRoute(){
		return this.model.getEventManager().getAverageRoute();
	}
}