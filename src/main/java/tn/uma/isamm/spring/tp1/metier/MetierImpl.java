package tn.uma.isamm.spring.tp1.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tn.uma.isamm.spring.tp1.dao.CategorieDAO;
import tn.uma.isamm.spring.tp1.dao.ProduitDAO;
import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.Produit;
import tn.uma.isamm.spring.tp1.entities.ProduitAlimentaire;
@Service
public class MetierImpl implements Metier{
	@Autowired
	ProduitDAO produitDAO;
	@Autowired
	CategorieDAO categorieDAO;
	
	public List<Produit> getProduits(){
		return produitDAO.findAll();
	}
	public void saveProduit(Produit p) {
		produitDAO.save(p);
	}
	
	public Produit getProduitById(Long id) {	
		Optional<Produit> opt = produitDAO.findById(id);
		if (opt.isPresent()){
			return opt.get();
		}
		return null;
	}
	//public List<ProduitAlimentaire> getProduitsAlimentaires() {
    //    return produitDAO.findAllByType("ProduitAlimentaire"); 
    //}
	public Page<Produit> getProduitsPageable(int page, int size){
		PageRequest pr = PageRequest.of(page, size);
		Page<Produit> _page = produitDAO.findAll(pr);
		return _page;		
	}
	public List<Categorie> getCategories(){
		return categorieDAO.findAll();	
	}
	
	public void deleteProduit(Long id) {
		produitDAO.deleteById(id);
	}
}
