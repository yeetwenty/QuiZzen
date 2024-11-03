package com.example.quizzen.flashcards;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzen.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlashcardSearchActivity extends AppCompatActivity {
    private FlashCardSetAVLTree<String,FlashcardSet> tree;
    private final List<FlashcardSet> flashcardSetList = new ArrayList<>();
    private final List<FlashcardSet> searchFlashcardSetList = new ArrayList<>();
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_flashcards);
        RecyclerView recyclerView = findViewById(R.id.FCS_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);
        // get reference to the SearchView
        SearchView searchView = findViewById(R.id.searchFlashcard);
        // set query listener on SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                Log.d("showLogcat", " query " + query);
                //flashcardTree.search(query);
                String[] keyArray = query.split(" ");
                searchFlashcardSetList.clear();
                if (tree != null){
                    for (int i = 0; i < keyArray.length; i++) {
                        FlashCardSetAVLNode<String,FlashcardSet> result = tree.search(keyArray[i]);
                        if (result != null){
                            Log.d("showLogcat", "result title "+result.getKey() );
                            searchFlashcardSetList.add(result.getValue());
                        }else {
                            Log.d("showLogcat", "result is null " );
                        }
                    }
                }
                if (searchFlashcardSetList.size() > 0){
                    refreshListView(searchFlashcardSetList);
                }else {
                    Toast.makeText(FlashcardSearchActivity.this,"No result found!",Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text changes
                if (TextUtils.isEmpty(newText)){
                    refreshListView(flashcardSetList);
                }
                return false;
            }
        });
        loadData();
    }

    private void loadData() {
        FirebaseDatabase.getInstance().getReference().child("sets")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<Map<String, FlashcardSet>> genericTypeIndicator = new GenericTypeIndicator<Map<String, FlashcardSet>>() {};
                        Map<String, FlashcardSet> map = snapshot.getValue(genericTypeIndicator );
                        if (map != null){
                            Log.d("showLogcat", "Value is: " + map.size());
                            tree = new FlashCardSetAVLTree<>();
                            flashcardSetList.clear();
                            for (FlashcardSet flashcardSet : map.values()){
                                Log.d("showLogcat", "Value title is: " + flashcardSet.title);
                                tree.insert(flashcardSet.title,flashcardSet);
                                flashcardSetList.add(flashcardSet);
                            }
                            refreshListView(flashcardSetList);
                        }else {
                            Log.d("showLogcat", "Value1 is null " );
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

    private static class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{
        private final List<FlashcardSet> flashcardSets = new ArrayList<>();

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

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            FlashcardSet data = flashcardSets.get(position);
            holder.titleTv.setText("Title: "+data.getTitle());
            holder.idTv.setText("ID: "+data.getSetID());
            holder.favoriteTv.setText("isFavorite: "+data.isFavorite());
        }

        @Override
        public int getItemCount() {
            return flashcardSets.size();
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView idTv;
        private TextView favoriteTv;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_tv);
            idTv = itemView.findViewById(R.id.id_tv);
            favoriteTv = itemView.findViewById(R.id.favorite_tv);
        }
    }
}

