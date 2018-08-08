package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    TimeEntryRepository entryRepo = null;
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.entryRepo=timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
       TimeEntry newTimeEntry= entryRepo.create(timeEntryToCreate);
       return new ResponseEntity<TimeEntry>(newTimeEntry, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry timeEntryObject = entryRepo.find(id);
        if(timeEntryObject == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }

        return new ResponseEntity<TimeEntry>(timeEntryObject, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> newList = entryRepo.list();
        return new ResponseEntity<List<TimeEntry>>(newList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry timeEntryObject) {
        TimeEntry updateEntry= entryRepo.update(id,timeEntryObject);
        if(updateEntry == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
        return new ResponseEntity<TimeEntry>(updateEntry, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        entryRepo.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }
}
