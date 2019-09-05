import com.tw.vapasi.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {
    @Test
    void expectDriverAbleToParkVehicle() throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        Vehicle car = new Vehicle("safari");
        ParkingLot space = new ParkingLot(10);
        Vehicle anotherSafari = new Vehicle("safari");
        space.park(car);
        try{
            space.park(anotherSafari);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
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

}


