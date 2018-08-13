package com.avenuecode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avenuecode.model.GraphModel;

@Repository
public interface GraphRepository extends JpaRepository<GraphModel, Long> {

}
