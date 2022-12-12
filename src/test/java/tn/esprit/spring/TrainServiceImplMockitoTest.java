package tn.esprit.spring;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Train;
import tn.esprit.spring.repository.TrainRepository;
import tn.esprit.spring.services.TrainServiceImpl;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class TrainServiceImplMockitoTest {

@Mock
TrainRepository tr;
@InjectMocks
TrainServiceImpl ts;

Train t = new Train(5468,70);


@Test
@Order(0)
void TestgetAllTrain(){
    Iterable<Train> Trains = tr.findAll();
    Assertions.assertNotNull(Trains);
}

@Test
@Order(2)
void TestretrieveTrain() {
    Mockito.when(tr.findById(Mockito.anyLong())).thenReturn(Optional.of(t));

    Mockito.when(tr.findById(Mockito.anyLong())).thenReturn(Optional.of(t))
    ;
    Train tra = ts.recupererTrainParId(2L);
    Assertions.assertNotNull(tra);


}

@Test
@Order(3)
void TestaddTrain() {
	Train tra = new Train();
    List<Train> Trains = new ArrayList<>();
    for (Long i=1L;i<=10L;i++) {
        tra.setIdTrain(i);
        tra.setCodeTrain(456);
        tra.setNbPlaceLibre(100);
        Train vo=tr.save(tra);
        Trains.add(vo);
    }
    assertEquals(10,Trains.size());
}
@Test
@Order(4)
void TestdeleteAllTrain() {
    tr.deleteAll();
    assertEquals(0,tr.findAll().spliterator().estimateSize());
}



}	
