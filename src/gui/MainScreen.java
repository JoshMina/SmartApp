package gui;

import controller.Controller;
import gui.dialogs.LogoutDialog;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainScreen{
private Scene scene = null;
	/*Loads the image from the Resource folder and turns it to imageView to be used as an icon
	with the button*/

	 public MainScreen(Controller controller) {

		BorderPane root = new BorderPane();

		//Button Creation
		Button button = new Button ("Event Manager", null);
		button.setMaxSize(150, 100);
		button.setWrapText(true);
		//Mouse event handling for Transport Cost
		button.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("Event Manager button clicked");
			            controller.handleEvent(Controller.EVENTGUI);
			        }
			});

		Button button2 = new Button ("Decision Support", null);
		button2.setMaxSize(150, 100);
		button2.setWrapText(true);
		//Mouse event handling for Mail Delivery
		button2.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("Business Monitor button clicked");
			            controller.handleEvent(Controller.DECISIONSUPPORT);
			        }
			});

		Button button3 = new Button ("Account Management", null);
		button3.setMaxSize(150, 100);
		button3.setWrapText(true);
		//Mouse event handling for Customer Price Update
		button3.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("Account Management button clicked");
			            controller.handleEvent(Controller.ACCOUNTMANAGE);
			        }
			});
		
		
		Button button5 = new Button ("Logout", null);
		button5.setMaxSize(150, 100);
		button5.setWrapText(true);
		//Mouse event handling for Transport Discontinued
		button5.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			        	System.out.println("Logout button clicked");
			            LogoutDialog logoutDialog = new LogoutDialog(controller);
			            logoutDialog.display();
			        }
			});

		scene = new Scene(root, 600, 400);
		VBox box = new VBox();

		//Add all buttons to a container. Place the container on the center of the BorderPane
		box.getChildren().addAll(button, button2, button3);
		box.setAlignment(Pos.CENTER);

		//This adds spaces when items are being added (top,left, bottom, right)
		box.setMargin(button, new Insets(20,20,20,20));
		box.setMargin(button2, new Insets(20,20,20,20));
		box.setMargin(button3, new Insets(20,20,20,20));
		
		VBox box2 = new VBox();
		box2.getChildren().add(button5);
		box2.setAlignment(Pos.BOTTOM_RIGHT);
		box2.setMargin(button5, new Insets(20,20,20,20));

		root.setCenter(box);
		root.setBottom(box2);

	}

	public Scene scene() {
			return scene;
	}


}
