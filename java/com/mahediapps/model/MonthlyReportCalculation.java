package com.mahediapps.model;

import android.content.Context;

import java.util.ArrayList;

public class MonthlyReportCalculation {

    Context context;
    DatabaseHelper dbHelper;

    public MonthlyReportCalculation(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }


    public String getAverageType(String monthId, String type) {

        Double totalDay = Double.parseDouble(getTotalTypeDay(monthId, type));
        Double totalData = Double.parseDouble(getTotalType(monthId, type));

        if (totalData == 0.0) {
            return "0.0";
        } else {
            return String.valueOf(totalData / totalDay);
        }
    }


    public String getTotalType(String monthId, String type) {
        ArrayList<String> typeTotal = dbHelper.getEachColumnForMonthFromDatabase(monthId, type);
        double sum = 0.0D;

        for (int i = 0; i < typeTotal.size(); i++) {
            if ((typeTotal.get(i) != null) && (!(typeTotal.get(i)).equals("")) &&  (!(typeTotal.get(i)).equals("."))){

                try{
                     Double.parseDouble(typeTotal.get(i));
                }catch (Throwable e){
                    continue;
                }
                sum = sum + Double.parseDouble(typeTotal.get(i));
            }
        }
        return String.valueOf((int) sum);
    }


    public String getTotalTypeDay(String monthId, String type) {
        ArrayList<String> typeTotal = dbHelper.getEachColumnForMonthFromDatabase(monthId, type);
        int sum = 0;
        Double value;
        for (int i = 0; i < typeTotal.size(); i++) {
            if ((typeTotal.get(i) != null) && (!(typeTotal.get(i)).equals("")) &&  (!(typeTotal.get(i)).equals("."))) {
                try{
                    value = Double.parseDouble(typeTotal.get(i));
                }catch (Throwable e){
                    continue;
                }

                if(!(value == 0.0D)){
                    sum++;
                }
            }
        }
        return String.valueOf(sum);
    }


    public String getAllContact(String monthId, String contactType) {
        double sum = 0.0D;
        ArrayList<String> allContact = dbHelper.getEachColumnForMonthFromDatabase(monthId, contactType);

        for (int i = 0; i < allContact.size(); i++) {
            if ((allContact.get(i) != null) && (!(allContact.get(i)).equals(""))) {
                sum = sum + Double.parseDouble(allContact.get(i));
            }
        }

        return String.valueOf((int) sum);
    }


    public String getAllOthers(String monthId, String appendixType) {
        int sum = 0;

        ArrayList<String> allOthers = dbHelper.getEachColumnForMonthFromDatabase(monthId, appendixType);
        for (int i = 0; i < allOthers.size(); i++) {
            if ((allOthers.get(i) != null) && (!(allOthers.get(i)).equals(""))) {
                sum = sum + Integer.parseInt(allOthers.get(i));
            }
        }
        return String.valueOf(sum);
    }


    public String getTotalPage(String monthId, String islamicPage, String otherPage) {
        return String.valueOf((Integer.parseInt(getAllOthers(monthId, islamicPage)) +
                Integer.parseInt(getAllOthers(monthId, otherPage))));
    }

}
