package com.tw.vapasi;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class ValetTest {
    @Test
    void expectValetCanParkAParkable() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        //Arrange
        ArrayList<ParkingLot> availableParkingLots = new ArrayList<>();
        ArrayList<ParkingLot> fullParkingLots = new ArrayList<>();
        ParkingLot parkingLotOne = new ParkingLot(2);
        ParkingLot parkingLotTwo = new ParkingLot(4);
        availableParkingLots.add(parkingLotOne);
        fullParkingLots.add(parkingLotTwo);
        Parkable car = mock(Parkable.class);
        Valet valet = new Valet(availableParkingLots, fullParkingLots);
        parkingLotOne.registerListener(valet);

        //Act + Assert
        assertDoesNotThrow(() -> valet.park(car));
    }

    @Test
    void expectValetCannotParkAParkable() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        //Arrange
        ArrayList<ParkingLot> availableParkingLots = new ArrayList<>();
        ArrayList<ParkingLot> fullParkingLots = new ArrayList<>();
        ParkingLot parkingLotOne = new ParkingLot(2);
        fullParkingLots.add(parkingLotOne);
        Parkable car = mock(Parkable.class);
        Valet valet = new Valet(availableParkingLots, fullParkingLots);
        parkingLotOne.registerListener(valet);

        //Act + Assert
        assertThrows(SpaceNotAvailableException.class, () -> valet.park(car));
    }

}
