package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Train;
import tn.esprit.spring.services.ITrainService;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrainServiceImplJUnitTest {
	
	@Autowired
	ITrainService ts;
	 
	 @Test
      void testAddTrain(){
	    List<Train> Trains = ts.recupererAll();
	    int expected = Trains.size();
	    Train t = new Train(1546,70);
	    Train savedTrain= ts.ajouterTrain(t);
	    assertEquals(expected+1, ts.recupererAll().size());
	    assertNotNull(savedTrain.getIdTrain());
	    ts.supprimerTrain(savedTrain);

     }
	 
	 @Test
	     void testUpdateTrain() {
		    Train t = new Train(1546,70);
		 	Train savedTrain= ts.ajouterTrain(t);
		    savedTrain.setCodeTrain(546);
		    ts.modifierTrain(savedTrain);
		    assertEquals(t.getCodeTrain(),savedTrain.getCodeTrain());
		    ts.supprimerTrain(savedTrain);
	    }
	
	 @Test
	    void retrieveAllTrains() {
	    	List<Train> Trains = ts.recupererAll();
	        Assertions.assertEquals(0, Trains.size());
	    }

	 @Test
	     void testDeleteTrain() {
		    Train t = new Train(1546,70);
		 	Train savedTrain= ts.ajouterTrain(t);
		 	ts.supprimerTrain(savedTrain);
		    assertNotNull(savedTrain.getIdTrain());

	    }

	
}
