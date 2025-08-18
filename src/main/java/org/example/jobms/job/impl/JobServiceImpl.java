package org.example.jobms.job.impl;


import org.example.jobms.dto.JobWithCompanyDto;
import org.example.jobms.external.Company;
import org.example.jobms.job.Job;
import org.example.jobms.job.JobRepository;
import org.example.jobms.job.JobService;
import org.example.jobms.job.clients.CompanyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
//    private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;
    private final CompanyClient companyClient;

    @Autowired
    RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
    }

//    private Long nextId = 1L;

    @Override
    public List<JobWithCompanyDto> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::getJobWithCompanyDto).toList();
    }

    private JobWithCompanyDto getJobWithCompanyDto(Job job) {
        JobWithCompanyDto jobWithCompanyDto = new JobWithCompanyDto();
        jobWithCompanyDto.setJob(job);
        // using restTemplate
//        jobWithCompanyDto.setCompany(restTemplate.getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(), Company.class));

        // using openfeign
        jobWithCompanyDto.setCompany(companyClient.getCompany(job.getCompanyId()));
        return jobWithCompanyDto;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDto getJobById(Long id) {
        var job = jobRepository.findById(id).orElse(null);
        return getJobWithCompanyDto(job);
    }

    @Override
    public Boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        var jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}
