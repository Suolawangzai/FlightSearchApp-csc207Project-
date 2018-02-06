package group_0700.flighthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import user.Admin;

public class admin_menu extends AppCompatActivity {

    Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        Intent intent = getIntent();
        admin = (Admin) intent.getSerializableExtra("Admin");
    }

    public void goToAdminViewClientInfo(View view){
        Intent intent = new Intent(this, ViewAllClientsInfo.class);
        intent.putExtra("Admin",admin);
        startActivity(intent);
    }
}
