package dao;

import bo.Compte;
import dal.IDAO;
import dal.PersistanceManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAO implements IDAO<Long, Compte> {

    private static final String INSERT_QUERY = "INSERT INTO compte (solde, type) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE compte SET solde = ?, SET type = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM compte WHERE id= ?";
    private static final String FIND_QUERY = "SELECT * FROM compte WHERE id= ?";
    private static final String FIND_ALLQUERY = "SELECT * FROM compte";

    @Override
    public void create(Compte object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                ps.setDouble(1, object.getSolde());
                ps.setString(2, object.getType());
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

                ps.setDouble(1, object.getSolde());
                ps.setString(2, object.getType());
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
                        //compte = new CompteSimple();
                        compte.setId(rs.getInt("id"));
                        compte.setSolde(rs.getDouble("solde"));
                        compte.setType(rs.getString("type"));
                    }
                }
            }
        }
        return compte;
    }

    @Override
    public List<Compte> findAll() throws SQLException, IOException, ClassNotFoundException {

        List<Compte> list = new ArrayList<>();
        Compte compte = null;
        Connection connection = PersistanceManager.getConnection();

        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_ALLQUERY)) {
                try(ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        //compte = new Compte();
                        compte.setId(rs.getInt("id"));
                        compte.setSolde(rs.getDouble("solde"));
                        compte.setType(rs.getString("type"));
                        list.add(compte);
                    }
                }
            }
        }
        return list;
    }
}
