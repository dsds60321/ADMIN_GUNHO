package dev.gunho.user.repository;

import dev.gunho.global.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Template getById(String id);
}
