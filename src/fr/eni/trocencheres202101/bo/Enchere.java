package fr.eni.trocencheres202101.bo;

import java.io.Serializable;
import java.time.LocalDate;

/* Les BO doivent implémenter l'interface Serializable pour que l'on puisse passer d'une forme "données" en BDD 
 * à une forme "Objet" en Java : */
public class Enchere implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Utilisateur vendeur;
	private Utilisateur dernierAcheteur;
	private ArticleVendu articleVendu;
	private LocalDate dateEnchere;
	private int montantEnchere;
		
	public Enchere() {
		
	}
	
	public Enchere(Utilisateur vendeur, Utilisateur dernierAcheteur, ArticleVendu articleVendu, LocalDate dateEnchere, int montantEnchere) {
		this.vendeur = vendeur;
		this.dernierAcheteur = dernierAcheteur;
		this.articleVendu = articleVendu;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}
	
	public Enchere(Utilisateur vendeur, ArticleVendu articleVendu, LocalDate dateEnchere, int montantEnchere) {
		this.vendeur = vendeur;
		this.articleVendu = articleVendu;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}
	
	public Utilisateur getVendeur() {
		return vendeur;
	}
	
	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}
	
	public Utilisateur getDernierAcheteur() {
		return dernierAcheteur;
	}
	
	public void setDernierAcheteur(Utilisateur dernierAcheteur) {
		this.dernierAcheteur = dernierAcheteur;
	}
	
	public ArticleVendu getArticleVendu() {
		return articleVendu;
	}
	
	public void setArticleVendu(ArticleVendu articleVendu) {
		this.articleVendu = articleVendu;
	}
	
	public LocalDate getDateEnchere() {
		return dateEnchere;
	}
	
	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}
	
	public int getMontantEnchere() {
		return montantEnchere;
	}
	
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	
	@Override
	public String toString() {
		return "Enchere [vendeur = " + vendeur + ", dernier acheteur = " + dernierAcheteur + ", article vendu = " + articleVendu + ", date enchère = " + dateEnchere + ", montant enchère ="
				+ montantEnchere + "]";
	}

}
