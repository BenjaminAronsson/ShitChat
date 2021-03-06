package se.shitchat.shitchatapp.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import se.shitchat.shitchatapp.R;
import se.shitchat.shitchatapp.classes.User;
import se.shitchat.shitchatapp.adapters.SearchAdapter;

public class SearchActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ImageButton searchButton;
    private SearchAdapter searchAdapter;
    private Query userDb;
    private EditText input;
    private Query query;
    private String searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Initialise widgets
        input = findViewById(R.id.editText);
        searchButton = findViewById(R.id.imageButtonSearch);
        Toolbar searchToolbar = findViewById(R.id.searchToolbar);
        RecyclerView searchRecycler = findViewById(R.id.searchRecyclerView);

        setSupportActionBar(searchToolbar);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        //skickar med group id (Singelton ish)
        SearchAdapter.groupID = getIntent().getStringExtra("groupId");

        searchClicked();

        onClickSetup();


    }

    //Method listening for search query
    private void onClickSetup() {

        //Listens for "enter"
        input.setOnClickListener(view -> searchClicked());

        //Listens for search button
        searchButton.setOnClickListener(view -> {

            searchInput = input.getText().toString();
            searchClicked();

            //works wit lowercase
    userDb = db.collection("users").whereEqualTo("username".toLowerCase(), searchInput.toLowerCase());

    setUpSearchRecycler();

    searchAdapter.startListening();
        });

    }

    //Handles search input
    private void searchClicked() {

        searchInput = input.getText().toString();



        userDb = db.collection("users").whereEqualTo("username", searchInput);

        setUpSearchRecycler();

        searchAdapter.startListening();

    }

    private String SearchQuery() {
        searchInput = input.getText().toString();

        return searchInput;
    }


    //Fetches data and adds to recyclerView
    private void setUpSearchRecycler() {

        //Set up recycler view with User class
        FirestoreRecyclerOptions<User> option = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(userDb, User.class)
                .build();

        searchAdapter = new SearchAdapter(option);

        RecyclerView searchRecyclerView = findViewById(R.id.searchRecyclerView);

        //Sets recycler view to activity
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(searchAdapter);

        db.collection("users").document("username").get();


    }


    //Handles back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        searchAdapter.stopListening();
        finish();
    }
}