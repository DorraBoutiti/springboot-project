package tn.uma.isamm.spring.tp1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import groovyjarjarpicocli.CommandLine.Model;
import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.ProduitAlimentaire;
import tn.uma.isamm.spring.tp1.metier.MetierImpl;

@Controller
public class ProduitController {
	@Autowired
	private MetierImpl prodImpl;
	
	@GetMapping( "/admin/ProduitAlimentaire/ajout")
	public String ajouterProduitAlimentaire (ModelMap model) {
		ProduitAlimentaire produitAlimentaire = new ProduitAlimentaire();
		model.addAttribute("Prod",produitAlimentaire);
		List<Categorie> categorie = prodImpl.getCategories();
		model.addAttribute("Categs",categorie);
		return "form_ajout";
		
	}
	@PostMapping( "/admin/ProduitAlimentaire/ajout")
	public String saveProduitAlimentaire (ProduitAlimentaire p) {
		prodImpl.saveProduit(p);
		return "liste_produits";
		
	}
	

}
