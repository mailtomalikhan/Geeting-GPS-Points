package com.example.ali.points;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper  extends SQLiteOpenHelper
{
    ArrayList<Integer> returnMsg = new ArrayList<Integer>();
    String a[]=null;
    private static final String DATABASE_NAME = "points.db";    // Database Name
    private static final String TABLE_NAME = "POINTS_TABLE";   // Table Name
    private static final int DATABASE_Version = 1;   // Database Version
    private static final String ID="_id";     // Column I (Primary Key)
    private static final String Longitude = "Long";    //Column II
    private static final String latitude= "lati";    // Column III
    private static final String P_KEY= "prim";    // Column III

    private static final String TABLE_NAME1 = "AREA_TABLE";   // Table Name
    private static final String ID1="_id";     // Column I (Primary Key)
    private static final String AREA="area";     // Column I (Primary Key)
    // FOREIGN KEY ("+P_KEY+") REFERENCES "+TABLE_NAME1+" ("+ID1+")


    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Longitude + " TEXT, " + latitude + " TEXT," + P_KEY + " INTEGER )";
    String CREATE_TABLE1= "CREATE TABLE " + TABLE_NAME1 + "(" + ID1 + " INTEGER, " + AREA + " TEXT )";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String DROP_TABLE1 ="DROP TABLE IF EXISTS "+TABLE_NAME1;
    private Context context;

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);

    }

    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE1);
        onCreate(db);

    }


    public  boolean  insertarea(int area , int id){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues1= new ContentValues();
        contentValues1.put(AREA,area);
        contentValues1.put(ID1,id);
        long result = db.insert(TABLE_NAME1,null,contentValues1);
        db.close();

        if(result==-1){
            return false;
        }
        else{

            return true;
        }
    }

    public  boolean  insertData(double passlongitude, double passlatitude,int courser){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues= new ContentValues();
        contentValues.put(Longitude,passlongitude);
        contentValues.put(latitude,passlatitude);
        contentValues.put(P_KEY,courser);
        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if(result==-1){
            return false;
        }
        else{

            return true;
        }
    }

    public boolean  isMasterEmpty() {

        boolean flag;
        String quString = "select exists(select 1 from " + TABLE_NAME1  + ");";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(quString, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        if (count ==1) {
            flag =  false;
        } else {
            flag = true;
        }
        cursor.close();
        db.close();

        return flag;
    }
    public int last_location(){
        int last;
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery(" select * from "+TABLE_NAME1,null);
        res.moveToLast();
        last=res.getInt(0);
        db.close();
        return  last;
    }


    public String[] readdtat(){
        int counter=0;
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor res=db.rawQuery(" select * from "+TABLE_NAME1,null);
        if(res.moveToFirst()) {

            do {
                counter++;
            } while (res.moveToNext());
        }
        int i=0;
        int arr[] = new int[counter];
        if(res.moveToFirst()){

            do {
               arr[i]= res.getInt(0);
               i++;
            }while (res.moveToNext());
        }
        if (res != null && !res.isClosed()) {
            res.close();
        }
        if (db!=null){
            db.close();
        }
       String StrArray[]=new String[arr.length];
        for (int j=arr.length-1;j>=0;j--)
            StrArray[j]=String.valueOf(arr[(arr.length-1)-j]);

        db.close();
        return StrArray;
    }

    public String[][] readpoints(String name){
        int counter=0;
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor res=db.rawQuery(" select * from "+TABLE_NAME,null );
        if(res.moveToFirst()) {

            do {
                counter++;
            } while (res.moveToNext());
        }

        String[][] points= new String[counter][4];
        if(res.moveToFirst()){
            int i=0;
            do {
                int num =res.getInt(3);
                String numc= String.valueOf(num);

                if(numc.equals(name)){
                    num =res.getInt(0);
                    numc= String.valueOf(num);
                points[i][0]= numc;
                points[i][1]= res.getString(1);
                points[i][2]= res.getString(2);
                   num =res.getInt(3);
                    numc= String.valueOf(num);
                    points[i][3]= numc;
                i++;}
            }while (res.moveToNext());
        }
        if (res != null && !res.isClosed()) {
            res.close();
        }
        if (db!=null){
            db.close();
        }

        db.close();
        return points;
    }


}

