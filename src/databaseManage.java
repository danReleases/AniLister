import java.util.ArrayList;
import java.util.Collections;

public class databaseManage {

     public enum days{
        unknown, Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday;
    }
    
    public static ArrayList<ani_object> database = new ArrayList<>();
    
    public static void AddElement(String title,int ep,int index)
    { 
       database.add(new ani_object(title,ep,days.values()[index]));  
    }
    
     public static void sort_db(){
        Collections.sort​(database, (ani_object a, ani_object b) -> a.air_day.compareTo(b.air_day));
    }
    
     public static void clear_db(){
         database.clear();
     }
}
