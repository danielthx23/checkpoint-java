package br.com.fiap.resource;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.FuncionarioDaoImpl;
import br.com.fiap.entity.Funcionario;
import br.com.fiap.exception.CommitException;
import br.com.fiap.exception.IdNaoEncontradoException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Path("/funcionarios") // Define a rota base
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    private EntityManager em = Persistence.createEntityManagerFactory("FUNCIONARIO_ORACLE").createEntityManager();
    private FuncionarioDao funcionarioDao = new FuncionarioDaoImpl(em);

    @POST
    public Response cadastrar(Funcionario funcionario) {
        funcionarioDao.cadastrar(funcionario);
        try {
            funcionarioDao.commit();
            return Response.status(Response.Status.CREATED).entity(funcionario).build();
        } catch (CommitException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") Integer id) {
        try {
            Funcionario funcionario = funcionarioDao.buscarPorId(id);
            return Response.ok(funcionario).build();
        } catch (IdNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
        }
    }

    @PUT
    public Response atualizar(Funcionario funcionario) {
        try {
            funcionarioDao.atualizar(funcionario);
            funcionarioDao.commit();
            return Response.ok(funcionario).build();
        } catch (IdNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
        } catch (CommitException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Integer id) {
        try {
            funcionarioDao.remover(id);
            funcionarioDao.commit();
            return Response.noContent().build();
        } catch (IdNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
        } catch (CommitException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}