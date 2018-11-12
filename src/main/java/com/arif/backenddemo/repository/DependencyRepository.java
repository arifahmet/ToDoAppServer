package com.arif.backenddemo.repository;

import com.arif.backenddemo.domain.Dependency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependencyRepository extends CrudRepository<Dependency, Long> {
    List<Dependency> findByToDoItemId(Long toDoItemId);
    List<Dependency> findByToDoItemIdOrDependencyToDoItemId(Long toDoItemId, Long dependencyToDoItemId);
}
