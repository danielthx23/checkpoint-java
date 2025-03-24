package br.com.fiap.entity;

import br.com.fiap.anotation.Coluna;
import br.com.fiap.anotation.Tabela;

import javax.persistence.*;

@Entity
@Tabela(nome = "TDS_TB_FUNCIONARIO")
@Table(name = "TDS_TB_FUNCIONARIO")
@SequenceGenerator(name = "funcionario", sequenceName = "SQ_TDS_TB_FUNCIONARIO", allocationSize = 1)
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario")
    @Coluna(nome = "id_funcionario")
    @Column(name = "id_funcionario")
    private Integer id;

    @Coluna(nome = "fn_nome", tamanho = 100, obrigatorio = true)
    @Column(name = "fn_nome", nullable = false, length = 100)
    private String nome;

    @Coluna(nome = "hr_trabalhadas", obrigatorio = true)
    @Column(name = "hr_trabalhadas", nullable = false)
    private Integer horasTrabalhadas;

    @Coluna(nome = "vl_hora", obrigatorio = true)
    @Column(name = "vl_hora", nullable = false)
    private Double valorHora;

    public Funcionario() {
    }

    public Funcionario(String nome, Integer horasTrabalhadas, Double valorHora) {
        this.nome = nome;
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorHora = valorHora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(Integer horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(Double valorHora) {
        this.valorHora = valorHora;
    }

    public double calcularSalario() {
        return horasTrabalhadas * valorHora;
    }

    public void imprimirInformacao() {
        System.out.println("\nDados do funcionário -> ");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Horas Trabalhadas: " + getHorasTrabalhadas());
        System.out.println("Valor Hora: " + getValorHora());
        System.out.println("Salário: " + calcularSalario());
    }
}
