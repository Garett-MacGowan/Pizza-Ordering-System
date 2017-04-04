/*
 * Garett MacGowan
 * 10197107
 */

package application;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class PizzaController {

    @FXML
    private RadioButton sizeMedium;

    @FXML
    private ToggleGroup Size;

    @FXML
    private RadioButton hamDouble;

    @FXML
    private RadioButton pepperoniDouble;

    @FXML
    private ToggleGroup Cheese;

    @FXML
    private RadioButton quantityTwo;

    @FXML
    private TextField total;

    @FXML
    private RadioButton quantityOne;

    @FXML
    private TextField singleCost;

    @FXML
    private Button addOrder;

    @FXML
    private TextField quantityField;

    @FXML
    private RadioButton cheeseRegular;

    @FXML
    private RadioButton hamNone;

    @FXML
    private TextArea orderItems;

    @FXML
    private RadioButton cheeseDouble;

    @FXML
    private RadioButton pepperoniTriple;

    @FXML
    private RadioButton hamTriple;

    @FXML
    private RadioButton sizeSmall;

    @FXML
    private ToggleGroup Quantity;

    @FXML
    private RadioButton quantityThree;

    @FXML
    private TextArea errorField;

    @FXML
    private RadioButton pepperoniNone;

    @FXML
    private RadioButton pepperoniRegular;

    @FXML
    private RadioButton cheeseTriple;

    @FXML
    private ToggleGroup Ham;

    @FXML
    private RadioButton hamRegular;

    @FXML
    private RadioButton sizeLarge;

    @FXML
    private ToggleGroup Pepperoni;

    @FXML
    private TextField totalCost;
    
    private static ArrayList<LineItem> orders = new ArrayList<>();
    
    private String size;
    private int cheese, ham, pepperoni;
    private int quantity;
    private float tCost;
    private int line;
    
    public PizzaController() {
    	size = "medium";
    	cheese = 1;
    	pepperoni = 1;
    	line = 1;
    	// update and display pizza object in displaySingle and displayTotal
    }

    @FXML
    void changeSize(ActionEvent event) {
    	if (sizeSmall.isSelected()) {
    		size = "small";
    	}
    	if (sizeMedium.isSelected()) {
    		size = "medium";
    	}
    	if (sizeLarge.isSelected()) {
    		size = "large";
    	}
    	// update and display pizza object in displaySingle and displayTotal
    }

    @FXML
    void changeCheese(ActionEvent event) {
    	if (cheeseRegular.isSelected()) {
    		cheese = 1;
    	}
    	if (cheeseDouble.isSelected()) {
    		cheese = 2;
    	}
    	if (cheeseTriple.isSelected()) {
    		cheese = 3;
    	}
    	// update and display pizza object in displaySingle and displayTotal
    }

    @FXML
    void changeHam(ActionEvent event) {
    	if (hamNone.isSelected()) {
    		ham = 0;
    	}
    	if (hamRegular.isSelected()) {
    		ham = 1;
    	}
    	if (hamDouble.isSelected()) {
    		ham = 2;
    	}
    	if (hamTriple.isSelected()) {
    		ham = 3;
    	}
    	// update and display pizza object in displaySingle and displayTotal
    }

    @FXML
    void changePepperoni(ActionEvent event) {
    	if (pepperoniNone.isSelected()) {
    		pepperoni = 0;
    	}
    	if (pepperoniRegular.isSelected()) {
    		pepperoni = 1;
    	}
    	if (pepperoniDouble.isSelected()) {
    		pepperoni = 2;
    	}
    	if (pepperoniTriple.isSelected()) {
    		pepperoni = 3;
    	}
    	// update and display pizza object in displaySingle and displayTotal
    }

    @FXML
    void changeQuantity(ActionEvent event) {
    	// If the text field isn't null and isn't empty.
    	if (quantityField.getText() != null && !quantityField.getText().isEmpty()) {
    		String tempQuantity = quantityField.getText();
    		// try / catch to see if input is number, if not, catch exception.
    		try {
    			// set the finalQuantity and check if it is in legal range.
	    		int finalQuantity = Integer.valueOf(tempQuantity);
	        	if (finalQuantity < 101 && finalQuantity > 0) {
	        		quantity = finalQuantity;
	        		// If the text field gets input, user must not want to use radio button -- unselect them.
	            	quantityOne.setSelected(false);
	                quantityTwo.setSelected(false);
	                quantityThree.setSelected(false);
	        	}
	        	// If number not in legal range, print error.
	        	else {
	        		displayErrors("Please enter a valid number");
	        	}
	    	} catch(NumberFormatException e) {
	    		displayErrors("Please enter a number.");
	    	}
    	}
    	// Setting quantity based off of radio button selection
    	else if (quantityOne.isSelected()) {
    		quantity = 1;
    		quantityField.clear();
    	}
    	else if (quantityTwo.isSelected()) {
    		quantity = 2;
    		quantityField.clear();
    	}
    	else if (quantityThree.isSelected()) {
    		quantity = 3;
    		quantityField.clear();
    	}
    	// Update the current pizza cost any time a quantity is changed.
    	try {
			Pizza pizza = new Pizza(size, cheese, ham, pepperoni);
			displaySingleCost(pizza);
	    	displayTotalCost(pizza);
		} catch (IllegalPizza e) {
			displayErrors(e.getMessage());
		}
    }
    
    @FXML
    void add(ActionEvent event) {
    	// Adds the pizza object to the array list and calls to display the order.
    	try {
    		Pizza pizza = new Pizza(size, cheese, ham, pepperoni);
    		orders.add(new LineItem(quantity, pizza));
    	} catch (IllegalPizza e) {
    		displayErrors(e.getMessage());
    	}
    	displayOrder();
    }
    
    void displaySingleCost(Pizza pizza) {
    	singleCost.setText("Single Pizza Cost: $" + String.format("%.2f", pizza.getCost()));
    }
    
    void displayTotalCost(Pizza pizza) {
    	totalCost.setText("Total Pizza Cost: $" + String.format("%.2f", pizza.getCost()*quantity));
    }
    
    void displayOrder() {
    	// Prints line item at whichever line is the current line, and ads it to the order display.
    	try {
	    	LineItem order;
	    	order = orders.get(line - 1);
	    	tCost += order.getCost();
			orderItems.replaceSelection(line++ + "\t" + order + " each.\n");
    	} catch (IndexOutOfBoundsException e) {
    		displayErrors(e.getMessage());
    	}
    	displayTotal();
    }
    
    void displayTotal() {
    	// Printing order total.
    	total.setText("Total: $" + String.format("%.2f", tCost));
    }
    
    void displayErrors(String error) {
    	errorField.replaceSelection(error + "\n");
    }
}
