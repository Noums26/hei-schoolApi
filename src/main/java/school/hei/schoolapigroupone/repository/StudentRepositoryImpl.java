package school.hei.schoolapigroupone.repository;

import lombok.NoArgsConstructor;
import school.hei.schoolapigroupone.model.Groups;
import school.hei.schoolapigroupone.model.Students;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {
    DatabaseConnection schoolDb = new DatabaseConnection("YourUserName", "yourPwd");

    private Statement setSchoolDb(DatabaseConnection schoolDb) throws SQLException {
        this.schoolDb = schoolDb;
        Statement stm = schoolDb.connection();

        return stm;
    }

    @Override
    public List<Students> fondAll() throws SQLException {
        Statement stm = setSchoolDb(schoolDb);
        ResultSet res =  stm.executeQuery("SELECT * FROM students");

        List<Students> lists = new ArrayList<>();

        while (res.next()){
            long id = res.getLong("id");
            String name = res.getString("name");
            Groups group = getGroupsById(res.getLong("groups_id"));

            Students std = new Students(id, name, group);
            lists.add(std);
        }

        return lists;
    }

    private Groups getGroupsById(long id) throws SQLException {
        Groups group = null;
        Statement stm = setSchoolDb(schoolDb);
        ResultSet res =  stm.executeQuery("SELECT * FROM groups WHERE id = "+id);

        while(res. next()){
            Date creation_date = res.getDate("creation_date");
            String name = res.getString("name");
            Groups gp = new Groups(id, name, creation_date.toLocalDate());

            group = gp;
        }

        return group;
    }

    private Students getStudentsById(long id) throws SQLException {
        Students std = null;
        Statement stm = setSchoolDb(schoolDb);
        ResultSet res =  stm.executeQuery("SELECT * FROM students WHERE id = "+id);

        while(res. next()){
            String name = res.getString("name");
            Groups gp = getGroupsById(res.getLong("groups_id"));

            std = new Students(id, name, gp);
        }

        return std;
    }

    @Override
    public List<Students> add(Students s) throws SQLException {
        Statement stm = setSchoolDb(schoolDb);
        String sqlReq = "INSERT INTO students(name, groups_id) VALUES ('"
                .concat(s.getName())
                .concat("', ")
                .concat(s.getGroup().getId().toString())
                .concat(");");
        stm.executeUpdate(sqlReq);
        return fondAll();
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Statement stm = setSchoolDb(schoolDb);
        stm.executeUpdate("DELETE FROM students WHERE id = "+id);
    }

    @Override
    public Students updateNameById(Long id, String newName) throws SQLException {
        Statement stm = setSchoolDb(schoolDb);
        stm.executeUpdate("UPDATE students SET name = "+newName+" WHERE id = "+id);

        return getStudentsById(id);
    }

    @Override
    public List<Students> findWhereNameLike(String query) throws SQLException {
        Statement stm = setSchoolDb(schoolDb);
        ResultSet res =  stm.executeQuery("SELECT * FROM students WHERE name LIKE '%"+query+"%'");

        List<Students> lists = new ArrayList<>();

        while (res.next()){
            long id = res.getLong("id");
            String name = res.getString("name");
            Groups group = getGroupsById(res.getLong("groups_id"));

            Students std = new Students(id, name, group);
            lists.add(std);
        }

        return lists;
    }
}
