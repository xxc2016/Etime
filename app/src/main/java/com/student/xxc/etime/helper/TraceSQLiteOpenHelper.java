package com.student.xxc.etime.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class TraceSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_DATABASE = "CREATE TABLE "+
            "userTrace(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "ESTime TEXT,LETime TEXT,time TEXT,event TEXT,date TEXT,traceId INTEGER,finish INTEGER," +
            "hasESTime INTEGER,hasLETime INTEGER,siteId TEXT,siteText Text,predict INTEGER)";

    public  static final String DROP_TABLE = "DROP TABLE "+
            "userTrace";
    private Context  context;

    public TraceSQLiteOpenHelper(Context context, String name,SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
        this.context = context;
    }

    public  void setDropTable(SQLiteDatabase db)
    {
        db.execSQL(DROP_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
       // Toast.makeText(context,"数据库创建成功",Toast.LENGTH_LONG).show();
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {

    }
}
