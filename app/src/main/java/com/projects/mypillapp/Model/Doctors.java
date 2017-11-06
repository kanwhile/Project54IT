package com.projects.mypillapp.Model;

/**
 * Created by นนทรักษ์ on 7/12/2558.
 */
public class Doctors {
    // Contacts table name
    public static final String TABLE_CONTACT_DOCTORS = "doctors";

    public class Column {

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_IMAGE = "image";
    }
    public int id = -1;
    public String name;
    public String phone;
    public String email;
    public String address;
    public int image;
}
