package com.minhkhoa.taskmanagement.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.User;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://taskmanagement-b965a.appspot.com/");

    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();

    Button btnLogin, btnPickImage;
    EditText edtUsername;
    CircleImageView imgAvata;
    private int REQUEST_CODE_FOLDER = 1;
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    String url = "https://firebasestorage.googleapis.com/v0/b/taskmanagement-b965a.appspot.com/o/hot-girl-3.jpg?alt=media&token=7beb21ab-9b10-4750-9108-eac61d85e47a";

    String user_id;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        //set trans stt
        setStatusBarTranslucent(true);

        addControls();
        //set imamge
//        Picasso.get().load(url).into(imgAvata);
        //set events
        btnPickImage.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        //set animation
        relativeLayout = findViewById(R.id.relative_layout);
        animationDrawable =(AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);

        user_id = user_firebase.getUid();

        getData();

    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundleuser");
        user_email = bundle.getString("user_email");
    }

    private void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void addControls() {
        btnLogin = findViewById(R.id.buttonloginmain);
        edtUsername = findViewById(R.id.edittextname);
        imgAvata = findViewById(R.id.imgavatainfo);
        btnPickImage = findViewById(R.id.buttonchonanh);
    }

    @Override
    public void onClick(View v) {
        if(v == btnPickImage){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_FOLDER);
        }
        if(v == btnLogin){
            pushUserToFirebaseAndLogin();
        }
    }

    @Override
    protected void onResume() {
        animationDrawable.start();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvata.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void pushUserToFirebaseAndLogin(){
        final User user = new User();
        user.setUserID(user_id);
        user.setUserEmail(user_email);
        user.setUserName(edtUsername.getText().toString());
        user.setUserPermission(0);

        Calendar calendar = Calendar.getInstance();
        StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

        imgAvata.setDrawingCacheEnabled(true);
        imgAvata.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgAvata.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uri = taskSnapshot.getDownloadUrl();
                user.setUserAvata(String.valueOf(uri));
            }
        });

        Log.d("AAA",String.valueOf(user.getUserAvata()));

        databaseReference.child("User").child(user.getUserID()).setValue(user);

        startActivity(new Intent(UpdateInfoActivity.this,MainActivity.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInfoActivity.this);
        builder.setTitle(R.string.create_board);
        builder.setMessage(R.string.sure_exit);

        builder.setPositiveButton(R.string.agree, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finish();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
}
