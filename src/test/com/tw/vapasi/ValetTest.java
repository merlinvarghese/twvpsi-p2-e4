package com.tw.vapasi;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ValetTest {
    @Test
    void expectValetCanParkAParkable() {
        //Arrange
        ParkingLot parkingLotOne = new ParkingLot(1);
        ParkingLot parkingLotTwo = new ParkingLot(2);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLotOne, parkingLotTwo);
        Valet valet = new Valet(parkingLots);
        Parkable car = mock(Parkable.class);

        //Act + Assert
        assertDoesNotThrow(() -> valet.park(car));
        assertTrue(parkingLotOne.isCarParked(car));
        assertFalse(parkingLotTwo.isCarParked(car));
    }

    @Test
    void expectValetCannotParkAParkable() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        //Arrange
        ParkingLot parkingLotOne = new ParkingLot(1);
        ParkingLot parkingLotTwo = new ParkingLot(1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLotOne, parkingLotTwo);
        Parkable carOne = mock(Parkable.class);
        Parkable carTwo = mock(Parkable.class);
        Parkable carThree = mock(Parkable.class);
        Valet valet = new Valet(parkingLots);
        valet.park(carOne);
        valet.park(carTwo);

        //Act + Assert
        assertThrows(SpaceNotAvailableException.class, () -> valet.park(carThree));
        assertTrue(parkingLotOne.isCarParked(carOne));
        assertTrue(parkingLotTwo.isCarParked(carTwo));
        assertFalse(parkingLotOne.isCarParked(carThree));
        assertFalse(parkingLotTwo.isCarParked(carThree));
    }

}
