/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancorrw.conta;

import bancorrw.cliente.Cliente;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kato
 */
public class ContaCorrente extends Conta {

    private double limite;
    private double taxaJurosLimite;

    public ContaCorrente(double limite, double taxaJurosLimite, long id, Cliente cliente, double saldo) throws Exception{
        super(id, cliente, saldo);
        this.limite = limite;
        this.taxaJurosLimite = taxaJurosLimite;
        
        //Valida se conta corrente anterior tem o saldo zerado.
        if (cliente.getContaCorrente()!=null && cliente.getContaCorrente().getSaldo() > 0.0) {
            throw new Exception("Não pode modificar a conta corrente, pois saldo da original não está zerado. "
                    + "Para fazer isso primeiro zere o saldo da conta do cliente. Saldo=" + cliente.getContaCorrente().getSaldo());
        }
        cliente.setContaCorrente(this);
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getTaxaJurosLimite() {
        return taxaJurosLimite;
    }

    public void setTaxaJurosLimite(double taxaJurosLimite) {
        this.taxaJurosLimite = taxaJurosLimite;
    }

    @Override
    public void aplicaJuros() {
        if (this.getSaldo() < 0) {
            try {
                double juros = (this.getSaldo() * this.taxaJurosLimite)*(-1.0);
                super.saca(juros);
            } catch (Exception ex) {
                Logger.getLogger(ContaCorrente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void saca(double valor) throws Exception {
        if (valor <= 0) {
            throw new Exception("Valor do saque não pode ser negativo ou zero. Valor=" + valor);
        }
        if (valor <= this.getSaldo() + this.limite) {
            super.saca(valor);
        } else {
            throw new Exception("Saldo insuficiente na conta."
                    + "\nValor saque=1300.0"
                    + "\nSaldo=" + this.getSaldo()
                    + "\nLimite=" + this.getLimite()
            );
        }
    }
}
