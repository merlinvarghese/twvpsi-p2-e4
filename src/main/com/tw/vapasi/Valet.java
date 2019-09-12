package com.tw.vapasi;

import java.util.ArrayList;
import java.util.List;

class Valet implements ParkingLotListener {
    private final List<ParkingLot> parkingLotFullList = new ArrayList<>();
    private final List<ParkingLot> parkingLotAvailableList = new ArrayList<>();
    private final List<ParkingLot> parkingLots;

    Valet(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        registerForParkingLots();
        arrangeParkingLots();
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

    private void registerForParkingLots() {
        for (ParkingLot parkingLot : parkingLots) {
            parkingLot.registerListener(this);
        }
    }

    private void arrangeParkingLots() {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.isParkingFull()) {
                parkingLotFullList.add(parkingLot);
            } else {
                parkingLotAvailableList.add(parkingLot);
            }
        }
    }
}
