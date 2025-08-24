package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J
 */
public class MarcaDAO {
    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Marca marca){
        String sql = "INSERT INTO marca(nome) VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, marca.getNome());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alterar(Marca marca){
        String sql = "UPDATE marca SET nome=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, marca.getNome());
            stmt.setInt(2, marca.getId());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remover(Marca marca){
        String sql = "DELETE FROM marca WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Marca> listar(){
        String sql = "SELECT * FROM marca";
        List<Marca> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Marca marca = new Marca();
                marca.setId(resultado.getInt("id"));
                marca.setNome(resultado.getString("nome"));
                retorno.add(marca);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Marca buscar(Marca marca){
        String sql = "SELECT * FROM marca WHERE id=?";
        Marca retorno = new Marca();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                marca.setNome(resultado.getString("nome"));
                retorno = marca;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
