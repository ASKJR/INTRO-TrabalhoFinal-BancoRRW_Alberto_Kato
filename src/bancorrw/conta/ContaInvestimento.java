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
public class ContaInvestimento extends Conta {

    private double taxaRemuneracaoInvestimento;
    private double montanteMinimo;
    private double depositoMinimo;

    public ContaInvestimento(
            double taxaRemuneracaoInvestimento, 
            double montanteMinimo, 
            double depositoMinimo, 
            double saldo, 
            long id, 
            Cliente cliente) throws Exception{
        super(id, cliente, saldo);
        if (saldo < montanteMinimo){
               throw new Exception("Saldo não pode ser menor que montante mínimo.");
        }
        this.taxaRemuneracaoInvestimento = taxaRemuneracaoInvestimento;
        this.montanteMinimo = montanteMinimo;
        this.depositoMinimo = depositoMinimo;
    }

    public double getTaxaRemuneracaoInvestimento() {
        return taxaRemuneracaoInvestimento;
    }

    public void setTaxaRemuneracaoInvestimento(double taxaRemuneracaoInvestimento) {
        this.taxaRemuneracaoInvestimento = taxaRemuneracaoInvestimento;
    }

    public double getMontanteMinimo() {
        return montanteMinimo;
    }

    public void setMontanteMinimo(double montanteMinimo) {
        this.montanteMinimo = montanteMinimo;
    }

    public double getDepositoMinimo() {
        return depositoMinimo;
    }

    public void setDepositoMinimo(double depositoMinimo) {
        this.depositoMinimo = depositoMinimo;
    }

    @Override
    public void aplicaJuros() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void saca(double valor) throws Exception {
        if ((this.getSaldo()-valor)< this.montanteMinimo) {
            throw new Exception("Saldo insuficiente para saque. Valor Saque="+ valor 
                    + " Saldo=" + this.getSaldo() +" Montante Minimo=" + this.montanteMinimo);
        }
        super.saca(valor);
    }

    @Override
    public void deposita(double valor) throws Exception{
        if (valor < this.depositoMinimo) {
            throw new Exception("Valor do depóstio não atingiu o mínimo. Valor Depósito="+ valor 
                    +" Depóstio Mínimo=" + this.depositoMinimo);
        }
        super.deposita(valor);
    }
}
