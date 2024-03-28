package fr.eni.encheres.ihm;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet ("/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Connexion.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// Vérification des infos de connexion 
		
try {
	Utilisateur utilisateur = utilisateurManager.checkConnection(username, password);
	
	//si la connexion renvoie bien un utilisateur (donc que le mdp correspond au login)
	
		 HttpSession session = request.getSession();
		 session.setAttribute("loggedIn", true);
		 session.setAttribute("utilisateur", utilisateur);
		 
		 response.sendRedirect("/eni-encheres/ServletPageAccueil");  
	
	
		
	
} catch (BusinessException e) {
	
	e.printStackTrace();
	request.setAttribute("listeCodesErreur", e.getListeCodesErreur());;
	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Connexion.jsp");
	rd.forward(request, response);
}

	
		
	}

}
