package com.setare.setare_96.store.Basic_Activity.Main_Activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.setare.setare_96.store.Basic_Activity.Users.SessionManager;
import com.setare.setare_96.store.R;

import java.util.HashMap;

@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    SessionManager session;
    String username = "Hoda :)";
    String email = "aforootan75@gmail.com";

    @View(R.id.profileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.emailTxt)
    private TextView emailTxt;

    @Resolve
    private void onResolved() {

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        username = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);

        nameTxt.setText(username);
        emailTxt.setText(email);
    }
}