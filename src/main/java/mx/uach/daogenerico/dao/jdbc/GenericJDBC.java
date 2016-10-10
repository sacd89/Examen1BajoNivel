package mx.uach.daogenerico.dao.jdbc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uach.daogenerico.dao.GenericDao;
import mx.uach.daogenerico.enums.CRUD;
import mx.uach.daogenerico.generico.Model;
import mx.uach.daogenerico.dao.jdbc.helper.GenericJDBCHelper;
import mx.uach.videoclub.conexion.Conexion;

/**
 * Modelo generico para implementar el DAO.
 *
 * @author Daniela Santillanes Castro
 * @version 1.0
 * @param <T> que es la clase que se traera de parametro.
 * @since 07/10/2016
 */
public class GenericJDBC<T extends Model<T>> implements GenericDao<T> {

    Model<T> t;

    private Class<T> type;

    public GenericJDBC() {
        this.t = new Model<>();
        Type t = getClass().getGenericSuperclass();
        System.out.println("t = " + t);
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * Metodo que regresa un objeto basado en un id del registro de la base de
     * datos.
     *
     * @param id entero que identifica la entidad.
     * @return null si el id no se encuentra en la base de datos ó un objeto si
     * se encuentra en la base de datos.
     */
    @Override
    public T getById(Integer id) {
        try {
            Statement st = Conexion.getInstance().getCon().createStatement();
            ResultSet rs = st.executeQuery(String.format("%s %s %s ", t.Q,
                    t.Q_WHERE_ID, id));
            T obj = null;
            while (rs.next()) {
                obj = (T) GenericJDBCHelper.makeGeneric(rs, t.getClass());
            }
            return obj;
        } catch (SQLException ex) {

        }
        return null;
    }

    /**
     * Metodo que regresa una lista de objetos basado en un criterio de
     * busqueda.
     *
     * @param criterio que es la restricción impuesta en la consulta para
     * obtener registros especificos de la base de datos.
     * @return objects que es la lista de objeto
     */
    @Override
    public List<T> getByCriteria(String criterio) {
        List<T> objects = new ArrayList<>();
        try {
            Statement st = Conexion.getInstance().getCon().createStatement();
            ResultSet rs = st.executeQuery(String.format("%s %s %s ", t.Q,
                    criterio.isEmpty() ? "" : t.Q_WHERE, criterio));
            T obj = null;
            while (rs.next()) {
                obj = (T) GenericJDBCHelper.makeGeneric(rs, t.getClass());
                objects.add(obj);
            }

        } catch (SQLException ex) {

        }
        return objects;
    }

    /**
     * Metodo donde se realizar operaciones CRUD.
     * 
     * @param t es la clase del modelo.
     * @param crud que es el enum de las operaciones CRUD.
     */
    @Override
    public void genericProcess(T t, CRUD crud) {
        try {
            PreparedStatement ps = null;
            switch (crud) {
                case CREATE:
                    ps = Conexion.getInstance().
                            getCon().prepareStatement(t.INSERT_GENERIC);
                    ps.setString(1, t.getNombre());
                    break;
                case UPDATE:
                    //UPDATE TABLA SET()
                    ps = Conexion.getInstance().
                            getCon().prepareStatement(t.UPDATE_GENERIC);
                    ps.setString(1, t.getNombre());
                    ps.setInt(2, t.getId());
                    break;
                case DELETE:
                    ps = Conexion.getInstance().
                            getCon().prepareStatement(t.DELETE_GENERIC);
                    ps.setInt(1, t.getId());
                    break;
                default:
                    break;
            }

            Boolean result = ps.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
