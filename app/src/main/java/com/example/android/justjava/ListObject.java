package com.example.android.justjava;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Matthew on 2/19/2018.
 */

public class ListObject{

    private LinearLayout mainLayout;

    private TextView quantityTxtVw;
    private TextView sizeTxtVw;
    private LinearLayout toppingsLayout;
    private TextView toppingsTxtVw;
    private TextView costTxtVw;

    private CoffeeObject coffeeOrder;
    private View coffeeView;
    ArrayList<TextView> tpgTxtVws = new ArrayList<>();

    Context context;

    public ListObject(CoffeeObject coffeeObject, View orderView){

        coffeeOrder = coffeeObject;
        coffeeView = orderView;

    }

    public LinearLayout getMainLayout() {
        return mainLayout;
    }

    public void createView(){

        context = coffeeView.getContext();

        mainLayout = new LinearLayout(context);
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);

        // set up quanity text view
        quantityTxtVw = new TextView(context);
        quantityTxtVw.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        quantityTxtVw.setTextColor(Color.BLACK);
        quantityTxtVw.setText("" + coffeeOrder.getcQuantity());

        // set up size text view
        sizeTxtVw = new TextView(context);
        sizeTxtVw.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        sizeTxtVw.setTextColor(Color.BLACK);
        sizeTxtVw.setText("" + coffeeOrder.getCoffeeSize());

        // set up cost text view
        costTxtVw = new TextView(context);
        costTxtVw.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        costTxtVw.setTextColor(Color.BLACK);
        costTxtVw.setText("" + NumberFormat.getCurrencyInstance().format(coffeeOrder.getcPrice()));

        // set up toppings text view
        /*toppingsTxtVw = new TextView(context);
        toppingsTxtVw.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        toppingsTxtVw.setTextColor(Color.BLACK);
        toppingsTxtVw.setText("" + coffeeOrder.getExtraOptions());*/

        // set up toppings layout view
        toppingsLayout = new LinearLayout(context);
        toppingsLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        toppingsLayout.setOrientation(LinearLayout.VERTICAL);
        // toppingsLayout.addView(toppingsTxtVw);

        // set up toppings list text views
        for (int i = 0; i < coffeeOrder.getToppingsList().size(); i++){

            String filler = "" + coffeeOrder.getToppingsList().get(i);
            TextView testView = new TextView(context);
            testView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            testView.setTextColor(Color.BLACK);
            testView.setText("" + filler);
            toppingsLayout.addView(testView);
        }

        // add all to main linear layout
        mainLayout.addView(quantityTxtVw);
        mainLayout.addView(sizeTxtVw);
        mainLayout.addView(toppingsLayout);
        mainLayout.addView(costTxtVw);

    }

}
