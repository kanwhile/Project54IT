package com.projects.mypillapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.dom.DOMResult;

/**
 * Created by นนทรักษ์ on 27/10/2558.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "PILL";
    private static final int DB_VERSION = 1;

    public DBHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Todo table create Pill
    private static final String CREATE_TABLE_PILL = "CREATE TABLE " + Pill.TABLE_CONTACT_PILL + "("
            + Pill.Column.COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Pill.Column.COLUMN_NAME_PILL + " TEXT,"
            + Pill.Column.COLUMN_LOCATION_PILL + " TEXT,"
            + Pill.Column.EAT + " TEXT,"
            + Pill.Column.FOR_PILL + " INTEGER,"
            + Pill.Column.TYPEPILL + " TEXT,"
            + Pill.Column.STARTDAY + " TEXT,"
            + Pill.Column.ENDDAY + " TEXT,"
            + Pill.Column.BREAKFAST + " BOOLEAN,"
            + Pill.Column.LUNCH + " BOOLEAN,"
            + Pill.Column.DINNER + " BOOLEAN,"
            + Pill.Column.NIGHT + " BOOLEAN,"
            + Pill.Column.HOURLY + " BOOLEAN,"
            + Pill.Column.HOUR + " INTEGER,"
            + Pill.Column.MINUTE + " INTEGER,"
            + Pill.Column.DETAIL + " TEXT,"
            + Pill.Column.ID_DISEASE + " INTEGER,"
            + Pill.Column.ID_DOCTOR + " INTEGER,"
            + Pill.Column.PILL_IMAGE + " TEXT"
            + ")";
    private static final String SQL_DELETE_PILL =
            "DROP TABLE IF EXISTS " + Pill.TABLE_CONTACT_PILL;

    // Todo table create History
    private static final String CREATE_TABLE_HISTORY_PILL = "CREATE TABLE " + Pill_history.TABLE + "("
            + Pill_history.Column.DOSE + " INTEGER,"
            + Pill_history.Column.PILL_ID + " INTEGER,"
            + Pill_history.Column.PILL_TIME + " TEXT,"
            + Pill_history.Column.TIMESTAMP + " TEXT"
            + ")";
    private static final String SQL_DELETE_HISTORY_PILL =
            "DROP TABLE IF EXISTS " + Pill_history.TABLE;

    // Todo table create Disease
    private static final String CREATE_TABLE_DISEASE = "CREATE TABLE " + Disease.TABLE_CONTACT_DISEASE + "("
            + Disease.Column.COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Disease.Column.COLUMN_NAME_DISEASE + " TEXT"
            + ")";
    private static final String SQL_DELETE_DISEASE =
            "DROP TABLE IF EXISTS " + Disease.TABLE_CONTACT_DISEASE;

    // Todo table create Doctor
    private static final String CREATE_TABLE_DOCTORS = "CREATE TABLE " + Doctors.TABLE_CONTACT_DOCTORS + "("
            + Doctors.Column.COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Doctors.Column.COLUMN_NAME + " TEXT,"
            + Doctors.Column.COLUMN_PHONE + " TEXT,"
            + Doctors.Column.COLUMN_ADDRESS + " TEXT,"
            + Doctors.Column.COLUMN_IMAGE + " INTEGER,"
            + Doctors.Column.COLUMN_EMAIL + " TEXT"
            + ")";
    private static final String SQL_DELETE_DOCTORS =
            "DROP TABLE IF EXISTS " + Doctors.TABLE_CONTACT_DOCTORS;

    // Todo table create Event
    private static final String CREATE_TABLE_EVENT = "CREATE TABLE " + Event.TABLE_CONTACT_EVENT + "("
            + Event.Column.ID + " INTEGER PRIMARY KEY,"
            + Event.Column.NAME_EVENT + " TEXT,"
            + Event.Column.NAME_DOCTER + " TEXT,"
            + Event.Column.LOCATION_EVENT + " TEXT,"
            + Event.Column.HOUR + " INTEGER,"
            + Event.Column.MINUTE + " INTEGER,"
            + Event.Column.DAY + " INTEGER,"
            + Event.Column.MONTH + " INTEGER,"
            + Event.Column.YEAR + " INTEGER,"
            + Event.Column.DETAIL_EVENT + " TEXT,"
            + Event.Column.ID_DOCTOR + " INTEGER,"
            + Event.Column.IMG_EVENT + " TEXT"
            + ")";
    private static final String SQL_DELETE_EVENT =
            "DROP TABLE IF EXISTS " + Event.TABLE_CONTACT_EVENT;



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PILL);
        db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_DISEASE);
        db.execSQL(CREATE_TABLE_HISTORY_PILL);
        db.execSQL(CREATE_TABLE_DOCTORS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PILL);
        db.execSQL(SQL_DELETE_EVENT);
        db.execSQL(SQL_DELETE_DISEASE);
        db.execSQL(SQL_DELETE_HISTORY_PILL);
        db.execSQL(SQL_DELETE_DOCTORS);
        onCreate(db);
    }

    //  ตารางแจ้งเตือนทานยา
    private PillModel populateModel(Cursor c) {
        PillModel model = new PillModel();
        model.id = c.getInt(c.getColumnIndex(Pill.Column.COLUMN_ID));
        model.name_pill = c.getString(c.getColumnIndex(Pill.Column.COLUMN_NAME_PILL));
        model.location = c.getString(c.getColumnIndex(Pill.Column.COLUMN_LOCATION_PILL));
        model.eat = c.getString(c.getColumnIndex(Pill.Column.EAT));
        model.for_pill = c.getInt(c.getColumnIndex(Pill.Column.FOR_PILL));
        model.typepill = c.getString(c.getColumnIndex(Pill.Column.TYPEPILL));
        model.startday = c.getString(c.getColumnIndex(Pill.Column.STARTDAY));
        model.endday = c.getString(c.getColumnIndex(Pill.Column.ENDDAY));
        model.breakfast = c.getInt(c.getColumnIndex(Pill.Column.BREAKFAST))== 0 ? false:true;
        model.lunch = c.getInt(c.getColumnIndex(Pill.Column.LUNCH))== 0 ? false : true;
        model.dinner = c.getInt(c.getColumnIndex(Pill.Column.DINNER))== 0 ? false : true;
        model.night = c.getInt(c.getColumnIndex(Pill.Column.NIGHT))== 0 ? false : true;
        model.hourly = c.getInt(c.getColumnIndex(Pill.Column.HOURLY));
        model.hour = c.getInt(c.getColumnIndex(Pill.Column.HOUR));
        model.minute = c.getInt(c.getColumnIndex(Pill.Column.MINUTE));
        model.detail = c.getString(c.getColumnIndex(Pill.Column.DETAIL));
        model.pillImg = c.getString(c.getColumnIndex(Pill.Column.PILL_IMAGE));
        model.id_disease = c.getInt(c.getColumnIndex(Pill.Column.ID_DISEASE));
        model.id_doctor = c.getInt(c.getColumnIndex(Pill.Column.ID_DOCTOR));
        return model;
    }
    private ContentValues populateContent(PillModel model) {
        ContentValues values = new ContentValues();
        values.put(Pill.Column.COLUMN_NAME_PILL, model.name_pill);
        values.put(Pill.Column.COLUMN_LOCATION_PILL,model.location);
        values.put(Pill.Column.EAT,model.eat);
        values.put(Pill.Column.FOR_PILL,model.for_pill);
        values.put(Pill.Column.TYPEPILL,model.typepill);
        values.put(Pill.Column.STARTDAY,model.startday);
        values.put(Pill.Column.ENDDAY,model.endday);
        values.put(Pill.Column.BREAKFAST,model.breakfast);
        values.put(Pill.Column.LUNCH,model.lunch);
        values.put(Pill.Column.DINNER,model.dinner);
        values.put(Pill.Column.NIGHT,model.night);
        values.put(Pill.Column.HOURLY,model.hourly);
        values.put(Pill.Column.HOUR,model.hour);
        values.put(Pill.Column.MINUTE,model.minute);
        values.put(Pill.Column.DETAIL, model.detail);
        values.put(Pill.Column.PILL_IMAGE, model.pillImg);
        values.put(Pill.Column.ID_DISEASE,model.id_disease);
        values.put(Pill.Column.ID_DOCTOR,model.id_doctor);
        return values;
    }
    public long createPill(PillModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().insert(Pill.TABLE_CONTACT_PILL, null, values);
    }



    public List<PillModel> getPillAll() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL;

        Cursor c = db.rawQuery(select, null);

        List<PillModel> alarmList = new ArrayList<PillModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    public List<PillModel> getPillBreakfast(String eat) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        if (eat == null){
            String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.BREAKFAST + "=" + 1;
            c = db.rawQuery(select, null);
        }else {
            String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.BREAKFAST + "=" + 1 +" AND "
                    + Pill.Column.EAT + " LIKE "+"?";
            c = db.rawQuery(select, new String[] {eat});
        }

        List<PillModel> alarmList = new ArrayList<PillModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }


    public List<PillModel> getPillLUNCH(String eat) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c;
        if (eat == null){
            String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.LUNCH + "=" + 1;
            c = db.rawQuery(select, null);
        }else {
            String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.LUNCH + "=" + 1 +" AND "
                    + Pill.Column.EAT + " LIKE "+"?";
            c = db.rawQuery(select, new String[] {eat});
        }

        List<PillModel> alarmList = new ArrayList<PillModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }
    public List<PillModel> getPillDINNER(String eat) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c;
        if (eat == null){
            String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.DINNER + "=" + 1;
            c = db.rawQuery(select, null);
        }else {
            String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.DINNER + "=" + 1 +" AND "
                    + Pill.Column.EAT + " LIKE "+"?";
            c = db.rawQuery(select, new String[] {eat});
        }


        List<PillModel> alarmList = new ArrayList<PillModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    public List<PillModel> getPillNIGHT(String eat) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c;
        if (eat == null){
            String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.NIGHT + "=" + 1;
            c = db.rawQuery(select, null);
        }else {
            String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.NIGHT + "=" + 1 +" AND "
                    + Pill.Column.EAT + " LIKE "+"?";
            c = db.rawQuery(select, new String[] {eat});
        }


        List<PillModel> alarmList = new ArrayList<PillModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }


    public List<PillModel> getPillHour(int H,int M) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c;

        String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.HOUR + "=" + H +" AND "
                + Pill.Column.MINUTE + M;
        c = db.rawQuery(select,null);


        List<PillModel> alarmList = new ArrayList<PillModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }



    public List<PillModel> getPillDoctor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.ID_DOCTOR + "=" + id;

        Cursor c = db.rawQuery(select, null);

        List<PillModel> alarmList = new ArrayList<PillModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    public PillModel getPill(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL + " WHERE " + Pill.Column.COLUMN_ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateModel(c);
        }

        return null;
    }



    public long updatePill(PillModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(Pill.TABLE_CONTACT_PILL, values, Pill.Column.COLUMN_ID + " = ?", new String[]{String.valueOf(model.id)});
    }
    public int deletePill(long id) {
        return getWritableDatabase().delete(Pill.TABLE_CONTACT_PILL, Pill.Column.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public int deletePillHistory(long id) {
        return getWritableDatabase().delete(Pill_history.TABLE, Pill_history.Column.PILL_ID + " = ?", new String[]{String.valueOf(id)});
    }


    //ตารางแจ้งเตือนนัดหมาย
    private EventModel populateEventModel(Cursor c) {
        EventModel model = new EventModel();
        model.id = c.getInt(c.getColumnIndex(Event.Column.ID));
        model.name_event = c.getString(c.getColumnIndex(Event.Column.NAME_EVENT));
        model.location = c.getString(c.getColumnIndex(Event.Column.LOCATION_EVENT));
        model.name_docter = c.getString(c.getColumnIndex(Event.Column.NAME_DOCTER));
        model.hour = c.getInt(c.getColumnIndex(Event.Column.HOUR));
        model.minute = c.getInt(c.getColumnIndex(Event.Column.MINUTE));
        model.day = c.getInt(c.getColumnIndex(Event.Column.DAY));
        model.month = c.getInt(c.getColumnIndex(Event.Column.MONTH));
        model.year = c.getInt(c.getColumnIndex(Event.Column.YEAR));
        model.detail_event = c.getString(c.getColumnIndex(Event.Column.DETAIL_EVENT));
        model.img_event = c.getString(c.getColumnIndex(Event.Column.IMG_EVENT));
        model.id_doctor = c.getInt(c.getColumnIndex(Event.Column.ID_DOCTOR));

        return model;
    }
    private ContentValues eventContent(EventModel model) {
        ContentValues values = new ContentValues();
        values.put(Event.Column.NAME_EVENT, model.name_event);
        values.put(Event.Column.LOCATION_EVENT,model.location);
        values.put(Event.Column.NAME_DOCTER,model.name_docter);
        values.put(Event.Column.HOUR,model.hour);
        values.put(Event.Column.MINUTE,model.minute);
        values.put(Event.Column.DAY,model.day);
        values.put(Event.Column.MONTH,model.month);
        values.put(Event.Column.YEAR, model.year);
        values.put(Event.Column.DETAIL_EVENT,model.detail_event);
        values.put(Event.Column.IMG_EVENT,model.img_event);
        values.put(Event.Column.ID_DOCTOR,model.id_doctor);
        return values;
    }
    public long createEvent(EventModel model) {
        ContentValues values = eventContent(model);
        return getWritableDatabase().insert(Event.TABLE_CONTACT_EVENT, null, values);
    }

    public List<EventModel> getEventAll() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Event.TABLE_CONTACT_EVENT;

        Cursor c = db.rawQuery(select, null);

        List<EventModel> alarmList = new ArrayList<EventModel>();

        while (c.moveToNext()) {
            alarmList.add(populateEventModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    public EventModel getEventAll(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Event.TABLE_CONTACT_EVENT + " WHERE " + Event.Column.ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateEventModel(c);
        }

        return null;
    }

    public List<EventModel> getEventDoctor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Event.TABLE_CONTACT_EVENT + " WHERE " + Event.Column.ID_DOCTOR + " = " + id;

        Cursor c = db.rawQuery(select, null);

        List<EventModel> alarmList = new ArrayList<EventModel>();

        while (c.moveToNext()) {
            alarmList.add(populateEventModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }


    public long updateEvent(EventModel model) {
        ContentValues values = eventContent(model);
        return getWritableDatabase().update(Event.TABLE_CONTACT_EVENT, values, Event.Column.ID + " = ?", new String[]{String.valueOf(model.id)});
    }
    public int deleteEvent(long id) {
        return getWritableDatabase().delete(Event.TABLE_CONTACT_EVENT, Event.Column.ID + " = ?", new String[]{String.valueOf(id)});
    }




    //  ตารางประวัติยา
    private Pill_history populateModelHistory(Cursor c) {
        Pill_history model = new Pill_history();
        model.dose = c.getInt(c.getColumnIndex(Pill_history.Column.DOSE));
        model.pill_id = c.getInt(c.getColumnIndex(Pill_history.Column.PILL_ID));
        model.pill_time = c.getString(c.getColumnIndex(Pill_history.Column.PILL_TIME));
        model.timestamp  = c.getString(c.getColumnIndex(Pill_history.Column.TIMESTAMP));



        return model;
    }

    private ContentValues populatePillHistory(Pill_history model) {
        ContentValues values = new ContentValues();
        values.put(Pill_history.Column.DOSE, model.dose);
        values.put(Pill_history.Column.PILL_ID,model.pill_id);
        values.put(Pill_history.Column.PILL_TIME,model.pill_time);
        values.put(Pill_history.Column.TIMESTAMP,model.timestamp);

        return values;
    }
    public long createPillHistory(Pill_history model) {
        ContentValues values = populatePillHistory(model);
        return getWritableDatabase().insert(Pill_history.TABLE, null, values);
    }




    private ContentValues populateModelDisease(Disease model) {
        ContentValues values = new ContentValues();
        values.put(Disease.Column.COLUMN_NAME_DISEASE,model.name_disease);

        return values;
    }
    private Disease populateDisease(Cursor c) {
        Disease model = new Disease();
        model.id = c.getInt(c.getColumnIndex(Disease.Column.COLUMN_ID));
        model.name_disease = c.getString(c.getColumnIndex(Disease.Column.COLUMN_NAME_DISEASE));
        return model;
    }
    public long createDisease(Disease model) {
        ContentValues values = populateModelDisease(model);
        return getWritableDatabase().insert(Disease.TABLE_CONTACT_DISEASE, null, values);
    }
    public Disease getDisease(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Disease.TABLE_CONTACT_DISEASE + " WHERE " + Disease.Column.COLUMN_ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateDisease(c);
        }

        return null;
    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Disease.TABLE_CONTACT_DISEASE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    //ตารางแพทย์
    private Doctors populateDoctorsModel(Cursor c) {
        Doctors model = new Doctors();
        model.id = c.getInt(c.getColumnIndex(Doctors.Column.COLUMN_ID));
        model.name = c.getString(c.getColumnIndex(Doctors.Column.COLUMN_NAME));
        model.phone = c.getString(c.getColumnIndex(Doctors.Column.COLUMN_PHONE));
        model.email = c.getString(c.getColumnIndex(Doctors.Column.COLUMN_EMAIL));
        model.address = c.getString(c.getColumnIndex(Doctors.Column.COLUMN_ADDRESS));
        model.image = c.getInt(c.getColumnIndex(Doctors.Column.COLUMN_IMAGE));

        return model;
    }
    private ContentValues doctorContent(Doctors model) {
        ContentValues values = new ContentValues();
        values.put(Doctors.Column.COLUMN_NAME, model.name);
        values.put(Doctors.Column.COLUMN_PHONE,model.phone);
        values.put(Doctors.Column.COLUMN_EMAIL,model.email);
        values.put(Doctors.Column.COLUMN_ADDRESS,model.address);
        values.put(Doctors.Column.COLUMN_IMAGE,model.image);

        return values;
    }
    public long createDoctor(Doctors model) {
        ContentValues values = doctorContent(model);
        return getWritableDatabase().insert(Doctors.TABLE_CONTACT_DOCTORS, null, values);
    }

    public List<Doctors> getDoctorAll() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Doctors.TABLE_CONTACT_DOCTORS;

        Cursor c = db.rawQuery(select, null);

        List<Doctors> alarmList = new ArrayList<Doctors>();

        while (c.moveToNext()) {
            alarmList.add(populateDoctorsModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    public Doctors getDoctorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Doctors.TABLE_CONTACT_DOCTORS + " WHERE " + Doctors.Column.COLUMN_ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateDoctorsModel(c);
        }

        return null;
    }
    public long updatedoctor(Doctors model) {
        ContentValues values = doctorContent(model);
        return getWritableDatabase().update(Doctors.TABLE_CONTACT_DOCTORS, values, Doctors.Column.COLUMN_ID + " = ?", new String[]{String.valueOf(model.id)});
    }
    public int deleteDoctor(long id) {
        return getWritableDatabase().delete(Doctors.TABLE_CONTACT_DOCTORS, Doctors.Column.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int getMaxIdDoctor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+Doctors.TABLE_CONTACT_DOCTORS, null);
        c.moveToLast();
        return c.getInt(c.getColumnIndex("id"));
    }

    public List<PillModel> getDoctorPill(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Pill.TABLE_CONTACT_PILL  + " WHERE " + Pill.Column.ID_DOCTOR + "=" + id;

        Cursor c = db.rawQuery(select, null);

        List<PillModel> alarmList = new ArrayList<PillModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    public List<String>getListDoctor(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Doctors.TABLE_CONTACT_DOCTORS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}
