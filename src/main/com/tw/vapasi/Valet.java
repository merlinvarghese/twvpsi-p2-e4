package com.tw.vapasi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Valet {
    private final ArrayList<ParkingLot> parkingLotList;
    HashMap map = new HashMap();

    Valet(ArrayList<ParkingLot> list) {
        this.parkingLotList = list;
    }

    void park(Parkable vehicle) throws VehicleAlreadyParkedException,
            SpaceNotAvailableException {
        getAvailableParkingLot().park(vehicle);

    }

    private ParkingLot getAvailableParkingLot() throws SpaceNotAvailableException {
        Iterator<ParkingLot> parkingLotIterator = parkingLotList.iterator();
        while (parkingLotIterator.hasNext()) {
            ParkingLot parkingLot = parkingLotIterator.next();
            if (parkingLot.isParkingSpaceAvailable()) {
                return parkingLot;
            }
        }
        throw new SpaceNotAvailableException("Parking Full");
    }
}
