package com.shipyard.domain.repository;

import com.shipyard.domain.data.Building;

import java.util.List;

public interface BuildingRepository {
    List<Building> listAll();
}
