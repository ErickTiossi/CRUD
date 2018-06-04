package javafxmvc.model.database;

import java.sql.Connection;

public interface IDataBase {
    public Connection Connect();
    public void Disconnect(Connection conn);
}
