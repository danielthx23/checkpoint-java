package br.com.fiap.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

public class FuncionarioPleno extends Funcionario {
    private static final double BONUS = 25.0;

    public FuncionarioPleno() {
    }

    public FuncionarioPleno( String nome, Integer horasTrabalhadas, Double valorHora) {
        super( nome, horasTrabalhadas, valorHora);
    }

    @Override
    public double calcularSalario() {
        int bonusQtd = getHorasTrabalhadas() / 15;
        return super.calcularSalario() + (bonusQtd * BONUS);
    }

    @Override
    public void imprimirInformacao() {
        System.out.println("\nDados do funcionário -> ");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Horas Trabalhadas: " + getHorasTrabalhadas());
        System.out.println("Valor Hora: " + getValorHora());
        System.out.println("Salário: " + calcularSalario());
        System.out.println("Bonus: " + BONUS);
    }
}
