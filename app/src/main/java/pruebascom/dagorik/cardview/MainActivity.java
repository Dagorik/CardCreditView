package pruebascom.dagorik.cardview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class MainActivity extends AppCompatActivity {

    private int MY_SCAN_REQUEST_CODE = 100;
    private TextView resultTextView,number_card,valido_hasta,csv;
    private Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scanButton = (Button) findViewById(R.id.scanButton);
        number_card= (TextView) findViewById(R.id.number_card);
        valido_hasta = (TextView) findViewById(R.id.valido_hasta);
        csv= (TextView) findViewById(R.id.csv);

        resultTextView.setText("card.io library version: " + CardIOActivity.sdkVersion() + "\nBuilt: " + CardIOActivity.sdkBuildDate());

    }


    public void onScanPress(View view) {

        Intent scanInten = new Intent(this, CardIOActivity.class);

        scanInten.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY,true);
        scanInten.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV,true);
        scanInten.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, true);

        startActivityForResult(scanInten,MY_SCAN_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String resultStr;
        String numer = "holi";
        String expe = "expira";
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
            
            // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
            resultStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
            numer = scanResult.getRedactedCardNumber();
            numer=scanResult.cardNumber;
            // Do something with the raw number, e.g.:
            // myService.setCardNumber( scanResult.cardNumber );

            if (scanResult.isExpiryValid()) {
                resultStr += "Expira el dia: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                 expe = scanResult.expiryMonth + "/" + scanResult.expiryYear;
            }

            if (scanResult.cvv != null) {
                // Never log or display a CVV
                resultStr += "CVV es " + scanResult.cvv.length() + " digits.\n";
            }

            if (scanResult.postalCode != null) {
                resultStr += "Codigo Postal: " + scanResult.postalCode + "\n";
            }

            if (scanResult.cardholderName != null) {
                resultStr += "Cardholder Name : " + scanResult.cardholderName + "\n";
            }
        } else {
            resultStr = "Scan was canceled.";
        }
        resultTextView.setText(resultStr);
        number_card.setText(numer);
        valido_hasta.setText(expe);
        Log.d("MyLogNumbre", numer);
    }
}