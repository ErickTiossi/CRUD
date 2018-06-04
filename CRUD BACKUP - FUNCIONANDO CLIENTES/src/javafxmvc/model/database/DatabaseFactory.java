package javafxmvc.model.database;

public class DatabaseFactory {
    public static IDataBase getDataBase(String name){
        if(name.equals("mysql")){
            return new MySQLDataBase();
        } else if (name.equals("postgree")) {
            //return new DabaseMySql();
        }
        return null;
    }
}
