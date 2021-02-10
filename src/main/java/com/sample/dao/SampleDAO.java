package com.sample.dao;

import com.sample.Entities.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleDAO extends JpaRepository<SampleEntity, Long> {

}
