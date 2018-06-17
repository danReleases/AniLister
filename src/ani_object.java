public class ani_object {
 public String show_title;
 public int count_episodes;
 public databaseManage.days air_day;
 
 ani_object(String name,int n,databaseManage.days d)
 {
    this.show_title=name;
    this.count_episodes=n;
    this.air_day=d;
 }
 
 public String day_string()
 {  
     return(""+this.air_day);
 }
 
 @Override
 public String toString()
 {
     return(""+(databaseManage.database.size())+". "+show_title+" - "+count_episodes+" - "+air_day);
 }
}
