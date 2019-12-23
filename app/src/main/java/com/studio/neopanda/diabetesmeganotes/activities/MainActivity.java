package com.studio.neopanda.diabetesmeganotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.adapters.UserConnectionAdapter;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
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
    private boolean isTableNotEmpty = true;
    private List<User> usersList;
    private int imageResInput = 0;
    private String usernameNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        usersList = new ArrayList<>();

        checkIfUserExists();
        if (isTableNotEmpty) {
            loadRecyclerView();
            containerExistingUser.setVisibility(View.VISIBLE);
        }

        onClickNewUser();
        setImageListeners();
    }

    private void setImageListeners() {
        avatarOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageResInput = R.id.avatar_img_one;
                avatarOne.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple_green));
                avatarTwo.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarThree.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarFour.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                Toast.makeText(getApplicationContext(), "Avatar choisit!", Toast.LENGTH_SHORT).show();
            }
        });
        avatarTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageResInput = R.id.avatar_img_two;
                avatarOne.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarTwo.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple_green));
                avatarThree.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarFour.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                Toast.makeText(getApplicationContext(), "Avatar choisit!", Toast.LENGTH_SHORT).show();
            }
        });
        avatarThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageResInput = R.id.avatar_img_three;
                avatarOne.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarTwo.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarThree.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple_green));
                avatarFour.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                Toast.makeText(getApplicationContext(), "Avatar choisit!", Toast.LENGTH_SHORT).show();
            }
        });
        avatarFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageResInput = R.id.avatar_img_four;
                avatarOne.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarTwo.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarThree.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple));
                avatarFour.setImageDrawable(getResources().getDrawable(R.drawable.btn_simple_green));
                Toast.makeText(getApplicationContext(), "Avatar choisit!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickNewUser() {
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewUserViews();
                validateNewUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        usernameNewUser = usernameInput.getEditableText().toString();
                        if (!usernameNewUser.equals("") && imageResInput != 0) {
                            dbHelper.writeUserssInDB(usernameNewUser, imageResInput);
                            loadDashboard();
                        } else if (usernameNewUser.equals("")) {
                            Toast.makeText(MainActivity.this, "Vous devez remplir un nom d'utilisateur!", Toast.LENGTH_SHORT).show();
                        } else if (usernameNewUser.length() > 20) {
                            Toast.makeText(MainActivity.this, "Votre nom d'utilisateur est trop long, 20 caractères max autorisés!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Vous devez choisir une image!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void loadDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.go_up_anim, R.anim.go_down_anim);
        finish();
    }

    private void setNewUserViews() {
        newUserBtn.setVisibility(View.GONE);
        containerExistingUser.setVisibility(View.GONE);
        instructionsUsernameTv.setVisibility(View.VISIBLE);
        usernameInput.setVisibility(View.VISIBLE);
        containerImageSelections.setVisibility(View.VISIBLE);
        validateNewUser.setVisibility(View.VISIBLE);
    }

    private void loadRecyclerView() {
        UserConnectionAdapter adapter = new UserConnectionAdapter(getApplicationContext(), usersList, imageResInput);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void checkIfUserExists() {
        isTableNotEmpty = dbHelper.isTableNotEmpty("Users");
    }

    //Close database connection onDestroy
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
