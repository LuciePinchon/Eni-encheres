package fr.eni.encheres.ihm;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletAfficherProfil
 */
public class ServletAfficherProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	 Utilisateur utilisateur = new Utilisateur ();
	 
	 request.setAttribute("utilisateur", utilisateur);
	 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AfficherUnProfil.jsp");
	 dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
