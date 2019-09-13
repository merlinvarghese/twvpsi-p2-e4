package com.tw.vapasi;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ValetTest {
    @Test
    void expectValetCanParkAParkableInParkingLotOne() throws VehicleAlreadyParkedException, SpaceNotAvailableException {
        ParkingLot parkingLotOne = spy(new ParkingLot(1));
        ParkingLot parkingLotTwo = spy(new ParkingLot(2));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLotOne, parkingLotTwo);
        Valet valet = new Valet(parkingLots);
        Parkable car = mock(Parkable.class);

        assertDoesNotThrow(() -> valet.park(car));
        verify(parkingLotOne).park(any(Parkable.class));
        verify(parkingLotTwo, never()).park(any(Parkable.class));
    }

    @Test
    void expectValetCanParkAParkableInParkingLotTwo() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        ParkingLot parkingLotOne = spy(new ParkingLot(1));
        ParkingLot parkingLotTwo = spy(new ParkingLot(1));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLotOne, parkingLotTwo);
        Parkable carOne = mock(Parkable.class);
        Parkable carTwo = mock(Parkable.class);
        Valet valet = new Valet(parkingLots);
        valet.park(carOne);

        valet.park(carTwo);

        verify(parkingLotOne).park(carOne);
        verify(parkingLotOne, never()).park(carTwo);
        verify(parkingLotTwo).park(carTwo);
        verify(parkingLotTwo, never()).park(carOne);
    }

    @Test
    void expectValetCannotParkAParkable() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        ParkingLot parkingLotOne = spy(new ParkingLot(1));
        ParkingLot parkingLotTwo = spy(new ParkingLot(1));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLotOne, parkingLotTwo);
        Parkable carOne = mock(Parkable.class);
        Parkable carTwo = mock(Parkable.class);
        Parkable carThree = mock(Parkable.class);
        Valet valet = new Valet(parkingLots);
        valet.park(carOne);
        valet.park(carTwo);

        assertThrows(SpaceNotAvailableException.class, () -> valet.park(carThree));
        verify(parkingLotOne).park(carOne);
        verify(parkingLotOne, never()).park(carThree);
        verify(parkingLotTwo).park(carTwo);
        verify(parkingLotTwo, never()).park(carThree);
    }

    @Test
    void expectValetCanUnparkAParkable() throws SpaceNotAvailableException, VehicleAlreadyParkedException, VehicleNotParkedException {
        ParkingLot parkingLot = spy(new ParkingLot(1));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);
        Parkable car = mock(Parkable.class);
        Valet valet = new Valet(parkingLots);
        valet.park(car);

        valet.unpark(car);

        verify(parkingLot).park(car);
        verify(parkingLot).unPark(car);
    }

    @Test
    void expectValetCanParkAParkableInParkingLotTwoAfterUnparking() throws SpaceNotAvailableException, VehicleAlreadyParkedException, VehicleNotParkedException {
        ParkingLot parkingLotOne = spy(new ParkingLot(1));
        ParkingLot parkingLotTwo = spy(new ParkingLot(2));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLotOne, parkingLotTwo);
        Parkable carOne = mock(Parkable.class);
        Parkable carTwo = mock(Parkable.class);
        Parkable carThree = mock(Parkable.class);
        Parkable carFour = mock(Parkable.class);
        Valet valet = new Valet(parkingLots);
        valet.park(carOne);
        valet.park(carTwo);
        valet.park(carThree);

        assertThrows(SpaceNotAvailableException.class, () -> valet.park(carFour));
        valet.unpark(carTwo);
        valet.park(carFour);

        verify(parkingLotOne).park(carOne);
        verify(parkingLotTwo).park(carTwo);
        verify(parkingLotTwo).park(carThree);
        verify(parkingLotOne, never()).park(carFour);
        verify(parkingLotTwo).park(carFour);
        verify(parkingLotTwo).unPark(carTwo);
    }

}
