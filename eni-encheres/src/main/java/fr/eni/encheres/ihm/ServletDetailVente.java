package fr.eni.encheres.ihm;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.messages.LecteurMessage;


@WebServlet("/detailVente")
public class ServletDetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 UtilisateurManager utilisateurManager = new UtilisateurManager();
		 EncheresManager encheresManager = new EncheresManager();
		 ArticleManager articleManager = new ArticleManager();
		 int id= Integer.parseInt(request.getParameter("idArticle"));
		 ArticlesVendus articleDetail;
		 
		try {
			
			articleDetail = articleManager.selectArticle(id);
			//je vais récupérer l'enchère correspondant à cet article si il y a au moins une enchere
			if(articleDetail.getPrixVente()!=0) {
			
			Utilisateur vainqueur = utilisateurManager.selectUtilisateur(encheresManager.selectByIDAarticleAndMontant(id, articleDetail.getPrixVente()));
			request.setAttribute("vainqueur", vainqueur);

			}
			request.setAttribute("articleDetail", articleDetail);
			request.setAttribute("idArticle", id);
			request.setAttribute("dateActuelle",LocalDate.now());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		 
		 
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/DetailVente.jsp");
		 dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id= Integer.parseInt(request.getParameter("idArticle"));
		EncheresManager encheresManager = new EncheresManager ();
		ArticleManager articleManager = new ArticleManager();
		request.setAttribute("dateActuelle",LocalDate.now());
		ArticlesVendus articleDetail = new ArticlesVendus();
		Utilisateur utilisateurEncherisseur = new Utilisateur();
		Encheres enchere = new Encheres();
		
		 
		try {
			
			articleDetail = articleManager.selectArticle(id);
			request.setAttribute("articleDetail", articleDetail);
			String proposition = request.getParameter("proposition");
			HttpSession session = request.getSession();
			
			
			utilisateurEncherisseur=(Utilisateur) session.getAttribute("utilisateur");
			
				if(proposition!=null) 
				{
					int montantPropose = Integer.parseInt(proposition);
					
					enchere.setDateEnchere(LocalDate.now());
					enchere.setNoArticle(id);
					enchere.setMontantEnchere(montantPropose);
					enchere.setNoUtilisateur(utilisateurEncherisseur.getNoUtilisateur());
					encheresManager.encherir(enchere);
					request.setAttribute("idArticle", id);
				}
				
				doGet(request, response);
			
		}
		catch(BusinessException e) 
		{
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/DetailVente.jsp");
			rd.forward(request, response);
		}
		
		
		
		
		
	}
	
}
