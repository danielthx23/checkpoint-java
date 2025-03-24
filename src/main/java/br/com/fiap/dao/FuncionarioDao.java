package br.com.fiap.dao;

import br.com.fiap.entity.Funcionario;
import br.com.fiap.exception.CommitException;
import br.com.fiap.exception.IdNaoEncontradoException;

public interface FuncionarioDao {
    void cadastrar(Funcionario funcionario);

    void atualizar(Funcionario funcionario) throws IdNaoEncontradoException;

    void remover(Integer id) throws IdNaoEncontradoException;

    Funcionario buscarPorId(Integer id) throws IdNaoEncontradoException;

    void commit() throws CommitException;
}
