/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int qValue = 0, numberOfToppings = 0;
    double sizeCost = 0, toppingsCost = 0;
    ArrayList<String> toppingsList = new ArrayList<>();
    double cValue = sizeCost + toppingsCost;
    int coffeeCounter = 0;
    double coffeePrice = 0;
    String coffeeSize;
    int[] chkBxIDs = {R.id.checkbox_sugar, R.id.checkbox_milk, R.id.checkbox_cream, R.id.checkbox_whipped_cream, R.id.checkbox_chocolate_sauce, R.id.checkbox_caramel_sauce};
    String[] chkBxNames = {"Sugar", "Milk", "Cream", "Whip", "Choc Sc", "Crml Sc"};
    ArrayList<CoffeeObject> coffeeObjectArrayList = new ArrayList<>();
    String custName;

    TextView quantityTextView;
    TextView costTextView;
    TextView totalCoffeeCount;
    TextView totalCoffeePrice;
    EditText customerName;

    ToggleButton tSml;
    ToggleButton tMed;
    ToggleButton tLrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMainPage();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    private void setMainPage(){

        setContentView(R.layout.activity_main);

        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        costTextView = (TextView) findViewById(R.id.cost_text_view);
        totalCoffeeCount = (TextView) findViewById(R.id.total_quantity_view);
        totalCoffeePrice = (TextView) findViewById(R.id.total_cost_view);

        customerName = (EditText) findViewById(R.id.edittext_customer_name);
        customerName.setText(custName);

        tSml = (ToggleButton) findViewById(R.id.toggle_small);
        tMed = (ToggleButton) findViewById(R.id.toggle_medium);
        tLrg = (ToggleButton) findViewById(R.id.toggle_large);

        display();
        displayTotalCount();

    }

    public void nameInput(View view){

        if (customerName.getText() != null) {
            custName = customerName.getText().toString();
        }

    }

    public void sizeOrder(View view) {

        switch (view.getId()) {
            case R.id.toggle_small:
                tMed.setChecked(false);
                tLrg.setChecked(false);
                coffeeSize = "Small";
                sizeCost = 1.25;
                break;
            case R.id.toggle_medium:
                tSml.setChecked(false);
                tLrg.setChecked(false);
                coffeeSize = "Medium";
                sizeCost = 1.75;
                break;
            case R.id.toggle_large:
                tSml.setChecked(false);
                tMed.setChecked(false);
                coffeeSize = "Large";
                sizeCost = 2.25;
                break;
        }

        calculateCost();
        display();

    }

    public void toppingUpdate(View view) {

        numberOfToppings = 0;
        toppingsList.clear();

        for (int i = 0; i < 6; i++) {

            AppCompatCheckBox boxCheck = (AppCompatCheckBox) findViewById(chkBxIDs[i]);
            if (boxCheck.isChecked() == true) {
                toppingsList.add(chkBxNames[i]);
                numberOfToppings++;
            }

        }

        calculateCost();
        display();

    }

    /**
     * This method is called when the add quantity button is clicked.
     */
    public void addQuantity(View view) {
        qValue++;
        calculateCost();
        display();
    }

    /**
     * This method is called when the subtract quantity button is clicked.
     */
    public void subtractQuantity(View view) {
        qValue--;
        if (qValue <= 0) {
            qValue = 0;
        }
        calculateCost();
        display();
    }

    public void addToOrder(View view) {

        if (qValue > 0 & coffeeSize != null) {
            coffeeObjectArrayList.add(new CoffeeObject(coffeeSize, numberOfToppings, qValue, cValue, toppingsList));
        }

        /*for (int i = 0; i < coffeeObjectArrayList.size(); i++){
            System.out.println(coffeeObjectArrayList.get(i) + ": " + coffeeObjectArrayList.get(i).getExtraOptions() + ": " + coffeeObjectArrayList.get(i).getToppingsList());
        }*/

        updateOrder();

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        if (customerName.getText().toString() != "" && !customerName.getText().toString().isEmpty()) {
            custName = customerName.getText().toString();
        } else {
            custName = "Customer";
        }

        String bodyText = "Thank you " + custName + ", your order of " + coffeeCounter + " coffees has been placed.\n\nThe total cost is " + NumberFormat.getCurrencyInstance().format(coffeePrice) + "\n\nOrder Details:\n";
        String format = "%1$-20s%2$-20s%3$-20s%4$-15s\n";
        bodyText = (bodyText + String.format("%1$-20s%2$-20s%3$-20s%4$-15s\n", "Quantity", "Size", "Toppings", "Price"));

        for(int i = 0; i < coffeeObjectArrayList.size(); i++){
            bodyText = bodyText + String.format(format, coffeeObjectArrayList.get(i).getcQuantity(), coffeeObjectArrayList.get(i).getCoffeeSize(), coffeeObjectArrayList.get(i).getExtraOptions(), NumberFormat.getCurrencyInstance().format(coffeeObjectArrayList.get(i).getcPrice()));
        }

        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.setType("*/*");
        //sendEmail.setData(Uri.parse("mailto:"));
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, (custName + "'s Coffee Order"));
        sendEmail.putExtra(Intent.EXTRA_TEXT, bodyText);
        if (sendEmail.resolveActivity(getPackageManager()) != null) {
            startActivity(sendEmail);
        }

        displayFinal();

    }



    public void resetOrder(View view) {

        /*for(int i = coffeeObjectArrayList.size(); i > 0; i--){
            coffeeObjectArrayList.remove(i);
        }*/
        coffeeObjectArrayList.clear();
        updateOrder();

    }

    public void newOrder(View view){

        coffeeObjectArrayList.clear();
        custName = null;

        setMainPage();
        updateOrder();

    }

    public void viewOrder(View view){

        displayOrderView();

    }

    public void returnToOrder(View view){

        setMainPage();

    }

    /**
     * This method displays the given quantity value and cost value on the screen.
     */
    private void display() {

        quantityTextView.setText("" + qValue);
        //costTextView.setText("$ " + BigDecimal.valueOf(cNumber).setScale(2));
        costTextView.setText(NumberFormat.getCurrencyInstance().format(cValue));
    }

    private void displayTotalCount() {

        calculateTotal();

        totalCoffeeCount.setText("" + coffeeCounter);
        totalCoffeePrice.setText("" + NumberFormat.getCurrencyInstance().format(coffeePrice));

    }

    /**
     * This method displays the final summary of a users order in another content pane.
     */
    private void displayFinal() {
        setContentView(R.layout.activity_submitted);

        TextView finalTextView = (TextView) findViewById(R.id.submitted_text_view);
        finalTextView.setText("Thank you, " + custName + ", your order of " + coffeeCounter + " coffees has been placed.\n\nThe total cost is " + NumberFormat.getCurrencyInstance().format(coffeePrice));


    }

    private void displayOrderView(){
        if (customerName.getText().toString() != "" && !customerName.getText().toString().isEmpty()) {
            custName = customerName.getText().toString();
        }

        setContentView(R.layout.activity_vieworder);

        LinearLayout orderLayout = findViewById(R.id.view_order_layout);

        for(int i = 0; i < coffeeObjectArrayList.size(); i++){
            ListObject object = new ListObject(coffeeObjectArrayList.get(i), orderLayout);
            object.createView();
            orderLayout.addView(object.getMainLayout());
        }

    }

    /**
     * This method calculates total cost for quantity.
     */
    private void calculateCost() {

        toppingsCost = 0.1 * numberOfToppings;
        cValue = (sizeCost + toppingsCost) * qValue;

    }

    private void resetCost(){

        // reset quantity and cost
        qValue = 0;
        sizeCost = 0;
        numberOfToppings = 0;
        calculateCost();

    }

    private void calculateTotal(){

        coffeeCounter = 0;
        coffeePrice = 0;

        if(coffeeObjectArrayList.size() > 0) {
            for (int i = 0; i < coffeeObjectArrayList.size(); i++) {
                coffeeCounter = coffeeCounter + coffeeObjectArrayList.get(i).getcQuantity();
                coffeePrice = coffeePrice + coffeeObjectArrayList.get(i).getcPrice();
            }
        }
    }

    private void updateOrder(){

        // reset all size options
        tSml.setChecked(false);
        tMed.setChecked(false);
        tLrg.setChecked(false);
        // reset all toppings
        for (int i = 0; i < 6; i++) {

            AppCompatCheckBox boxCheck = (AppCompatCheckBox) findViewById(chkBxIDs[i]);
            boxCheck.setChecked(false);

        }
        coffeeSize = null;
        toppingsList.clear();
        resetCost();
        display();

        displayTotalCount();
    }
}