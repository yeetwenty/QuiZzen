package com.example.quizzen.navigation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.quizzen.R;
import com.example.quizzen.book.BookRepository;
//import com.example.quizzen.database.;
import com.example.quizzen.book.BookRepository;
//import com.example.quizzen.database.BookDao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadFragment extends Fragment {

    private static final int PICKFILE_REQUEST_CODE = 100;
    private static final String TAG = "UploadFragment";

    private EditText mEditTextFileName;
    private Button mButtonChooseFile;
    private Button mButtonUploadFile;

    private BookRepository mStorageRef;

    public UploadFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_upload, container, false);
        // Initialize views and perform any necessary setup
        return rootView;
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Get a reference to the Firebase Storage instance
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_upload, container, false);
//
//        mEditTextFileName = view.findViewById(R.id.txt_selected_file);
//        mButtonChooseFile = view.findViewById(R.id.btn_select_file);
//        mButtonUploadFile = view.findViewById(R.id.btn_upload);
//
//        mButtonChooseFile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Open the file chooser dialog
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                startActivityForResult(Intent.createChooser(intent, "Select a file"), PICKFILE_REQUEST_CODE);
//            }
//        });
//
//        mButtonUploadFile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Upload the selected file to Firebase Storage
//                uploadFile();
//            }
//        });
//
//        return view;
//    }
//
//    private void uploadFile() {
//        // Get the selected file's URI
//        Uri fileUri = ((MainActivity) getActivity()).getSelectedFileUri();
//
//        if (fileUri != null) {
//            // Get the file name from the edit text field
//            String fileName = mEditTextFileName.getText().toString().trim();
//
//            if (TextUtils.isEmpty(fileName)) {
//                // If the file name is empty, use the original file name
//                fileName = fileUri.getLastPathSegment();
//            }
//
//            // Create a reference to the file in Firebase Storage
//            StorageReference fileRef = mStorageRef.child("uploads/" + fileName);
//
//            // Upload the file to Firebase Storage
//            fileRef.putFile(fileUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            // The file was successfully uploaded
//                            Toast.makeText(getContext(), "File uploaded", Toast.LENGTH_SHORT).show();
//
//                            // Add the file to the Firebase Realtime Database
//                            addFileToDatabase(fileName, fileRef);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // The upload failed
//                            Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            Log.e(TAG, "Upload failed", e);
//                        }
//                    });
//        } else {
//            // No file was selected
//            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void addFileToDatabase(String fileName, StorageReference fileRef) {
//        // Get a reference to the Firebase Realtime Database instance
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference uploadsRef = database.getReference("uploads");
//
//        // Create a new upload object with the file name and download URL
//        Upload upload = new Upload(fileName, fileRef.getDownloadUrl().toString());
//
//        // Add the upload object to the Firebase Realtime Database
//        uploadsRef.push().setValue(upload);
//    }
}
