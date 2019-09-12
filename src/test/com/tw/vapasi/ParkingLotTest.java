package com.tw.vapasi;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("ALL")
class ParkingLotTest {

    @Test
    void expectDriverAbleToParkVehicle() {
        Vehicle car = new Vehicle("safari");
        ParkingLot space = new ParkingLot(id, 10);

        assertDoesNotThrow(() -> {
            space.park(car);
        });

    }

    @Test
    void expectDriverNotAbleParkWhenParkingLotFull() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        Vehicle car = new Vehicle("safari");
        Vehicle anotherCar = new Vehicle("i10");
        ParkingLot space = new ParkingLot(id, 1);
        space.park(car);

        try {
            space.park(anotherCar);
        } catch (SpaceNotAvailableException se) {
            assertEquals("Parking full", se.getMessage());
        }
    }

    @Test
    void expectDriverAbleRemoveVehicle() throws SpaceNotAvailableException,
            VehicleAlreadyParkedException {
        Vehicle car = new Vehicle("safari");
        ParkingLot space = new ParkingLot(id, 10);
        space.park(car);

        assertDoesNotThrow(() -> {
            space.unPark(car);
        });
    }

    @Test
    void expectDriverNotAbleRemoveUnParkedVehicle() {
        Vehicle car = new Vehicle("safari");
        ParkingLot space = new ParkingLot(id, 10);

        assertThrows(VehicleNotParkedException.class, () -> {
            space.unPark(car);
        });
    }

    @Test
    void expectTrueIfAGivenCarIsParked() throws VehicleAlreadyParkedException
            , SpaceNotAvailableException {
        Vehicle car = new Vehicle("alto");
        ParkingLot space = new ParkingLot(id, 10);
        space.park(car);

        assertTrue(space.isCarParked(car));
    }

    @Test
    void expectFalseIfAGivenCarIsNotParked() {
        Vehicle car = new Vehicle("bmw");
        ParkingLot space = new ParkingLot(id, 10);

        assertFalse(space.isCarParked(car));
    }

    @Test
    void expectCarIsParked() {
        Parkable car = mock(Parkable.class);
        ParkingLot space = new ParkingLot(id, 2);

        assertDoesNotThrow(() -> {
            space.park(car);
        });
    }

    @Nested
    class NotificationToOwnerTest {
        @Test
        void expectNotificationSentToOwnerOnParkingLotFull() throws VehicleAlreadyParkedException,
                SpaceNotAvailableException {
            ParkingLotOwner owner = mock(ParkingLotOwner.class);
            ParkingLot parkingLot = new ParkingLot(1, owner);
            Parkable car1 = mock(Parkable.class);

            parkingLot.park(car1);

            verify(owner).notifyParkingFull();
        }

        @Test
        void expectNotificationNotSentToOwnerOnParkingSpaceAvailable() throws VehicleAlreadyParkedException,
                SpaceNotAvailableException {
            ParkingLotOwner owner = mock(ParkingLotOwner.class);
            ParkingLot parkingLot = new ParkingLot(2, owner);
            Parkable car1 = mock(Parkable.class);

            parkingLot.park(car1);

            verify(owner, never()).notifyParkingFull();
        }

        @Test
        void expectNotificationSentToOwnerWhenParkingSpaceAvailableAgain() throws VehicleAlreadyParkedException,
                SpaceNotAvailableException, VehicleNotParkedException {
            ParkingLotOwner owner = mock(ParkingLotOwner.class);
            ParkingLot parkingLot = new ParkingLot(1, owner);
            Parkable car = mock(Parkable.class);
            parkingLot.park(car);

            parkingLot.unPark(car);

            verify(owner).notifyParkingAvailable();
        }

        @Test
        void expectNotificationNotSentToOwnerWhenParkingIsAvailable() throws VehicleAlreadyParkedException,
                SpaceNotAvailableException, VehicleNotParkedException {
            ParkingLotOwner owner = mock(ParkingLotOwner.class);
            ParkingLot parkingLot = new ParkingLot(2, owner);
            Parkable car = mock(Parkable.class);
            parkingLot.park(car);

            parkingLot.unPark(car);

            verify(owner, never()).notifyParkingAvailable();
        }
    }

    @Test
    void expectValetCanParkAParkable() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        ParkingLotOwner owner = mock(ParkingLotOwner.class);
        ParkingLot parkingLot1 = new ParkingLot(2, owner);
        ParkingLot parkingLot2 = new ParkingLot(2, owner);
        Parkable car = mock(Parkable.class);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        Valet valet = new Valet(parkingLots);

        valet.park(car);
    }
}
