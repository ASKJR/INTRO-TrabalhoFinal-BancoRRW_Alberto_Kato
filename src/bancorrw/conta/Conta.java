/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancorrw.conta;

import bancorrw.cliente.Cliente;

/**
 *
 * @author Kato
 */
public abstract class Conta {

    private long id;
    private Cliente cliente;
    private double saldo;

    public Conta(long id, Cliente cliente, double saldo) {
        this.id = id;
        this.cliente = cliente;
        this.saldo = saldo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void deposita(double valor) throws Exception {
        if (valor <= 0) {
            throw new Exception(
                "Valor do depósito não pode ser negativo ou zero. Valor=" + valor
            );
        }
        this.saldo += valor;
    }

    public void saca(double valor) throws Exception {
        this.saldo -= valor;
    }

    public abstract void aplicaJuros();

    public long getNumero() {
        return this.id;
    }

    public double getSaldo() {
        return saldo;
    }
}
