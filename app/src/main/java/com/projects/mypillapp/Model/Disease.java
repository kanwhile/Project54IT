package com.projects.mypillapp.Model;

/**
 * Created by นนทรักษ์ on 24/11/2558.
 */
public class Disease {
    // Contacts table name
    public static final String TABLE_CONTACT_DISEASE = "disease";

    public class Column {

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME_DISEASE = "name_disease";
    }
    public int id = -1;
    public String name_disease;
}
