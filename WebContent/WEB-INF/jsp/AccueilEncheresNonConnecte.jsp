<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.trocencheres202101.messages.LecteurMessage" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width initial-scape=1.0">
<title>Accueil</title>
</head>
<body>
	<header>
		<h3 style="float:left">Les Enchères du Campus</h3>
		<img src="images/116805775.jpg" alt="" />
		
		<a href="${pageContext.request.contextPath}/Connexion" style="float:right">S'inscrire - Se connecter</a>
	</header>
	
	<div>
		<h2 style="text-align:center">Liste des Enchères</h2>
	</div>
	
	<div>
		<%
			String nomArticleFiltre = null;
		if(request.getParameter("nomArticle")!= null){
			nomArticleFiltre = request.getParameter ("nomArticle");
		}
		%>
		
		<%
			String libelleCategorieFiltre = "toutes";
		if(request.getParameter("libelleCategorie") != "toutes"){
			libelleCategorieFiltre = request.getParameter("libelleCategorie");
		}
		%>
		<form method="post" action="<%=request.getContextPath()%>/Accueil">
			<label>Filtres:</label>
			<input type="text" name="nomArticle" placeholder="Le nom de l'article contient" />
		
			<label for="choisir catégorie">Catégorie:</label>
			<select name="libelleCategorie" id="choix-catégorie">
    			<option value="toutes">Toutes</option>
   				<option value="informatique">Informatique</option>
   				<option value="ameublement">Ameublement</option>
   				<option value="vêtement">Vêtement</option>
   				<option value="sport&loisir">Sport&Loisir</option>
			</select>
			<input type="submit" value="Rechercher"/>
		</form>
	</div>
	
	<a href="">Rafraichir</a>
	
	<c:if test="${!empty listeCodesErreur}">
		<div>
		  <strong>Erreur!</strong>
		  <ul>			
		   	<c:forEach var="code" items="${listeCodesErreur}">
				<li>${LecteurMessage.getMessageErreur(code)}</li>
		  	</c:forEach>
		  </ul>
		</div>
	</c:if>
	
	<div>
		<c:choose>
			<c:when test="${les_encheres.size()>0 }">
				<c:forEach var="current" items="${les_encheres }">
					<dl>
  						<dt>${current.nomArticle }</dt>
  						<dd>Prix: ${current.miseAPrix } points</dd>
 						<dd>Fin de l'enchère: ${current.dateFinEnchere }</dd>
 						<dd>Vendeur: ${current.utilisateur.pseudo }</dd>
 						</dl>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p>il n'y a pas d'enchère pour le moment!</p>
			</c:otherwise>
		</c:choose>
	</div>
		
	
	
</body>
</html>