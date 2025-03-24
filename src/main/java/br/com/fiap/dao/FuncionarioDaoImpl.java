package br.com.fiap.dao;

import java.lang.reflect.Field;
import javax.persistence.EntityManager;

import br.com.fiap.anotation.Coluna;
import br.com.fiap.anotation.Tabela;
import br.com.fiap.entity.Funcionario;
import br.com.fiap.exception.CommitException;
import br.com.fiap.exception.IdNaoEncontradoException;

public class FuncionarioDaoImpl implements FuncionarioDao {

    private EntityManager em;

    public FuncionarioDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void cadastrar(Funcionario funcionario) {
        String sql = gerarInsert(funcionario);
        System.out.println("\nSQL -> " + sql);

        em.persist(funcionario);

        funcionario.imprimirInformacao();
        System.out.println("\nOperação de inserção realizada com sucesso!");

    }

    @Override
    public void atualizar(Funcionario funcionario) throws IdNaoEncontradoException {

        buscarPorId(funcionario.getId());

        String sql = gerarUpdate(funcionario);
        System.out.println("\nSQL -> " + sql);

        em.merge(funcionario);

        funcionario.imprimirInformacao();
        System.out.println("\nOperação de atualização realizada com sucesso!");
    }

    @Override
    public void remover(Integer id) throws IdNaoEncontradoException {
        Funcionario funcionario = buscarPorId(id);

        String sql = gerarDelete(funcionario.getClass(), id);
        System.out.println("\nSQL -> " + sql);

        em.remove(funcionario);

        funcionario.imprimirInformacao();
        System.out.println("\nOperação de remoção realizada com sucesso!");

    }

    @Override
    public Funcionario buscarPorId(Integer id) throws IdNaoEncontradoException {
        String sql = gerarSelect(Funcionario.class, id);
        System.out.println("\nSQL -> " + sql);

        Funcionario funcionario= em.find(Funcionario.class, id);
        if (funcionario == null) {
            throw new IdNaoEncontradoException("Funcionario não encontrado");
        }

        funcionario.imprimirInformacao();
        System.out.println("\nOperação de busca realizada com sucesso!");
        return funcionario;
    }

    @Override
    public void commit() throws CommitException {
        try {
            em.getTransaction().begin();
            System.out.println("\nSQL -> COMMIT TRANSACTION");
            System.out.println("\n---------- Prints do Hibernate ----------\n");
            em.getTransaction().commit();
            System.out.println("\n-----------------------------------------");
            System.out.println("\nCommit da transação realizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new CommitException();
        }
    }

    // MÉTODOS PARA GERAÇÃO AUTOMÁTICA DE SQL USANDO REFLECTION DAS ANNOTATIONS

    private String gerarInsert(Object obj) {
        Class<?> classe = obj.getClass();
        if (!classe.isAnnotationPresent(Tabela.class)) return null;

        Tabela tabela = classe.getAnnotation(Tabela.class);
        String nomeTabela = tabela.nome();

        StringBuilder colunas = new StringBuilder();
        StringBuilder valores = new StringBuilder();

        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(Coluna.class)) {
                Coluna coluna = field.getAnnotation(Coluna.class);
                field.setAccessible(true);
                try {
                    colunas.append(coluna.nome()).append(", ");
                    valores.append("'").append(field.get(obj)).append("', ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        colunas.setLength(colunas.length() - 2); 
        valores.setLength(valores.length() - 2); 

        return "INSERT INTO " + nomeTabela + " (" + colunas + ") VALUES (" + valores + ")";
    }

    private String gerarUpdate(Object obj) {
        Class<?> classe = obj.getClass();
        if (!classe.isAnnotationPresent(Tabela.class)) return null;

        Tabela tabela = classe.getAnnotation(Tabela.class);
        String nomeTabela = tabela.nome();

        StringBuilder sql = new StringBuilder("UPDATE " + nomeTabela + " SET ");
        Integer id = null;

        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(Coluna.class)) {
                Coluna coluna = field.getAnnotation(Coluna.class);
                field.setAccessible(true);
                try {
                    if (coluna.nome().equals("id_funcionario")) {
                        id = (Integer) field.get(obj);
                    } else {
                        sql.append(coluna.nome()).append(" = '").append(field.get(obj)).append("', ");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        sql.setLength(sql.length() - 2); 
        return sql.append(" WHERE id_funcionario = ").append(id).toString();
    }

    private String gerarDelete(Class<?> classe, Integer id) {
        if (!classe.isAnnotationPresent(Tabela.class)) return null;
        Tabela tabela = classe.getAnnotation(Tabela.class);
        return "DELETE FROM " + tabela.nome() + " WHERE id_funcionario = " + id;
    }

    private String gerarSelect(Class<?> classe, Integer id) {
        if (!classe.isAnnotationPresent(Tabela.class)) return null;
        Tabela tabela = classe.getAnnotation(Tabela.class);
        return "SELECT * FROM " + tabela.nome() + " WHERE id_funcionario = " + id;
    }
}
