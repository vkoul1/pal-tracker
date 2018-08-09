package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.*;
import java.sql.*;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private JdbcTemplate jdbcTemplateVal = null;
    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );
    private final ResultSetExtractor<TimeEntry> rsExtractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
    public JdbcTimeEntryRepository(){ }
    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplateVal = new JdbcTemplate(dataSource);
    }
    @Override
    public TimeEntry create(TimeEntry timeEntryObject) {

        KeyHolder keyHolderVal = new GeneratedKeyHolder();

        jdbcTemplateVal.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)",RETURN_GENERATED_KEYS);
                    stmt.setLong(1, timeEntryObject.getProjectId());
                    stmt.setLong(2, timeEntryObject.getUserId());
                    stmt.setDate(3, Date.valueOf(timeEntryObject.getDate()));
                    stmt.setInt(4, timeEntryObject.getHours());
                    return stmt;
            }, keyHolderVal);

        return find(keyHolderVal.getKey().longValue());
    }

    @Override
    public TimeEntry find(Long id) {
        String query = "Select * from time_entries where id = ?";
        TimeEntry obj;
        obj = (TimeEntry)jdbcTemplateVal.query(query, new Object[]{id},rsExtractor);
        return obj;
    }

    @Override
    public List<TimeEntry> list() {
        String query = "Select * from time_entries";
        return jdbcTemplateVal.query(query,mapper);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntryObject) {
        jdbcTemplateVal.update("UPDATE time_entries SET project_id = ?, user_id = ?, date = ?,  hours = ? WHERE id = ?",timeEntryObject.getProjectId(),
                timeEntryObject.getUserId(),Date.valueOf(timeEntryObject.getDate()),timeEntryObject.getHours(),id);
        return find(id.longValue());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplateVal.update("Delete from time_entries WHERE id = ?",id);
    }
}
