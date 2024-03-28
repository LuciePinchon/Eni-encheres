<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Afficher un profil</title>
<link rel=stylesheet type="text/css" href="${pageContext.request.contextPath}/css/AfficherUnProfil.css">
</head>
<body>
   <header>
<nav>
 
<jsp:include page="/WEB-INF/fragments/NavFragment.jsp"></jsp:include>
	
    </nav>
    </header>
    
    <main>
<h1>Profil utilisateur</h1>

<ul class ="details-utilisateur">
<li><strong>Pseudo:</strong> ${sessionScope.utilisateur.getPseudo()}</li>
<li><strong>Nom:</strong> ${sessionScope.utilisateur.getNom()}</li>
<li><strong>Prénom:</strong> ${sessionScope.utilisateur.getPrenom()}</li>
<li><strong>Téléphone:</strong> ${sessionScope.utilisateur.getTelephone() }</li>
<li><strong>Rue:</strong> ${sessionScope.utilisateur.getRue() }</li>
<li><strong>Code postal:</strong> ${sessionScope.utilisateur.getCodePostal() }</li>
<li><strong>Ville:</strong> ${sessionScope.utilisateur.getVille() }</li>
<li><strong>Votre crédit:</strong> ${sessionScope.utilisateur.getCredit() }</li>




</ul>

</main>
</body>
</html>