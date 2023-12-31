package tn.uma.isamm.spring.tp1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.Produit;
import tn.uma.isamm.spring.tp1.entities.ProduitAlimentaire;
import tn.uma.isamm.spring.tp1.metier.MetierImpl;

@Controller
public class ProduitController {
	@Autowired
	private MetierImpl prodImpl;
	
	@GetMapping( "/admin/ProduitAlimentaire/ajout")
	public String ajouterProduitAlimentaire (Model model) {
		ProduitAlimentaire produitAlimentaire = new ProduitAlimentaire();
		model.addAttribute("Prod",produitAlimentaire);
		List<Categorie> categories = prodImpl.getCategories();
		model.addAttribute("Categs",categories);
		return "ajoutp";		
	}
	//@GetMapping("/admin/ProduitAlimentaire/listeProduitsAlimentaires")
	//public String getListeProduitsAlimentaires(Model model) {
	//    List<ProduitAlimentaire> produitsAlimentaires = prodImpl.getProduitsAlimentaires(); // Obtenez la liste des produits alimentaires depuis votre service
	//    model.addAttribute("produitsAlimentaires", produitsAlimentaires); // Ajoutez la liste à l'objet Model
	//    return "listeproduitsalim"; // Retournez le nom de la vue (fichier HTML)
	//}

	@PostMapping( "/admin/ProduitAlimentaire/ajout")
	public String saveProduitAlimentaire (ProduitAlimentaire p) {
		prodImpl.saveProduit(p);
		return "liste_produits";		
	}
	@GetMapping( "/admin/Produits/list")
	public String afficherList(Model model) {
		List <Produit> produits;
		produits = prodImpl.getProduits();
		model.addAttribute("produits",produits);
		return "listp";	    	
	}
	@GetMapping("/admin/Produits/listpagine")
	public String afficherListeProduits(Model model, @RequestParam(value = "page" , defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
		System.out.println(size);
	    Page<Produit> produitsPage = prodImpl.getProduitsPageable(page, size);
	    List<Produit> produits = produitsPage.getContent();
	    
	    model.addAttribute("produits", produits);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", produitsPage.getTotalPages());
	    
	    return "listppage";
	}
	@PostMapping("/user/rechercheProduit")
	public String rechercheProduit(@RequestParam("id") Long id, Model model) {
	    Produit produit = prodImpl.getProduitById(id); 
	    List<Produit> produits = new ArrayList<>();

	    if (produit != null) {
	        produits.add(produit);
	    }
	    int page = 0;	   
	    model.addAttribute("produits", produits); 
	    model.addAttribute("currentPage", 0); 
	    model.addAttribute("totalPages", 2);	   
	    return "listppage"; 
	}
	@GetMapping("/admin/supprimer")
	public String supprimer(@RequestParam("id") Long id,
	                        @RequestParam(value = "page", defaultValue = "0") int page,
	                        @RequestParam(defaultValue = "10") int size,
	                        Model model) {
	    prodImpl.deleteProduit(id);

	    if (size != 0) {
	        long totalElements = prodImpl.countTotalProduits();
	        int totalPages = (int) Math.ceil((double) totalElements / size);

	        if (totalPages <= page) {
	            // Si la page précédente est hors de la portée des pages actuelles, revenez à la dernière page
	            return "redirect:/admin/Produits/listpagine?page=" + (totalPages - 1) + "&size=" + size;
	        }
	    }

	    return "redirect:/admin/Produits/listpagine?page=" + page + "&size=" + size;
	}

	



}
