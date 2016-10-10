package mx.uach.daogenerico.dao;

import java.util.List;
import mx.uach.daogenerico.enums.CRUD;

/**
 * Declaración de los métodos de escritura y lectura de un modelo a otro.
 *
 * @author Daniela Santillanes Castro
 * @version 1.0
 * @param <T> que es la clase que traemos de parametro
 * @since 07/10/2016
 */
public interface GenericDao<T> {

    /**
     * Metodo que regresa un objeto basado en un id del registro de la base de
     * datos.
     *
     * @param id entero que identifica la entidad.
     * @return null si el id no se encuentra en la base de datos ó un objeto si
     * se encuentra en la base de datos.
     */
    public T getById(Integer id);

    /**
     * Metodo que regresa una lista de objetos basado en un criterio de
     * busqueda.
     *
     * @param criterio que es la restricción impuesta en la consulta para
     * obtener registros especificos de la base de datos.
     * @return objects que es la lista de objeto.
     */
    public List<T> getByCriteria(String criterio);
    
    /**
     * Metodo donde se realizar operaciones CRUD.
     *
     * @param t es la clase del modelo.
     * @param crud que es el enum de las operaciones CRUD.
     */
    public void genericProcess(T t, CRUD crud);
}
