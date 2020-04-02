package com.example.kaisersentinel.ui.notification;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaisersentinel.R;
import com.example.kaisersentinel.SentinalDatabase;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notification_Fragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    int count=0;
    private TableRow tableRow;
    String dirpath;

    private String[] column;

    private int COLUMN_SIZE;
    private int ROW_SIZE;
    private Context context;
    private String[] row;
    private TextView textView;
    private ImageView checkBox;
    private String[] data;
//    private ArrayList<String> array = new ArrayList<>();


//       private SentinalDatabase sentinalDatabase;

//    private DatabaseHandler databaseHandler;

    private RelativeLayout relativeLayout;
    private HorizontalScrollView hsv;
    private Context mContext;
    private byte[] bb;
    int h =0;
    //    private List<Reminder_Data> contacts;
    ArrayList<String> dataa = new ArrayList<>();
    ArrayList<byte[]> image = new ArrayList<>();
    private SentinalDatabase sentinalDatabase;
    private int m=0;
    String[] status;
    int i;

    String arrStr[];

    File exportDir;

    PdfPCell imageCell;

    PdfPCell[] cells1;

    ArrayList<String> tabdata = new ArrayList<String>();

    int noofrows;


    int id[];

    ImageView imageView;

    public int NUM_ITEMS_PAGE   = 10;

    private int d=0;

    private ScrollView scroll;
    private HorizontalScrollView horiscroll;

    TableLayout tableLayout ;

    Button previous;
    Button next;

    public Notification_Fragment() {
        // Required empty public constructor
    }
    Button b1,b2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification_, container, false);
        context=getContext();
//        databaseHandler = new DatabaseHandler(this);
//        contacts = databaseHandler.getAllContacts();
        sentinalDatabase=new SentinalDatabase(context);
        b1=(Button)view.findViewById(R.id.configure);
        b2=(Button)view.findViewById(R.id.mark);


//
//        for(Reminder_Data r : contacts){
//            bb = r.get_image1();
//            Log.e(TAG, "onCreate: "+r.get_id()+".."+r.get_type()+".."+r.get_content1()+".."+r.get_content2()+".."+".."+bb+".."+r.get_state()+".."+r.get_frequency()+".."+r.getAlram_id() );
//            data = new String[]{String.valueOf(r.get_id()), r.get_type(), r.get_content1(), r.get_content2(), r.get_state(), String.valueOf(r.get_frequency()), r.getAlram_id()};
//
//            dataa.add(String.valueOf(Arrays.asList(data)));
//            image.add(bb);
//
//        }
//
//        for (int ar=0;ar<dataa.size();ar++) {
//            Log.e(TAG, "onCreate: size" + dataa.get(ar));
//        }
//        int si = databaseHandler.getProfilesCount();
//        int dd = contacts.size();
//

        final SQLiteDatabase my = sentinalDatabase.getReadableDatabase();
        String selectQuery = "SELECT  id_no,unixtime,channel,alert_type,description,image FROM homesentinal where processed='NO'";
        Cursor cursor = my.rawQuery(selectQuery, null);

        noofrows=cursor.getCount();

        status = new String[noofrows];

        for (i=0;i<noofrows;i++)
        {
            status[i]="0";
        }

        while(cursor.moveToNext()) {
            data = new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)};
            dataa.add(String.valueOf(Arrays.asList(data)));
            image.add(cursor.getBlob(5));

            id= new int[]{cursor.getInt(0)};

            Log.d(TAG, "onCreateView: "+dataa.get(m));
            m=m+1;

            arrStr=new String[] {
                    cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), String.valueOf(cursor.getBlob(4)),
            };

            tabdata.add(String.valueOf(Arrays.asList(arrStr)));

        }

//         row = new String[10];//{"ROW1","ROW2"};


        final int dd =noofrows ;

        int val = dd%NUM_ITEMS_PAGE;
        val = val==0?0:1;
        final int noOfBtns=dd/NUM_ITEMS_PAGE+val;

        Log.d(TAG, "pagination: "+noOfBtns);

        column = new String[]{"ID", "TIME", "CAMERA", "DESCRIPTION","IMAGE"};
//     data = new String[]{"1","2","3"};
        int rl= dataa.size();
        final int cl= column.length;

        Log.d("--", "R-Lenght--"+ rl +"   "+"C-Lenght--"+cl);
//        Log.d("-1 ", "R-Lenght--"+row.length+"   "+"C-Lenght--"+column.length);

        Log.d(TAG, "pagination: "+noOfBtns);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relati);
        scroll = (ScrollView) view.findViewById(R.id.scroll1);
        horiscroll = (HorizontalScrollView) view.findViewById(R.id.horscro1);

        final ScrollView sv = new ScrollView(getContext());
        mContext = getContext();
        sv.setId(R.id.scroll);


        tableLayout = createTableLayout(column, dd, cl,0);
        tableLayout.setId(R.id.lay);
        //hsv = new HorizontalScrollView(this);
        // hsv.setId(R.id.horscroll);
        horiscroll.addView(tableLayout);
        // parent.addView(sv);
        //setContentView(relativeLayout);
        Button Pdf1 = (Button) view.findViewById(R.id.Pdf);
        Button xls1 = (Button) view.findViewById(R.id.xls);

        Pdf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

//              ;

                Document document = new Document();
                PdfPTable table = new PdfPTable(new float[] { 2, 2, 2,2,6 });
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.setTotalWidth(10000);
//                try {
//                    table.setWidths(new int[]{200,50});
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                }
                table.addCell("ID");
                table.addCell("TIME");
                table.addCell("CAMERA");

                table.addCell("DESCRIPTION");
                table.addCell("IMAGE");

                table.setHeaderRows(1);
                PdfPCell[] cells = table.getRow(0).getCells();
                for (int j=0;j<cells.length;j++)
                {
                    cells[j].setBackgroundColor(BaseColor.GRAY);

                }



                String selectQuery = "SELECT  id_no,unixtime,channel,alert_type,description,image FROM homesentinal where processed='NO'";
                Cursor cursor1 = my.rawQuery(selectQuery, null);


                while(cursor1.moveToNext())
                {
                    table.addCell(cursor1.getString(0));
                    table.addCell(cursor1.getString(1));
                    table.addCell(cursor1.getString(2));

                    table.addCell(cursor1.getString(4));
                    //Image img3 = new Image(ImageDataFactory.create(cursor1.getBlob(4)));

                    //  byte[] photo=cursor1.getBlob(4);




                    try {



                        imageCell = new PdfPCell(Image.getInstance(cursor1.getBlob(5)));

                        imageCell.setFixedHeight(72f);

                    } catch (BadElementException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                    imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                    table.addCell(imageCell);



                }
//                for(int k=0;k<=50;k++)
//                {
//                    cells1[k] = table.getRow(k).getCells();
//
//                    cells1[k].setPadding(3);
//
//                }


                try {
                    String state = Environment.getExternalStorageState();
                    //check if the external directory is availabe for writing
                    if (!Environment.MEDIA_MOUNTED.equals(state)) {
                        return;
                    }
                    else {
                        exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    }

                    //if the external storage directory does not exists, we create it
                    if (!exportDir.exists()) {
                        exportDir.mkdirs();
                    }
                    File file;
                    file = new File(exportDir, "notification.pdf");

                    PdfWriter.getInstance(document, new FileOutputStream(file));
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                document.open();
                try {
                    document.add(table);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                document.close();
                System.out.println("Done");
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            }
        });


        xls1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root,"notification.csv");

                    FileWriter writer = new FileWriter(gpxfile);

                    for(int k=0;k<noofrows;k++)
                    {
                        writer.append(tabdata.get(k));

                        writer.append("\n");

                    }

                    writer.flush();
                    writer.close();
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int con;
                SQLiteDatabase my = sentinalDatabase.getWritableDatabase();
                sentinalDatabase=new SentinalDatabase(context);
                //   System.out.println("length"+status.length);
                for (i=0;i<status.length-1;i++)
                {
                    System.out.println("value"+status[i]);
                    if(status[i].contains("1"))
                    {
////
                       // con=i;
                        con=i;
                        System.out.println("con value"+con);
                        my.execSQL("UPDATE homesentinal SET processed='YES' WHERE id_no='"+ con+"' ");
////
                    }
                }
                Toast.makeText(getContext(), "pressed", Toast.LENGTH_SHORT).show();
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int con;
                SQLiteDatabase my = sentinalDatabase.getWritableDatabase();
                sentinalDatabase=new SentinalDatabase(context);
                //   System.out.println("length"+status.length);
                for (i=0;i<status.length;i++)
                {
                    //  System.out.println(status[i]);
                    if(status[i].contains("1"))
                    {
//
                        con=i;
                        // System.out.println("con value"+con);
                        my.execSQL("UPDATE homesentinal SET marked='YES' WHERE id_no='"+ con+"' ");
//
                    }
                }
                Toast.makeText(getContext(), "pressed", Toast.LENGTH_SHORT).show();
            }
        });

        previous=(Button)view.findViewById(R.id.previous);
        next=(Button)view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+d);
                if(d<noOfBtns-1) {
                    d++;
                    horiscroll.removeAllViews();
                    count = 0;
                    tableLayout = createTableLayout(column, dd, cl, d);
                    tableLayout.setId(R.id.lay);
                    horiscroll.addView(tableLayout);
                    horiscroll.post(new Runnable() {
                        @Override
                        public void run() {
                            int totalHeight = horiscroll.getChildAt(0).getHeight();
                            int totalWidth = horiscroll.getChildAt(0).getWidth();
                            Log.e(TAG, "run: "+totalHeight+",,,"+totalWidth);

                        }
                    });
                }
                else {
                    Toast.makeText(mContext, "No More Record Found", Toast.LENGTH_SHORT).show();
                }


            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+d);
                if(d <noOfBtns && d>0){
                    d--;
                    horiscroll.removeAllViews();
                    count =0;
                    tableLayout = createTableLayout(column, dd, cl, d);
                    tableLayout.setId(R.id.lay);
                    horiscroll.addView(tableLayout);

                }
                else{
                    Toast.makeText(mContext, "No Previous Record", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return relativeLayout;

        // return view;

    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
    public void makeCellEmpty(TableLayout tableLayout, int rowIndex, int columnIndex) {
        // get row from table with rowIndex
        TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

        // get cell from row with columnIndex
        TextView textView = (TextView)tableRow.getChildAt(columnIndex);

        // make it black
        textView.setBackgroundColor(Color.BLACK);
    }
    public void setHeaderTitle(TableLayout tableLayout, int rowIndex, int columnIndex){

        // get row from table with rowIndex
        TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

        // get cell from row with columnIndex
        TextView textView = (TextView)tableRow.getChildAt(columnIndex);

        textView.setText("Hello");
    }

    private TableLayout createTableLayout(String [] cv,int rowCount, int cc,int number)
    {

        FrameLayout.LayoutParams pTable = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        final TableLayout tableLayout = new TableLayout(getContext());
        tableLayout.setStretchAllColumns(true);
        tableLayout.setLayoutParams(pTable);
        int start = number * NUM_ITEMS_PAGE;
        // System.out.println("colomn count"+cc);


//                    layout1 = new LinearLayout(getContext());
//
//                    mainLayout1 = new LinearLayout(getContext());
//
//                    t1=new TextView(getContext());

        // im=(imageView) imageView.findViewById()

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(150,
                80);
        TableRow.LayoutParams tableRowParams1 = new TableRow.LayoutParams(250, 50);
        tableRowParams1.setMargins(1,1,1,1);
        tableRowParams1.weight=1;
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;



        int add = (start)+NUM_ITEMS_PAGE;
        //  for (int i = 0; i<contacts.size(); i++) {
        for (int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            // 3) create tableRow
            if(i==noofrows)
            {
                break;
            }
            tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            tableRow.setClickable(true);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setGravity(Gravity.CENTER);
            //count=i;
            tableRow.setId(count);
            //System.out.println("count "+count);




            StringBuilder dummy = new StringBuilder(dataa.get(i));
            dummy.deleteCharAt(0);
            String dum = dummy.toString();
            String[] spl = dum.split(",|]");

            //data = new String[]{String.valueOf(conta.get_id()), r.get_type(), r.get_content1(), r.get_content2(), String.valueOf(bb), r.get_state(), String.valueOf(r.get_frequency()), r.getAlram_id()};

//                  textView.setText(String.valueOf(data[i]));
//                  textView.setTextSize(15);



            for (int j= 0; j <5; j++) {

                // 4) create textView
                checkBox = new ImageView(getContext());
                checkBox.setBackgroundColor(Color.WHITE);
                textView = new TextView(getContext());
                //  textView.setText(String.valueOf(j));
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                textView.setId(j);
                String s1 = Integer.toString(i);
                String s2 = Integer.toString(j);
                String s3 = s1 + s2;
                int id = Integer.parseInt(s3);
                Log.d("TAG", "-___>"+id);
//                if (count ==0 && j==0){
//                    textView.setText("CORNER");
//                }
                if(count==0)
                {
                    Log.d("TAAG", "set Column Headers");
                    textView.setText(cv[j]);
                    Log.d(TAG, "createTableLayout: "+cv[j]+" lk"+cc+"jh"+j);
                    textView.setTextSize(20);
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundColor(Color.GRAY);
                    textView.setPadding(5,5,5,5);


                }

                else if(j == cc-1)
                {
                    final byte[] bb = image.get(i);

                    checkBox.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));

                    Bitmap bmp = BitmapFactory.decodeByteArray(bb, 0, bb.length);

                    imageView = new ImageView(getContext());
// Set the Bitmap data to the ImageView
                    imageView.setImageBitmap(bmp);
                    imageView.setBackgroundColor(Color.rgb(255, 255, 255));

//                    checkBox.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_gallery));
                    tableRow.addView(imageView,tableRowParams1);




                    imageView.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {

                            System.out.println("inside of image button");


                            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(getContext());

                            ImageDialog.setTitle("Image");

                            ImageView showImage1 = new ImageView(getContext());

                            Bitmap bmp = BitmapFactory.decodeByteArray(bb, 0, bb.length);


                            showImage1.setImageBitmap(bmp);

                            showImage1.setBackgroundColor(Color.rgb(255, 255, 255));

                            ImageDialog.setView(showImage1);

                            ImageDialog.setNegativeButton("ok", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface arg0, int arg1)
                                {

                                }
                            });
                          //  ImageDialog.show();

                            AlertDialog alertDialog;

                            alertDialog = ImageDialog.create();

                            alertDialog.show();

                            alertDialog.getWindow().setLayout(1200, 820);




                        }
                    });

                }
                else {
                    textView.setText(String.valueOf(spl[j]));
                    //Log.d("TAAG", "Set Row Headers"+spl[j]);
                    textView.setTextSize(15);
                    //Log.d(TAG, "createTableLayoutidfree: "+tableRow.getId());

                    // check id=23

                }

//
                tableRow.addView(textView, tableRowParams);
                tableRow.setOnClickListener(new View.OnClickListener()
                {
                    private Object img;
                    private String text;
                    private int iRowNumber;
                    Boolean isPressed = true;
                    List<Object> strings = new ArrayList<>();
                    TextView data;

                    @Override
                    public void onClick(final View v) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TableRow t = (TableRow) v;
                                int j = t.getId();
                                final int gh = t.getChildCount();
                                Log.d(TAG, "onClick: "+gh);
                                b1.setVisibility(View.VISIBLE);
                                b2.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), String.valueOf(j), Toast.LENGTH_SHORT).show();
                                if (j>0) {
                                    if (isPressed) {
                                        for (int h = 0; h < gh; h++) {
                                            View view = t.getChildAt(h);
                                            if(view instanceof ImageView){
                                                ImageView imageView = (ImageView) view;
                                                BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
                                                Bitmap bitmap = drawable.getBitmap();
                                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                                if(bitmap != null)
                                                {

                                                    bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
                                                    strings.add(bitmap);
                                                    img = bos.toByteArray().toString();
                                                    imageView.setBackgroundColor(Color.LTGRAY);

                                                }
                                            }else if(h !=-1){
                                                TextView firstTextView = (TextView) view;
                                                text = firstTextView.getText().toString();
                                                strings.add(text);
                                                firstTextView.setBackgroundColor(Color.LTGRAY);
                                                status[j]="1";
                                                isPressed = false;
                                            }

                                        }
                                    } else {
                                        for (int h = 0; h < gh; h++) {
                                            View view = t.getChildAt(h);
                                            if(view instanceof ImageView){
                                                ImageView imageView = (ImageView) view;
                                                imageView.setBackgroundColor(Color.WHITE);
                                                //imgg.setImageDrawable(null);
                                            }else if(h!=-1) {
                                                TextView firstTextView = (TextView) t.getChildAt(h);
                                                //text = tvSelectedRow.getText().toString();
                                                firstTextView.setBackgroundColor(Color.WHITE);
                                                status[j]="0";
                                                //  Toast.makeText(TableActivity.this, text, Toast.LENGTH_SHORT).show();
                                                isPressed = true;
                                            }


                                        }

                                    }
                                }
                                for(Object o : strings){
                                    System.out.println(o);
                                }

                            }

                        });
                    }
                });

                tableRow.setId(Integer.parseInt(spl[0]));


            }

            if(count == 0){
                i--;
                //count=start;

            }
            count++;

            tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                    PopupMenu popup = new PopupMenu(getContext(), v);
                    popup.setOnMenuItemClickListener(Notification_Fragment.this);
                    popup.inflate(R.menu.menu_example);
                    popup.show();
                    return true;
                }
            });
            // tvSelectedRow = (TextView)  tableRow.getChildAt(0);


            // 6) add tableRow to tableLayout

            tableLayout.addView(tableRow, tableLayoutParams);

        }


        return tableLayout;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Toast.makeText(getContext(), "Selected Item: " +menuItem.getTitle(), Toast.LENGTH_SHORT).show();
        switch (menuItem.getItemId()) {
            case R.id.ascending:
                // do your code
                return true;
            case R.id.descending:
                // do your code
                return true;

            default:
                return false;
        }
    }

    public void layoutToImage(Bitmap view) {
        // get view group using reference

//        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relati);
//        relativeLayout.setDrawingCacheEnabled(true);
//                Bitmap bmp = Bitmap.createBitmap(relativeLayout.getDrawingCache());
//        Log.e(TAG, "layoutToImage: "+String.valueOf(relativeLayout) +"--"+bmp);
//        final Bitmap bm = relativeLayout.getDrawingCache();
//                Log.e(TAG, "onCreate: '"+bmp+""+bm);
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("image/jpeg");
        final ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        view.compress(Bitmap.CompressFormat.JPEG, 100, bytes1);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image2.jpg");
        try {
            if(!f.createNewFile()) {
                Log.e("mDebug", "Couldn't create the file");
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes1.toByteArray());
            }else{
                Log.e("mDebug", " create the file");
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes1.toByteArray());


            }
            imageToPDF();

            //f.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // convert view group to bitmap


    }
    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/NewPDF.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image2.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Log.e(TAG, "imageToPDF: " );
            Toast.makeText(getContext(), "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    private class Asyb extends AsyncTask<byte[], String, Bitmap> {
        @Override
        protected Bitmap doInBackground(byte[]... bytes) {
            return null;
        }
    }
}
