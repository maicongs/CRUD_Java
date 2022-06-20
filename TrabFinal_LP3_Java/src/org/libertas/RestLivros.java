package org.libertas;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.libertas.dao.LivrosDao;
import org.libertas.pojo.Livros;


import com.google.gson.Gson;

@WebServlet("/RestLivros/*")
public class RestLivros extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private void enviaResposta(HttpServletResponse response, String json, int codigo) throws IOException{
		
		response.addHeader("Content-Type", "application/json; charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
		
		response.setStatus(codigo);
		
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(json.getBytes("UTF-8"));
		out.close();
	}
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RestLivros() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Livros> lista = new LinkedList<Livros>();
		String json;
		int id = 0;
		if(request.getPathInfo() != null) {
			String info = request.getPathInfo().replace("/", "");
		id = Integer.parseInt(info);
		}
		
		
		Gson gson = new Gson();
		
		LivrosDao ldao = new LivrosDao();
	    if(id == 0) {
	    	lista = ldao.listar();
			json = gson.toJson(lista);
	    }else {
	    	Livros l = ldao.consultar(id);
	    	json = gson.toJson(l);
	    }
		
		
		//envia resposta
		enviaResposta(response, json, 200);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String json = request.getReader().lines().collect(Collectors.joining());
			Gson gson = new Gson();
			Livros l = (Livros) gson.fromJson(json, Livros.class);
			LivrosDao ldao = new LivrosDao();
			ldao.inserir(l);
			enviaResposta(response, "inserido com sucesso", 200);
			
		}catch(Exception e){
			e.printStackTrace();
			enviaResposta(response, e.getMessage(), 500);
			
		}
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String json = request.getReader().lines().collect(Collectors.joining());
			Gson gson = new Gson();
			Livros l = (Livros) gson.fromJson(json, Livros.class);
			LivrosDao ldao = new LivrosDao();
			ldao.alterar(l);
			enviaResposta(response, "alterado com sucesso", 200);
		}catch(Exception e){
			e.printStackTrace();
			enviaResposta(response, e.getMessage(), 500);
		}
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				int id = 0;
				if(request.getPathInfo() != null) {
					String info = request.getPathInfo().replace("/", "");
				id = Integer.parseInt(info);
				}
				
				Livros l = new Livros();
				l.setIdlivros(id);
				LivrosDao ldao = new LivrosDao();
				ldao.excluir(l);
				enviaResposta(response, "excluido com sucesso", 200);
			}catch(Exception e){
				e.printStackTrace();
				enviaResposta(response, e.getMessage(), 500);
				
			}
	}

}



