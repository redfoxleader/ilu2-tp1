package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		// 1) libererEtal sur un étal jamais occupé
		Etal etal = new Etal();
		etal.libererEtal();

		// 2a) acheteur null
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		etal.occuperEtal(bonemine, "fleurs", 20);
		System.out.println(etal.acheterProduit(10, null));

		// 2b) quantité négative
		Gaulois obelix = new Gaulois("Obélix", 25);
		try {
			System.out.println(etal.acheterProduit(-5, obelix));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		// 2c) étal non occupé
		Etal etalVide = new Etal();
		try {
			System.out.println(etalVide.acheterProduit(5, obelix));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		// 3) village sans chef
				Village villageSansChef = new Village("village sans chef", 5, 3);
				try {
					System.out.println(villageSansChef.afficherVillageois());
				} catch (VillageSansChefException e) {
					e.printStackTrace();
				}

		System.out.println("Fin du test");
	}
}