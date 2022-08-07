package school.hei.schoolapigroupone.repository;

import school.hei.schoolapigroupone.model.Students;

import java.sql.SQLException;
import java.util.List;

public interface StudentRepository {
    List<Students> fondAll() throws SQLException;

    List<Students> add(Students s) throws SQLException;

    void deleteById(Long id) throws SQLException;

    Students updateNameById(Long id, String newName) throws SQLException;

    List<Students> findWhereNameLike(String query) throws SQLException;

    // implementez cette interface avec une classe concrète avec JDBC
    // SUR GOOGLE: proposez une autre impl ac JPQL
    // @Query
}
