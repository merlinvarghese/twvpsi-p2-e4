package com.tw.vapasi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotTest {

    @Test
    void expectDriverAbleToParkVehicle() {
        Vehicle car = new Vehicle("safari");
        ParkingLot space = new ParkingLot(10);

        assertDoesNotThrow(() -> {
            space.park(car);
        });

    }

    @Test
    void expectDriverNotAbleParkWhenParkingLotFull() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        Vehicle car = new Vehicle("safari");
        Vehicle anotherCar = new Vehicle("i10");
        ParkingLot space = new ParkingLot(1);
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
        ParkingLot space = new ParkingLot(10);
        space.park(car);

        assertDoesNotThrow(() -> {
            space.unPark(car);
        });
    }

    @Test
    void expectDriverNotAbleRemoveUnParkedVehicle() {
        Vehicle car = new Vehicle("safari");
        ParkingLot space = new ParkingLot(10);

        assertThrows(VehicleNotParkedException.class, () -> {
            space.unPark(car);
        });
    }

    @Test
    void expectTrueIfAGivenCarIsParked() throws VehicleAlreadyParkedException
            , SpaceNotAvailableException {
        Vehicle car = new Vehicle("alto");
        ParkingLot space = new ParkingLot(10);
        space.park(car);

        assertTrue(space.isCarParked(car));
    }

    @Test
    void expectFalseIfAGivenCarIsNotParked() {
        Vehicle car = new Vehicle("bmw");
        ParkingLot space = new ParkingLot(10);

        assertFalse(space.isCarParked(car));
    }

    @Test
    void expectCarIsParked() {
        Parkable car = mock(Parkable.class);
        ParkingLot space = new ParkingLot(2);

        assertDoesNotThrow(() -> {
            space.park(car);
        });
    }

    @Nested
    class NotificationToOwnerTest {
        @Disabled
        void expectNotificationSentToOwnerOnParkingLotFull() throws VehicleAlreadyParkedException,
                SpaceNotAvailableException {
            ParkingLotListener owner = mock(ParkingLotListener.class);
            ParkingLot parkingLot = new ParkingLot(1);
            Parkable car1 = mock(Parkable.class);

            parkingLot.park(car1);

            verify(owner).notifyParkingFull(parkingLot);
        }

        @Test
        void expectNotificationNotSentToOwnerOnParkingSpaceAvailable() throws VehicleAlreadyParkedException,
                SpaceNotAvailableException {
            ParkingLotListener owner = mock(ParkingLotListener.class);
            ParkingLot parkingLot = new ParkingLot(2);
            Parkable car1 = mock(Parkable.class);

            parkingLot.park(car1);

            verify(owner, never()).notifyParkingFull(parkingLot);
        }

        @Test
        void expectNotificationSentToOwnerWhenParkingSpaceAvailableAgain() throws VehicleAlreadyParkedException,
                SpaceNotAvailableException, VehicleNotParkedException {
            ParkingLotListener owner = mock(ParkingLotListener.class);
            ParkingLot parkingLot = new ParkingLot(1);
            parkingLot.registerListener(owner);
            Parkable car = mock(Parkable.class);
            parkingLot.park(car);

            parkingLot.unPark(car);

            verify(owner).notifyParkingAvailable(parkingLot);
        }

        @Test
        void expectNotificationNotSentToOwnerWhenParkingIsAvailable() throws VehicleAlreadyParkedException,
                SpaceNotAvailableException, VehicleNotParkedException {
            ParkingLotListener owner = mock(ParkingLotListener.class);
            ParkingLot parkingLot = new ParkingLot(2);
            Parkable car = mock(Parkable.class);
            parkingLot.park(car);

            parkingLot.unPark(car);

            verify(owner, never()).notifyParkingAvailable(parkingLot);
        }
    }

}
