package com.example.kaisersentinel.ui.configure;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaisersentinel.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Configure_Fragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    int count=0;
    private TableRow tableRow;
    String dirpath;

    private String[] column;


    private String[] row;
    private TextView textView;
    private ImageView checkBox;
    private String[] data;
    //private DatabaseHandler databaseHandler;

    private RelativeLayout relativeLayout;
    private HorizontalScrollView hsv;
    private Context mContext;
    private byte[] bb;
    int h =0;
    //private List<Reminder_Data> contacts;
    ArrayList<String> dataa = new ArrayList<>();
    ArrayList<byte[]> image = new ArrayList<>();


    public Configure_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_, container, false);

//        databaseHandler = new DatabaseHandler(this);
//        contacts = databaseHandler.getAllContacts();
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



        row = new String[]{"ROW1", "ROW2", "Row3", "Row4"};
        column = new String[]{"ID", "Type", "Content1", "Content2",
                "State","Frequncy","Alram_id","Image"};
        data = new String[]{"1","2","3","4","hello","how","45"};
        int cl=column.length;
        int rl= row.length;

        Log.d("--", "R-Lenght--"+ row +"   "+"C-Lenght--"+cl);
        Log.d("-1 ", "R-Lenght--"+row+"   "+"C-Lenght--"+column.length);

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relati);
        final ScrollView sv = new ScrollView(getContext());
        mContext = getContext();




        sv.setId(R.id.scroll);
        TableLayout tableLayout ;

        tableLayout = createTableLayout(row, column, rl, cl);
        tableLayout.setId(R.id.lay);
        hsv = new HorizontalScrollView(getContext());
        hsv.setId(R.id.horscroll);
        hsv.addView(tableLayout);

        sv.addView(hsv);
        relativeLayout.addView(sv);


        // parent.addView(sv);
        //setContentView(relativeLayout);
        Button Pdf1 = (Button) view.findViewById(R.id.Pdf);
        Button xls = (Button) view.findViewById(R.id.xls);

        Pdf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        View u = ((Activity) mContext).findViewById(R.id.horscroll);

                        HorizontalScrollView z = (HorizontalScrollView) ((Activity) mContext).findViewById(R.id.horscroll);
                        int totalHeight = z.getChildAt(0).getHeight();
                        int totalWidth = z.getChildAt(0).getWidth();
                        //Bitmap b = getBitmapFromView(u,totalHeight,totalWidth);
                        View v1 = hsv.getRootView();
                        Bitmap b = getBitmapFromView(u,totalHeight,totalWidth);
                        Log.e(TAG, "run: "+b );


                        layoutToImage(b);


                    }
                });

            }
        });
        xls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.doctor);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                b1.compress(Bitmap.CompressFormat.PNG, 100, bos);
//                byte[] img = bos.toByteArray();
//
//                boolean s =databaseHandler.insertContact("Doctor",null,null,"Check up","Evening 1",null,null,null,null,null,null,null,null,img,null,null,null,"active",1,"4");
//                if(s) {
//                    Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(mContext, "not inserted", Toast.LENGTH_SHORT).show();
//                }
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

    private TableLayout createTableLayout(String [] rv, String [] cv,int rowCount, int columnCount)  {
        // 1) Create a tableLayout and its params
        FrameLayout.LayoutParams pTable = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        final TableLayout tableLayout = new TableLayout(getContext());
        tableLayout.setStretchAllColumns(true);
        tableLayout.setLayoutParams(pTable);




        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                50);
        TableRow.LayoutParams tableRowParams1 = new TableRow.LayoutParams(250, 50);
        tableRowParams1.setMargins(1,1,1,1);
        tableRowParams1.weight=1;
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;




        //  for (int i = 0; i<contacts.size(); i++) {
        for (int i = 0; i<rowCount; i++) {
            // 3) create tableRow
            tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            tableRow.setClickable(true);
            tableRow.setBackgroundColor(Color.BLUE);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setId(count);
//            String dum = dataa.get(i);
            //       String[] spl = dum.split(",|]");

            //data = new String[]{String.valueOf(conta.get_id()), r.get_type(), r.get_content1(), r.get_content2(), String.valueOf(bb), r.get_state(), String.valueOf(r.get_frequency()), r.getAlram_id()};





            for (int j= 0; j <columnCount; j++) {

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
                if(count==0){
                    Log.d("TAAG", "set Column Headers");
                    textView.setText(cv[j]);
                    Log.d(TAG, "createTableLayout: "+cv[j]+" lk"+columnCount+"jh"+j);
                    textView.setTextSize(20);
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundColor(Color.GRAY);
                    textView.setPadding(5,5,5,5);
                }
//                else if( j==0){
//                            Log.d("TAAG", "Set Row Headers");
//                            textView.setText("Row"+i);
//                            textView.setTextSize(10);
//                    textView.setTextColor(Color.WHITE);
//                    textView.setBackgroundColor(Color.GRAY);
//
//                    //tableRow.addView(checkBox);
//
//                    // tableRow.setId(j);
//
//                }

                else if(j == columnCount-1){
//                    byte[] bb = image.get(i);
//                    checkBox.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));
                    checkBox.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_gallery));
                    tableRow.addView(checkBox,tableRowParams1);
//

                }
                else{
                    textView.setText(String.valueOf(data[j]));
                    //Log.d("TAAG", "Set Row Headers"+spl[j]);
                    textView.setTextSize(15);
                    //Log.d(TAG, "createTableLayoutidfree: "+tableRow.getId());

                    // check id=23

                }

//
                tableRow.addView(textView, tableRowParams);
                tableRow.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(getContext(), String.valueOf(j), Toast.LENGTH_SHORT).show();
                                if (t.getId() != 0) {
                                    if (isPressed) {
                                        for (int h = 0; h < gh; h++) {
                                            View view = t.getChildAt(h);
                                            if(view instanceof ImageView){
                                                ImageView imageView = (ImageView) view;
                                                BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
                                                Bitmap bitmap = drawable.getBitmap();
                                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                                bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
                                                strings.add(bitmap);
                                                img = bos.toByteArray();
                                                //imgg.setImageBitmap(bitmap);
                                                Log.d(TAG, "onClick: image"+String.valueOf(img));
                                            }else if(h !=0){
                                                TextView firstTextView = (TextView) view;
                                                text = firstTextView.getText().toString();
                                                strings.add(text);
                                                firstTextView.setBackgroundColor(Color.RED);
                                                isPressed = false;
                                            }

                                        }
                                    } else {
                                        for (int h = 0; h < gh; h++) {
                                            View view = t.getChildAt(h);
                                            if(view instanceof ImageView){
                                                ImageView imageView = (ImageView) view;
                                                //imgg.setImageDrawable(null);
                                            }else if(h!=0) {
                                                TextView firstTextView = (TextView) t.getChildAt(h);
                                                //text = tvSelectedRow.getText().toString();
                                                firstTextView.setBackgroundColor(Color.WHITE);
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


            }
            if(count == 0){
                i--;
            }
            count++;

            tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                    PopupMenu popup = new PopupMenu(getContext(), v);
                    popup.setOnMenuItemClickListener(Configure_Fragment.this);
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
