package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    //int price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox cbwhippedCream = (CheckBox) findViewById(R.id.has_whipped_cream);
        boolean hasWhippedCream = cbwhippedCream.isChecked();

        CheckBox cbChocolate = (CheckBox) findViewById(R.id.has_chocolate);
        boolean hasChocolate = cbChocolate.isChecked();

        EditText etCustomerName = (EditText) findViewById(R.id.customer_name);
        String customerName = etCustomerName.getText().toString();

        //int price = 0;
        //int quantity = 10;
        //display(quantity);
        //displayPrice(quantity * 5);
        //quantity = Integer.valueOf(String.valueOf(R.id.quantity_text_view));
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        //price = quantity * 5;
        //String priceMessage = "Free";
        //displayMessage(priceMessage);
        //displayMessage("Total: $" + price + "\nThank you!");

        //displayMessage(createOrderSummary(customerName, price, hasWhippedCream, hasChocolate));

        emailOrderSummary(createOrderSummary(customerName, price, hasWhippedCream, hasChocolate), customerName);

        //resetOrderSummary();

    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        //Max cups of coffee = 100
        if(quantity == 100){
            Toast.makeText(this, R.string.err_max_coffee, Toast.LENGTH_LONG).show();
            return;
        }
        else {
            quantity = quantity + 1;
        }

        displayQuantity(quantity);
        //displayPrice(quantity * 5);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        //Min cups of coffee = 1
        if(quantity == 1){
            Toast.makeText(this, R.string.err_min_coffee, Toast.LENGTH_LONG).show();
            return;
        }
        else {
            quantity = quantity - 1;
        }

        displayQuantity(quantity);
        //displayPrice(quantity * 5);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number2) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number2));
    }

    /**
     * This method sends the order summary to an email app
     */
    private void emailOrderSummary(String orderSummary, String customerName) {
        //TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        //orderSummaryTextView.setText(message);
        //priceTextView.setText("sfsds & sdfsdfsdf");

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_subject, customerName));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }



    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;

        if(hasWhippedCream){
            price += 1;
        }
        if(hasChocolate){
            price += 2;
        }

        return (quantity * price);
    }

    /**
     * Creates the order summary
     *
     * @param price is the total price of the order
     * @param hasWhippedCream is whether or not whipped cream is added
     * @param hasChocolate is whether or not chocolate is added
     */
    private String createOrderSummary(String customerName, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String orderSummary;
        String addWhippedCream = hasWhippedCream ? getString(R.string.yes) : getString(R.string.no);
        String addChocolate = hasChocolate ? getString(R.string.yes) : getString(R.string.no);

        orderSummary = getString(R.string.order_summary_name, customerName) + "\n"
            + getString(R.string.order_summary_whipped_cream) + " " + addWhippedCream + "\n"
            + getString(R.string.order_summary_chocolate) + " " + addChocolate + "\n"
            + getString(R.string.order_summary_quantity, quantity) + "\n"
            + getString(R.string.order_summary_total, NumberFormat.getCurrencyInstance().format(price)) + "\n"
            + getString(R.string.order_summary_thankyou);

        return orderSummary;
    }
}