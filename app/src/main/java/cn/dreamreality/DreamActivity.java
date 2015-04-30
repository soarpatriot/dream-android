package cn.dreamreality;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.dreamreality.dialogs.CustomDialog;
import cn.dreamreality.handlers.LoginHandler;
import cn.dreamreality.handlers.PostHandler;
import cn.dreamreality.runners.LoginRunner;
import cn.dreamreality.runners.PostRunner;
import cn.dreamreality.utils.SettingsUtils;


public class DreamActivity extends ActionBarActivity {

    private EditText dream_edit_text;
    private EditText reality_edit_text;
    private Button submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dream_edit_text = (EditText)findViewById(R.id.dream_edit_text);
        reality_edit_text = (EditText)findViewById(R.id.reality_edit_text);
        submit_btn = (Button)findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dream_text = dream_edit_text.getText().toString().trim();
                String reality_text = reality_edit_text.getText().toString().trim();
                if(TextUtils.isEmpty(dream_text) || TextUtils.isEmpty(reality_text)) {
                    Toast.makeText(getApplicationContext(), R.string.dream_or_reality_blank,
                            Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    PostHandler postHandler = new PostHandler(v.getContext());
                    String token = SettingsUtils.getSettings(v.getContext().getApplicationContext(),"token");
                    PostRunner runner = new PostRunner(dream_text,reality_text,token,postHandler);
                    runner.start();
                }

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dream_text = dream_edit_text.getText().toString().trim();
                String reality_text = reality_edit_text.getText().toString().trim();
                if(!TextUtils.isEmpty(dream_text) || !TextUtils.isEmpty(reality_text)) {

                    LayoutInflater inflater = getLayoutInflater();
                    //View layout = inflater.inflate(R.layout.dialog_back_write,(ViewGroup) findViewById(R.id.dialog));
                    CustomDialog dialog=new CustomDialog(v.getContext(), R.style.customDialog, R.layout.custom_dialog);
                    dialog.show();


                    //final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Dialog);
                    /**
                    builder.setMessage(R.string.back_no_save)
                            .setTitle(R.string.back_or_not);


                    builder.setPositiveButton(R.string.button_confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            onBackPressed();
                        }
                    });
                    builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();**/

                }else{
                    onBackPressed();
                }

                //What to do on back clicked
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
