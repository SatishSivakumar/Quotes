package drawable.Restaurant.app.src.main.java.com.example.satish.restaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    int i=1;
    String msg;
    Random r = new Random();
    int Low = 10;
    int High = 25;
    int addvalue=0;
    String des;
    int position;
    TextView tvs;
    int abc=0;
    String price;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.content_main);

        //Initialization
        final EditText myEditText = (EditText) findViewById(R.id.myedittext);
        final EditText etdes = (EditText) findViewById(R.id.etdescription);
        final EditText etprice = (EditText) findViewById(R.id.etprice);
        final ListView myListView = (ListView) findViewById(R.id.mylistview);
        final ArrayList<String> todoItems = new ArrayList<String>();
        final ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, todoItems);
        myListView.setAdapter(aa);
        Button additem = (Button) findViewById(R.id.badditem);
        final Button playorder = (Button) findViewById(R.id.bplayorder);
        Button reset = (Button) findViewById(R.id.breset);
        tvs = (TextView)findViewById(R.id.tvinmain);




        final int Result1 = 24;
        todoItems.add((i++) + ": Chicken Tikka Masala" + "\nPrice: $" + Result1);
        final int Result2 = 15;
        todoItems.add((i++)+": Daal"+"\nPrice: $"+Result2);
        final int Result3 = 13;
        todoItems.add((i++)+": Paneer Masala"+"\nPrice: $"+Result3);
        final int Result4 = 16;
        todoItems.add((i++) + ": Butter Chicken" + "\nPrice: $" + Result4);
        final int Result5=15;
        todoItems.add((i++) + ": Biryani" + "\nPrice: $" + Result5);

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.badditem:


                        String added_item = myEditText.getText().toString();
                        price = etprice.getText().toString();

                        des = etdes.getText().toString();
                        if (added_item.equals(""))
                            Toast.makeText(MainActivity.this, "Please enter some data", Toast.LENGTH_SHORT).show();
                        else {
                            todoItems.add((i++) + ": " + added_item + "\nPrice: $" + price);
                            aa.notifyDataSetChanged();

                            myEditText.setText("");
                            etdes.setText("");
                            abc = Integer.parseInt(price);
                            etprice.setText("");
                            //hiding the Keyboard after using edit Text
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);

                        }

                        break;

                }
            }
        });

        // Resetting all the Checkboxess
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgg=tvs.toString();
                for (int am = 0; am <= myListView.getCount(); am++) {
                    myListView.setItemChecked(am, false);
                }

                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("Ar You Sure");
                builder2.setMessage("Do you want to reset the Total ?");
                builder2.setCancelable(true);
                builder2.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                tvs.setText("No item is Selected");
                            }
                        });
                builder2.setNegativeButton("No", null);
                AlertDialog alert11 = builder2.create();
                alert11.show();


            }
        });

        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myListView.setItemChecked(0, false);

        playorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray checked = myListView.getCheckedItemPositions();


                if (checked.valueAt(0) == true)
                    addvalue = addvalue + 24;
                if (checked.valueAt(1) == true)
                    addvalue = addvalue + 15;
                if (checked.valueAt(2) == true)
                    addvalue = addvalue + 13;
                if (checked.valueAt(3) == true)
                    addvalue = addvalue + 16;
                if (checked.valueAt(4) == true)
                    addvalue = addvalue+15;
                if (checked.valueAt(5) == true)
                    addvalue = addvalue+Integer.parseInt(price);




                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {

                    // Item position in adapter
                    position = checked.keyAt(i);
                    // Add item if it is checked i.e.) == TRUE!

                    if (checked.valueAt(i))
                        selectedItems.add(aa.getItem(position));

                }
                String[] outputStrArr = new String[selectedItems.size()];

                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                }
                Intent intent = new Intent(getApplicationContext(),
                        Display.class);


                // Create a bundle object
                Bundle b = new Bundle();
                b.putStringArray("selectedItems", outputStrArr);
                Bundle c = new Bundle();
                c.putInt("totalvalue", addvalue);
                Bundle d = new Bundle();
                d.putString("des", des);


                // Add the bundle to the intent.
                intent.putExtras(b);
                intent.putExtras(c);

                addvalue=0;
                // start the ResultActivity
                startActivityForResult(intent, 1);


            }
        });

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String item_name1 = (String) myListView.getItemAtPosition(position);

                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle(item_name1);
                builder2.setMessage("Do you want to Delete this item");
                builder2.setCancelable(true);

                builder2.setPositiveButton(
                        "ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                i--;
                                todoItems.remove(item_name1);
                                aa.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, item_name1 + "removed from the menu", Toast.LENGTH_LONG).show();
                            }
                        });
                builder2.setNegativeButton("Cancel", null);
                AlertDialog alert11 = builder2.create();
                alert11.show();
                return true;

            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                int setval= Integer.parseInt(result);
                tvs.setText("Total Bill is $" + setval);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result

            }
        }
    }

}