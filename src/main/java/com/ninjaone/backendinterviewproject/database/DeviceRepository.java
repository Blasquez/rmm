package com.ninjaone.backendinterviewproject.database;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ninjaone.backendinterviewproject.model.Devices;

@Transactional
@Repository
public interface DeviceRepository extends JpaRepository<Devices, Long>{

}
