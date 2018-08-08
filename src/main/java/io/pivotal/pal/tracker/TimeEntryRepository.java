package io.pivotal.pal.tracker;
import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry timeEntryObject);
    public TimeEntry find(Long id);
    public List<TimeEntry> list();
    public TimeEntry update(Long id, TimeEntry timeEntryObject);
    public void delete(Long id);
}
