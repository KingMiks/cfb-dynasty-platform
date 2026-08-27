package io.github.kingmiks.cfbdynastyplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.kingmiks.cfbdynastyplatform.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}