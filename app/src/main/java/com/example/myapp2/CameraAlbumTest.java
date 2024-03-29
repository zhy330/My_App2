package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class CameraAlbumTest extends AppCompatActivity {



    public static final int TAKE_PHOTO = 1;



    public static final int CHOOSE_PHOTO = 2;



    private ImageView picture;



    private Uri imageUri;

    private Context context;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera_album_test);

        Button takePhoto = (Button) findViewById(R.id.take_photo);

        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);

        Button btn_show = (Button) findViewById(R.id.btn_show);

        picture = (ImageView) findViewById(R.id.picture);

        takePhoto.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // 创建File对象，用于存储拍照后的图片

                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

                try {

                    if (outputImage.exists()) {

                        outputImage.delete();

                    }

                    outputImage.createNewFile();

                } catch (IOException e) {

                    e.printStackTrace();

                }

                if (Build.VERSION.SDK_INT < 24) {

                    imageUri = Uri.fromFile(outputImage);

                } else {

                    imageUri = FileProvider.getUriForFile(CameraAlbumTest.this, "com.example.myapplication.FileProvider", outputImage);

                }

                // 启动相机程序

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(intent, TAKE_PHOTO);

            }

        });

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(CameraAlbumTest.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(CameraAlbumTest.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);

                } else {

                    openAlbum();

                }

            }

        });

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseUpdate();
            }

        });

    }

    private void dataBaseUpdate() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");

        ContentValues values = new ContentValues();
        values.put("photo", bitmapToBytes(context);
        database.update("user", values, "user_id=?", new String[]{user_id});
        Toast.makeText(CameraAlbumTest.this, "修改成功", Toast.LENGTH_LONG).show();
        database.close();


    }

   public byte[] bitmapToBytes(Context context){
        /* 将图片转化为位图 */
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();
            return imagedata;
        }catch (Exception e){
        }finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }




    private void openAlbum() {

        Intent intent = new Intent("android.intent.action.GET_CONTENT");

        intent.setType("image/*");

        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册

    }



    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case 1:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openAlbum();

                } else {

                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();

                }

                break;

            default:

        }

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case TAKE_PHOTO:

                if (resultCode == RESULT_OK) {

                    try {

                        // 将拍摄的照片显示出来

                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                        picture.setImageBitmap(bitmap);

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                }

                break;

            case CHOOSE_PHOTO:

                if (resultCode == RESULT_OK) {

                    // 判断手机系统版本号

                    if (Build.VERSION.SDK_INT >= 19) {

                        // 4.4及以上系统使用这个方法处理图片

                        handleImageOnKitKat(data);

                    } else {

                        // 4.4以下系统使用这个方法处理图片

                        handleImageBeforeKitKat(data);

                    }

                }

                break;

            default:

                break;

        }

    }



    @TargetApi(19)

    private void handleImageOnKitKat(Intent data) {

        String imagePath = null;

        Uri uri = data.getData();

        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);

        if (DocumentsContract.isDocumentUri(this, uri)) {

            // 如果是document类型的Uri，则通过document id处理

            String docId = DocumentsContract.getDocumentId(uri);

            if("com.android.providers.media.documents".equals(uri.getAuthority())) {

                String id = docId.split(":")[1]; // 解析出数字格式的id

                String selection = MediaStore.Images.Media._ID + "=" + id;

                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {

                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));

                imagePath = getImagePath(contentUri, null);

            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // 如果是content类型的Uri，则使用普通方式处理

            imagePath = getImagePath(uri, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {

            // 如果是file类型的Uri，直接获取图片路径即可

            imagePath = uri.getPath();

        }

        displayImage(imagePath); // 根据图片路径显示图片

    }



    private void handleImageBeforeKitKat(Intent data) {

        Uri uri = data.getData();

        String imagePath = getImagePath(uri, null);

        displayImage(imagePath);

    }



    private String getImagePath(Uri uri, String selection) {

        String path = null;

        // 通过Uri和selection来获取真实的图片路径

        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }

            cursor.close();

        }

        return path;

    }



    private void displayImage(String imagePath) {

        if (imagePath != null) {

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            picture.setImageBitmap(bitmap);

        } else {

            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();

        }

    }

}