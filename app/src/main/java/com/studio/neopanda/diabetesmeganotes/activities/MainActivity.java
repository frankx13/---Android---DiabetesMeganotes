package com.studio.neopanda.diabetesmeganotes.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.adapters.UserConnectionAdapter;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.CurrentUser;
import com.studio.neopanda.diabetesmeganotes.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //UI
    @BindView(R.id.container_existing_user_user)
    LinearLayout containerExistingUser;
    @BindView(R.id.new_user_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.title_new_user)
    Button newUserBtn;
    @BindView(R.id.new_user_username_instructions_TV)
    TextView instructionsUsernameTv;
    @BindView(R.id.create_username_input)
    EditText usernameInput;
    @BindView(R.id.container_new_user_image_selection)
    LinearLayout containerImageSelections;
    @BindView(R.id.new_user_image_instructions_TV)
    TextView instructionsImageTv;
    @BindView(R.id.subcontainer_image_one)
    LinearLayout subcontainerImageOne;
    @BindView(R.id.subcontainer_image_two)
    LinearLayout subcontainerImageTwo;
    @BindView(R.id.avatar_img_one)
    ImageView avatarOne;
    @BindView(R.id.avatar_img_two)
    ImageView avatarTwo;
    @BindView(R.id.avatar_img_three)
    ImageView avatarThree;
    @BindView(R.id.avatar_img_four)
    ImageView avatarFour;
    @BindView(R.id.validation_new_user_btn)
    Button validateNewUser;

    //DATA
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private boolean isUserTableEmpty = true;
    private List<User> usersList;
    private String imageResInput;
    private String usernameNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        List<CurrentUser> currentUserList;
        checkIfCurrentUserExists();
        String[] currentUserArray = new String[]{"currentUserDefault"};
        currentUserList = dbHelper.getActiveUserInDB();
        if (!currentUserList.isEmpty()) {
            String currentUser = currentUserList.get(0).getUsername();
            currentUserArray[0] = currentUser;
            dbHelper.resetActiveUserInDB(currentUserArray);
        }

        usersList = new ArrayList<>();

        checkIfUserExists();
        usersList = dbHelper.getUsers();
        if (isUserTableEmpty) {
            containerExistingUser.setVisibility(View.VISIBLE);
            loadRecyclerView();
        }

        setImageListeners();
        onClickNewUser();
    }

    private void checkIfCurrentUserExists() {
        boolean isCurrentUserActive = dbHelper.isTableNotEmpty("CurrentUser");
    }

    private void setImageListeners() {
        //TODO cleanup code and remove the double parsing of types for image resources
        avatarOne.setOnClickListener(v -> {
            imageResInput = String.valueOf(R.drawable.ic_avatar_one);
            avatarOne.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple_green));
            avatarTwo.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarThree.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarFour.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            Toast.makeText(getApplicationContext(), "Avatar choisit!", Toast.LENGTH_SHORT).show();
        });
        avatarTwo.setOnClickListener(v -> {
            imageResInput = String.valueOf(R.drawable.ic_avatar_two);
            avatarOne.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarTwo.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple_green));
            avatarThree.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarFour.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            Toast.makeText(getApplicationContext(), "Avatar choisit!", Toast.LENGTH_SHORT).show();
        });
        avatarThree.setOnClickListener(v -> {
            imageResInput = String.valueOf(R.drawable.ic_avatar_three);
            avatarOne.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarTwo.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarThree.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple_green));
            avatarFour.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            Toast.makeText(getApplicationContext(), "Avatar choisit!", Toast.LENGTH_SHORT).show();
        });
        avatarFour.setOnClickListener(v -> {
            imageResInput = String.valueOf(R.drawable.ic_avatar_four);
            avatarOne.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarTwo.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarThree.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
            avatarFour.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple_green));
            Toast.makeText(getApplicationContext(), "Avatar choisit!", Toast.LENGTH_SHORT).show();
        });
    }

    private void onClickNewUser() {
        newUserBtn.setOnClickListener(v -> {
            if (usersList.size() < 3) {
                containerExistingUser.setVisibility(View.GONE);
                setNewUserViews();
                validateNewUser.setOnClickListener(v1 -> {
                    usernameNewUser = usernameInput.getEditableText().toString();
                    if (!usernameNewUser.equals("") && !imageResInput.equals("")) {
                        dbHelper.writeUserssInDB(usernameNewUser, imageResInput);
                        usersList = dbHelper.getUsers();
                        setMenuViews();
                        containerExistingUser.setVisibility(View.VISIBLE);
                        loadRecyclerView();
                        Toast.makeText(MainActivity.this, "Utilisateur créé!", Toast.LENGTH_SHORT).show();
                    } else if (usernameNewUser.equals("")) {
                        Toast.makeText(MainActivity.this, "Vous devez choisir un nom d'utilisateur!", Toast.LENGTH_SHORT).show();
                    } else if (usernameNewUser.length() > 20) {
                        Toast.makeText(MainActivity.this, "Votre nom d'utilisateur est trop long, 20 caractères max autorisés!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Vous devez choisir une image!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                onCreateDialog();
            }
        });
    }

    public void onCreateDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_delete_existing_user)
                .setPositiveButton(R.string.ok, (dialog, id) -> loopThroughRV());
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    private void loopThroughRV() {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            recyclerView.getChildAt(i).findViewById(R.id.delete_user_btn).setVisibility(View.VISIBLE);
        }
    }

    private void setNewUserViews() {
        newUserBtn.setVisibility(View.GONE);
        containerExistingUser.setVisibility(View.GONE);
        instructionsUsernameTv.setVisibility(View.VISIBLE);
        usernameInput.setVisibility(View.VISIBLE);
        containerImageSelections.setVisibility(View.VISIBLE);
        validateNewUser.setVisibility(View.VISIBLE);
    }

    private void setMenuViews() {
        newUserBtn.setVisibility(View.VISIBLE);
        containerExistingUser.setVisibility(View.VISIBLE);
        instructionsUsernameTv.setVisibility(View.GONE);
        usernameInput.setVisibility(View.GONE);
        containerImageSelections.setVisibility(View.GONE);
        validateNewUser.setVisibility(View.GONE);
    }

    private void loadRecyclerView() {
        UserConnectionAdapter adapter = new UserConnectionAdapter(this, this, usersList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void checkIfUserExists() {
        isUserTableEmpty = dbHelper.isTableNotEmpty("Users");
    }

    //Close database connection onDestroy
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
