package com.tw.vapasi;

import java.util.ArrayList;

class Valet implements ParkingLotListener {
    private final ArrayList<ParkingLot> parkingLotFullList;
    private final ArrayList<ParkingLot> parkingLotAvailableList;

    Valet(ArrayList<ParkingLot> parkingLotAvailableList, ArrayList<ParkingLot> parkingLotFullList) {
        this.parkingLotAvailableList = parkingLotAvailableList;
        this.parkingLotFullList = parkingLotFullList;
    }

    void park(Parkable vehicle) throws VehicleAlreadyParkedException, SpaceNotAvailableException {
        if (!parkingLotAvailableList.isEmpty()) {
            parkingLotAvailableList.get(0).park(vehicle);
        } else {
            throw new SpaceNotAvailableException("Parking full");
        }
    }


    @Override
    public void notifyParkingFull(ParkingLot parkingLot) {
        if (parkingLotAvailableList.contains(parkingLot)) {
            parkingLotAvailableList.remove(parkingLot);
        }
        parkingLotFullList.add(parkingLot);
    }

    @Override
    public void notifyParkingAvailable(ParkingLot parkingLot) {
        if (parkingLotFullList.contains(parkingLot)) {
            parkingLotFullList.remove(parkingLot);
        }
        parkingLotAvailableList.add(parkingLot);
    }
}
