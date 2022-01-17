<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.trocencheres202101.messages.LecteurMessage" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width initial-scape=1.0">
<title>Profil</title>
</head>
<body style="width:100%; height:100%">
	<header>
		<h3 >Les Enchères du Campus</h3>
	</header>
	<div>
			<h4>Pseudo : ${utilisateur.pseudo}</h4>
			<h4>Nom : ${utilisateur.nom}</h4>
			<h4>Prénom : ${utilisateur.prenom}</h4>
			<h4>Email : ${utilisateur.email}</h4>
			<h4>Téléphone : ${utilisateur.telephone}</h4>
			<h4>Rue : ${utilisateur.rue}</h4>
			<h4>Code postal : ${utilisateur.codePostal}</h4>
			<h4>Ville : ${utilisateur.ville}</h4>
		<a class="btn" href="${pageContext.request.contextPath}/ServletModifierProfil" title="Modifier profil">Modifier</a>
	</div>
</body>
</html>