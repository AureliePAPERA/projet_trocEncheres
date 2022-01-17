<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.trocencheres202101.messages.LecteurMessage" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width initial-scape=1.0">
<!-- Ajouter le lien vers les CSS -->
<title>Connexion</title>
<body style="text-align:center" class="container">
	<div class="col-12">
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
		<%
			String inputId = null;
			
			if(request.getAttribute("identifiant") != null)
			{
				inputId = String.valueOf(request.getAttribute("identifiant"));
			}
		%>
		<form method="post" action="${pageContext.request.contextPath}/Connexion">
			<fieldset>
				<label for="nom">Identifiant :</label> 
					<input type="text" id="identifiant" name="identifiant" placeholder="Pseudo ou Email" value="${cookie['identifiant'].value }" size="20" maxlength="60" /> 
						<span>${message_erreurs['identifiant']}</span><br />
					<label for="motdepasse">Mot de passe :</label> 
					<input type="password" id="motDePasse" name="motDePasse" value="<c:out value="${utilisateur.motDePasse}"/>" size="20" maxlength="20" /> 
						<span>${message_erreurs['motdepasse']}</span><br /> 
					<input type="checkbox" id="memoriser" name="memoriser">
					<label>Se souvenir de moi</label> <br />
				    <input type="submit" value="Connexion" /> <br />
			</fieldset>
		</form>
		<a href="${pageContext.request.contextPath}/Inscription" title="Créer un compte">Créer un compte</a><br>
		<a href="${pageContext.request.contextPath}/Accueil" title="Retourner à l'accueil">Retourner à l'accueil</a>
	</div>
</body>
</html>



