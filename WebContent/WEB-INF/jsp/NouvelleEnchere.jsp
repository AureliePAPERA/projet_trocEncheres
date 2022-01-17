<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.trocencheres202101.messages.LecteurMessage" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vendre un article</title>
</head>
<body>

	<header>
		<h3 style="float:left">Les Enchères du Campus</h3> <br/>
	</header>
	
	<div style="text-align: center">
		<h2>Nouvelle Vente</h2>
	</div>

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

	<div style="text-align: center">
	<form action="<%=request.getContextPath()%>/Nouvelle_Enchere_Enregistrement" method="post">
		
		<label for="nomArticle">Article:</label>
		<input type="text" name="nomArticle" value="${param.nomArticle }"/> <br/>
		
		<label for="description">Description:</label>
		<input type="text" name="description" value="${param.description }"/> <br/>
		
		<label for="choisir catégorie">Catégorie:</label>
			<select name="libelleCategorie" id="choix-catégorie" value="${param.libelleCategorie }">
   				<option value="vide"></option>
   				<option value="Informatique">Informatique</option>
   				<option value="Ameublement">Ameublement</option>
   				<option value="Vêtement">Vêtement</option>
   				<option value="Sport & Loisir">Sport&Loisir</option>
			</select> <br/>
		
		<label for="miseAPrix">Mise à prix:</label>
		<input type="number" name="miseAPrix" value="${param.miseAPrix }"/> <br/>
		
		<label for="image">Photo de l'article:</label>
		<input type="button" name="image" value="Uploader"/> <br/>
		
		<label for="dateDebutEnchere">Début de l'enchère : </label>
		<input type="datetime-local" name="dateDebutEnchere" type="datetime-local" value="${param.dateDebutEnchere }"/> <br/>
		
		<label for="dateFinEnchere">Fin de l'enchère : </label>
		<input type="datetime-local" name="dateFinEnchere" type="datetime-local" value="${param.dateFinEnchere }"/> <br/>
		
		<fieldset>
			<legend>Retrait</legend>
			
			<label for="rue">Rue:</label>
			<input type="text" name="rue" value="${utilisateur.rue}"/> <br/>
		
			<label for="codePostal">Code Postal:</label>
			<input type="text" name="codePostal" value="${utilisateur.codePostal}"/> <br/>
		
			<label for="ville">Ville:</label>
			<input type="text" name="ville" value="${utilisateur.ville}"/> <br/>
		
		</fieldset>
		<input type="submit" value="Enregistrer"/>
	
	</form>
	<a href="<%=request.getContextPath()%>/Accueil_Abonne"><input type="button" value="Annuler"/></a>
	</div>
</body>
</html>