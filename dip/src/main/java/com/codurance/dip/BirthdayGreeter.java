package com.codurance.dip;

import java.time.MonthDay;

public class BirthdayGreeter {
    private final EmployeeRepository employeeRepository;
    private final Clock clock;
    private final Clock emailSender;

    public BirthdayGreeter(EmployeeRepository employeeRepository, Clock clock, EmailSender anEmailSender) {
        this.employeeRepository = employeeRepository;
        this.clock = clock;
        this.emailSender = anEmailSender;
    }

    public void sendGreetings() {
        MonthDay today = clock.monthDay();
        employeeRepository.findEmployeesBornOn(today)
                .stream()
                .map(employee -> emailFor(employee))
                .forEach(email -> this.emailSender.send(email));
    }

    private Email emailFor(Employee employee) {
        String message = String.format("Happy birthday, dear %s!", employee.getFirstName());
        return new Email(employee.getEmail(), "Happy birthday!", message);
    }

}
