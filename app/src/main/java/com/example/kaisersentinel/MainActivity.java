package com.example.kaisersentinel;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.kaisersentinel.ui.home.HomeFragment;
import com.example.kaisersentinel.ui.home.Home_Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    SentinalDatabase myDb;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference _firedbreference = database.getReference();
    String image1,time1,event1,state1,image,time,event;
    ArrayList<byte[]> image_ = new ArrayList<>();
    ImageView imageView;
    int lastTime;
    byte imageInByte[];
    private SimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String, Object>> transferRecordMaps;


    private String[] android_image_urls = null;

    int temp=0;

    String mac;
    int unixtime;
    private AmazonS3Client s3;
    private Util util;
    private String bucket;
    boolean mboolean = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if (!mboolean) {
            // do the thing for the first time
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putString("last_time", String.valueOf(1));
            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
        }

//imageView=findViewById(R.id.imageView3);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        util = new Util();
        bucket = new AWSConfiguration(this).optJsonObject("S3TransferUtility").optString("Bucket");
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function
                new GetFileListTask().execute();

                ha.postDelayed(this, 120000);// 2 minutes
            }
        }, 120000);



        TinyDB tinyDB = new TinyDB(this);
//        tinyDB.putString("last_time", String.valueOf(1));

        mac = tinyDB.getString("IdSystem");

        System.out.println("mac address="+mac);

        final String value[]=new String[64];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        // System.out.println("hello");
        myDb = new SentinalDatabase(this);

        final SQLiteDatabase db = myDb.getWritableDatabase();


//        _firedbreference.child("sentinal").addValueEventListener(new ValueEventListener()
//        {
//            private String arrival;
//            private String departure;
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                /*String newvalue=dataSnapshot.getValue("commandaidoresult");
//                 */
//                String flag = dataSnapshot.child("flag").getValue(String.class);
//                String imagestring = dataSnapshot.child("image").getValue(String.class);
//
//                String time = dataSnapshot.child("timestamp").getValue(String.class);
//
//
//                if(flag.equals("true"))
//                {
//                    System.out.println("image:" +imagestring);
////                    String value = Arrays.toString(image.split(""));
////
////                    System.out.println("value[]="+value);
//
//                    Arrays.fill(value,null);
//
//                    int l=imagestring.length();
//                    int k=0;
//                    for (int i=0; i< l; i++)
//                    {
//                       // System.out.println(image.charAt(i));
//                        if((imagestring.charAt(i))=='1')
//                        {
//                            value[k]= String.valueOf(i+1);
//                            k++;
//                        }
//                    }
//                    System.out.println("value[]="+Arrays.toString(value));
//
//                    temp=0;
//
//                    if(temp==0)
//                    {
//
//                        System.out.println("started");
//
//                        for(int j=0;j<value.length;j++)
//                        {
//                            int p = j+1;
//
//                            if(!(value[j]==null))
//                            {
//
//
//                                android_image_urls[p] = "http://ec2-100-26-43-244.compute-1.amazonaws.com/"+mac+"/"+value[j]+"/image.jpg";
//                                System.out.println("URL"+android_image_urls[p]);
//
//                                //where we want to download it from
//                                URL url = null;  //http://example.com/image.jpg
//                                try {
//                                    url = new URL(android_image_urls[p]);
//                                } catch (MalformedURLException e) {
//                                    e.printStackTrace();
//                                }
//                                //open the connection
//                                URLConnection ucon = null;
//                                try {
//                                    ucon = url.openConnection();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                //buffer the download
//                                InputStream is = null;
//                                try {
//                                    is = ucon.getInputStream();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                BufferedInputStream bis = new BufferedInputStream(is,128);
//                             //   ByteArrayBuffer baf = new ByteArrayBuffer(128);
//
//                                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//                                //get the bytes one by one
////                                int current = 0;
////                                while (true) {
////                                    try {
////                                        if (!((current = bis.read()) != -1)) break;
////                                    } catch (IOException e) {
////                                        e.printStackTrace();
////                                    }
////                                    baf.append((byte) current);
////                                }
//                                byte[] data = new byte[50];
//                                int current = 0;
//
////                                while(true)
////                                {
////                                    try {
////                                        if (!((current = bis.read(data,0,data.length)) != -1))
////                                            break;
////                                    } catch (IOException e) {
////                                        e.printStackTrace();
////                                    }
////                                    buffer.write(data,0,current);
////                                }
//
//                                try {
//
//                                    while((current = bis.read(data,0,data.length)) != -1)
//                                    {
//                                        buffer.write(data,0,current);
//
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                long dv = Long.valueOf(time)*1000;// its need to be in milisecond
//                                Date df = new java.util.Date(dv);
//                                String vv = new SimpleDateFormat("MM/dd/yyyy,hh:mma", Locale.US).format(df);
//
//                                System.out.println("date="+df);
//                                System.out.println("time="+vv);
//                                //store the data as a ByteArray
//                                //db is a SQLiteDatabase object
//                                ContentValues dataToInsert = new ContentValues();
//
//                                dataToInsert.put("channel","C"+value[j]);
//                                dataToInsert.put("image",buffer.toByteArray());
//                                dataToInsert.put("unixtime",time);
//                                dataToInsert.put("timestamp",vv);
//                                dataToInsert.put("hub_mac",mac);
//                                dataToInsert.put("alert_type","PERSON");
//
//                                dataToInsert.put("description","PERSON");
//
//                                dataToInsert.put("marked","NO");
//
//                                dataToInsert.put("processed","NO");
//
//                                long rowInserted =db.insert("homesentinal", null, dataToInsert);
//
//                                if(rowInserted != -1) {
//
//                                    Toast.makeText(getApplicationContext(), "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
//                                    System.out.println("new record is added"+rowInserted);
//                                }
//                                else
//                                {
//                                    Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
//
//                                }
//
////////////////And this is how you get the data back and convert it into a Bitmap:
//
//                                //select the data
//                                Cursor cursor = db.query("ec2image", new String[] {"channel","image"},
//                                        null, null, null, null, null);
//                               System.out.println("after the query");
//                                //get it as a ByteArray
////
////                                String no = cursor.getString(0);
////                              //  byte[] imageByteArray=cursor.getBlob(1);
////                                //the cursor is not needed anymore
////
////                                System.out.println("1 st column="+no);
//                                cursor.close();
//
//                                //convert it back to an image
//                                ByteArrayInputStream imageStream = new ByteArrayInputStream(buffer.toByteArray());
//                                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
//
//
//                            }
//                        }
//                        _firedbreference.child("sentinal").child("flag").setValue("false");
//
//
//
//
//                    }
//
//
//
//
//
//                }else{
//                    System.out.println("insertion is not complete");
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });
        android_image_urls = new String[value.length];

        int length;
        int k=0;



//        boolean isInserted= false;
//        isInserted = myDb.insertData();
//        if (isInserted) {
//            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
//
//        }
//


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notification, R.id.nav_report,
                R.id.nav_marked, R.id.nav_configure, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        initData();
        initUI();
        new GetFileListTask().execute();
    }

    private void initData() {
        // Gets the default S3 client.
        s3 = util.getS3Client(MainActivity.this);
        transferRecordMaps = new ArrayList<HashMap<String, Object>>();
        System.out.println("s3"+s3);
    }
    private void initUI() {
        simpleAdapter = new SimpleAdapter(this, transferRecordMaps,
                R.layout.bucket_item, new String[] {
                "key"
        },
                new int[] {
                        R.id.key
                });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                switch (view.getId()) {
                    case R.id.key:
                        TextView fileName = (TextView) view;
                        fileName.setText((String) data);
                        return true;
                }
                return false;

            }
        });


    }

    private class GetFileListTask extends AsyncTask<Void, Void, Void> {
        // The list of objects we find in the S3 bucket
        private List<S3ObjectSummary> s3ObjList;
        // A dialog to let the user know we are retrieving the files
        private ProgressDialog dialog;


        @Override
        protected Void doInBackground(Void... inputs) {
            // Queries files in the bucket from S3.

            s3ObjList = s3.listObjects(bucket).getObjectSummaries();

            transferRecordMaps.clear();
            for (S3ObjectSummary summary : s3ObjList) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("key", summary.getKey());
                transferRecordMaps.add(map);
            }

            ObjectListing listing = s3.listObjects(bucket);
            List<S3ObjectSummary> summaries = listing.getObjectSummaries();

            while (listing.isTruncated()) {
                listing = s3.listNextBatchOfObjects(listing);
                summaries.addAll(listing.getObjectSummaries());
            }


            for (S3ObjectSummary objectSummary : summaries) {
                String key = objectSummary.getKey();
                String delimiter = "\\.";
                String[] t = key.split(delimiter);
                String[] tt = t[0].split("_");
                String channel = tt[0];
                 unixtime = Integer.parseInt(tt[1]);
                TinyDB tinyDB = new TinyDB(MainActivity.this);
//                lastTime = tinyDB.putString("last_time",1);
                lastTime = Integer.parseInt(tinyDB.getString("last_time"));
                System.out.println("last time"+lastTime);

                System.out.println("channel  = " + channel);
                System.out.println("time  = " + unixtime);
//                System.out.println("ttt = "+ Arrays.toString(t));

                //download the file with object key = key
                if (unixtime > lastTime) {
                    System.out.println("\n" + key);
                    String imagePath = "https://s3.us-east-1.amazonaws.com/sentinel1111/" + key;
                    System.out.println(imagePath);

//              String imagePath = "https://s3.us-east-1.amazonaws.com/sentinel1111/Image_6.jpeg";

                    System.out.println("image path" + imagePath);
                    URL url = null;
                    try {
                        url = new URL(imagePath);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    //open the connection
                    URLConnection ucon = null;
                    try {
                        ucon = url.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //buffer the download
                    InputStream is = null;
                    try {
                        is = ucon.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    BufferedInputStream bis = new BufferedInputStream(is, 128);
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                    byte[] data = new byte[50];
                    int current = 0;
                    try {

                                    while((current = bis.read(data,0,data.length)) != -1)
                                    {
                                        buffer.write(data,0,current);

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                    System.out.println("image buffer" + buffer.toByteArray());

                    tinyDB.putString("last_time", String.valueOf(unixtime));
                    long dv = Long.valueOf(unixtime)*1000;// its need to be in milisecond
                    Date df = new java.util.Date(dv);
                    String vv = new SimpleDateFormat("MM/dd/yyyy,hh:mma", Locale.US).format(df);
                    String[] dat = vv.split("/");
                    String[] tim  = vv.split(",");
//                    String[] timm = tim[1].split("P | A");
                    String[] timm = tim[1].split(":");
                    String vvv = dat[0]+"/"+dat[1]+"-"+timm[0];

                    System.out.println("date="+df);
                    System.out.println("time="+unixtime);
                    System.out.println("date and time" + vvv);
                    mac = tinyDB.getString("IdSystem");
                    //store the data as a ByteArray
                    //db is a SQLiteDatabase object
                    System.out.println("mac"+mac);
                    ContentValues dataToInsert = new ContentValues();

                    dataToInsert.put("channel","C"+ channel);
                    dataToInsert.put("image",buffer.toByteArray());
                    dataToInsert.put("unixtime",unixtime);
                    dataToInsert.put("timestamp",vvv);
                    dataToInsert.put("hub_mac",mac);
                    dataToInsert.put("alert_type","PERSON");

                    dataToInsert.put("description","PERSON");

                    dataToInsert.put("marked","NO");

                    dataToInsert.put("processed","NO");
                    final SQLiteDatabase db = myDb.getWritableDatabase();
                    long rowInserted =db.insert("homesentinal", null, dataToInsert);
                    if(rowInserted != -1) {
////
//                                    Toast.makeText(getApplicationContext(), "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                                    System.out.println("new record is added"+rowInserted);
                                }
                                else
                                {
                                    System.out.println("not inserted");
//                                    Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
//
                                }


//                    ContentValues dataToInsert = new ContentValues();
//                    dataToInsert.put("id_no", id);
//                    id++;
//                    dataToInsert.put("image", buffer.toByteArray());
//                    long rowInserted = db.insert("s3table", null, dataToInsert);

//                    if (rowInserted != -1) {
//
//                        Toast.makeText(getApplicationContext(), "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
//                        System.out.println("new record is added" + rowInserted);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }




                }

            }

//            TransferObserver observer = transferUtility.download("Image_10.jpeg", file);


                return null;
            }


        @Override
        protected void onPostExecute(Void result) {
//            dialog.dismiss();
//            simpleAdapter.notifyDataSetChanged();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the file list.
//                new GetFileListTask().execute();
    }


//    public void basicReadWrite() {
//        _firedbreference.child("Sentinal").child("event").setValue("got error ");
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
////        System.out.println();
////        switch (item.getItemId()){
////            case R.id.refresh:
////                Fragment frg = getSupportFragmentManager().findFragmentByTag("homefragment");
////                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////                ft.detach(frg);
////                ft.attach(frg); ft.commit();
////                Home_Fragment frag_name;
////                frag_name = new frag_name();
////                FragmentManager manager = this.getSupportFragmentManager();
////                manager.beginTransaction().replace(R.id.nav_home, frag_name, frag_name.getTag()).commit();
////                getSupportFragmentManager() .beginTransaction() .detach() .commitNowAllowingStateLoss();
////                getSupportFragmentManager() .beginTransaction() .attach(oldFragment) .commitAllowingStateLoss();
//
//
//
//
//
////                DrawerLayout drawer = findViewById(R.id.drawer_layout);
////                System.out.println("drawer"+drawer);
////                NavigationView navigationView = findViewById(R.id.nav_view);
////                mAppBarConfiguration = new AppBarConfiguration.Builder(
////                        R.id.nav_home, R.id.nav_notification, R.id.nav_report,
////                        R.id.nav_marked, R.id.nav_configure, R.id.nav_logout)
////                        .setDrawerLayout(drawer)
////                        .build();
////                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
////                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
////                NavigationUI.setupWithNavController(navigationView, navController);
//
////                Fragment frg = null;
////                frg = getSupportFragmentManager().findFragmentByTag("Home_Fragment");
////                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////                ft.detach(frg);
////                ft.attach(frg);
////                ft.commit();
////                Fragment f = new Home_Fragment();
////                Activity c = new MainActivity();
//
//
//
////                System.out.println("f"+f);
//                System.out.println("refresh button pressed");
//                System.out.println(item.getTitle());
//                return true;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}


