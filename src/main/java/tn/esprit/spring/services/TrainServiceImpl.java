package tn.esprit.spring.services;

import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;
import tn.esprit.spring.entities.Voyage;
import tn.esprit.spring.entities.etatTrain;
import tn.esprit.spring.repository.TrainRepository;
import tn.esprit.spring.repository.VoyageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.spring.repository.VoyageurRepository;

import tn.esprit.spring.entities.Voyageur;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class TrainServiceImpl implements ITrainService {
	private static final Logger l = LogManager.getLogger(TrainServiceImpl.class);

    @Autowired
    VoyageurRepository voyageurRepository;


    @Autowired
    TrainRepository trainRepository;

    @Autowired
    VoyageRepository voyageRepository;


    public Train ajouterTrain(Train t) {

        return trainRepository.save(t);
    }

    public int trainPlacesLibres(Ville nomGareDepart) {
        int cpt = 0;
        int occ = 0;
        List<Voyage> listvoyage = (List<Voyage>) voyageRepository.findAll();
        l.info("tailee" + listvoyage.size());

        for (int i = 0; i < listvoyage.size(); i++) {
        	l.info("gare" + nomGareDepart + "value" + listvoyage.get(0).getGareDepart());
            if (listvoyage.get(i).getGareDepart() == nomGareDepart) {
                cpt = cpt + listvoyage.get(i).getTrain().getNbPlaceLibre();
                occ = occ + 1;
                l.info("cpt " + cpt);
            } else {

            }
        }
        if(occ != 0) {
        	return cpt / occ;
        }
        else {
        	return 0;
        }
    }


    public List<Train> listerTrainsIndirects(Ville nomGareDepart, Ville nomGareArrivee) {

        List<Train> lestrainsRes = new ArrayList<>();
        List<Voyage> lesvoyage = (List<Voyage>) voyageRepository.findAll();
        for (int i = 0; i < lesvoyage.size(); i++) {
            if (lesvoyage.get(i).getGareDepart() == nomGareDepart) {
                for (int j = 0; j < lesvoyage.size(); j++) {
                    if (lesvoyage.get(i).getGareArrivee() == lesvoyage.get(j).getGareDepart() & lesvoyage.get(j).getGareArrivee() == nomGareArrivee) {
                        lestrainsRes.add(lesvoyage.get(i).getTrain());
                        lestrainsRes.add(lesvoyage.get(j).getTrain());

                    } 

                }
            }
        }


        return lestrainsRes;
        //
    }


    @Transactional
    public void affecterTainAVoyageur(Long idVoyageur, Ville nomGareDepart, Ville nomGareArrivee, double heureDepart) {

    	Optional<Voyageur> voyageur = voyageurRepository.findById(idVoyageur);
    	if(voyageur.isPresent()) {
    		l.info("taille test");
    	        Voyageur c = voyageur.get();
    	         List<Voyage> lesvoyages = voyageRepository.RechercheVoyage(nomGareDepart, nomGareDepart, heureDepart);
    	        l.info("taille" + lesvoyages.size());
    	        for (int i = 0; i < lesvoyages.size(); i++) {
    	            if (lesvoyages.get(i).getTrain().getNbPlaceLibre() != 0) {
    	                lesvoyages.get(i).getMesVoyageurs().add(c);
    	                lesvoyages.get(i).getTrain().setNbPlaceLibre(lesvoyages.get(i).getTrain().getNbPlaceLibre() - 1);
    	            } else
    	            	l.info("Pas de place disponible pour " + voyageur.get().getNomVoyageur());
    	            voyageRepository.save(lesvoyages.get(i));
    	        }
    	}
    }

    
    public void desaffecterVoyageursTrain(Ville nomGareDepart, Ville nomGareArrivee, double heureDepart) {
        List<Voyage> lesvoyages = voyageRepository.RechercheVoyage(nomGareDepart, nomGareArrivee, heureDepart);
        l.info("taille" + lesvoyages.size());

        for (int i = 0; i < lesvoyages.size(); i++) {
            for (int j = 0; j < lesvoyages.get(i).getMesVoyageurs().size(); j++)
                lesvoyages.get(i).getMesVoyageurs().remove(j);
            lesvoyages.get(i).getTrain().setNbPlaceLibre(lesvoyages.get(i).getTrain().getNbPlaceLibre() + 1);
            lesvoyages.get(i).getTrain().setEtat(etatTrain.Prevu);
            voyageRepository.save(lesvoyages.get(i));
            trainRepository.save(lesvoyages.get(i).getTrain());
        }
    }

    @Scheduled(fixedRate = 2000)
    public void trainsEnGare() {
        List<Voyage> lesvoyages = (List<Voyage>) voyageRepository.findAll();
        l.info("taille" + lesvoyages.size());

        Date date = new Date();
        l.info("In Schedular After Try");
        for (int i = 0; i < lesvoyages.size(); i++) {
            if (lesvoyages.get(i).getDateArrivee().before(date)) {
            	l.info("les trains sont " + lesvoyages.get(i).getTrain().getCodeTrain());
            }
           
        }
    }
    
    @Override
    public void modifierTrain(Train t) {
    	trainRepository.save(t);
    }


    @Override
    public List<Train> recupererAll() {
        return (List<Train>) trainRepository.findAll();
    }

    @Override
    public Train recupererTrainParId(long idTrain) {
        return  trainRepository.findById(idTrain).orElse(null);
    }

    @Override
    public void supprimerTrain(Train t) {
        trainRepository.deleteById(t.getIdTrain());
    }


}

    
