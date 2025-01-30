package dev.gunho.rule.repository;

import dev.gunho.rule.entity.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Long> {

    List<Rule> findAllByUser_Idx(Long userId);

    Page<Rule> findByOrderByRegDate(PageRequest pageRequest);
}
