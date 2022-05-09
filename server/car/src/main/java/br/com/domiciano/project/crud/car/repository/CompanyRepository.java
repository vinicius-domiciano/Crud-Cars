package br.com.domiciano.project.crud.car.repository;

import br.com.domiciano.project.crud.car.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
