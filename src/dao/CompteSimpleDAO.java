package dao;

import bo.Compte;
import bo.CompteSimple;
import dal.IDAO;
import dal.PersistanceManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteSimpleDAO implements IDAO<Long, CompteSimple> {

    private static final String INSERT_QUERY = "INSERT INTO compte (solde, type, decouvert, idAgence) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE compte SET solde= ?, type= ?, decouvert= ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM compte WHERE id= ?";
    private static final String FIND_QUERY = "SELECT * FROM compte WHERE id= ?";
    private static final String FIND_ALLQUERY = "SELECT * FROM compte WHERE type = 'simple' AND idAgence = ?";

    @Override
    public void create(CompteSimple object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                ps.setDouble(1, object.getSolde());
                ps.setString(2, object.getType());
                ps.setInt(3, object.getDecouvert());
                ps.setInt(4, object.getIdAgence());
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
    public void update(CompteSimple object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

                ps.setDouble(1, object.getSolde());
                ps.setString(2, object.getType());
                ps.setInt(3, object.getDecouvert());
                ps.setInt(4, object.getId());
                ps.executeUpdate();
            }
        }

    }

    @Override
    public void remove(CompteSimple object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public CompteSimple findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        CompteSimple compte_simple = null;
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1, aLong);
                try(ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        //compte = new CompteSimple();
                        compte_simple.setId(rs.getInt("id"));
                        compte_simple.setSolde(rs.getDouble("solde"));
                        compte_simple.setType(rs.getString("type"));
                        compte_simple.setDecouvert(rs.getInt("decouvert"));
                    }
                }
            }
        }
        return compte_simple;
    }

    @Override
    public List<CompteSimple> findAll(int idAgence) throws SQLException, IOException, ClassNotFoundException {

        List<CompteSimple> list = new ArrayList<>();
        Connection connection = PersistanceManager.getConnection();

        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_ALLQUERY)) {
                ps.setLong(1, idAgence);

                try(ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        CompteSimple account = new CompteSimple();
                        account.setId(rs.getInt("id"));
                        account.setSolde(rs.getDouble("solde"));
                        account.setType(rs.getString("type"));
                        account.setDecouvert(rs.getInt("decouvert"));
                        list.add(account);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<CompteSimple> findAllAgences() throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<CompteSimple> findAllOperations() throws SQLException, IOException, ClassNotFoundException {
        return null;
    }
}
