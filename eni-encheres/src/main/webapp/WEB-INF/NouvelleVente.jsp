<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ENI-Encheres Nouvelle Vente</title>
<link rel=stylesheet type="text/css" href="${pageContext.request.contextPath}/css/NouvelleVente.css">
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/fragments/NavFragment.jsp"></jsp:include>
</header>	

<main>	
	<c:if test="${not empty listeCodesErreur}">
    	<div class="alert" role="alert" 
	        style="display: block;
	        text-align: center;
	        border: 2px solid;
	        width: 50vw;
	        margin: auto;
	        color : darkblue">
        <strong>Erreur!</strong>
        <ul style="list-style-type: none;">
            <c:forEach var="code" items="${listeCodesErreur}">
                <li>${LecteurMessage.getMessageErreur(code)}</li>
            </c:forEach>
        </ul>
    	</div>
	</c:if>

		




	<p style="color:black; text-align:center;" id="articleposte">${articleposte}</p>

	<h1>Nouvelle vente</h1>
	<c:choose>
		<c:when test="${sessionScope.loggedIn}">
	
	<div>
		<form action="${pageContext.request.contextPath}/nouvelleVente" method="post" class="card">	
			<section class="venteArticle">
				<label for="nouvelArticle">Article</label>
				<input type="text" id="nouvelArticle" name="nouvelArticle" required>
			
				<label for="descriptionArticle">Description</label>
				<textarea id="descriptionArticle" name="descriptionArticle" required></textarea>
			
				<label for="categorie">Catégorie</label>
					<select name="categorie" id="categorie" required>
					<c:choose>
						<c:when test="${noCategorie==1}"><option value="1" selected>Informatique</option></c:when>
						<c:otherwise><option value="1">Informatique</option></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${noCategorie==2}"><option value="1" selected>Ameublement</option></c:when>
						<c:otherwise><option value="2">Ameublement</option></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${noCategorie==3}"><option value="1" selected>Vêtement</option></c:when>
						<c:otherwise><option value="3">Vêtement</option></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${noCategorie==4}"><option value="1" selected>Sport & Loisirs</option></c:when>
						<c:otherwise><option value="4">Sport & Loisirs</option></c:otherwise>
					</c:choose>

					</select>
					

				<label for="uploadPhotoArticle">Photo de l'article</label>
				<input type="file" name="uploadPhotoArticle" id="uploadPhotoArticle">
			
				<label for="miseAPrix">Mise à prix</label>
				<input type="number" name="miseAPrix" id="miseAPrix" required>
			
				<label for="debutEnchere">Début de l'enchère</label>
				<input type="date" name="debutEnchere" id="debutEnchere" required>
			
				<label for="finEnchere">Fin de l'enchère</label>
				<input type="date" name="finEnchere" id="finEnchere" required>
				
			</section>

			<section class="retraitArticle">
			<p>Retrait</p>
				<label for="retraitRue">Rue :</label>
				<input type="text" id="retraitRue" name="retraitRue" value="${sessionScope.utilisateur.getRue()}" required>
				<label for="retraitCP">Code postal :</label>
				<input type="text" id="retraitCP" name="retraitCP" value="${sessionScope.utilisateur.getCodePostal()}" required>
				<label for="retraitVille">Ville :</label>
				<input type="text" id="retraitVille" name="retraitVille" value="${sessionScope.utilisateur.getVille()}" required>
			</section>
			
			
				<input class="button-a" type="submit" name="action" value="Enregistrer" class="button">
				<a href="/eni-encheres/pageAccueil" class="button-a"><input type="button" value="Annuler" class="button"></a>
			
		</form>


	</div>

		</c:when>
		<c:otherwise>
		<p> Vous devez être connecté pour voir cette page.</p>
		</c:otherwise>
	</c:choose>
</main>

</body>
</html>