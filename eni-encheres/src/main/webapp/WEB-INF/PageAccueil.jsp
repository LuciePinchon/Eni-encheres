<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel=stylesheet href="${pageContext.request.contextPath}/css/PageAccueil.css">
<title>ENI-Encheres Accueil</title>
 <style>
        .hidden {
            display: none;
        }
    </style>
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
		<h1>Liste des enchères</h1>
		<form action="" method="post">
			
			<h2>Filtres :</h2>
			
			<section class="rechercheArticle">
				
				<div class="filter">
				<input type="text" id="" name=""
					placeholder="🔍 Le nom de l'article contient"> 
				<label for="categorie">Catégorie : </label> 
				
				<select name="categorie" id="categorie">
					<option value="informatique">Informatique</option>
					<option value="ameublement">Ameublement</option>
					<option value="vetement">Vêtement</option>
					<option value="sportEtLoisirs">Sport & Loisirs</option>
				</select>
				</div>
				
				<div class="search"><button type="submit">Rechercher</button></div>
				
			</section>
			
			<section class="rechercheAchatsVentes">
				<c:choose>	
					<c:when test="${sessionScope.loggedIn}"> 
				
					<div class="achat">
					
						<div>
    <input type="radio" name="selectionVenteAchat" id="achats" checked>
    <label for="achats">Achats</label>
</div>

<div>
    <input type="checkbox" value="encheresOuvertes" name="checkboxEncheres" id="encheresOuvertes">
    <label for="encheresOuvertes">Enchères ouvertes</label>
</div>

<div>
    <input type="checkbox" value="encheresTerminees" name="checkboxEncheres" id="encheresTerminees">
    <label for="encheresTerminees">Enchères terminées</label>
</div>

<div>
    <input type="checkbox" value="mesEncheresEnCours" name="checkboxEncheres" id="mesEncheresEnCours">
    <label for="mesEncheresEnCours">Mes enchères en cours</label>
</div>

<div>
    <input type="checkbox" value="mesEncheresRemportees" name="checkboxEncheres" id="mesEncheresRemportees">
    <label for="mesEncheresRemportees">Mes enchères remportées</label>
</div>

						
					</div>
						
						<div class="vente">
						
						<div><input type="radio" name="selectionVenteAchat"> 
						<label for="selectionVenteAchat">Mes ventes</label></div>
						 
						
						<div><input type="checkbox" name="mesVentesEnCours"> 
						<label for="mesVentesEnCours">Ventes en cours</label></div>
						 
						
						<div><input type="checkbox" name="VentesNonDeb">
						<label for="mesVentesNonDeb">Ventes non débutées</label></div>
						 
						
						<div><input type="checkbox" name="ventesTerminees"> 
						<label for="ventesTerminees">Ventes terminées</label></div>
						
						
						
					</div>
			  		</c:when>
				</c:choose> 
			</section>
		</form>


		<c:if test="${sessionScope.loggedIn==false}">
		<section class="articles">
		<!-- <form method="get" action="${pageContext.request.contextPath}/detailVente"> -->
		<c:if test="${!empty listeEncheresEnCours}">
			<div>
			  <ul>
			  <c:forEach var="article" items="${listeEncheresEnCours}" >
			 
			  
			  	<div class="articleAVendre">
			  	<div class="articleInfo " >
			  		<li style="text-decoration:underline;">${article.getNomArticle()}</li>
			  		<li>Prix : ${article.getPrixVente()} points</li>
			  		<li>Fin de l'enchère : ${article.getDateFinEnchere()}</li>
				  	<li>Vendeur : ${article.utilisateur.pseudo}</li>
				  	<input name="idArticle" value="${article.getNoArticle()}" hidden>	
						
			  		
			  		
			  		<c:if test="${sessionScope.loggedIn}">
			  		
			  		<a href="${pageContext.request.contextPath}/detailVente?idArticle=${article.getNoArticle()}">Détails articles</a>
					
					</c:if>
			  	</div>
			  	<div class="articlePhoto"></div>
			  	
			  	</div>
			  	</c:forEach>
			  </ul>
			</div>
	</c:if>
		
		</section>
		</c:if>



		<div id="contentOption1" class="hidden">
        		<section class="articles">
		<!-- <form method="get" action="${pageContext.request.contextPath}/detailVente"> -->
		<c:if test="${!empty listeEncheresEnCours}">
			<div>
			  <ul>
			  <c:forEach var="article" items="${listeEncheresEnCours}" >
			 
			  
			  	<div class="articleAVendre">
			  	<div class="articleInfo " >
			  		<li style="text-decoration:underline;">${article.getNomArticle()}</li>
			  		<li>Prix : ${article.getPrixVente()} points</li>
			  		<li>Fin de l'enchère : ${article.getDateFinEnchere()}</li>
				  	<li>Vendeur : ${article.utilisateur.pseudo}</li>
				  	<input name="idArticle" value="${article.getNoArticle()}" hidden>	
						
			  		
			  		
			  		<c:if test="${sessionScope.loggedIn}">
			  		
			  		<a href="${pageContext.request.contextPath}/detailVente?idArticle=${article.getNoArticle()}">Détails articles</a>
					
					</c:if>
			  	</div>
			  	<div class="articlePhoto"></div>
			  	
			  	</div>
			  	</c:forEach>
			  </ul>
			</div>
	</c:if>


		
		
		</section>
    </div>
    
    

    <div id="contentOption2" class="hidden">
    <section class="articles">
        		<c:if test="${!empty articlesEncheresTerminees}">
			<div>
			  <ul>
			  <c:forEach var="article" items="${articlesEncheresTerminees}" >
			 
			  
			  	<div class="articleAVendre">
			  	<div class="articleInfo " >
			  		<li style="text-decoration:underline;">${article.getNomArticle()}</li>
			  		<li>Prix : ${article.getPrixVente()} points</li>
			  		<li>Fin de l'enchère : ${article.getDateFinEnchere()}</li>
				  	<li>Vendeur : ${article.utilisateur.pseudo}</li>
				  	<input name="idArticle" value="${article.getNoArticle()}" hidden>	
						
			  		
			  		
			  		<c:if test="${sessionScope.loggedIn}">
			  		
			  		<a href="${pageContext.request.contextPath}/detailVente?idArticle=${article.getNoArticle()}">Voir résultats de l'enchère</a>
					
					</c:if>
			  	</div>
			  	<div class="articlePhoto"></div>
			  	
			  	</div>
			  	</c:forEach>
			  </ul>
			</div>
	</c:if>		
	</section>
    </div>
	
	<div id="contentOption3" class="hidden"></div>	
	<div id="contentOption4" class="hidden"></div>

	</main>
	
	
	<script>
    // Ajouter des écouteurs d'événements aux checkboxes
    var checkboxEncheresOuvertes = document.getElementById('encheresOuvertes');
    var checkboxEncheresTerminees = document.getElementById('encheresTerminees');
    var checkboxMesEncheresEnCours = document.getElementById('mesEncheresEnCours');
    var checkboxMesEncheresRemportees = document.getElementById('mesEncheresRemportees');
    
    var radioAchats = document.getElementById('achats');
    var radioMesVentes = document.getElementById('mesVentes');

    checkboxEncheresOuvertes.addEventListener('change', toggleContent);
    checkboxEncheresTerminees.addEventListener('change', toggleContent);
    checkboxMesEncheresEnCours.addEventListener('change', toggleContent);
    checkboxMesEncheresRemportees.addEventListener('change', toggleContent);
    radioAchats.addEventListener('change', toggleContent);
    radioMesVentes.addEventListener('change', toggleContent);

    function toggleContent() {
        var contentOption1 = document.getElementById('contentOption1');
        var contentOption2 = document.getElementById('contentOption2');
        var contentOption3 = document.getElementById('contentOption3');
        var contentOption4 = document.getElementById('contentOption4');

        // Afficher ou masquer le contenu en fonction des options sélectionnées
        if (checkboxEncheresOuvertes.checked) {contentOption1.classList.remove('hidden');}
        if (!checkboxEncheresOuvertes.checked) {contentOption1.classList.add('hidden');}     
        if (checkboxEncheresTerminees.checked) {contentOption2.classList.remove('hidden');}
        if (!checkboxEncheresTerminees.checked) {contentOption2.classList.add('hidden');}
        if (checkboxMesEncheresEnCours.checked) {contentOption3.classList.remove('hidden');}
        if (!checkboxMesEncheresEnCours.checked) {contentOption3.classList.add('hidden');}     
        if (checkboxMesEncheresRemportees.checked) {contentOption4.classList.remove('hidden');}
        if (!checkboxMesEncheresRemportees.checked) {contentOption4.classList.add('hidden');}

        // Ajoutez ici la logique pour les checkboxes si nécessaire
    }
</script>


</body>
</html>