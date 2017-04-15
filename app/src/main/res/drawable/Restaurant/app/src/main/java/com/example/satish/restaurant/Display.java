package drawable.Restaurant.app.src.main.java.com.example.satish.restaurant;

/**
 * Created by Satish on 2/20/2016.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satish.restaurant.R;

/**
 * Created by hatim on 18-02-2016.
 */
public class Display extends Activity{

    String msg="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.display);
        Bundle b = getIntent().getExtras();
        String[] resultArr = b.getStringArray("selectedItems");
        int total_val = b.getInt("totalvalue");
        final String dem = b.getString("des");
        final String temp = Integer.toString(total_val);
        //Initializing
        final ListView lv = (ListView) findViewById(R.id.displaymylistview);
        TextView tv= (TextView) findViewById(R.id.tvindisplay);
        Button ok = (Button)findViewById(R.id.bconfirmorder);
        Button cancel = (Button)findViewById(R.id.bcancel);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, resultArr);
        lv.setAdapter(adapter);
        if (resultArr.equals("") || total_val==0) {
            tv.setText("You have not Selected Anything");
            ok.setVisibility(View.INVISIBLE);
        }
        else
            tv.setText("Total bill will be $" + total_val);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //SparseBooleanArray sp=myListView.getCheckedItemPositions();
                String item_name = (String) lv.getItemAtPosition(position);
                //   Toast.makeText(MainActivity.this, "Details",Toast.LENGTH_SHORT).show();

                if (item_name.contains("Chicken Tikka Masala"))
                    msg = "Ingredients : Tomato , Chicken , Capcicum \n It is spicy";

                else if (item_name.contains("Daal"))
                    msg = "Ingredients : Lentils , Tomato , Onion \n It is not spicy";
                else if (item_name.contains("Paneer Masala"))
                    msg = "Ingredients : Paneer , Tomato , Cashew \n It is not spicy";
                else if (item_name.contains("Butter Chicken"))
                    msg = "Ingredients : Butter , Chicken , Capcicup \n It is  spicy";
                else if (item_name.contains("Biryani"))
                    msg = "Ingredients : Rice , Beef , Spices \n It is  spicy";
                else
                    msg = dem;

                Dialog d = new Dialog(Display.this);

                TextView tv = new TextView(Display.this);
                tv.setText(msg);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Display.this);
                builder1.setTitle(item_name);
                builder1.setMessage(msg);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(Display.this);
                builder2.setTitle("Confirm Order");
                builder2.setMessage("Do you wish to Confirm Your Order ?");
                builder2.setCancelable(true);
                builder2.setPositiveButton(
                        "ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent returnIntent = new Intent();

                                returnIntent.putExtra("result", temp);
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        });
                builder2.setNegativeButton("Cancel", null);
                AlertDialog alert11 = builder2.create();
                alert11.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}