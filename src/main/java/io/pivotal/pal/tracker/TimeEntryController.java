package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController implements InfoContributor {
    TimeEntryRepository entryRepo = null;
    private CounterService c = null;
    private GaugeService g = null;
    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService cService, GaugeService gService) {
        this.entryRepo=timeEntryRepository;
        this.c = cService;
        this.g = gService;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
       TimeEntry newTimeEntry= entryRepo.create(timeEntryToCreate);
       c.increment("TimeEntry Object Created");
       g.submit("Final Count Value for Create",entryRepo.list().size());
       return new ResponseEntity<TimeEntry>(newTimeEntry, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry timeEntryObject = entryRepo.find(id);
        c.increment("TimeEntry Object Read");
        g.submit("Final Count Value for Read",entryRepo.list().size());
        if(timeEntryObject == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }

        return new ResponseEntity<TimeEntry>(timeEntryObject, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> newList = entryRepo.list();
        c.increment("TimeEntry Object List");
        g.submit("Final Count Value for List",newList.size());
        return new ResponseEntity<List<TimeEntry>>(newList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry timeEntryObject) {
        TimeEntry updateEntry= entryRepo.update(id,timeEntryObject);
        c.increment("TimeEntry Object Update");
        g.submit("Final Count Value for Update",entryRepo.list().size());
        if(updateEntry == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
        return new ResponseEntity<TimeEntry>(updateEntry, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        entryRepo.delete(id);
        c.increment("TimeEntry Object Delete");
        g.submit("Final Count Value for Delete",entryRepo.list().size());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

    @Override
    public void contribute(Info.Builder builder) {

    }
}
