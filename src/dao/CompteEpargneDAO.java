package dao;

import bo.CompteEpargne;
import dal.IDAO;
import dal.PersistanceManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteEpargneDAO implements IDAO<Long, CompteEpargne> {

    private static final String INSERT_QUERY = "INSERT INTO compte (solde, type, tauxInteret, idAgence) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE compte SET solde = ?, type = ?, tauxInteret = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM compte WHERE id= ?";
    private static final String FIND_QUERY = "SELECT * FROM compte WHERE id= ?";
    private static final String FIND_ALLQUERY = "SELECT * FROM compte WHERE type = 'epargne' AND idAgence = ?";

    @Override
    public void create(CompteEpargne object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                ps.setDouble(1, object.getSolde());
                ps.setString(2, object.getType());
                ps.setFloat(3, object.getTauxInteret());
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
    public void update(CompteEpargne object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

                ps.setDouble(1, object.getSolde());
                ps.setString(2, object.getType());
                ps.setFloat(3, object.getTauxInteret());
                ps.setInt(4, object.getId());
                ps.executeUpdate();
            }
        }

    }

    @Override
    public void remove(CompteEpargne object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public CompteEpargne findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        CompteEpargne compte_epargne = new CompteEpargne();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1, aLong);
                try(ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        compte_epargne.setId(rs.getInt("id"));
                        compte_epargne.setSolde(rs.getDouble("solde"));
                        compte_epargne.setType(rs.getString("type"));
                        compte_epargne.setTauxInteret(rs.getFloat("tauxInteret"));
                        compte_epargne.setIdAgence(rs.getInt("idAgence"));
                    }
                }
            }
        }
        return compte_epargne;
    }

    @Override
    public List<CompteEpargne> findAll(int idAgence) throws SQLException, IOException, ClassNotFoundException {

        List<CompteEpargne> list = new ArrayList<>();
        CompteEpargne compte_epargne = new CompteEpargne();
        Connection connection = PersistanceManager.getConnection();

        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_ALLQUERY)) {
                ps.setLong(1, idAgence);

                try(ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        compte_epargne.setId(rs.getInt("id"));
                        compte_epargne.setSolde(rs.getDouble("solde"));
                        compte_epargne.setType(rs.getString("type"));
                        compte_epargne.setTauxInteret(rs.getFloat("TauxInteret"));
                        compte_epargne.setIdAgence(rs.getInt("idAgence"));
                        list.add(compte_epargne);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<CompteEpargne> findAllAgences() throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<CompteEpargne> findAllOperations(int idCompte) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }
}
