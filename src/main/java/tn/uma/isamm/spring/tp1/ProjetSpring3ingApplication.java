package tn.uma.isamm.spring.tp1;

import java.util.Date;
import java.util.HashSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import tn.uma.isamm.spring.tp1.dao.CategorieDAO;
import tn.uma.isamm.spring.tp1.dao.ClientDAO;
import tn.uma.isamm.spring.tp1.dao.CommandeDAO;
import tn.uma.isamm.spring.tp1.dao.FournisseurDAO;
import tn.uma.isamm.spring.tp1.dao.LigneCommandeDAO;
import tn.uma.isamm.spring.tp1.dao.ProduitDAO;
import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.Client;
import tn.uma.isamm.spring.tp1.entities.Commande;
import tn.uma.isamm.spring.tp1.entities.DetailClient;
import tn.uma.isamm.spring.tp1.entities.Fournisseur;
import tn.uma.isamm.spring.tp1.entities.LigneCommande;
import tn.uma.isamm.spring.tp1.entities.Produit;
import tn.uma.isamm.spring.tp1.entities.ProduitAlimentaire;
import tn.uma.isamm.spring.tp1.entities.ProduitCosmetique;



@SpringBootApplication
public class ProjetSpring3ingApplication {

	public static void main(String[] args) {
		ApplicationContext ctx=SpringApplication.run(ProjetSpring3ingApplication.class, args);
		
		/*** Persistance d'une relation One-To-One   ****/
		
		ClientDAO clientDAO=ctx.getBean(ClientDAO.class);
		ProduitDAO produitDAO=ctx.getBean(ProduitDAO.class);
		CategorieDAO categorieDAO = ctx.getBean(CategorieDAO.class);
		FournisseurDAO fournisseurDAO=ctx.getBean(FournisseurDAO.class);
		CommandeDAO commandeDAO = ctx.getBean(CommandeDAO.class);
		LigneCommandeDAO ligneCommandeDAO = ctx.getBean(LigneCommandeDAO.class);
	
		
		DetailClient dc1 = new DetailClient("tunis","202020","mail1@gmail.com");
		Client c1 = new Client("ali");
		
		c1.setDetailClient(dc1);
		dc1.setClient(c1);
		
		//La sauvegarde d'un client implique la mise à jour de sa clé étrangère dans la table Commande (grâce à l'attribut "inverse ="false" dans l'élément set de client.hbm.xml)
		clientDAO.save(c1);
				
		//session.save(dc1); //sauvegardé automatiquement grâce à l'attribut cascade="save-update"
						
		/*** Persistance d'une relation One-To-Many   ****/
		
		
		Commande cd1 = new Commande(new Date(),"Tunis", c1);
		Commande cd2 = new Commande(new Date(), "Bizerte", c1);
		Commande cd3 = new Commande(new Date(), "Nabeul", c1);
		
		// Etablir le lien entre le client et ses commandes, c'est Commande qui est proprietaire de la relation et c'est donc lui qui doit placer la valeur de la clè étrangère.
//		cd1.setClient(c1);
//		cd2.setClient(c1);
//		cd3.setClient(c1);	
	
		//Client est l'entité parente, c'est donc elle qui se charge de sauvegarder les commandes qui y sont relatives.
		c1.ajouterCommande(cd1);
		c1.ajouterCommande(cd2);
		c1.ajouterCommande(cd3);
		
		clientDAO.save(c1);	
		
		/*** Persistance d'une relation Many-To-Many non porteuse   ****/
			
		Categorie cat1 = new Categorie(1L, "catégorie 1");
		Categorie cat2 = new Categorie(2L, "catégorie 2");
		
		Produit p1= new Produit(111L, "stylo", 500, "piece", cat1);
		Produit p2= new ProduitAlimentaire(112L, "Lait", 500, "piece",250);
		p2.setCategorie(cat1);
		Produit p3= new ProduitCosmetique(113L, "Crème hydratante", 500, "piece", true);
		p3.setCategorie(cat2);
		Produit p44= new ProduitCosmetique(114L, "Crème solaire", 800, "piece", true);
		p44.setCategorie(cat2);
		Produit p55= new ProduitCosmetique(115L, "masque", 1500, "piece", true);
		p55.setCategorie(cat1);
		
		//*********************************************
		int nombreDeDonnees = 30; 

        for (int i = 0; i < nombreDeDonnees; i++) {            
            DetailClient dc = new DetailClient("Adresse " + i, "202020" + i, "mail" + i + "@gmail.com");
            Client client = new Client("Client " + i);
            client.setDetailClient(dc);
            dc.setClient(client);
            clientDAO.save(client);
            Commande commande = new Commande(new Date(), "Lieu " + i, client);
            commandeDAO.save(commande);            
            //Produit produit = new Produit(120L + i, "Produit " + i, 100 + i, "unité");
            //produitDAO.save(produit);
            Produit ptest1= new ProduitAlimentaire(160L + i, "Lait" + i, 500, "piece",250 + i);
            if(i %2 ==0) {
            	ptest1.setCategorie(cat1);
            }else {
            	ptest1.setCategorie(cat2);
            }
            ptest1.setCategorie(cat1);
    		Produit ptest2= new ProduitCosmetique(2500L + i, "Crème hydratante" + i , 500, "piece", true);
    		if(i %2 ==0) {
            	ptest2.setCategorie(cat1);
            }else {
            	ptest2.setCategorie(cat2);
            }
    		categorieDAO.save(cat1);
    		categorieDAO.save(cat2);
    		produitDAO.save(ptest1);
    		produitDAO.save(ptest2);
        }  
          //*********************************************  
		
		categorieDAO.save(cat1);
		categorieDAO.save(cat2);
		produitDAO.save(p1);
		produitDAO.save(p2);
		produitDAO.save(p3);
		produitDAO.save(p44);
		produitDAO.save(p55);
		
		HashSet<Produit> produits = new HashSet<Produit>();
		
		produits.add(p1);
		produits.add(p2);
		produits.add(p3);
		
		Fournisseur f = new Fournisseur(100,"ali");
		
		f.setProduits(produits);
		
		fournisseurDAO.save(f);
		
		
		
		/*** Persistance d'une relation Many-To-Many porteuse de données  ****/
		
		Produit p4 = new Produit(150L, "Gomme", 500, "piece");
		Produit p5= new ProduitAlimentaire(151L, "Beure", 500, "piece",250);
		Produit p6= new ProduitCosmetique(152L, "2.	Crème solaire", 500, "piece", true);
		
		Commande cd4 = new Commande(new Date(), null);
		Commande cd5 = new Commande(new Date(), null);
		Commande cd6 = new Commande(new Date(), null);
		
		LigneCommande l1 = new LigneCommande(10, p4, cd4);
		LigneCommande l2 = new LigneCommande(15, p5, cd4);
		LigneCommande l3 = new LigneCommande(20, p6, cd4);
		LigneCommande l4 = new LigneCommande(22, p4, cd5);
		LigneCommande l5 = new LigneCommande(4, p6, cd5);
		
		
//		ligneCommandeDAO.save(l1);
//		ligneCommandeDAO.save(l2);
//		ligneCommandeDAO.save(l3);
//		ligneCommandeDAO.save(l4);
//		ligneCommandeDAO.save(l5);
	}

}
