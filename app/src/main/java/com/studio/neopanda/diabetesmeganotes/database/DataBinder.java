package com.studio.neopanda.diabetesmeganotes.database;

import android.provider.BaseColumns;

public class DataBinder {

    private DataBinder() {

    }

    public static class DataGlycemies implements BaseColumns {
        public static final String TABLE_NAME = "GlycemyBinder";
        public static final String TABLE_TO_STOCK = "TableExternal";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_GLYCEMY = "Glycemy";
        public static final String COLUMN_NAME_EXTRA_INFOS = "Extras";
        public static final String COLUMN_NAME_DATA_ID = "DataID";
    }

    public static class DataInsulin implements BaseColumns {
        public static final String TABLE_NAME = "InsulinBinder";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_UNITS = "InsulinUnits";
        public static final String COLUMN_NAME_DATA_ID = "DataID";
    }

    public static class DataNote implements BaseColumns {
        public static final String TABLE_NAME = "Note";
        public static final String COLUMN_NAME_TEXT = "Text";
        public static final String COLUMN_NAME_DATA_ID = "DataID";
    }
}
