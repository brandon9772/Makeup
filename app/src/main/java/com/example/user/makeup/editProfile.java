package com.example.user.makeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

public class editProfile extends AppCompatActivity {
    int userId = 1; // can be set to variable for switching user, not complete
    DBHandler dbHandler;
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //on create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
//set content end
        dbHandler = new DBHandler(this,DBHandler.TABLE_PROFILE,null);
        final ImageButton backProfile = (ImageButton) findViewById(R.id.backToProfile);
        final ImageButton updateProfile = (ImageButton) findViewById(R.id.updateProfilebtn);
        final EditText editText1r = (EditText) findViewById(R.id.editText1r);
        final EditText editText2r = (EditText) findViewById(R.id.editText2r);
        final EditText editText3r = (EditText) findViewById(R.id.editText3r);
        final EditText editText4r = (EditText) findViewById(R.id.editText4r);
        //edit text
        backProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editProfile.this, profile.class));
            }
        });

        //on click for button
        //get text from editText,run,use constructor ProfileDatavase, run dbHandler.update(profileDatabase)
        updateProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameEdit = editText1r.getText().toString();
                String ageEdit = editText2r.getText().toString();
                int ageEditint = Integer.parseInt(ageEdit);
                    ProfileDatabase profileDatabase = new ProfileDatabase(nameEdit, ageEditint);
                    dbHandler.update(profileDatabase);
                    Toast.makeText(editProfile.this,"Profile updated",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
