
package dao;

import model.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO extends DAO {
	public LoginDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Login produto) {
		boolean status = false;
		try {
			System.out.println("INSERT INTO produto (nomecompleto, email, datanascimento, instrumento, descricao) "
					+ "VALUES ('" + produto.getNomeCompleto() + "', '"
					+ produto.getEmail() + "', ?"
					+ ", '" + produto.getInstrumento()
					+ "', '" + produto.getDescricao() + "')");
			String sql = "INSERT INTO produto (nomecompleto, email, datanascimento, instrumento, descricao) "
					+ "VALUES ('" + produto.getNomeCompleto() + "', '"
					+ produto.getEmail() + "', ?"
					+ ", '" + produto.getInstrumento()
					+ "', '" + produto.getDescricao() + "')";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setDate(1, Date.valueOf(produto.getDataNascimento()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public static Login get(int id) {
		Login login = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id=" + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				login = new Login(
						rs.getInt("id"),
						rs.getString("nomecompleto"),
						rs.getString("email"),
						rs.getDate("datanascimento").toLocalDate(),
						rs.getString("descricao"),
						rs.getString("instrumento"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return login;
	}

	public List<Login> get() {
		return get("");
	}

	public List<Login> getOrderByID() {
		return get("id");
	}

	public List<Login> getOrderByName() {

		return get("nomecompleto");
	}

	public List<Login> getOrderByEmail() {
		return get("email");
	}

	private List<Login> get(String orderBy) {
		List<Login> login = new ArrayList<Login>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Login p = new Login(rs.getInt("id"), rs.getString("nomecompleto"), rs.getString("email"),
						rs.getDate("dataNascimento").toLocalDate(),
						rs.getString("instrumento"), rs.getString("descricao"));
				login.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return login;
	}

	public static boolean update(Login login) {
		boolean status = false;
		try {
			String sql = "UPDATE produto SET" + " nomecompleto = '" + login.getNomeCompleto() + "', "
					+ "email = '" + login.getEmail() + "', "
					+ "datanascimento = ?, "
					+ "instrumento = '" + login.getInstrumento() + "', "
					+ "descricao = '" + login.getDescricao()
					+ "' WHERE id = " + login.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setDate(1, Date.valueOf(login.getDataNascimento()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public static boolean delete(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM produto WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
