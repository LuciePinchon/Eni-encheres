<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

     <!-- JSP pour inscription ou modification d'un profil -->   
     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Formulaire de connexion</title>
<link rel=stylesheet type="text/css" href="${pageContext.request.contextPath}/css/Connexion.css">
</head>
<body>
<header>
<nav>
<jsp:include page="/WEB-INF/fragments/TestFragment.jsp"></jsp:include>
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
	
    
   <form action ="ServletConnexion" method ="post">
   
   
	<div class="champ"><label for ="username">Identifiant:</label>
        <input type ="text" id="username" name="username" required></div>
       
    <div class="champ"><label for ="password" >Mot de passe :</label>
       <input type ="password" id ="password" name ="password" required></div>

      <div class="tentativeCo">
      <div id="boutonCo"><input type ="submit" value ="se connecter"></div>

       <div class="optionCo">
       <div><input type ="checkbox" id = "seSouvenir" name = "seSouvenir">
        <label for ="rememberMe"> Se souvenir de moi </label></div>

        <a href ="">Mot de passe oublié</a></div>
        </div>

        

        <a href ="/eni-encheres/ServletInscription"><input id="boutonCreer" type ="button" value="Créer un compte"></a>
        
       </form>
 </main>
</body>
</html>