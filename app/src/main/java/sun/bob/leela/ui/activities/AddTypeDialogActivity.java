package sun.bob.leela.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;

import sun.bob.leela.R;
import sun.bob.leela.adapters.CategorySpinnerAdapter;
import sun.bob.leela.utils.AppConstants;

public class AddTypeDialogActivity extends AppCompatActivity {

    private AppCompatButton buttonOK, buttonCancel;
    private AppCompatSpinner categorySpinner;
    private AppCompatImageView imageView;

    private static final int REQUEST_CODE_IMAGE     = 0x30;
    private static final int REQUEST_CODE_CROP      = 0x31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type_dialog);
        categorySpinner = (AppCompatSpinner) findViewById(R.id.spinner_category);
        categorySpinner.setAdapter(new CategorySpinnerAdapter(this, 0));

        buttonOK = (AppCompatButton) findViewById(R.id.button_ok);
        ViewCompat.setElevation(buttonOK, 10);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setResult();
                finish();
            }
        });

        buttonCancel = (AppCompatButton) findViewById(R.id.button_cancel);
        ViewCompat.setElevation(buttonCancel, 10);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.support.v7.app.AlertDialog.Builder(AddTypeDialogActivity.this)
                        .setCancelable(false)
                        .setTitle("Cancel?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

        imageView = (AppCompatImageView) findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, REQUEST_CODE_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_IMAGE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                Uri imageUri = data.getData();
                Intent intent = new Intent(AddTypeDialogActivity.this,
                        ImageCropActivity.class);
                intent.setData(imageUri);
                startActivityForResult(intent, 1);
                break;
            case REQUEST_CODE_CROP:
                if (resultCode != RESULT_OK) {
                    return;
                }
                break;
            case AppConstants.REQUEST_CODE_ADD_CATE:
                break;
            default:
                break;
        }
    }
}