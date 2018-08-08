package io.pivotal.pal.tracker;
import java.sql.Time;
import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{
    Map<Long,TimeEntry> map = new HashMap<Long,TimeEntry>();

    @Override
    public TimeEntry create(TimeEntry timeEntryObject) {
        // list.add(timeEntryObject);
        Long idKey = map.size()+1L;
        TimeEntry newTimeEntry = new TimeEntry(idKey,timeEntryObject.getProjectId(),timeEntryObject.getUserId(),timeEntryObject.getDate(),timeEntryObject.getHours());
        map.put(idKey,newTimeEntry);
        return newTimeEntry;
    }

    @Override
    public TimeEntry find(Long id) {
        TimeEntry timeEntry = null;
        /*for(TimeEntry t : list) {
        if(id == t.getId()) {
            timeEntry = t;
            break;
        }
    }*/
    timeEntry = map.get(id);
    return timeEntry;
}

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<TimeEntry>(map.values()) ;
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntryObject) {
        TimeEntry timeEntryUpdatedObject = new TimeEntry(id,timeEntryObject.getProjectId(),timeEntryObject.getUserId(),timeEntryObject.getDate(),timeEntryObject.getHours());
        map.replace(id,timeEntryUpdatedObject);
        return timeEntryUpdatedObject;
    }

    @Override
    public void delete(Long id) {
      /*  for(TimeEntry t : list) {
        if(id == t.getId()) {
            list.remove(t);
            t = null;
            break;
        }
    }*/
      map.remove(id);
    }
}
