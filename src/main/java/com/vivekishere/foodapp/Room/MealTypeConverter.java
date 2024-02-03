package com.vivekishere.foodapp.Room;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@TypeConverters
public class MealTypeConverter {
    @TypeConverter
    public String fromObjectToString(Object object){
        if (object==null){
            return "";
        }else {
            return object.toString();
        }
    }
    @TypeConverter
    public Object fromStringToObject(String string){
        if (string==null){
            return "";
        } else {
            return string;
        }
    }
}
