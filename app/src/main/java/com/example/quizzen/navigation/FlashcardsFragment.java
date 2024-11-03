package com.example.quizzen.navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzen.R;
import com.example.quizzen.flashcards.AddFlashcards;
import com.example.quizzen.flashcards.FlashCardSetAVLNode;
import com.example.quizzen.flashcards.FlashCardSetAVLTree;
import com.example.quizzen.flashcards.FlashcardSet;
import com.example.quizzen.flashcards.ViewFlashcard;
import com.example.quizzen.login.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Flashcard fragment class.
 * Displays the flashcard sets, and allows the user to search for a set.
 * The user can also create a new set, or view their own sets.
 * @author Jiazhe Lin
 * @author Judy Xie u7397058
 * @version 1.0
 */
public class FlashcardsFragment extends Fragment {
    // flashcard set tree, which will be used in search tree
    private FlashCardSetAVLTree<String, FlashcardSet> tree;
    // list of flashcard sets
    private static final List<FlashcardSet> flashcardSetList = new ArrayList<>();
    // list of flashcard sets that match the search
    private static final List<FlashcardSet> searchFlashcardSetList = new ArrayList<>();
    // recycler view casted onto listview
    private RecyclerView recyclerView;
    // adapter for recycler view, which shows individual sets
    private ItemAdapter adapter;
    TextView userFCS;
    TextView publicFCS;
    TextView createFCS;
    // whether the user is logged in
    public static boolean loggedIn = false;
    FirebaseAuth auth;
    FirebaseDatabase database;

    public FlashcardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_flashcards, container, false);

        // get instance of Firebase, check who is logged in
        auth = FirebaseAuth.getInstance();
        // is user logged in?
        Log.d("showLogcat", "loggedIn " + loggedIn);
        // get instance of Firebase database
        database = FirebaseDatabase.getInstance();

        userFCS = rootView.findViewById(R.id.tvYourFCS);
        publicFCS = rootView.findViewById(R.id.tvPublicFCS);
        createFCS = rootView.findViewById(R.id.btn_create);
        recyclerView = rootView.findViewById(R.id.FCS_list);

        // onClick listeners
        userFCS.setOnClickListener(this :: onClick);
        userFCS.setOnClickListener(this :: onClick);
        publicFCS.setOnClickListener(this :: onClick);
        createFCS.setOnClickListener(this :: onClick);

        // set the recycler view, and show the flashcard sets
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);
        // get reference to the SearchView
        SearchView searchView = rootView.findViewById(R.id.searchFlashcard);
        // set query listener on SearchView

        // On search for flashcard set
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //split key words by comma
                String[] keyArray = query.split(",");
                // Handle search query submission
                Log.d("showLogcat", " query " + query);
                // clear previous search list
                searchFlashcardSetList.clear();
                // search for the flashcard set
                if (tree != null){
                    for (String s : keyArray) {
                        FlashCardSetAVLNode<String, FlashcardSet> result = tree.search(s);
                        if (result != null) { // if result found
                            Log.d("showLogcat", "result title " + result.getKey());
                            searchFlashcardSetList.add(result.getValue());
                        } else { // if no result found
                            Log.d("showLogcat", "result is null");
                        }
                    }
                } // if the flashcard set is found, update the list view to show the result
                if (searchFlashcardSetList.size() > 0){
                    refreshListView(searchFlashcardSetList);
                } else { // if no result found, show toast
                    Toast.makeText(getActivity(),"No result found!",Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text changes
                if (TextUtils.isEmpty(newText)){
                    // if the search view is empty, show all flashcard sets
                    refreshListView(flashcardSetList);
                }
                return false;
            }
        });
        // refresh data
        loadData();
        return rootView;
    }

    // onClick events for different buttons
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvYourFCS:
                if (loggedIn) {
                    // TODO: FINISH THIS
                    Toast.makeText(getActivity(), "Your Flashcard Sets", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please login!", Toast.LENGTH_SHORT).show();
                }
            case R.id.tvPublicFCS:
                // TODO: FINISH THIS
                Toast.makeText(getActivity(), "Public Flashcard Sets", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_create: // create flashcard set
                if (loggedIn) {
                    Intent i = new Intent(getActivity(), AddFlashcards.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Please login!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    // Load data from firebase
    private void loadData() {
        FirebaseDatabase.getInstance().getReference().child("sets")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<Map<String, FlashcardSet>> genericTypeIndicator = new GenericTypeIndicator<Map<String, FlashcardSet>>() {};
                        Map<String, FlashcardSet> map = snapshot.getValue(genericTypeIndicator);
                        if (map != null){
                            Log.d("showLogcat", "Value is: " + map.size());
                            tree = new FlashCardSetAVLTree<>();
                            flashcardSetList.clear();

                            // add data to tree.
                            for (FlashcardSet flashcardSet : map.values()){
                                Log.d("showLogcat", "Value title is: " + flashcardSet.getTitle());
                                tree.insert(flashcardSet.getTitle(),flashcardSet);
                                flashcardSetList.add(flashcardSet);
                            }
                            refreshListView(flashcardSetList);
                        }else {
                            Log.d("showLogcat", "Value1 is null" );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("showLogcat", "Failed to read value.", error.toException());
                    }
                });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshListView(List<FlashcardSet> flashcardSetList) {
        adapter.setFlashcardSets(flashcardSetList);
        adapter.notifyDataSetChanged();
    }

    // item_flash_cards holder
    private static class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{
        private final List<FlashcardSet> flashcardSets = new ArrayList<>();

        // set data of item_flash_cards holders
        public void setFlashcardSets(List<FlashcardSet> data) {
            flashcardSets.clear();
            flashcardSets.addAll(data);
        }
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flash_cards, parent, false);
            return new ItemViewHolder(view);
        }

        // set data of item_flash_cards holders
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            FlashcardSet data = flashcardSets.get(position);
            holder.titleTv.setText(data.getTitle());
            holder.idTv.setText("By: " + data.getUserID());
            holder.favoriteTv.setText("Favorite: " + data.isFavorite());
        }

        @Override
        public int getItemCount() {
            return flashcardSets.size();
        }
    }

    // set contents of item_flash_cards
    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView idTv;
        private TextView favoriteTv;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_tv);
            idTv = itemView.findViewById(R.id.id_tv);
            favoriteTv = itemView.findViewById(R.id.favorite_tv);

            itemView.setOnClickListener(v -> {
                // get position of the item clicked
                int position = getAdapterPosition();
                ArrayList<FlashcardSet> list;

                // check if user is searching or viewing all flashcards
                if (searchFlashcardSetList.size() > 0){
                    list = (ArrayList<FlashcardSet>) searchFlashcardSetList;
                } else list = (ArrayList<FlashcardSet>) flashcardSetList;

                // if the position is valid, start ViewFlashcard activity
                if (position != RecyclerView.NO_POSITION) {
                    Intent i = new Intent(v.getContext(), ViewFlashcard.class);
                    i.putExtra("FlashcardSet", list.get(position));
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}