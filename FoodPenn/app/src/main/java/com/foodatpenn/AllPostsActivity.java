package com.foodatpenn;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodpenn.R;
import com.foodatpenn.data.PostStore;
import com.foodatpenn.data.PostStoreMongo;
import com.foodatpenn.data.Posts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AllPostsActivity extends AppCompatActivity {
    PostStore postList;

    Map<String, Posts> all;
    ArrayList<Posts> posts1;
    private ArrayList<Post> posts;
    private TextView textView;
    private String allPosts;
    private Button modifyButton;
    private Button backButton;
    private Button remove;
    EditText id;
    EditText food;
    EditText description;
    EditText locationFood;
    EditText removeId;
    String userEmail;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_posts_activity);
        postList = PostStoreMongo.getInstance();
        posts1 = new ArrayList<Posts>();
        posts = (ArrayList<Post>) getIntent().getSerializableExtra("RESULT");
        userEmail = this.getIntent().getStringExtra("Email");
        all = postList.getUsers();



        id = (EditText) findViewById(R.id.id1);
        food = (EditText) findViewById(R.id.food1);
        description = (EditText) findViewById(R.id.description1);
        locationFood = (EditText) findViewById(R.id.locationFood1);
        modifyButton = (Button) findViewById(R.id.modifyButton);
        backButton = (Button) findViewById(R.id.backButton);

        removeId = (EditText) findViewById(R.id.remove1);
        remove = (Button) findViewById(R.id.removeButton);
        showEntries();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveBack();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePost();
                finish();
                startActivity(getIntent());
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyButtonClicked();
                finish();
                startActivity(getIntent());
            }
        });


        }

        public void showEntries(){        //        String allPosts = "";
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");

            for (Posts curr: all.values()){

                    posts1.add(curr);

            }
            allPosts = posts1.size() + " ";
            for (int i = 0; i < posts1.size(); i++) {
                String date = posts1.get(i).getDate();
                String food = posts1.get(i).getFood();
                String description = posts1.get(i).getDescription();
                String location = posts1.get(i).getLocation();
                String id = posts1.get(i).getId();

                allPosts += date + "\n" +
                        "Food is " + food+ "\n"
                        + "Description is " + description + "\n"
                        + "Location is " + location + "\n" + "ID is " + id + "\n" + "\n";
            }

            textView = findViewById(R.id.textPosts);
            textView.setText(allPosts);
        }

    private void modifyButtonClicked() {
        Date current = Calendar.getInstance().getTime();
        String index = id.getText().toString();
        int finalValue = Integer.parseInt(index);
        postList.modifyPost(index,food.getText().toString(), description.getText().toString(),
                locationFood.getText().toString(), userEmail);
        for (int i = 0; i < posts.size(); i++){
            Post curr = posts.get(i);
            if (curr.getId() == finalValue){
                Post p = new Post(food.getText().toString(), description.getText().toString(),
                        locationFood.getText().toString(), current, finalValue);
                posts.set(i, p);
            }
        }

        id.getText().clear();
        food.getText().clear();
        description.getText().clear();
        locationFood.getText().clear();
    }

    public void moveBack() {
        Intent i = new Intent(this, CreatePostsActivity.class);
        i.putExtra("RESULT", posts);
        i.putExtra("Email", userEmail);
        startActivityForResult(i, 2);
    }

    public void removePost() {
        String index = removeId.getText().toString();
        postList.deletePost(index);
        int finalValue = Integer.parseInt(index);
        for (int i = 0; i < posts.size(); i++){
            Post curr = posts.get(i);
            if (curr.getId() == finalValue){
                posts.remove(i);
            }
        }

    }
}



