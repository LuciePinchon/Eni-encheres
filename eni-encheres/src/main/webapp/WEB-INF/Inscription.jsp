<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
    
 <!-- JSP pour inscription ou modification d'un profil -->   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Inscription - Modification du profil</title>
<link rel=stylesheet type="text/css" href="${pageContext.request.contextPath}/css/Inscription.css">
</head>
<body>


<header>
<nav>
<c:choose>
<c:when test="${sessionScope.loggedIn==false}">
<jsp:include page="/WEB-INF/fragments/TestFragment.jsp"></jsp:include>
</c:when>
<c:otherwise>
		<jsp:include page="/WEB-INF/fragments/NavFragment.jsp"></jsp:include>
</c:otherwise>

</c:choose>
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
	

<c:choose>
			<c:when test="${sessionScope.loggedIn}">
<h1>Création d'un profil</h1>
			</c:when>
			<c:otherwise>
	<h1>Mon profil</h1>
			</c:otherwise>
</c:choose>


<form method="post"  action="${pageContext.request.contextPath}/inscription">

<div>
<div class="champlabel"><label for="pseudo">Pseudo: </label>
<input type="text" name="pseudo" pattern="[a-zA-Z0-9]+" value="${sessionScope.utilisateur.getPseudo()}" required></div>

<div class="champlabel"><label for="nom">Nom: </label>
<input type="text" name="nom" value="${sessionScope.utilisateur.getNom()}" required></div>

</div>



<div>
<div class="champlabel"><label for="prenom">Prénom: </label>
<input type="text" name="prenom" value="${sessionScope.utilisateur.getPrenom()}" required></div>


<div class="champlabel">
<label for="email">Email: </label>
<input type="email" name="email" value="${sessionScope.utilisateur.getEmail()}" required></div>
</div>



<div>
<div class="champlabel"><label for="telephone">Téléphone: </label>
<input type="text" name="telephone" value="${sessionScope.utilisateur.getTelephone()}" required></div>

<div class="champlabel"><label for="rue">Rue: </label>
<input type="text" name="rue" value="${sessionScope.utilisateur.getRue()}" required></div>

</div>


<div>
<div class="champlabel"><label for="code">Code postal: </label>
<input type="text" name="code" value="${sessionScope.utilisateur.getCodePostal()}" required></div>

<div class="champlabel"><label for="ville">Ville: </label>
<input type="text" name="ville" value="${sessionScope.utilisateur.getVille()}" required></div>

</div>



<div>
<div class="champlabel"><label for="mdp">Mot de passe: </label>
<input type="password" name="mdp" value="${sessionScope.utilisateur.getMotDePasse()}" required></div>

<div class="champlabel"><label for="mdpconfirmation">Confirmation: </label>
<input type="password" name="mdpconfirmation" value="${sessionScope.utilisateur.getMotDePasse()}" required></div>

</div>


<div  id="boutons">
<c:choose>
			<c:when test="${not sessionScope.loggedIn}">
				<input type="submit" name="action" value="Créer">
				<a href="/eni-encheres/pageAccueil"><input type="button" value="Annuler"></a>
			</c:when>
			<c:otherwise>
	<input type="submit" name="action" value="Modifier">
	<input type="submit" name="action" value="Supprimer">
			</c:otherwise>
</c:choose>
</div>

</form>
</main>
</body>
</html>