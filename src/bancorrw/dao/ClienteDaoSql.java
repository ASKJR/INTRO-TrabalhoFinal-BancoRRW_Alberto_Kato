/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancorrw.dao;

import bancorrw.cliente.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author Kato
 */
public class ClienteDaoSql implements ClienteDao {

    private ClienteDaoSql() {
    }
    private static ClienteDaoSql dao;

    public static ClienteDaoSql getClienteDaoSql() {
        if (dao == null) {
            return dao = new ClienteDaoSql();
        } else {
            return dao;
        }
    }
    private String selectAll
            = "SELECT "
            + "id_cliente, "
            + "nome, "
            + "cpf, "
            + "data_nascimento, "
            + "cartao_credito "
            + "FROM "
            + "clientes ";
    private String selectById = selectAll + " "
            + "WHERE "
            + "id_cliente=?";
    private String insertCliente
            = "INSERT INTO "
            + "clientes "
            + "(nome,"
            + "cpf,"
            + "data_nascimento, "
            + "cartao_credito) "
            + "VALUES"
            + "(?,?,?,?)";
    private String updateCliente
            = "UPDATE "
            + "clientes "
            + "SET "
            + "nome=?, "
            + "cpf=?, "
            + "data_nascimento=?, "
            + "cartao_credito=? "
            + "WHERE id_cliente = ?";
    private String deleteById
            = "DELETE FROM "
            + "clientes "
            + "WHERE id_cliente=?";
    private String deleteAll
            = "DELETE FROM "
            + "clientes ";
    private final String ressetAIPessoas = "ALTER TABLE clientes AUTO_INCREMENT =1";
    private final String ressetAIContas = "ALTER TABLE contas AUTO_INCREMENT =1";

    @Override
    public void add(Cliente cliente) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtAdiciona = connection.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);) {
            // seta os valores
            stmtAdiciona.setString(1, cliente.getNome());
            stmtAdiciona.setString(2, cliente.getCpf());
            stmtAdiciona.setDate(3, java.sql.Date.valueOf(cliente.getDataNascimento()));
            stmtAdiciona.setString(4, cliente.getCartaoCredito());
            // executa
            stmtAdiciona.execute();
            //Seta o id do cliente
            ResultSet rs = stmtAdiciona.getGeneratedKeys();
            rs.next();
            long i = rs.getLong(1);
            cliente.setId(i);
        }
    }

    @Override
    public List<Cliente> getAll() throws Exception {
        throw new RuntimeException("Não implementado. Implemente aqui");
    }

    @Override
    public Cliente getById(long id) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtLista = connection.prepareStatement(selectById);) {
            stmtLista.setLong(1, id);
            try (ResultSet rs = stmtLista.executeQuery()) {
                if (rs.next()) {
                    // criando o objeto Cliente
                    java.sql.Date date = rs.getDate("data_nascimento");
                    LocalDate nascimento = date.toLocalDate();
                    return new Cliente(
                            rs.getLong("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            nascimento,
                            rs.getString("cartao_credito")
                    );
                } else {
                    throw new SQLException("Cliente não encontrada com id=" + id);
                }
            }
        }
    }

    @Override
    public void update(Cliente cliente) throws Exception {
        throw new RuntimeException("Não implementado. Implemente aqui");
    }

    @Override
    public void delete(Cliente cliente) throws Exception {
        throw new RuntimeException("Não implementado. Implemente aqui");
    }

    @Override
    public void deleteAll() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtExcluir = connection.prepareStatement(deleteAll); PreparedStatement stmtResetAI = connection.prepareStatement(ressetAIPessoas);) {
            stmtExcluir.executeUpdate();
            stmtResetAI.executeUpdate();
        }
    }

}
