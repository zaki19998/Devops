package tn.esprit.spring.services;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;

import java.util.List;

public interface ITrainService {
	 Train ajouterTrain(Train t);
     void affecterTainAVoyageur(Long   idTrainur, Ville nomGareDepart, Ville nomGareArrivee,  double heureDepart);
     int trainPlacesLibres(Ville nomGareDepart);
     List<Train> listerTrainsIndirects(Ville nomGareDepart, Ville nomGareArrivee);
     void desaffecterVoyageursTrain(Ville nomGareDepart, Ville nomGareArrivee, double heureDepart);
     void trainsEnGare();
	
     void modifierTrain(Train t);
	 List<Train> recupererAll();
	 Train recupererTrainParId(long idTrain);
	 void supprimerTrain(Train t);
}

