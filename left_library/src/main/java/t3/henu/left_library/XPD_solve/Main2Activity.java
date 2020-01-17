package t3.henu.left_library.XPD_solve;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import t3.henu.left_library.GYB_solve.MainActivity;
import t3.henu.left_library.R;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int BEIJING = 3;
    Bitmap bitmap, bitmap1;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;
    private TextView man;
    private TextView woman;
    private TextView sex;
    private EditText name;
    private LinearLayout beijing;
    private ImageView picture_bj;
    private Button baocun;
    private EditText qianming;
    private LinearLayout zhujiemian;
    private de.hdodenhof.circleimageview.CircleImageView icon_image;
    private ImageView picture;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sex = (TextView) findViewById(R.id.sex);
        name = (EditText) findViewById(R.id.name);
        picture = (ImageView) findViewById(R.id.picture);
        picture_bj = (ImageView) findViewById(R.id.picture_bj);
        beijing = (LinearLayout) findViewById(R.id.beijing);
        beijing.setOnClickListener(this);
        zhujiemian = (LinearLayout) findViewById(R.id.zhujiemian);
        baocun = (Button) findViewById(R.id.baocun);
        baocun.setOnClickListener(this);
        icon_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.icon_image);
        qianming = (EditText) findViewById(R.id.qianming);
    }

    public void show(View view) {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog1, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    public void show_sex(View view) {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog2, null);
        //初始化控件
        woman = (TextView) inflate.findViewById(R.id.woman);
        man = (TextView) inflate.findViewById(R.id.man);
        woman.setOnClickListener(this);
        man.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.takePhoto) {
            Toast.makeText(this, "点击了拍照", Toast.LENGTH_SHORT).show();
            //创建File对象，用于存储拍照后的照片
            File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT > 24) {
                imageUri = FileProvider.getUriForFile(Main2Activity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            //启动相机程序
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);
            dialog.dismiss();

        } else if (i == R.id.choosePhoto) {
            Toast.makeText(this, "点击了从相册选择", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(Main2Activity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Main2Activity.this, new
                        String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                openAlbum();
            }
            dialog.dismiss();

        } else if (i == R.id.man) {
            sex.setText("男");
            dialog.dismiss();

        } else if (i == R.id.woman) {
            sex.setText("女");
            dialog.dismiss();

        } else if (i == R.id.beijing) {
            if (ContextCompat.checkSelfPermission(Main2Activity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Main2Activity.this, new
                        String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            } else {
                openAlbum1();
            }


        } else if (i == R.id.baocun) {
            try {
                Bitmap bmp = bitmap;
                Intent intent2 = new Intent(Main2Activity.this, MainActivity_XPD.class);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bitmapByte = baos.toByteArray();
                intent2.putExtra("bitmap", bitmapByte);

                intent2.putExtra("name", name.getText().toString());

                Bitmap bmp1 = bitmap1;
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                bmp1.compress(Bitmap.CompressFormat.PNG, 100, baos1);
                byte[] bitmapByte1 = baos1.toByteArray();
                intent2.putExtra("bitmap1", bitmapByte1);
                startActivity(intent2);
                finish();
                Intent intent1 = new Intent(Main2Activity.this, Guanyuewo.class);
                intent1.putExtra("qianming", qianming.getText().toString());
                intent1.putExtra("xingbie", sex.getText().toString());
                startActivity(intent1);

            } catch (Exception e) {
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //将拍摄的照片显示出来
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                        //把bitmap传输到主界面中

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4以上的系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下的系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case BEIJING:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4以上的系统使用这个方法处理图片
                        handleImageOnKitKat1(data);
                    } else {
                        //4.4以下的系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }

    private void openAlbum1() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, BEIJING);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];  //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);

            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); //根据图片路径显示图片
    }

    private void handleImageOnKitKat1(Intent data) {
        String imagePath1 = null;
        Uri uri1 = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri1)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri1);
            if ("com.android.providers.media.documents".equals(uri1.getAuthority())) {
                String id = docId.split(":")[1];  //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath1 = getImagePath1(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri1.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath1 = getImagePath1(contentUri, null);

            }
        } else if ("content".equalsIgnoreCase(uri1.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath1 = getImagePath1(uri1, null);
        } else if ("file".equalsIgnoreCase(uri1.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath1 = uri1.getPath();
        }
        displayImage1(imagePath1); //根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        Uri uri1 = data.getData();
        String imagePath = getImagePath(uri, null);
        String imagePath1 = getImagePath1(uri1, null);
        displayImage(imagePath);

        displayImage1(imagePath1);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private String getImagePath1(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
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
            // 设置参数
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
            BitmapFactory.decodeFile(imagePath, options);
            int height = options.outHeight;
            int width = options.outWidth;
            int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
            int minLen = Math.min(height, width); // 原图的最小边长
            if (minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
                float ratio = (float) minLen / 100.0f; // 计算像素压缩比例
                inSampleSize = (int) ratio;
            }
            options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
            options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
            bitmap = BitmapFactory.decodeFile(imagePath, options); // 解码文件
            Log.w("TAG", "size: " + bitmap.getByteCount() + " width: " + bitmap.getWidth() + " heigth:" + bitmap.getHeight()); // 输出图像数据
            picture.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //以上压缩图片
            //bitmap=BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayImage1(String imagePath1) {
        if (imagePath1 != null) {
//            // 设置参数
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
            BitmapFactory.decodeFile(imagePath1, options);
            int height = options.outHeight;
            int width = options.outWidth;
            int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
            int minLen = Math.min(height, width); // 原图的最小边长
            if (minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
                float ratio = (float) minLen / 100.0f; // 计算像素压缩比例
                inSampleSize = (int) ratio;
            }
            options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
            options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
            bitmap1 = BitmapFactory.decodeFile(imagePath1, options); // 解码文件
            Log.w("TAG", "size: " + bitmap1.getByteCount() + " width: " + bitmap1.getWidth() + " heigth:" + bitmap1.getHeight()); // 输出图像数据
            picture_bj.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //以上压缩图片
            // bitmap1=BitmapFactory.decodeFile(imagePath1);
            picture_bj.setImageBitmap(bitmap1);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}



