package dao;

import dal.IDAO;
import dal.PersistanceManager;

import java.io.IOException;
import java.sql.*;

public class CompteDAO implements IDAO<Long, Compte> {

    private static final String INSERT_QUERY = "INSERT INTO compte (nom, email) VALUES (?,?)";
    private static final String UPDATE_QUERY = "UPDATE compte SET nom = ?, email = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM compte WHERE id= ?";
    private static final String FIND_QUERY = "SELECT * FROM compte WHERE id= ?";
    private static final String FIND_ALLQUERY = "SELECT * FROM contact";

    @Override
    public void create(Compte object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, object.getName());
                ps.setString(2, object.getEmail());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        object.setId(rs.getInt(1));
                    }

                }
            }
        }
    }

    @Override
    public void update(Compte object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

                ps.setString(1, object.getName());
                ps.setString(2, object.getEmail());
                ps.setInt(3, object.getId());
                ps.executeUpdate();
            }
        }

    }

    @Override
    public void remove(Compte object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Compte findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        Compte compte = null;
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1, aLong);
                try(ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        compte = new Compte();
                        compte.setId(rs.getInt("id"));
                        compte.setName(rs.getString("nom"));
                        compte.setEmail(rs.getString("email"));
                    }
                }
            }
        }
        return compte;
    }
}
