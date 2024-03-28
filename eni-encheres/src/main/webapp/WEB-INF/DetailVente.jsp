<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Détail vente </title>
<link rel=stylesheet type="text/css" href="${pageContext.request.contextPath}/css/DetailVente.css">
</head>

<body>

<header>
<nav>
		<jsp:include page="/WEB-INF/fragments/NavFragment.jsp"></jsp:include>
</nav>
</header>
<main>
        <c:if test="${!empty listeCodesErreur}">
			<div class="alert" role="alert" 
			style="display: block;
    		text-align: center;
    		border: 2px solid;
    		width: 50vw;
    		margin: auto;">
			  <strong>Erreur!</strong>
			  <ul style="list-style-type: none;">
			  <c:forEach var="code" items="${listeCodesErreur}">
			  		<li>${LecteurMessage.getMessageErreur(code)}</li>
			  	</c:forEach>
			  </ul>
			</div>
	</c:if>


<h1>Détails vente</h1>


<p><strong>Nom:</strong> ${articleDetail.getNomArticle()}<br>
<strong>Description: </strong> ${articleDetail.getDescriptionArticle()}<br>

<c:choose>
			<c:when test="${articleDetail.getCategorieArticle()==1}">
				<strong>Catégorie: </strong> Informatique<br>
			</c:when>
			<c:when test="${articleDetail.getCategorieArticle()==2}">
				<strong>Catégorie: </strong> Ameublement<br>
			</c:when>
			<c:when test="${articleDetail.getCategorieArticle()==3}">
				<strong>Catégorie: </strong> Vêtements<br>
			</c:when>
			<c:otherwise>
				<strong>Catégorie: </strong> Sport & Loisirs<br>
			</c:otherwise>
</c:choose>



<Strong>Vendeur:</Strong> ${articleDetail.utilisateur.pseudo}<br>
<Strong>Fin de l'enchère:</Strong> ${articleDetail.getDateFinEnchere()}<br>
<Strong>Mise à prix:</Strong> ${articleDetail.getPrixInitial()}<br>
<Strong>Meilleure offre: </Strong>
<c:choose>
			<c:when test="${articleDetail.getPrixVente()==0}">
				Pas encore d'enchères pour cet article
			</c:when>
			<c:otherwise>
				${articleDetail.getPrixVente()}
			</c:otherwise>
</c:choose>

<c:if test="${articleDetail.utilisateur.noUtilisateur!=sessionScope.utilisateur.noUtilisateur && !dateActuelle.isAfter(articleDetail.dateFinEnchere)}">

<form method="post" action="${pageContext.request.contextPath}/detailVente?idArticle=${idArticle}">
<br>
<label for="proposition"><strong>Ma proposition :</strong></label>
<c:choose>
			<c:when test="${articleDetail.getPrixVente()==0}">
				<input type="number" id="proposition" name ="proposition" min ="${articleDetail.getPrixInitial()}+1" max ="10000" />
			</c:when>
			<c:otherwise>
				<input type="number" id="proposition" name ="proposition" min ="${articleDetail.getPrixVente()}+1" max ="10000" />
			</c:otherwise>
</c:choose>

<button type ="submit">Enchérir</button>

</form>
</c:if>
<c:if test="${articleDetail.dateDebutEnchere!= null && dateActuelle.isBefore(articleDetail.dateDebutEnchere)}">			
		<a href="${pageContext.request.contextPath}/modifArticle?idArticle=${idArticle}"><button>Modifier</button></a>	
</c:if>


<c:if test="${articleDetail.dateFinEnchere!= null && dateActuelle.isAfter(articleDetail.dateFinEnchere)}">	
		<br>
				
		<strong>Cette vente est terminée.</strong>	
		<c:if test="${articleDetail.prixVente==0}">	
			<br>		
			<strong>Cet article n'a pas trouvé preneur.</strong>	
		</c:if>
		<c:if test="${vainqueur.noUtilisateur==sessionScope.utilisateur.noUtilisateur}">	
			<br>		
			<strong>Vous avez remporté cette vente !</strong>	
		</c:if>
		<c:if test="${vainqueur.noUtilisateur!=sessionScope.utilisateur.noUtilisateur && articleDetail.utilisateur.noUtilisateur!=sessionScope.utilisateur.noUtilisateur}">	
			<br>		
			<strong>Vous n'avez pas remporté ou participé à cette vente.</strong>	
		</c:if>	
		<c:if test="${articleDetail.utilisateur.noUtilisateur==sessionScope.utilisateur.noUtilisateur}">	
			<br>		
			<strong>${vainqueur.pseudo} a gagné cette vente.</strong>
			<input type="button" value="Retrait">	
		</c:if>	
	

</c:if>

</main>
</body>
</html>