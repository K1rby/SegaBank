package dao;

import bo.Operation;
import dal.IDAO;
import dal.PersistanceManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO implements IDAO<Long, Operation> {

    private static final String INSERT_QUERY = "INSERT INTO operation (type, date, montant, idAgence, idCompte) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE operation SET type = ?, date = ?, montant = ?, idAgence = ?, idCompte = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM operation WHERE id= ?";
    private static final String FIND_QUERY = "SELECT * FROM operation WHERE id= ?";
    private static final String FIND_ALLQUERY = "SELECT * FROM operation";

    @Override
    public void create(Operation object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, object.getType());
                ps.setDate(2, object.getDate());
                ps.setInt(3, object.getMontant());
                ps.setInt(4, object.getIdAgence());
                ps.setInt(5, object.getIdCompte());
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
    public void update(Operation object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

                ps.setString(1, object.getType());
                ps.setDate(2, object.getDate());
                ps.setInt(3, object.getMontant());
                ps.setInt(4, object.getIdAgence());
                ps.setInt(5, object.getIdCompte());
                ps.setInt(6, object.getId());
                ps.executeUpdate();
            }
        }

    }

    @Override
    public void remove(Operation object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Operation findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {

        Operation operation = new Operation();
        Connection connection = PersistanceManager.getConnection();
        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1, aLong);
                try(ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        operation.setId(rs.getInt("id"));
                        operation.setType(rs.getString("type"));
                        operation.setDate(rs.getDate("date"));
                        operation.setMontant(rs.getInt("montant"));
                        operation.setIdAgence(rs.getInt("idAgence"));
                        operation.setIdCompte(rs.getInt("idCompte"));
                    }
                }
            }
        }
        return operation;
    }

    @Override
    public List<Operation> findAll() throws SQLException, IOException, ClassNotFoundException {

        List<Operation> list = new ArrayList<>();
        Connection connection = PersistanceManager.getConnection();

        if (connection != null) {

            try(PreparedStatement ps = connection.prepareStatement(FIND_ALLQUERY)) {
                try(ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Operation operation = new Operation();
                        operation.setId(rs.getInt("id"));
                        operation.setType(rs.getString("type"));
                        operation.setDate(rs.getDate("date"));
                        operation.setMontant(rs.getInt("montant"));
                        operation.setIdAgence(rs.getInt("idAgence"));
                        operation.setIdCompte(rs.getInt("idCompte"));
                        list.add(operation);
                    }
                }
            }
        }
        return list;
    }
}
