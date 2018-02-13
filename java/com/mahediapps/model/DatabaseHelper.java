package com.mahediapps.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "personal_report";
    public static String DB_PATH = "/data/data/com.mahediapps.personalreport/databases/";
    public static final int DB_VERSION = 1;
    public static final String TABLE_1 = "tb_daily_report";
    public static final String TABLE_2 = "tb_et_mr";
    public static final String TABLE_3 = "tb_monthly_plan";

    public static SQLiteDatabase db;
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    private boolean checkDataBase() {
        return (new File((new StringBuilder(String.valueOf(DB_PATH))).append(DB_NAME).toString())).exists();
    }


    private void copyDataBase() throws IOException {
        InputStream inputstream = context.getAssets().open(DB_NAME);
        FileOutputStream fileoutputstream = new FileOutputStream((new StringBuilder(String.valueOf(DB_PATH)))
                .append(DB_NAME).toString());

        byte abyte0[] = new byte[1024];
        do {
            int i = inputstream.read(abyte0);
            if (i <= 0) {
                fileoutputstream.flush();
                fileoutputstream.close();
                inputstream.close();
                return;
            }
            fileoutputstream.write(abyte0, 0, i);
        } while (true);
    }


    public void openDataBase() throws SQLException {
        db = SQLiteDatabase.openDatabase((new StringBuilder(String.valueOf(DB_PATH)))
                .append(DB_NAME).toString(), null, 0);
    }

    
    public void close() {
        db.close();
    }


    public void onCreate(SQLiteDatabase sqlitedatabase) {
    }


    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
    }
    

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            getReadableDatabase().close();
            try {
                copyDataBase();
                return;

            } catch (IOException ioexception) {
                throw new Error("Error copying database");
            }
        } else {
            Log.e("allah help me", "database is already exits");
            getReadableDatabase().close();
            return;
        }
    }


    //table 1
    public int checkThisMonthExistOrNot(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT DISTINCT _id FROM " +
                TABLE_2 + " WHERE md_month = '")).append(monthId).append("'").toString(), null);

        int i = 0;
        if (myCursor != null) {
            if (myCursor.getCount() > 0) {
                myCursor.moveToFirst();
                i = 61;
            }
        }
        myCursor.close();
        sqlitedatabase.close();
        return i;
    }


    public ContentValues setAllContentValuesForTableDailyReport(HashMap<String, String> dailyDairy) {
        ContentValues contentvalues = new ContentValues();
        
        contentvalues.put("day_id", dailyDairy.get("day_id"));
        contentvalues.put("month_id", dailyDairy.get("month_id"));

        
        contentvalues.put("dr_study_quran",            dailyDairy.get("dr_study_quran"));
        contentvalues.put("dr_study_hadith",           dailyDairy.get("dr_study_hadith"));
        contentvalues.put("dr_study_literature_is",    dailyDairy.get("dr_study_literature_is"));
        contentvalues.put("dr_study_literature_other", dailyDairy.get("dr_study_literature_other"));
        contentvalues.put("dr_study_academic",         dailyDairy.get("dr_study_academic"));

        contentvalues.put("dr_namaz_jamaat",           dailyDairy.get("dr_namaz_jamaat"));
        contentvalues.put("dr_namaz_kajja",            dailyDairy.get("dr_namaz_kajja"));

        contentvalues.put("dr_duty_call",              dailyDairy.get("dr_duty_call"));
        contentvalues.put("dr_duty_other",             dailyDairy.get("dr_duty_other"));
    

        contentvalues.put("dr_contact_member",         dailyDairy.get("dr_contact_member"));
        contentvalues.put("dr_contact_associate",      dailyDairy.get("dr_contact_associate"));
        contentvalues.put("dr_contact_worker",         dailyDairy.get("dr_contact_worker"));
        contentvalues.put("dr_contact_supporter",      dailyDairy.get("dr_contact_supporter"));
        contentvalues.put("dr_contact_friend",         dailyDairy.get("dr_contact_friend"));
        contentvalues.put("dr_contact_book",           dailyDairy.get("dr_contact_book"));
        contentvalues.put("dr_contact_talent_student", dailyDairy.get("dr_contact_talent_student"));
        contentvalues.put("dr_contact_wellwisher",     dailyDairy.get("dr_contact_wellwisher"));

        contentvalues.put("dr_class",                  dailyDairy.get("dr_class"));
        contentvalues.put("dr_criticism",              dailyDairy.get("dr_criticism"));
        contentvalues.put("dr_physical_exercise",      dailyDairy.get("dr_physical_exercise"));
        contentvalues.put("dr_newspaper",              dailyDairy.get("dr_newspaper"));
        
        return contentvalues;
    }


    public HashMap<String, String> getDailyReportFromDatabase(String dayId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        HashMap<String, String> hashmap = new HashMap();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT * FROM " + TABLE_1 + " WHERE day_id = '"))
                .append(dayId).append("' ").toString(), null);

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();


            hashmap.put("dr_study_quran",             myCursor.getString(3));
            hashmap.put("dr_study_hadith",            myCursor.getString(4));
            hashmap.put("dr_study_literature_is",     myCursor.getString(5));
            hashmap.put("dr_study_literature_other",  myCursor.getString(6));
            hashmap.put("dr_study_academic",          myCursor.getString(7));

            hashmap.put("dr_namaz_jamaat",            myCursor.getString(8));
            hashmap.put("dr_namaz_kajja",             myCursor.getString(9));

            hashmap.put("dr_duty_call",               myCursor.getString(10));
            hashmap.put("dr_duty_other",              myCursor.getString(11));

            hashmap.put("dr_contact_member",          myCursor.getString(12));
            hashmap.put("dr_contact_associate",       myCursor.getString(13));
            hashmap.put("dr_contact_worker",          myCursor.getString(14));
            hashmap.put("dr_contact_supporter",       myCursor.getString(15));
            hashmap.put("dr_contact_friend",          myCursor.getString(16));
            hashmap.put("dr_contact_book",            myCursor.getString(17));
            hashmap.put("dr_contact_talent_student",  myCursor.getString(18));
            hashmap.put("dr_contact_wellwisher",      myCursor.getString(19));

            hashmap.put("dr_class",                   myCursor.getString(20));
            hashmap.put("dr_criticism",               myCursor.getString(21));
            hashmap.put("dr_physical_exercise",       myCursor.getString(22));
            hashmap.put("dr_newspaper",               myCursor.getString(23));
            
        }
        myCursor.close();
        sqlitedatabase.close();
        return hashmap;
    }


    public String getEachColumnForDayFromDatabase(String dayId, String column) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        String data = "";
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT ")).append(column).append(" FROM ")
                .append(TABLE_1).append(" WHERE ").append("day_id").append(" = '").append(dayId).append("'").toString(), null);

        if (myCursor.moveToFirst()) {
            do {
                data = myCursor.getString(0);

            } while (myCursor.moveToNext());
        }
        myCursor.close();
        sqlitedatabase.close();
        return data;
    }


    public ArrayList<String> getEachColumnForMonthFromDatabase(String monthId, String column) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        ArrayList<String> arraylist = new ArrayList();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT ")).append(column).append(" FROM ").append(TABLE_1)
                .append(" WHERE ").append("month_id").append(" = '").append(monthId).append("'").toString(), null);

        if (myCursor.moveToFirst()) {
            do {
                arraylist.add(myCursor.getString(0));
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        sqlitedatabase.close();
        return arraylist;
    }


    public int getIdFromDayId(String dayId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT DISTINCT _id FROM " + TABLE_1 + " WHERE day_id = '"))
                .append(dayId).append("'").toString(), null);

        int i = 0;
        if (myCursor != null) {
            if (myCursor.getCount() > 0) {
                myCursor.moveToFirst();
                i = myCursor.getInt(0);
            }
        }
        myCursor.close();
        sqlitedatabase.close();
        return i;
    }


    public ArrayList<Integer> getTotalNumberOfReportKeepingInfoForMonth(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        ArrayList<Integer> arraylist = new ArrayList();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT keeping_report FROM " + TABLE_1 +
                " WHERE month_id = '")).append(monthId).append("'").toString(), null);

        if (myCursor.moveToFirst()) {
            do {
                arraylist.add(myCursor.getInt(0));
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        sqlitedatabase.close();
        return arraylist;
    }


    public int getWthereReportKeepsOrNotFromDayId(String dayId) {

        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor cursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT DISTINCT keeping_report FROM " + TABLE_1 + " WHERE day_id = '"))
                .append(dayId).append("'").toString(), null);

        int i = 0;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                i = cursor.getInt(0);
            }
        }
        cursor.close();
        sqlitedatabase.close();
        return i;
    }


    public void insertRowInTableDailyReport(HashMap<String, String> hashmap) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.insert(TABLE_1, null, setAllContentValuesForTableDailyReport(hashmap));
        sqlitedatabase.close();
    }
    

    public void updateRowInTableDailyReport(HashMap<String, String> hashmap) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.update(TABLE_1, setAllContentValuesForTableDailyReport(hashmap),
                "_id = ?", new String[]{ hashmap.get("_id")});
        sqlitedatabase.close();
    }


    //table 2
    public ContentValues settAllContentValuesForTableEtMR(HashMap<String, String> monthlyReport) {
        ContentValues contentvalues = new ContentValues();

        contentvalues.put("md_month",                            monthlyReport.get("md_month"));

        contentvalues.put("mr_quran_sura_name",                  monthlyReport.get("mr_quran_sura_name"));
        contentvalues.put("mr_quran_memorize_ayat",              monthlyReport.get("mr_quran_memorize_ayat"));
        contentvalues.put("mr_quran_darse",                      monthlyReport.get("mr_quran_darse"));
        contentvalues.put("mr_hadith_book_name",                 monthlyReport.get("mr_hadith_book_name"));
        contentvalues.put("mr_hadith_memorize_hadis",            monthlyReport.get("mr_hadith_memorize_hadis"));
        contentvalues.put("mr_hadith_darse",                     monthlyReport.get("mr_hadith_darse"));

        contentvalues.put("mr_literature_book_name",             monthlyReport.get("mr_literature_book_name"));
        contentvalues.put("mr_literature_book_note",             monthlyReport.get("mr_literature_book_note"));

        contentvalues.put("mr_contact_school_student",           monthlyReport.get("mr_contact_school_student"));
        contentvalues.put("mr_contact_teacher",                  monthlyReport.get("mr_contact_teacher"));
        contentvalues.put("mr_contact_vip",                      monthlyReport.get("mr_contact_vip"));
        contentvalues.put("mr_orgduty_total_men",                monthlyReport.get("mr_orgduty_total_men"));

        contentvalues.put("mr_distribution_poriciti",            monthlyReport.get("mr_distribution_poriciti"));
        contentvalues.put("mr_distribution_english_paper",       monthlyReport.get("mr_distribution_english_paper"));
        contentvalues.put("mr_distribution_chatrosongbad",       monthlyReport.get("mr_distribution_chatrosongbad"));
        contentvalues.put("mr_distribution_kishor_potrika",      monthlyReport.get("mr_distribution_kishor_potrika"));
        contentvalues.put("mr_distribution_perspective",         monthlyReport.get("mr_distribution_perspective"));
        contentvalues.put("mr_distribution_class_routine",       monthlyReport.get("mr_distribution_class_routine"));
        contentvalues.put("mr_distribution_stikar",              monthlyReport.get("mr_distribution_stikar"));

        contentvalues.put("mr_increment_member",                 monthlyReport.get("mr_increment_member"));
        contentvalues.put("mr_increment_member_applicant",       monthlyReport.get("mr_increment_member_applicant"));
        contentvalues.put("mr_increment_associate",              monthlyReport.get("mr_increment_associate"));
        contentvalues.put("mr_increment_associate_applicant",    monthlyReport.get("mr_increment_associate_applicant"));
        contentvalues.put("mr_increment_worker",                 monthlyReport.get("mr_increment_worker"));
        contentvalues.put("mr_increment_supporter",              monthlyReport.get("mr_increment_supporter"));
        contentvalues.put("mr_increment_friend",                 monthlyReport.get("mr_increment_friend"));
        contentvalues.put("mr_increment_wellwisher",             monthlyReport.get("mr_increment_wellwisher"));

        contentvalues.put("mr_baitulmal_baitulmal",              monthlyReport.get("mr_baitulmal_baitulmal"));
        contentvalues.put("mr_baitulmal_student_wellfare",       monthlyReport.get("mr_baitulmal_student_wellfare"));
        contentvalues.put("mr_baitulmal_jar",                    monthlyReport.get("mr_baitulmal_jar"));
        contentvalues.put("mr_baitulmal_table_bank",             monthlyReport.get("mr_baitulmal_table_bank"));

        contentvalues.put("mr_misc_nonmuslim",                  monthlyReport.get("mr_misc_nonmuslim"));
        contentvalues.put("mr_misc_friend_org",                 monthlyReport.get("mr_misc_friend_org"));
        contentvalues.put("mr_misc_muharrama_contact",          monthlyReport.get("mr_misc_muharrama_contact"));
        contentvalues.put("mr_suggestion",                      monthlyReport.get("mr_suggestion"));

        return contentvalues;
    }


    public HashMap<String, String> getAllContentFromTableEtMR(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        HashMap<String, String> hashmap = new HashMap();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT * FROM " + TABLE_2 + " WHERE md_month='"))
                .append(monthId).append("'").toString(), null);

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();

            hashmap.put("mr_quran_sura_name",             myCursor.getString(2));
            hashmap.put("mr_quran_darse",                 myCursor.getString(3));
            hashmap.put("mr_quran_memorize_ayat",         myCursor.getString(4));
            hashmap.put("mr_hadith_book_name",            myCursor.getString(5));
            hashmap.put("mr_hadith_darse",                myCursor.getString(6));
            hashmap.put("mr_hadith_memorize_hadis",       myCursor.getString(7));


            hashmap.put("mr_literature_book_name",        myCursor.getString(8));
            hashmap.put("mr_literature_book_note",        myCursor.getString(9));

            hashmap.put("mr_contact_school_student",      myCursor.getString(10));
            hashmap.put("mr_contact_teacher",             myCursor.getString(11));
            hashmap.put("mr_contact_vip",                 myCursor.getString(12));
            hashmap.put("mr_orgduty_total_men",           myCursor.getString(13));


            hashmap.put("mr_distribution_poriciti",       myCursor.getString(14));
            hashmap.put("mr_distribution_english_paper",  myCursor.getString(15));
            hashmap.put("mr_distribution_chatrosongbad",  myCursor.getString(16));
            hashmap.put("mr_distribution_kishor_potrika", myCursor.getString(17));
            hashmap.put("mr_distribution_perspective",    myCursor.getString(18));
            hashmap.put("mr_distribution_class_routine",  myCursor.getString(19));
            hashmap.put("mr_distribution_stikar",         myCursor.getString(20));

            hashmap.put("mr_increment_member",            myCursor.getString(21));
            hashmap.put("mr_increment_member_applicant",  myCursor.getString(22));
            hashmap.put("mr_increment_associate",         myCursor.getString(23));
            hashmap.put("mr_increment_associate_applicant",  myCursor.getString(24));
            hashmap.put("mr_increment_worker",            myCursor.getString(25));
            hashmap.put("mr_increment_supporter",         myCursor.getString(26));
            hashmap.put("mr_increment_friend",            myCursor.getString(27));
            hashmap.put("mr_increment_wellwisher",        myCursor.getString(28));


            hashmap.put("mr_baitulmal_baitulmal",         myCursor.getString(29));
            hashmap.put("mr_baitulmal_student_wellfare",  myCursor.getString(30));
            hashmap.put("mr_baitulmal_jar",               myCursor.getString(31));
            hashmap.put("mr_baitulmal_table_bank",        myCursor.getString(32));

            hashmap.put("mr_misc_nonmuslim",              myCursor.getString(33));
            hashmap.put("mr_misc_friend_org",             myCursor.getString(34));
            hashmap.put("mr_misc_muharrama_contact",      myCursor.getString(35));

            hashmap.put("mr_suggestion",                  myCursor.getString(36));

        }
        myCursor.close();
        sqlitedatabase.close();
        return hashmap;
    }


    public void updateRowInTableEtMR(HashMap<String, String> hashmap) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.update(TABLE_2, settAllContentValuesForTableEtMR(hashmap),
                "md_month = ?", new String[]{ hashmap.get("md_month")});
        sqlitedatabase.close();
    }


    public void insertRowInTableEtOfMR(HashMap<String, String> hashmap) {

        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.insert(TABLE_2, null, settAllContentValuesForTableEtMR(hashmap));
        sqlitedatabase.close();
    }

    
    //table 3
    public ContentValues setAllContentValuesForTableMonthlyPlan(HashMap<String, String> monthlyPlan) {
        ContentValues contentvalues = new ContentValues();

        contentvalues.put(AllData.MP_Month_Id,                      monthlyPlan.get(AllData.MP_Month_Id));
        contentvalues.put(AllData.MP_Q_Sura_Name,                   monthlyPlan.get(AllData.MP_Q_Sura_Name));
        contentvalues.put(AllData.MP_Q_Total_day,                   monthlyPlan.get(AllData.MP_Q_Total_day));
        contentvalues.put(AllData.MP_Q_Average_Ayat,                monthlyPlan.get(AllData.MP_Q_Average_Ayat));
        contentvalues.put(AllData.MP_Q_Darse,                       monthlyPlan.get(AllData.MP_Q_Darse));
        contentvalues.put(AllData.MP_Q_Memorize_Ayat,               monthlyPlan.get(AllData.MP_Q_Memorize_Ayat));

        contentvalues.put(AllData.MP_H_Book_Name,                   monthlyPlan.get(AllData.MP_H_Book_Name));
        contentvalues.put(AllData.MP_H_Total_day,                   monthlyPlan.get(AllData.MP_H_Total_day));
        contentvalues.put(AllData.MP_H_Average_Hadis,               monthlyPlan.get(AllData.MP_H_Average_Hadis));
        contentvalues.put(AllData.MP_H_Darse,                       monthlyPlan.get(AllData.MP_H_Darse));
        contentvalues.put(AllData.MP_H_Memorize_Hadis,              monthlyPlan.get(AllData.MP_H_Memorize_Hadis));

        contentvalues.put(AllData.MP_L_Book_Name,                   monthlyPlan.get(AllData.MP_L_Book_Name));
        contentvalues.put(AllData.MP_L_Total_page,                  monthlyPlan.get(AllData.MP_L_Total_page));
        contentvalues.put(AllData.MP_L_Islamic_Page,                monthlyPlan.get(AllData.MP_L_Islamic_Page));
        contentvalues.put(AllData.MP_L_Other_Page,                  monthlyPlan.get(AllData.MP_L_Other_Page));
        contentvalues.put(AllData.MP_L_Book_Note,                   monthlyPlan.get(AllData.MP_L_Book_Note));

        contentvalues.put(AllData.MP_A_Total_Day,                   monthlyPlan.get(AllData.MP_A_Total_Day));
        contentvalues.put(AllData.MP_A_Average_Hour,                monthlyPlan.get(AllData.MP_A_Average_Hour));
        contentvalues.put(AllData.MP_A_Total_Present_Class,         monthlyPlan.get(AllData.MP_A_Total_Present_Class));

        contentvalues.put(AllData.MP_N_Jamaat,                      monthlyPlan.get(AllData.MP_N_Jamaat));
        contentvalues.put(AllData.MP_N_Nofol,                       monthlyPlan.get(AllData.MP_N_Nofol));

        contentvalues.put(AllData.MP_OD_C_Total_Day,                monthlyPlan.get(AllData.MP_OD_C_Total_Day));
        contentvalues.put(AllData.MP_OD_C_Total_Hour,               monthlyPlan.get(AllData.MP_OD_C_Total_Hour));
        contentvalues.put(AllData.MP_OD_O_Total_Day,                monthlyPlan.get(AllData.MP_OD_O_Total_Day));
        contentvalues.put(AllData.MP_OD_O_Average_Hour,             monthlyPlan.get(AllData.MP_OD_O_Average_Hour));

        contentvalues.put(AllData.MP_C_Member,                      monthlyPlan.get(AllData.MP_C_Member));
        contentvalues.put(AllData.MP_C_Associate,                   monthlyPlan.get(AllData.MP_C_Associate));
        contentvalues.put(AllData.MP_C_Worker,                      monthlyPlan.get(AllData.MP_C_Worker));
        contentvalues.put(AllData.MP_C_Supporter,                   monthlyPlan.get(AllData.MP_C_Supporter));
        contentvalues.put(AllData.MP_C_Friend,                      monthlyPlan.get(AllData.MP_C_Friend));
        contentvalues.put(AllData.MP_C_School_Student,              monthlyPlan.get(AllData.MP_C_School_Student));
        contentvalues.put(AllData.MP_C_Talent_Student,              monthlyPlan.get(AllData.MP_C_Talent_Student));
        contentvalues.put(AllData.MP_C_WellWisher,                  monthlyPlan.get(AllData.MP_C_WellWisher));
        contentvalues.put(AllData.MP_C_Teacher,                     monthlyPlan.get(AllData.MP_C_Teacher));
        contentvalues.put(AllData.MP_C_VIP,                         monthlyPlan.get(AllData.MP_C_VIP));


        contentvalues.put(AllData.MP_D_Islamic_Literature,          monthlyPlan.get(AllData.MP_D_Islamic_Literature));
        contentvalues.put(AllData.MP_D_Kishor_Potrika,              monthlyPlan.get(AllData.MP_D_Kishor_Potrika));
        contentvalues.put(AllData.MP_D_English_Paper,               monthlyPlan.get(AllData.MP_D_English_Paper));
        contentvalues.put(AllData.MP_D_Chatrosongbad,               monthlyPlan.get(AllData.MP_D_Chatrosongbad));
        contentvalues.put(AllData.MP_D_Perspective,                 monthlyPlan.get(AllData.MP_D_Perspective));
        contentvalues.put(AllData.MP_D_Poriciti,                    monthlyPlan.get(AllData.MP_D_Poriciti));
        contentvalues.put(AllData.MP_D_Class_Routine,               monthlyPlan.get(AllData.MP_D_Class_Routine));
        contentvalues.put(AllData.MP_D_Stikar_Card,                 monthlyPlan.get(AllData.MP_D_Stikar_Card));

        contentvalues.put(AllData.MP_I_Member,                      monthlyPlan.get(AllData.MP_I_Member));
        contentvalues.put(AllData.MP_I_Member_Applicant,            monthlyPlan.get(AllData.MP_I_Member_Applicant));
        contentvalues.put(AllData.MP_I_Associate,                   monthlyPlan.get(AllData.MP_I_Associate));
        contentvalues.put(AllData.MP_I_Associate_Applicant,         monthlyPlan.get(AllData.MP_I_Associate_Applicant));
        contentvalues.put(AllData.MP_I_Worker,                      monthlyPlan.get(AllData.MP_I_Worker));
        contentvalues.put(AllData.MP_I_Supporter,                   monthlyPlan.get(AllData.MP_I_Supporter));
        contentvalues.put(AllData.MP_I_Friend,                      monthlyPlan.get(AllData.MP_I_Friend));
        contentvalues.put(AllData.MP_I_Wellwisher,                  monthlyPlan.get(AllData.MP_I_Wellwisher));


        contentvalues.put(AllData.MP_BM_Baitulmal,                  monthlyPlan.get(AllData.MP_BM_Baitulmal));
        contentvalues.put(AllData.MP_BM_Student_Wellfare,           monthlyPlan.get(AllData.MP_BM_Student_Wellfare));
        contentvalues.put(AllData.MP_BM_Jar,                        monthlyPlan.get(AllData.MP_BM_Jar));
        contentvalues.put(AllData.MP_BM_Table_Bank,                 monthlyPlan.get(AllData.MP_BM_Table_Bank));


        contentvalues.put(AllData.MP_Misc_Criticism,                monthlyPlan.get(AllData.MP_Misc_Criticism));
        contentvalues.put(AllData.MP_Misc_Physical,                 monthlyPlan.get(AllData.MP_Misc_Physical));
        contentvalues.put(AllData.MP_Misc_Newspaper,                monthlyPlan.get(AllData.MP_Misc_Newspaper));
        contentvalues.put(AllData.MP_Misc_Muharram,                 monthlyPlan.get(AllData.MP_Misc_Muharram));
        contentvalues.put(AllData.MP_Misc_Non_Muslim,               monthlyPlan.get(AllData.MP_Misc_Non_Muslim));
        contentvalues.put(AllData.MP_Misc_Friend_Organization,      monthlyPlan.get(AllData.MP_Misc_Friend_Organization));
        return contentvalues;
    }



    public HashMap<String, String> getAllContentFromTableMonthlyPlan(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        HashMap<String, String> hashmap = new HashMap();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT * FROM " + TABLE_3 + " WHERE month_id ='"))
                .append(monthId).append("'").toString(), null);

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();

            hashmap.put(AllData.MP_Month_Id, myCursor.getString(2));
            hashmap.put(AllData.MP_Q_Sura_Name,                 myCursor.getString(3));
            hashmap.put(AllData.MP_Q_Total_day,                 myCursor.getString(4));
            hashmap.put(AllData.MP_Q_Average_Ayat,              myCursor.getString(5));
            hashmap.put(AllData.MP_Q_Darse,                     myCursor.getString(6));
            hashmap.put(AllData.MP_Q_Memorize_Ayat,             myCursor.getString(7));

            hashmap.put(AllData.MP_H_Book_Name,                 myCursor.getString(8));
            hashmap.put(AllData.MP_H_Total_day,                 myCursor.getString(9));
            hashmap.put(AllData.MP_H_Average_Hadis,             myCursor.getString(10));
            hashmap.put(AllData.MP_H_Darse,                     myCursor.getString(11));
            hashmap.put(AllData.MP_H_Memorize_Hadis,            myCursor.getString(12));

            hashmap.put(AllData.MP_L_Book_Name,                 myCursor.getString(13));
            hashmap.put(AllData.MP_L_Total_page,                myCursor.getString(14));
            hashmap.put(AllData.MP_L_Islamic_Page,              myCursor.getString(15));
            hashmap.put(AllData.MP_L_Other_Page,                myCursor.getString(16));
            hashmap.put(AllData.MP_L_Book_Note,                 myCursor.getString(17));

            hashmap.put(AllData.MP_A_Total_Day,                 myCursor.getString(18));
            hashmap.put(AllData.MP_A_Average_Hour,              myCursor.getString(19));
            hashmap.put(AllData.MP_A_Total_Present_Class,       myCursor.getString(20));

            hashmap.put(AllData.MP_N_Jamaat,                    myCursor.getString(21));
            hashmap.put(AllData.MP_N_Nofol,                     myCursor.getString(22));

            hashmap.put(AllData.MP_OD_C_Total_Day,              myCursor.getString(23));
            hashmap.put(AllData.MP_OD_C_Total_Hour,             myCursor.getString(24));
            hashmap.put(AllData.MP_OD_O_Total_Day,              myCursor.getString(25));
            hashmap.put(AllData.MP_OD_O_Average_Hour,           myCursor.getString(26));

            hashmap.put(AllData.MP_C_Member,                    myCursor.getString(27));
            hashmap.put(AllData.MP_C_Associate,                 myCursor.getString(28));
            hashmap.put(AllData.MP_C_Worker,                    myCursor.getString(29));
            hashmap.put(AllData.MP_C_Supporter,                 myCursor.getString(30));
            hashmap.put(AllData.MP_C_Friend,                    myCursor.getString(31));
            hashmap.put(AllData.MP_C_School_Student,            myCursor.getString(32));
            hashmap.put(AllData.MP_C_Talent_Student,            myCursor.getString(33));
            hashmap.put(AllData.MP_C_WellWisher,                myCursor.getString(34));
            hashmap.put(AllData.MP_C_Teacher,                   myCursor.getString(35));
            hashmap.put(AllData.MP_C_VIP,                       myCursor.getString(36));

            hashmap.put(AllData.MP_D_Islamic_Literature,        myCursor.getString(37));
            hashmap.put(AllData.MP_D_Kishor_Potrika,            myCursor.getString(38));
            hashmap.put(AllData.MP_D_English_Paper,             myCursor.getString(39));
            hashmap.put(AllData.MP_D_Chatrosongbad,             myCursor.getString(40));
            hashmap.put(AllData.MP_D_Perspective,               myCursor.getString(41));
            hashmap.put(AllData.MP_D_Poriciti,                  myCursor.getString(42));
            hashmap.put(AllData.MP_D_Class_Routine,             myCursor.getString(43));
            hashmap.put(AllData.MP_D_Stikar_Card,               myCursor.getString(44));

            hashmap.put(AllData.MP_I_Member,                    myCursor.getString(45));
            hashmap.put(AllData.MP_I_Member_Applicant,          myCursor.getString(46));
            hashmap.put(AllData.MP_I_Associate,                 myCursor.getString(47));
            hashmap.put(AllData.MP_I_Associate_Applicant,       myCursor.getString(48));
            hashmap.put(AllData.MP_I_Worker,                    myCursor.getString(49));
            hashmap.put(AllData.MP_I_Supporter,                 myCursor.getString(50));
            hashmap.put(AllData.MP_I_Friend,                    myCursor.getString(51));
            hashmap.put(AllData.MP_I_Wellwisher,                myCursor.getString(52));

            hashmap.put(AllData.MP_BM_Baitulmal,                myCursor.getString(53));
            hashmap.put(AllData.MP_BM_Student_Wellfare,         myCursor.getString(54));
            hashmap.put(AllData.MP_BM_Jar,                      myCursor.getString(55));
            hashmap.put(AllData.MP_BM_Table_Bank,               myCursor.getString(56));

            hashmap.put(AllData.MP_Misc_Criticism,              myCursor.getString(57));
            hashmap.put(AllData.MP_Misc_Physical,               myCursor.getString(58));
            hashmap.put(AllData.MP_Misc_Newspaper,              myCursor.getString(59));
            hashmap.put(AllData.MP_Misc_Muharram,               myCursor.getString(60));
            hashmap.put(AllData.MP_Misc_Non_Muslim,             myCursor.getString(61));
            hashmap.put(AllData.MP_Misc_Friend_Organization,    myCursor.getString(62));

        }
        myCursor.close();
        sqlitedatabase.close();
        return hashmap;
    }
    


    public void insertRowInTableMonthlyPlan(HashMap<String, String> hashmap) {

        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.insert(TABLE_3, null, setAllContentValuesForTableMonthlyPlan(hashmap));
        sqlitedatabase.close();
    }



    public void updateRowInTableMonthlyPlan(HashMap<String, String> hashmap) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.update(TABLE_3, setAllContentValuesForTableMonthlyPlan(hashmap),
                "month_id = ?", new String[]{ hashmap.get(AllData.MP_Month_Id)});
        sqlitedatabase.close();
    }


    public int checkMonthlyPlanExistOrNot(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT  keeping_plan FROM " +
                TABLE_3 + " WHERE month_id = '")).append(monthId).append("'").toString(), null);

        int i = 0;
        if (myCursor != null) {
            if (myCursor.getCount() > 0) {
                myCursor.moveToFirst();
                i = myCursor.getInt(0);
            }
        }
        myCursor.close();
        sqlitedatabase.close();
        return i;
    }


}
