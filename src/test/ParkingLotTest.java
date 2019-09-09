import com.tw.vapasi.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("ALL")
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
    void expectDriverAbleRemoveVehicle() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
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
    void expectTrueIfAGivenCarIsParked() throws VehicleAlreadyParkedException, SpaceNotAvailableException {
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
    void expectNotificationNotSentToOwnerOnParkingSuccess() throws VehicleAlreadyParkedException,
            SpaceNotAvailableException {
        ParkingLotOwner owner = mock(ParkingLotOwner.class);
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Parkable car1 = mock(Parkable.class);

        parkingLot.park(car1);

        verify(owner, never()).notifyParkingFull();
    }

    @Test
    void expectNotificationSentToOwnerOnParkingSpaceAvailableAgain() throws VehicleAlreadyParkedException, SpaceNotAvailableException, VehicleNotParkedException {
        ParkingLotOwner owner = mock(ParkingLotOwner.class);
        ParkingLot parkingLot = new ParkingLot(1, owner);
        Parkable car1 = mock(Parkable.class);
        parkingLot.park(car1);

        parkingLot.unPark(car1);

        verify(owner).notifyParkingAvailable();
    }
}


