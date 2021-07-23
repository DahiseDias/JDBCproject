package project.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
	Long id;
	String name;
	String email;
	String senha;
	int tipo_usuario;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//resp.addHeader("Charset", "UTF-8");
		try {
			Class.forName("org.postgresql.Driver");
			
			String url = "jdbc:postgresql://localhost/JDBC?user=postgres&password=1234&ssl=false";
			Connection conn = DriverManager.getConnection(url);
			Statement listarUsuarios = conn.createStatement();
			
			ResultSet rsUsuarios = listarUsuarios.executeQuery("SELECT * from usuarios");
			
			
			while (rsUsuarios.next()) {
				id = rsUsuarios.getLong("id");
				name = rsUsuarios.getString("name");
				senha = rsUsuarios.getString("senha");
				email = rsUsuarios.getString("email");
				tipo_usuario = rsUsuarios.getInt("tipo_usuario");

				resp.getWriter().write("[\n ID: " + id + ";\n nome: " + name + "\n email: " + email + "\n senha: " + senha + "\n tipo de usuario: " + tipo_usuario +"\n]");
			}
			
			
			
			resp.getWriter().write("conectados");
			conn.close();
			listarUsuarios.close();
			rsUsuarios.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/JDBC?user=postgres&password=1234&ssl=false";
			Connection conn = DriverManager.getConnection(url);
			Statement inserirUsuario = conn.createStatement();
			id = Long.parseLong(req.getParameter("id"));
			name = req.getParameter("name");
			senha = req.getParameter("senha");
			email = req.getParameter("email");
			tipo_usuario = Integer.parseInt(req.getParameter("tipo_usuario"));
			
			
			String sql = String.format("INSERT INTO public.usuarios(id, name, email, senha, tipo_usuario) VALUES(%s, '%s', '%s', '%s', %s)", id, name, email, senha, tipo_usuario);
			resp.getWriter().write(sql);
			
			ResultSet rsUsuarios = inserirUsuario.executeQuery(sql);
			
			conn.close();
			inserirUsuario.close();
			rsUsuarios.close();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/JDBC?user=postgres&password=1234&ssl=false";
			Connection conn = DriverManager.getConnection(url);
			
			Statement deletarUsuario = conn.createStatement();
			id = Long.parseLong(req.getParameter("id"));
			
			String sql = String.format("DELETE FROM public.usuarios	WHERE id = %s", id);
			
			resp.getWriter().write("delete realizado com sucesso" + sql);
			deletarUsuario.executeQuery(sql);
			
			conn.close();
			deletarUsuario.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
	}
	

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/JDBC?user=postgres&password=1234&ssl=false";
			Connection conn = DriverManager.getConnection(url);
			Statement atualizarUsuario = conn.createStatement();
			id = Long.parseLong(req.getParameter("id"));
			name = req.getParameter("name");
			senha = req.getParameter("senha");
			email = req.getParameter("email");
			tipo_usuario = Integer.parseInt(req.getParameter("tipo_usuario"));
			
			String sql = "";
			if(name != null && senha != null && email != null) {
				sql = String.format("UPDATE public.usuarios SET name= %s, email= %s, senha= %s WHERE id = %s", name, email, senha, id);
			}/*
			if(name == null ) {
				sql = String.format("UPDATE public.usuarios SET email= %s, senha= %s WHERE id = %s", email, senha, id);
			}
			if(senha == null ) {
				sql = String.format("UPDATE public.usuarios SET name= %s, email= %s WHERE id = %s", name , email, id);
			}
			if(email == null) {
				sql = String.format("UPDATE public.usuarios SET name= %s, senha= %s WHERE id = %s", name, senha, id);
			}*/
			
			resp.getWriter().write("atualização realizada com sucesso" + sql);
			atualizarUsuario.executeQuery(sql);
			
			conn.close();
			atualizarUsuario.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
	}

}
