package com.example.wtyul.singupandin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Hashtable;
import java.util.Random;

public class MenuPage extends Activity {
    ImageView imageView;
        public Bitmap createQRCode(String context){
            Bitmap bitmap = null;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
            /* Encode to utf-8 */
                Hashtable hints = new Hashtable();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

                BitMatrix bitMatrix = multiFormatWriter.encode(context, BarcodeFormat.QR_CODE,300,300, hints);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                bitmap = barcodeEncoder.createBitmap(bitMatrix);

            } catch (WriterException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
            return bitmap;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        imageView = (ImageView) this.findViewById(R.id.imageView);
        Random rnd = new Random();
        final String randomStr = String.valueOf(rnd.nextInt(10));

        String text2Qr = "http://192.168.5.149/thread-"+randomStr+".html";
        createQRCode(text2Qr);
    }

}
