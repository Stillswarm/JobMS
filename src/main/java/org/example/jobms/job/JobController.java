package org.example.jobms.job;

import org.example.jobms.dto.JobWithCompanyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("")
    public ResponseEntity<List<JobWithCompanyDto>> findAll() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return new ResponseEntity<>("Job added successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDto> getJobById(@PathVariable Long id) {
        var jwc = jobService.getJobById(id);
        if (jwc.getJob()   != null) return new ResponseEntity<>(jwc, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        Boolean deleted = jobService.deleteJobById(id);
        if (deleted) return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job job) {
        boolean updated = jobService.updateJob(id, job);
        if (updated) return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
        return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
    }
}
