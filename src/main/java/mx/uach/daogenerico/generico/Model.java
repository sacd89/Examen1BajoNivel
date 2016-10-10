package mx.uach.daogenerico.generico;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modelo general de todo el sistema.
 *
 * @author Daniela Santillanes Castro
 * @version 1.0
 */
public class Model<T> {

    T obj;

    public String Q_WHERE_ID = "WHERE id = ";

    public String Q_WHERE = "WHERE";

    public String INSERT = "INSERT INTO";

    public String UPDATE = "UPDATE";

    public String DELETE = "DELETE FROM";

    public String ID = "id";

    public String TABLA = obj.getClass().getName();

    public String Q = String.format("SELECT %s FROM %s",
            fieldsToQuery(fieldNames(TABLA), Boolean.FALSE), TABLA);

    public String INSERT_GENERIC
            = String.format("%s %s (%s) VALUES (%s);",
                    INSERT, TABLA, fieldsToQuery(fieldNames(TABLA), Boolean.TRUE),
                    paramsToStatement(fieldNames(TABLA), Boolean.TRUE));

    public String UPDATE_GENERIC
            = String.format("%s %s SET %s WHERE %s = ?",
                    UPDATE, TABLA, paramsToStatementToCreate(fieldNames(TABLA), Boolean.TRUE),
                    ID);

    public String DELETE_GENERIC
            = String.format("%s %s %s ?", DELETE, TABLA, Q_WHERE_ID);

    private Integer id;
    private String nombre;

    public Model() {
    }

    public Model(Integer id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Convierte de un arreglo de campos a un String para un query.
     *
     * @param fields son los atributos de la tabla.
     * @param noId si es verdadero excluye el campo id.
     * @return attr1, attr2, ... attrn
     */
    public String fieldsToQuery(List<String> fields, Boolean noId) {
        String campos = "";
        fields = noId
                ? fields.stream()
                .filter(field -> !field.equals(ID))
                .collect(Collectors.toList()) : fields;
        for (String field : fields) {
            campos = String.format("%s, %s", campos, field);
        }
        return campos.substring(1);
    }

    public String paramsToStatement(List<String> fields, Boolean noId) {
        String campos = "";
        fields = noId
                ? fields.stream()
                .filter(field -> !field.equals(ID))
                .collect(Collectors.toList()) : fields;
        for (String field : fields) {
            campos = String.format("%s, ?", campos);
        }
        return campos.substring(1);
    }

    public String paramsToStatementToCreate(List<String> fields, Boolean noId) {
        String campos = "";
        fields = noId
                ? fields.stream()
                .filter(field -> !field.equals(ID))
                .collect(Collectors.toList()) : fields;
        for (String field : fields) {
            campos = String.format("%s, %s = ?", campos, field);
        }
        return campos.substring(1);
    }

    public static List<String> fieldNames(Object obj) {
        List<String> fields = new ArrayList<>();
        for (Field f : obj.getClass().getFields()) {
            fields.add(f.getName());
        }
        return fields;
    }
}
