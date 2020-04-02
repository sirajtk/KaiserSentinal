package com.example.kaisersentinel.PopUp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.example.kaisersentinel.R;
import com.example.kaisersentinel.SentinalDatabase;
import com.example.kaisersentinel.Tableview.Model.Cell;
import com.example.kaisersentinel.Tableview.Model.ColumnHeader;
import com.example.kaisersentinel.Tableview.Model.RowHeader;

import java.util.ArrayList;
import java.util.List;

public class TableViewModel {
    public static final int MOOD_COLUMN_INDEX = 3;
    public static final int GENDER_COLUMN_INDEX = 4;

    // Constant values for icons
    public static final int SAD = 1;
    public static final int HAPPY = 2;
    public static final int BOY = 1;
    public static final int GIRL = 2;
    public Object gh;

    // Constant size for dummy data sets
    private int COLUMN_SIZE;
    private int ROW_SIZE;


    // Drawables
    @DrawableRes
    private final int mBoyDrawable;
    @DrawableRes
    private final int mGirlDrawable;
    @DrawableRes
    private final int mHappyDrawable;
    @DrawableRes
    private final int mSadDrawable;
    private Context context;

    public TableViewModel(Context c) {
        // initialize drawables
        mBoyDrawable = R.drawable.ic_male;
        mGirlDrawable = R.drawable.ic_female;
        mHappyDrawable = R.drawable.ic_happy;
        mSadDrawable = R.drawable.three;
        this.context = c;
    }

    @NonNull
    private List<RowHeader> getSimpleRowHeaderList() {
        final SentinalDatabase db = new SentinalDatabase(context);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal where processed='NO'";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            Log.e("error", "getCellListForSortingTest: " + cursor.getString(3));

        }
        ROW_SIZE = cursor.getCount();
        List<RowHeader> list = new ArrayList<>();
        for (int i = 1; i <= ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), String.valueOf(i));
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<ColumnHeader> getRandomColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();
        final SentinalDatabase db = new SentinalDatabase(context);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            Log.e("error", "getCellListForSortingTest: " + cursor.getString(3));


            COLUMN_SIZE = cursor.getColumnCount();

            for (int i = 0; i < COLUMN_SIZE; i++) {
                String title = cursor.getColumnName(i);
//            int nRandom = new Random().nextInt();
//            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
//                title = "large column " + i;
//            }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<Cell>> getCellListForSortingTest(Context c) {
        List<List<Cell>> list = new ArrayList<>();
        final SentinalDatabase db = new SentinalDatabase(c);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal";


        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);


        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            if (cursor.moveToNext()) {
                Log.e("error", "getCellListForSortingTest: " + cursor.getString(3) + " lgk" + cursor.getCount());
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Object text = cursor.getString(j);

//                final int random = new Random().nextInt();
                    if (j == 0) {
                        text = cursor.getInt(j);
                    }

                    if(j==COLUMN_SIZE-1)
                    {
                        Object blob= cursor.getString(j);
                        String id = j + "-" + i;
                        Cell cell;
                        cell = new Cell(id, blob);
                        cellList.add(cell);


                    }
                    //else if (j == 1) {
//                    text = gh;
//                } else if (j == MOOD_COLUMN_INDEX) {
//                    text = random % 2 == 0 ? HAPPY : SAD;
//                } else if (j == GENDER_COLUMN_INDEX) {
//                    text = random % 2 == 0 ? BOY : GIRL;
//                }

                    //Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
                    cell = new Cell(id, text);
                    //}
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        }

        return list;
    }

    @NonNull
    private List<ColumnHeader> getRandomColumnHeaderListhome() {
        List<ColumnHeader> list = new ArrayList<>();
        final SentinalDatabase db = new SentinalDatabase(context);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description FROM sentinal";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            Log.e("error", "getCellListForSortingTest: " + cursor.getString(3));


            COLUMN_SIZE = cursor.getColumnCount();

            for (int i = 0; i < COLUMN_SIZE; i++) {
                String title = cursor.getColumnName(i);
//            int nRandom = new Random().nextInt();
//            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
//                title = "large column " + i;
//            }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        }

        return list;
    }

    @NonNull
    private List<RowHeader> getSimpleRowHeaderListhome() {
        final SentinalDatabase db = new SentinalDatabase(context);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description FROM sentinal";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            Log.e("error", "getCellListForSortingTest: " + cursor.getString(3));

        }
        ROW_SIZE = cursor.getCount();
        List<RowHeader> list = new ArrayList<>();
        for (int i = 1; i <= ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), String.valueOf(i));
            list.add(header);
        }

        return list;
    }

    @NonNull
    private List<List<Cell>> getCellListForSortingTesthome(Context c) {
        List<List<Cell>> list = new ArrayList<>();
        final SentinalDatabase db = new SentinalDatabase(c);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description FROM sentinal";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);


        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            if (cursor.moveToNext()) {
                Log.e("error", "getCellListForSortingTest: " + cursor.getString(3) + " lgk" + cursor.getCount());
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Object text = cursor.getString(j);

//                final int random = new Random().nextInt();
                    if (j == 0) {
                        text = cursor.getInt(j);
                    }
                    //else if (j == 1) {
//                    text = gh;
//                } else if (j == MOOD_COLUMN_INDEX) {
//                    text = random % 2 == 0 ? HAPPY : SAD;
//                } else if (j == GENDER_COLUMN_INDEX) {
//                    text = random % 2 == 0 ? BOY : GIRL;
//                }

                    //Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
                    cell = new Cell(id, text);
                    //}
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        }

        return list;
    }

    @DrawableRes
    public int getDrawable(int value, boolean isGender) {
        if (isGender) {
            return value == BOY ? mBoyDrawable : mGirlDrawable;
        } else {
            return value == SAD ? mSadDrawable : mHappyDrawable;
        }
    }

    @NonNull
    public List<List<Cell>> getCellList(Context c) {
        return getCellListForSortingTest(c);
    }
    @NonNull
    public List<RowHeader> getRowHeaderList() {
        return getSimpleRowHeaderList();
    }
    @NonNull
    public List<ColumnHeader> getColumnHeaderList() {
        return getRandomColumnHeaderList();
    }

    @NonNull
    public List<List<Cell>> getCellListhome(Context c) {
        return getCellListForSortingTesthome(c);
    }


    @NonNull
    public List<RowHeader> getRowHeaderListhome() {
        return getSimpleRowHeaderListhome();
    }



    @NonNull
    public List<ColumnHeader> getColumnHeaderListhome() {
        return getRandomColumnHeaderListhome();
    }
    @NonNull
    public List<List<Cell>> getCellListmarked(Context c) {
        return getCellListForSortingTestmarked(c);
    }


    @NonNull
    public List<RowHeader> getRowHeaderListmarked() {
        return getSimpleRowHeaderListmarked();
    }



    @NonNull
    public List<ColumnHeader> getColumnHeaderListmarked() {
        return getRandomColumnHeaderListmarked();
    }

    @NonNull
    private List<RowHeader> getSimpleRowHeaderListmarked() {
        final SentinalDatabase db = new SentinalDatabase(context);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal WHERE marked='YES'";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            Log.e("error", "getCellListForSortingTest: " + cursor.getString(3));

        }
        ROW_SIZE = cursor.getCount();
        List<RowHeader> list = new ArrayList<>();
        for (int i = 1; i <= ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), String.valueOf(i));
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<ColumnHeader> getRandomColumnHeaderListmarked() {
        List<ColumnHeader> list = new ArrayList<>();
        final SentinalDatabase db = new SentinalDatabase(context);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal WHERE marked='NO'";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            Log.e("error", "getCellListForSortingTest: " + cursor.getString(3));


            COLUMN_SIZE = cursor.getColumnCount();

            for (int i = 0; i < COLUMN_SIZE; i++) {
                String title = cursor.getColumnName(i);
//            int nRandom = new Random().nextInt();
//            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
//                title = "large column " + i;
//            }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<Cell>> getCellListForSortingTestmarked(Context c) {
        List<List<Cell>> list = new ArrayList<>();
        final SentinalDatabase db = new SentinalDatabase(c);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal WHERE marked='NO'";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);


        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            if (cursor.moveToNext()) {
                Log.e("error", "getCellListForSortingTest: " + cursor.getString(3) + " lgk" + cursor.getCount());
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Object text = cursor.getString(j);

//                final int random = new Random().nextInt();
                    if (j == 0) {
                        text = cursor.getInt(j);
                    }
                    //else if (j == 1) {
//                    text = gh;
//                } else if (j == MOOD_COLUMN_INDEX) {
//                    text = random % 2 == 0 ? HAPPY : SAD;
//                } else if (j == GENDER_COLUMN_INDEX) {
//                    text = random % 2 == 0 ? BOY : GIRL;
//                }

                    //Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
                    cell = new Cell(id, text);
                    //}
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        }

        return list;
    }
    @NonNull
    public List<List<Cell>> getCellListreport(Context c) {
        return getCellListForSortingTestreport(c);
    }


    @NonNull
    public List<RowHeader> getRowHeaderListreport() {
        return getSimpleRowHeaderListreport();
    }



    @NonNull
    public List<ColumnHeader> getColumnHeaderListreport() {
        return getRandomColumnHeaderListreport();
    }

    @NonNull
    private List<RowHeader> getSimpleRowHeaderListreport() {
        final SentinalDatabase db = new SentinalDatabase(context);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            Log.e("error", "getCellListForSortingTest: " + cursor.getString(3));

        }
        ROW_SIZE = cursor.getCount();
        List<RowHeader> list = new ArrayList<>();
        for (int i = 1; i <= ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), String.valueOf(i));
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<ColumnHeader> getRandomColumnHeaderListreport() {
        List<ColumnHeader> list = new ArrayList<>();
        final SentinalDatabase db = new SentinalDatabase(context);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            Log.e("error", "getCellListForSortingTest: " + cursor.getString(3));


            COLUMN_SIZE = cursor.getColumnCount();

            for (int i = 0; i < COLUMN_SIZE; i++) {
                String title = cursor.getColumnName(i);
//            int nRandom = new Random().nextInt();
//            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
//                title = "large column " + i;
//            }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<Cell>> getCellListForSortingTestreport(Context c) {
        List<List<Cell>> list = new ArrayList<>();
        final SentinalDatabase db = new SentinalDatabase(c);
        String selectQuery = "SELECT  timestamp,channel,alert_type,description,image FROM sentinal";

        SQLiteDatabase my = db.getWritableDatabase();
        Cursor cursor = my.rawQuery(selectQuery, null);


        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            if (cursor.moveToNext()) {
                Log.e("error", "getCellListForSortingTest: " + cursor.getString(3) + " lgk" + cursor.getCount());
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Object text = cursor.getString(j);

//                final int random = new Random().nextInt();
                    if (j == 0) {
                        text = cursor.getInt(j);
                    }
                    //else if (j == 1) {
//                    text = gh;
//                } else if (j == MOOD_COLUMN_INDEX) {
//                    text = random % 2 == 0 ? HAPPY : SAD;
//                } else if (j == GENDER_COLUMN_INDEX) {
//                    text = random % 2 == 0 ? BOY : GIRL;
//                }

                    //Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
                    cell = new Cell(id, text);
                    //}
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        }

        return list;
    }
}
