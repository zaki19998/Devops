package tn.esprit.spring.services;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;

import java.util.ArrayList;
import java.util.List;

public interface ITrainService {
	 Train ajouterTrain(Train t);
     void affecterTainAVoyageur(Long   idTrainur, Ville nomGareDepart, Ville nomGareArrivee,  double heureDepart);
     int TrainPlacesLibres(Ville nomGareDepart);
     List<Train> ListerTrainsIndirects(Ville nomGareDepart, Ville nomGareArrivee);
     void DesaffecterVoyageursTrain(Ville nomGareDepart, Ville nomGareArrivee, double heureDepart);
     void TrainsEnGare();
	
     void modifierTrain(Train t);
	 List<Train> recupererAll();
	 Train recupererTrainParId(long idTrain);
	 void supprimerTrain(Train t);
}

