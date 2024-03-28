package fr.eni.encheres.ihm;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;

/**
 * Servlet Filter implementation class EncheresFilter
 */
/*@WebFilter(
		urlPatterns = "/pageAccueil",
		dispatcherTypes = {
				DispatcherType.REQUEST,
				DispatcherType.FORWARD,
				DispatcherType.INCLUDE,
				DispatcherType.ERROR
	}
		)*/
public class EncheresFilter implements Filter {
	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		String encheresOuvertes = request.getParameter("encheresOuvertes");
		String mesEncheresRemportees = request.getParameter("mesEncheresRemportees");
		String mesVentesEnCours = request.getParameter("mesVentesEnCours");
		String ventesNonDebutees = request.getParameter("VentesNonDeb");
		String ventesTerminees = request.getParameter("ventesTerminees");
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("utilisateur");
		
		ArticleManager articleManager = new ArticleManager();
		if("true".equals(encheresOuvertes)) {
			try {
				List<ArticlesVendus> articlesEncheresOuvertes = articleManager.selectEncheresOuvertes();
				request.setAttribute("listeEncheres", articlesEncheresOuvertes);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			if("true".equals(mesEncheresRemportees)) {
				try {
					List<ArticlesVendus> articlesMesEncheresRemportees = articleManager.selectMesEncheresRemportees(utilisateurSession);
					request.setAttribute("listeEncheres", articlesMesEncheresRemportees);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if("true".equals(mesVentesEnCours)) {
					try {
						List<ArticlesVendus> articlesMesVentesEnCours = articleManager.selectMesVentesEnCours(utilisateurSession);
						request.setAttribute("listeEncheres", articlesMesVentesEnCours);
					} catch (BusinessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					if("true".equals(ventesNonDebutees)) {
						try {
							List<ArticlesVendus> articlesMesVentesNonDebutees = articleManager.selectMesVentesNonDebutees(utilisateurSession);
							request.setAttribute("listeEncheres", articlesMesVentesNonDebutees);
						} catch (BusinessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						if("true".equals(ventesTerminees)) {
							try {
								List<ArticlesVendus> articlesMesVentesTerminees = articleManager.selectMesVentesTerminees(utilisateurSession);
								request.setAttribute("listeEncheres", articlesMesVentesTerminees);
							} catch (BusinessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							List<ArticlesVendus> articlesEncheresEnCours;
							try {
								articlesEncheresEnCours = articleManager.selectEncheresOuvertes();
								request.setAttribute("listeEncheres", articlesEncheresEnCours);
							} catch (BusinessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				}
			}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
