package fr.eni.encheres.ihm;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletPageAccueil
 */
@WebServlet("/pageAccueil")
public class ServletPageAccueil extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// création de la session
		try {
			ArticleManager articleManager = new ArticleManager();
			
			HttpSession session = request.getSession();
			
			if(session.getAttribute("loggedIn")==null)
			{session.setAttribute("loggedIn",false);}
			
			
			List<ArticlesVendus> articlesEncheresEnCours = articleManager.selectEncheresOuvertes();
			List<ArticlesVendus> articlesEncheresTerminees = articleManager.selectEncheresTerminees();		
			request.setAttribute("listeEncheresEnCours", articlesEncheresEnCours);
			request.setAttribute("articlesEncheresTerminees", articlesEncheresTerminees);
			
			// délégation de l'affichage à la JSP
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/PageAccueil.jsp");
			rd.forward(request, response);
		
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		
		String id = request.getParameter("numeroarticle");
		request.setAttribute("noarticle", id);
		
		RequestDispatcher rd = request.getRequestDispatcher("/ServletDetailVente");
		rd.forward(request, response);
	}

}
