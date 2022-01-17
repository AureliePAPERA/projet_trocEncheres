<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.trocencheres202101.messages.LecteurMessage" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width initial-scape=1.0">
<title>Mon Profil</title>
</head>
<body>
	<header>
		<h3 style="float: left">Les Enchères du Campus</h3>
	</header>
	<div style="text-align: center">
		<h1>Mon Profil</h1>
		<div>
		<c:if test="${!empty listeCodesErreur}">
			<div class="alert alert-danger" role="alert">
			  <strong>Erreur !</strong>
			  <ul>
			  	<c:forEach var="code" items="${listeCodesErreur}">
			  		<li>${LecteurMessage.getMessageErreur(code)}</li>
			  	</c:forEach>
			  </ul>
			</div>
		</c:if>
		</div>
		<form method="POST" action="<%=request.getContextPath()%>/Inscription">
			<div class="saisie">
				<label for="Pseudo">Pseudo : </label><input type="text" id="pseudo" name="pseudo" placeholder="Entrez votre pseudo">
			</div>
			<div class="saisie">
				<label for="Nom">Nom : </label><input type="text" id="nom" name="nom" placeholder="Entrez votre nom">
			</div>
			<div class="saisie">
				<label for="Prenom">Prénom : </label> <input type="text" id="prenom" name="prenom" placeholder="Entrez votre prénom">
			</div>
			<div class="saisie">
				<label for="Email">Email : </label> <input type="email" id="email" name="email" placeholder="Entrez votre email">
			</div>
			<div class="saisie">
				<label for="Telephone">Téléphone :</label> <input type="tel" id="Telephone" name="Telephone" placeholder="Entrez votre téléphone">
			</div>
			<div class="saisie">
				<label for="Rue">Rue : </label> <input type="text" id="rue" name="rue" placeholder="Entrez votre rue">
			</div>
			<div class="saisie">
				<label for="CodePostal">CodePostal : </label> <input type="text" id="codePostal" name="codePostal" placeholder="Entrez votre code postal">
			</div>
			<div class="saisie">
				<label for="Ville">Ville : </label> <input type="text" id="ville" name="ville" placeholder="Entrez le nom de la ville">
			</div>
			<div class="saisie">
				<label for="MotdePasse">Mot de passe : </label> <input type="password" id="motDePasse" name="motDePasse" placeholder="Entrez le mot de passe">
			</div>
			<div class="saisie">
				<label for="ConfirmationMdp">Confirmation : </label> <input type="password" id="confirmationMdp" name="confirmationMdp" placeholder="Confirmez le mot de passe">
			</div>
			<input type="submit" value="Créer" />
		</form>
		<a href="<%=request.getContextPath()%>/Accueil"><input type="button" value="Annuler"/></a>
	</div>
</body>
</html>