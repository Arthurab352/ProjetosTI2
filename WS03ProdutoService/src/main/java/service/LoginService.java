
package service;

import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import dao.LoginDAO;
import model.Login;
import spark.Request;
import spark.Response;

public class LoginService {

	private LoginDAO produtoDAO = new LoginDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NAME = 2;
	private final int FORM_ORDERBY_EMAIL = 3;

	public LoginService() {
		makeForm();
	}

	public void makeForm() {
		makeForm(FORM_INSERT, new Login(), FORM_ORDERBY_ID);
	}

	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Login(), orderBy);
	}

	public void makeForm(int tipo, Login login, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try {
			Scanner entrada = new Scanner(new File(nomeArquivo));
			while (entrada.hasNext()) {
				form += (entrada.nextLine() + "\n");
			}
			entrada.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String umLogin = "";
		if (tipo != FORM_INSERT) {
			umLogin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/produto/list/1\">Novo User</a></b></font></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t</table>";
			umLogin += "\t<br>";
		}

		if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "action=\"/produto/";
			String name, descricao, buttonLabel, form;
			if (tipo == FORM_INSERT) {
				action += "insert";
				name = "Inserir Descrição";
				descricao = "Escreva aqui...";
				buttonLabel = "Inserir";
				form = "method=\"post\"";
			} else {
				action += "update/" + login.getId();
				name = "Atualizar Produto (ID " + login.getId() + ")";
				descricao = login.getDescricao();
				buttonLabel = "Atualizar";
				form = "method=\"post\"";
			}
			umLogin += "\t<form class=\"form--register\" " + action + "\" " + form + " id=\"form-add\">";
			umLogin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name
					+ "</b></font></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td>Nome Completo: <input class=\"input--register\" type=\"text\" name=\"nomeCompleto\" value=\""
					+ login.getNomeCompleto() + "\"></td>";
			umLogin += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""
					+ descricao + "\"></td>";
			umLogin += "\t\t\t<td>email: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""
					+ login.getEmail() + "\"></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td>&nbsp;Data de Nascimento: <input class=\"input--register\" type=\"text\" name=\"dataNascimento\" value=\""
					+ login.getDataNascimento().toString() + "\"></td>";
			umLogin += "\t\t\t<td>Instrumento: <input class=\"input--register\" type=\"text\" name=\"instrumento\" value=\""
					+ login.getInstrumento() + "\"></td>";
			umLogin += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel
					+ "\" class=\"input--main__style input--button\"></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t</table>";
			umLogin += "\t</form>";
		} else if (tipo == FORM_DETAIL) {
			umLogin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (ID "
					+ login.getId() + ")</b></font></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td>&nbsp;Nome: " + login.getNomeCompleto() + "</td>";
			umLogin += "\t\t\t<td>Descriçao: " + login.getDescricao() + "</td>";
			umLogin += "\t\t\t<td>Email: " + login.getEmail() + "</td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td>&nbsp;Data de fabricação: " + login.getDataNascimento().toString() + "</td>";
			umLogin += "\t\t\t<td>Instrumento: " + login.getInstrumento() + "</td>";
			umLogin += "\t\t\t<td>&nbsp;</td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t</table>";
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umLogin);

		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Perfil: </b></font></td></tr>\n"
				+
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
				"\n<tr>\n" +
				"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
				"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_NAME + "\"><b>Nome</b></a></td>\n" +
				"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_EMAIL + "\"><b>Email</b></a></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
				"</tr>\n";

		List<Login> produtos;
		if (orderBy == FORM_ORDERBY_ID) {
			produtos = produtoDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NAME) {
			produtos = produtoDAO.getOrderByName();
		} else if (orderBy == FORM_ORDERBY_EMAIL) {
			produtos = produtoDAO.getOrderByEmail();
		} else {
			produtos = produtoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Login p : produtos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
					"\t<td>" + p.getId() + "</td>\n" +
					"\t<td>" + p.getNomeCompleto() + "</td>\n" +
					"\t<td>" + p.getEmail() + "</td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getId()
					+ "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/update/" + p.getId()
					+ "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteProduto('" + p.getId()
					+ "', '" + p.getNomeCompleto() + "', '" + p.getEmail()
					+ "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"</tr>\n";
		}
		list += "</table>";
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);
	}

	public Object insert(Request request, Response response) {
		String nomeCompleto = request.queryParams("nomeCompleto");
		String email = request.queryParams("email");
		LocalDate dataNascimento = LocalDate.parse(request.queryParams("dataNascimento"));
		String instrumento = request.queryParams("instrumento");
		String descricao = request.queryParams("descricao");

		String resp = "";

		Login login = new Login(-1, nomeCompleto, email, dataNascimento, instrumento, descricao);

		if (produtoDAO.insert(login) == true) {
			resp = "Produto (" + descricao + ") inserido!";
			response.status(201); // 201 Created
		} else {
			resp = "Produto (" + descricao + ") não inserido!";
			response.status(404); // 404 Not found
		}

		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Login login= (Login) LoginDAO.get(id);

		if (login != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, login, FORM_ORDERBY_NAME);
		} else {
			response.status(404); // 404 Not found
			String resp = "Produto " + id + " não encontrado.";
			makeForm();
			form.replaceFirst("%ID%", resp);
		}

		return form;
	}

	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Login login = (Login) LoginDAO.get(id);

		if (login != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, login, FORM_ORDERBY_NAME);
		} else {
			response.status(404); // 404 Not found
			String resp = "Produto " + id + " não encontrado.";
			makeForm();
			form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
					"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
		}

		return form;
	}

	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
		response.header("Content-Type", "text/html");
		response.header("Content-Encoding", "UTF-8");
		return form;
	}

	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Login login = LoginDAO.get(id);
		String resp = "";

		if (login != null) {
			login.setNomeCompleto(request.queryParams("nomeCompleto"));
			login.setDataNascimento(LocalDate.parse(request.queryParams("dataNascimento")));
			login.setDescricao(request.queryParams("descricao"));
			login.setInstrumento(request.queryParams("instrumento"));
			login.setEmail(request.queryParams("email"));
			LoginDAO.update(login);
			response.status(200); // success
			resp = "Produto (ID " + login.getId() + ") atualizado!";
		} else {
			response.status(404); // 404 Not found
			resp = "Produto (ID \" + produto.getId() + \") não encontrado!";
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}

	public Object delete(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Login login = (Login) LoginDAO.get(id);
		String resp = "";

		if (login != null) {
			LoginDAO.delete(id);
			response.status(200); // success
			resp = "Produto (" + id + ") excluído!";
		} else {
			response.status(404); // 404 Not found
			resp = "Produto (" + id + ") não encontrado!";
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}
}
