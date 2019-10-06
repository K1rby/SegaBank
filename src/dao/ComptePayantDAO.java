package dao;

import bo.ComptePayant;
import dal.IDAO;
import dal.PersistanceManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComptePayantDAO implements IDAO<Long, ComptePayant> {

    private static final String INSERT_QUERY = "INSERT INTO compte (solde, type, idAgence) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE compte SET solde = ?, type = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM compte WHERE id= ?";
    private static final String FIND_QUERY = "SELECT * FROM compte WHERE id= ?";
    private static final String FIND_ALLQUERY = "SELECT * FROM compte WHERE type = 'payant' AND idAgence = ?";

    @Override
    public void create(ComptePayant object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                ps.setDouble(1, object.getSolde());
                ps.setString(2, object.getType());
                ps.setInt(3, object.getIdAgence());
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
    public void update(ComptePayant object) throws SQLException, IOException, ClassNotFoundException {

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
    public void remove(ComptePayant object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public ComptePayant findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        ComptePayant compte_payant = new ComptePayant();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1, aLong);
                try(ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        compte_payant.setId(rs.getInt("id"));
                        compte_payant.setSolde(rs.getDouble("solde"));
                        compte_payant.setType(rs.getString("type"));
                        compte_payant.setIdAgence(rs.getInt("idAgence"));
                    }
                }
            }
        }
        return compte_payant;
    }

    @Override
    public List<ComptePayant> findAll(int idAgence) throws SQLException, IOException, ClassNotFoundException {

        List<ComptePayant> list = new ArrayList<>();
        ComptePayant compte_payant = new ComptePayant();
        Connection connection = PersistanceManager.getConnection();

        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_ALLQUERY)) {
                ps.setLong(1, idAgence);

                try(ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        compte_payant.setId(rs.getInt("id"));
                        compte_payant.setSolde(rs.getDouble("solde"));
                        compte_payant.setType(rs.getString("type"));
                        compte_payant.setIdAgence(rs.getInt("idAgence"));
                        list.add(compte_payant);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<ComptePayant> findAllAgences() throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ComptePayant> findAllOperations() throws SQLException, IOException, ClassNotFoundException {
        return null;
    }
}
