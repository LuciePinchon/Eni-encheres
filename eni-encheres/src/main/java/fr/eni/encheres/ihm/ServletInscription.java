package fr.eni.encheres.ihm;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.BusinessException;


@WebServlet("/inscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Inscription.jsp");

		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//je récupére l'action souhaitée : créer, modifier, supprimer
		 String action = request.getParameter("action");
		 
		try {
			
			HttpSession session = request.getSession();
			
			UtilisateurManager utilisateurManager = new UtilisateurManager();
		
			String pseudo = request.getParameter("pseudo");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String telephone = request.getParameter("telephone");
			String email = request.getParameter("email");
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("code");
			String ville = request.getParameter("ville");
			String motDePasse = request.getParameter("mdp");
			String motDePasseConfirm = request.getParameter("mdpconfirmation");
			
			Utilisateur utilisateur = new Utilisateur();
			
			utilisateur.setPseudo(pseudo);
	        utilisateur.setNom(nom);
	        utilisateur.setPrenom(prenom);
	        utilisateur.setEmail(email);
	        utilisateur.setTelephone(telephone);
	        utilisateur.setRue(rue);
	        utilisateur.setCodePostal(codePostal);
	        utilisateur.setVille(ville);
	        utilisateur.setMotDePasse(motDePasse);
	        utilisateur.setCredit(100);// car crédit initial de 100 est alloué au nouvel utilisateur
	        utilisateur.setAdministrateur(false); 
			
			if("Créer".equals(action)) {
				utilisateurManager.ajouterUtilisateur(utilisateur,motDePasseConfirm);
				session.setAttribute("utilisateur", utilisateur);
				session.setAttribute("loggedIn", true);
				RequestDispatcher rd =request.getRequestDispatcher("/ServletAfficherProfil");
				rd.forward(request, response);
				
				}
			
			if("Modifier".equals(action)) {
				//on cherche l'id de l'utilisateur avec son pseudo précédent
				String pseudoPrecedent=((Utilisateur) session.getAttribute("utilisateur")).getPseudo();
				utilisateur.setNoUtilisateur(utilisateurManager.selectByPseudoUtilisateur(pseudoPrecedent).getNoUtilisateur());
				utilisateurManager.updateUtilisateur(utilisateur,motDePasseConfirm);
				session.setAttribute("utilisateur", utilisateur);
				session.setAttribute("loggedIn", true);
				RequestDispatcher rd =request.getRequestDispatcher("/ServletAfficherProfil");
				rd.forward(request, response);
				
			}
			
			if("Supprimer".equals(action)) 
			{
				
				utilisateurManager.deleteUtilisateur(utilisateurManager.selectByPseudoUtilisateur(pseudo).getNoUtilisateur());;
				session.setAttribute("loggedIn", false);
				session.removeAttribute("utilisateur");
				RequestDispatcher rd =request.getRequestDispatcher("WEB-INF/PageAccueil.jsp");
				rd.forward(request, response);
			}
			
			
		
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Inscription.jsp");
			rd.forward(request, response);
			
			
		}
		
	
		
	}

}
