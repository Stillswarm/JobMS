package org.example.jobms.job;

import org.example.jobms.dto.JobWithCompanyDto;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDto> findAll();
    void createJob(Job job);

    JobWithCompanyDto getJobById(Long id);

    Boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job job);
}
