package tn.uma.isamm.spring.tp1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.ProduitCosmetique;
import tn.uma.isamm.spring.tp1.metier.MetierImpl;

public class produitcosmetiquesController {
	@Autowired
	private MetierImpl prodImpl;
	
	@GetMapping( "/admin/produitCosmetique/ajout")
	public String ajouterProduitAlimentaire (Model model) {		
		ProduitCosmetique produitCosmetique = new ProduitCosmetique();
		model.addAttribute("Prod",produitCosmetique);
		List<Categorie> categorie = prodImpl.getCategories();
		model.addAttribute("Categs",categorie);
		return "form_ajout";
		
	}
	@PostMapping( "/admin/produitCosmetique/ajout")
	public String saveProduitAlimentaire (ProduitCosmetique p) {
		prodImpl.saveProduit(p);
		return "liste_produits";
		
	}

}
