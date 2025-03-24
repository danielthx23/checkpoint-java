package br.com.fiap.view;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.FuncionarioDaoImpl;
import br.com.fiap.entity.Funcionario;
import br.com.fiap.entity.FuncionarioSenior;
import br.com.fiap.exception.IdNaoEncontradoException;

public class TesteDao {

    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("FUNCIONARIO_ORACLE");
        EntityManager em = fabrica.createEntityManager();

        FuncionarioDao dao = new FuncionarioDaoImpl(em);

        System.out.println("\n--------------------------------------------------");
        System.out.println("Iniciando operação: Cadastrar Funcionario (CREATE -> INSERT)");

        Funcionario funcionario = new Funcionario("Daniel Saburo Akiyama", 160, 25.0);
        try {
            dao.cadastrar(funcionario);
            dao.commit();
            System.out.println("\nCadastro realizado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar funcionario: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------\n");

        System.out.println("--------------------------------------------------");
        System.out.println("Iniciando operação: Buscar Funcionario (READ -> SELECT)");

        try {
            Funcionario busca = dao.buscarPorId(funcionario.getId());
            System.out.println("\nFuncionario encontrado: " + busca.getNome());
        } catch (IdNaoEncontradoException e) {
            System.out.println("\nErro ao buscar funcionario: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------\n");

        System.out.println("--------------------------------------------------");
        System.out.println("Iniciando operação: Atualizar Funcionario (SELECT -> UPDATE)");

        Funcionario funcionarioAtualizar = new Funcionario("Danilo Junzo Akiyama", 200, 45.0);

        funcionarioAtualizar.setId(funcionario.getId());

        try {
            dao.atualizar(funcionarioAtualizar);
            dao.commit();
            System.out.println("\nFuncionario atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao atualizar funcionario: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------\n");

        System.out.println("--------------------------------------------------");
        System.out.println("Iniciando operação: Remover Funcionario (SELECT -> DELETE)");

        try {
            dao.remover(funcionario.getId());
            dao.commit();
            System.out.println("\nFuncionario removido com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao remover funcionario: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------\n");
    }
}
