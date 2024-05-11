/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancorrw.dao;

import bancorrw.cliente.Cliente;
import bancorrw.conta.ContaInvestimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kato
 */
public class ContaInvestimentoDaoSql implements ContaInvestimentoDao {

    private ContaInvestimentoDaoSql() {
    }
    private static ContaInvestimentoDaoSql dao;

    public static ContaInvestimentoDaoSql getContaInvestimentoDaoSql() {
        if (dao == null) {
            return dao = new ContaInvestimentoDaoSql();
        } else {
            return dao;
        }
    }
    private String insertContaInvstimento
            = "INSERT INTO "
            + "contas_investimento "
            + "(id_conta,"
            + "taxa_remuneracao_investimento,"
            + "montante_minimo,"
            + "deposito_minimo) "
            + "VALUES"
            + "(?,?,?,?)";
    private String insertConta
            = "INSERT INTO "
            + "contas "
            + "(id_cliente,"
            + "saldo) "
            + "VALUES"
            + "(?,?)";
    private String selectAll
            = "SELECT "
            + "contas_investimento.id_conta, "
            + "saldo, "
            + "taxa_remuneracao_investimento, "
            + "montante_minimo, "
            + "deposito_minimo, "
            + "clientes.id_cliente,"
            + "nome, "
            + "cpf, "
            + "data_nascimento, "
            + "cartao_credito "
            + "FROM "
            + "contas "
            + "INNER JOIN "
            + "contas_investimento "
            + "ON "
            + "contas.id_conta=contas_investimento.id_conta "
            + "INNER JOIN "
            + "clientes "
            + "ON "
            + "contas.id_cliente=clientes.id_cliente ";
    private String selectById
            = "SELECT "
            + "contas_investimento.id_conta, "
            + "saldo, "
            + "taxa_remuneracao_investimento, "
            + "montante_minimo, "
            + "deposito_minimo, "
            + "clientes.id_cliente,"
            + "nome, "
            + "cpf, "
            + "data_nascimento, "
            + "cartao_credito "
            + "FROM "
            + "contas "
            + "INNER JOIN "
            + "contas_investimento "
            + "ON "
            + "contas.id_conta=contas_investimento.id_conta "
            + "INNER JOIN "
            + "clientes "
            + "ON "
            + "contas.id_cliente=clientes.id_cliente "
            + "WHERE "
            + "contas.id_conta=?";
    private String selectByCliente
            = "SELECT "
            + "contas_investimento.id_conta, "
            + "saldo, "
            + "taxa_remuneracao_investimento, "
            + "montante_minimo, "
            + "deposito_minimo, "
            + "clientes.id_cliente,"
            + "nome, "
            + "cpf, "
            + "data_nascimento, "
            + "cartao_credito "
            + "FROM "
            + "contas "
            + "INNER JOIN "
            + "contas_investimento "
            + "ON "
            + "contas.id_conta=contas_investimento.id_conta "
            + "INNER JOIN "
            + "clientes "
            + "ON "
            + "contas.id_cliente=clientes.id_cliente "
            + "WHERE "
            + "contas.id_cliente=?";

    private String updateContaInvestimento
            = "UPDATE "
            + "contas_investimento "
            + "SET "
            + "taxa_remuneracao_investimento=? ,"
            + "montante_minimo=? ,"
            + "deposito_minimo=? "
            + "WHERE id_conta = ?";
    private String updateConta
            = "UPDATE "
            + "contas "
            + "SET "
            + "saldo=? "
            + "WHERE id_conta = ?";
    private String deleteById
            = "DELETE FROM "
            + "contas "
            + "WHERE "
            + "id_conta=?";
    private String deleteAll
            = "DELETE "
            + "contas,contas_investimento "
            + "FROM "
            + "contas "
            + "INNER JOIN "
            + "contas_investimento "
            + "ON "
            + "contas.id_conta=contas_investimento.id_conta ";
    private final String ressetAIContas = "ALTER TABLE contas AUTO_INCREMENT =1";

    @Override
    public void add(ContaInvestimento contaInvestimento) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtAdicionaConta = connection.prepareStatement(insertConta, Statement.RETURN_GENERATED_KEYS); PreparedStatement stmtAdicionaContaInvestimento = connection.prepareStatement(insertContaInvstimento);) {
            // seta os valores
            connection.setAutoCommit(false);
            stmtAdicionaConta.setLong(1, contaInvestimento.getCliente().getId());
            stmtAdicionaConta.setDouble(2, contaInvestimento.getSaldo());
            // executa
            stmtAdicionaConta.execute();
            //Seta o id da conta
            ResultSet rs = stmtAdicionaConta.getGeneratedKeys();
            rs.next();
            long idConta = rs.getLong(1);
            contaInvestimento.setId(idConta);
            stmtAdicionaContaInvestimento.setLong(1, idConta);
            stmtAdicionaContaInvestimento.setDouble(2, contaInvestimento.getTaxaRemuneracaoInvestimento());
            stmtAdicionaContaInvestimento.setDouble(3, contaInvestimento.getMontanteMinimo());
            stmtAdicionaContaInvestimento.setDouble(4, contaInvestimento.getDepositoMinimo());
            stmtAdicionaContaInvestimento.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<ContaInvestimento> getAll() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtLista = connection.prepareStatement(selectAll); ResultSet rs = stmtLista.executeQuery();) {
            List<ContaInvestimento> contas = new ArrayList();
            while (rs.next()) {
                // adicionando o objeto à lista
                Cliente cliente = new Cliente(
                        rs.getLong("clientes.id_cliente"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getString("cartao_credito")
                );
                contas.add(
                        new ContaInvestimento(
                                rs.getDouble("taxa_remuneracao_investimento"),
                                rs.getDouble("montante_minimo"),
                                rs.getDouble("deposito_minimo"),
                                rs.getDouble("saldo"),
                                rs.getLong("contas_investimento.id_conta"),
                                cliente
                        )
                );
            }

            return contas;
        }
    }

    @Override
    public ContaInvestimento getById(long id) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtLista = connection.prepareStatement(selectById);) {
            stmtLista.setLong(1, id);
            try (ResultSet rs = stmtLista.executeQuery()) {
                if (rs.next()) {
                    // adicionando o objeto à lista
                    Cliente cliente = new Cliente(
                            rs.getLong("clientes.id_cliente"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getDate("data_nascimento").toLocalDate(),
                            rs.getString("cartao_credito")
                    );
                    ContaInvestimento contaInvestimento = new ContaInvestimento(
                            rs.getDouble("taxa_remuneracao_investimento"),
                            rs.getDouble("montante_minimo"),
                            rs.getDouble("deposito_minimo"),
                            rs.getDouble("saldo"),
                            rs.getLong("contas_investimento.id_conta"),
                            cliente
                    );
                    contaInvestimento.getCliente().addContaInvestimento(contaInvestimento);
                    return contaInvestimento;
                } else {
                    throw new Exception("Cliente não encontrado com id=" + id);
                }
            }
        }
    }

    @Override
    public void update(ContaInvestimento contaInvestimento) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtAtualizaConta = connection.prepareStatement(updateConta); PreparedStatement stmtAtualizaContaInvestimento = connection.prepareStatement(updateContaInvestimento);) {

            connection.setAutoCommit(false);
            stmtAtualizaConta.setDouble(1, contaInvestimento.getSaldo());
            stmtAtualizaConta.setLong(2, contaInvestimento.getId());
            stmtAtualizaConta.executeUpdate();

            stmtAtualizaContaInvestimento.setDouble(1, contaInvestimento.getTaxaRemuneracaoInvestimento());
            stmtAtualizaContaInvestimento.setDouble(2, contaInvestimento.getMontanteMinimo());
            stmtAtualizaContaInvestimento.setDouble(3, contaInvestimento.getDepositoMinimo());
            stmtAtualizaContaInvestimento.setLong(4, contaInvestimento.getId());
            stmtAtualizaContaInvestimento.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void delete(ContaInvestimento contaInvestimento) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtExcluir = connection.prepareStatement(deleteById);) {
            stmtExcluir.setLong(1, contaInvestimento.getId());
            stmtExcluir.executeUpdate();
            contaInvestimento.setId(-1);
        }
    }

    @Override
    public void deleteAll() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement stmtExcluir = connection.prepareStatement(deleteAll); PreparedStatement stmtResetAI = connection.prepareStatement(ressetAIContas);) {
            stmtExcluir.executeUpdate();
            stmtResetAI.executeUpdate();
        }
    }

    @Override
    public List<ContaInvestimento> getContasInvestimentoByCliente(Cliente cliente) throws Exception {
        throw new RuntimeException("Não implementado. Implemente aqui");
    }

}
