package mx.uach.daogenerico.dao.jdbc.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.uach.daogenerico.generico.Model;

/**
 * Helper que genera objetos del Dao
 * 
 * @author Daniela Santillanes Castro
 * @version 1.0
 * @since 07/10/2016
 */
public class GenericJDBCHelper{
    
    public final static Model makeGeneric(ResultSet rs, Class clase) throws SQLException {
        Model obj = new Model();
                rs.getString(Model.FIELDS[1]));
        return obj;
    }
}
