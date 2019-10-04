package dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IDAO<ID, E> {

    void create(E object) throws SQLException, IOException, ClassNotFoundException;

    void update(E object) throws SQLException, IOException, ClassNotFoundException;

    void remove(E object) throws SQLException, IOException, ClassNotFoundException;

    E findById(ID id) throws SQLException, IOException, ClassNotFoundException;

    List<E> findAll() throws SQLException, IOException, ClassNotFoundException;
}
