package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException(
					"Le village " + nom + " n'a pas de chef.");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "
				+ nbProduit + " " + produit + ".\n");
		int indice = marche.TrouverEtalLibre();
		if (indice == -1) {
			chaine.append("Désolé " + vendeur.getNom()
					+ ", il n'y a plus d'étal libre.\n");
		} else {
			marche.utiliserEtal(indice, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des "
					+ produit + " à l'étal n°" + (indice + 1) + ".\n");
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		if (etals.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit
					+ " au marché.\n");
		} else if (etals.length == 1) {
			chaine.append("Seul le vendeur " + etals[0].getVendeur().getNom()
					+ " propose des " + produit + " au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (Etal etal : etals) {
				chaine.append("- " + etal.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal == null) {
			return vendeur.getNom() + " n'a pas d'étal dans ce marché.\n";
		}
		return etal.libererEtal();
	}

	public String afficherMarche() {
		return "Le marché du village \"" + nom + "\" possède plusieurs étals :\n"
				+ marche.afficherMarche();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static class Marche{
		private Etal[] etals;
		private Marche(int nbetal) {
			etals = new Etal[nbetal];
			for(int i=0;i < nbetal;i++) {
				etals[i]=new Etal();
			}
		}
		void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
			}
		int TrouverEtalLibre() {
			for(int i=0;i< etals.length;i++) {
				if (!etals[i].isEtalOccupe()) {
		            return i;
		        }
				
			}
			return -1;
		}
		Etal[] trouverEtals(String produit) {
			Etal[] reponse;
			int taille=0;
			for(int i=0;i< etals.length;i++) {
				if(etals[i].contientProduit(produit)&&etals[i].isEtalOccupe()) {
					taille++;
				}
			}
			reponse = new Etal[taille];
			int j=0;
			for(int i=0;i< etals.length;i++) {
				if(etals[i].contientProduit(produit)&&etals[i].isEtalOccupe()) {
					reponse[j]=etals[i];
					j++;
				}
				}
			return reponse;
			}
		Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i< etals.length;i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		String afficherMarche(){
			StringBuilder chaine = new StringBuilder();
			int nbEtalvide=0;
			for(int i=0;i<etals.length;i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {nbEtalvide++;}
			}
			if(nbEtalvide>0) {
				chaine.append("Il reste " + nbEtalvide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
		}
	
		
	}
