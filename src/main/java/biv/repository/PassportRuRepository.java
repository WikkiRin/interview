package biv.repository;

import biv.domain.PassportRu;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PassportRuRepository implements PanacheRepository<PassportRu> {
}
