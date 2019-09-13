package com.tw.vapasi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Valet implements ParkingLotListener {
    private final List<ParkingLot> parkingLotFullList = new ArrayList<>();
    private final List<ParkingLot> parkingLotAvailableList = new ArrayList<>();
    private final List<ParkingLot> parkingLots;
    private final Map<Parkable, ParkingLot> parkingInformation = new HashMap<>();

    Valet(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        registerForParkingLots();
        arrangeParkingLots();
    }

    void park(Parkable vehicle) throws VehicleAlreadyParkedException, SpaceNotAvailableException {
        if (!parkingLotAvailableList.isEmpty()) {
            ParkingLot parkingLot = parkingLotAvailableList.get(0);
            parkingLot.park(vehicle);
            parkingInformation.put(vehicle, parkingLot);
        } else {
            throw new SpaceNotAvailableException("Parking full");
        }
    }

    void unpark(Parkable vehicle) throws VehicleNotParkedException {
        ParkingLot parkingLot = parkingInformation.get(vehicle);
        if (parkingLot != null) {
            parkingLot.unPark(vehicle);
        } else throw new VehicleNotParkedException("Vehicle not parked");
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
