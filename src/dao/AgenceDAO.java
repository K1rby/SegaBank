package dao;

import bo.Agence;
import dal.IDAO;
import dal.PersistanceManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenceDAO implements IDAO<Long, Agence> {

    private static final String INSERT_QUERY = "INSERT INTO agence (code, adresse ) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE agence SET code = ?, adresse = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM agence WHERE id= ?";
    private static final String FIND_QUERY = "SELECT * FROM agence WHERE id= ?";
    private static final String FIND_ALLQUERY = "SELECT * FROM agence";

    @Override
    public void create(Agence object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, object.getCode());
                ps.setString(2, object.getAdresse());
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
    public void update(Agence object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

                ps.setString(1, object.getCode());
                ps.setString(2, object.getAdresse());
                ps.setInt(3, object.getId());
                ps.executeUpdate();
            }
        }

    }

    @Override
    public void remove(Agence object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Agence findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        Agence agence = null;
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1, aLong);
                try(ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        //compte = new CompteSimple();
                        agence.setCode(rs.getString("code"));
                        agence.setId(rs.getInt("id"));
                        agence.setAdresse(rs.getString("adresse"));
                    }
                }
            }
        }
        return agence;
    }

    @Override
    public List<Agence> findAll(int idAgence) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Agence> findAllAgences() throws SQLException, IOException, ClassNotFoundException {

        List<Agence> list = new ArrayList<>();
        Connection connection = PersistanceManager.getConnection();

        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_ALLQUERY)) {
                try(ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Agence agence = new Agence();
                        agence.setCode(rs.getString("code"));
                        agence.setId(rs.getInt("id"));
                        agence.setAdresse(rs.getString("adresse"));
                        list.add(agence);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<Agence> findAllOperations(int idCompte) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }
}
