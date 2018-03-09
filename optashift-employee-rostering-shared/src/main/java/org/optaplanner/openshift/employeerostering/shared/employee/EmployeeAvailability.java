/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.openshift.employeerostering.shared.employee;

import java.time.LocalDate;
import java.time.OffsetTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.optaplanner.openshift.employeerostering.shared.common.AbstractPersistable;
import org.optaplanner.openshift.employeerostering.shared.employee.view.EmployeeAvailabilityView;
import org.optaplanner.openshift.employeerostering.shared.jackson.LocalDateDeserializer;
import org.optaplanner.openshift.employeerostering.shared.jackson.LocalDateSerializer;
import org.optaplanner.openshift.employeerostering.shared.jackson.OffsetTimeDeserializer;
import org.optaplanner.openshift.employeerostering.shared.jackson.OffsetTimeSerializer;

@Entity
@NamedQueries({
               @NamedQuery(name = "EmployeeAvailability.findAll",
                           query = "select distinct ea from EmployeeAvailability ea" +
                                   " left join fetch ea.employee e" +
                                   " where e.tenantId = :tenantId" +
                                   " order by e.name, ea.date, ea.startTime"),
})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"tenantId", "employee_id", "date", "startTime", "endTime"}))
public class EmployeeAvailability extends AbstractPersistable {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    @NotNull
    private LocalDate date;
    @NotNull
    private OffsetTime startTime;
    @NotNull
    private OffsetTime endTime;

    @NotNull
    private EmployeeAvailabilityState state;

    @SuppressWarnings("unused")
    public EmployeeAvailability() {}

    public EmployeeAvailability(Integer tenantId, Employee employee, LocalDate date, OffsetTime startTime, OffsetTime endTime) {
        super(tenantId);
        this.employee = employee;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EmployeeAvailability(EmployeeAvailabilityView employeeAvailabilityView, Employee employee, LocalDate date, OffsetTime startTime, OffsetTime endTime) {
        super(employeeAvailabilityView);
        this.employee = employee;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return employee + " " + date + ":" + startTime + "-" + endTime;
    }

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @JsonSerialize(using = OffsetTimeSerializer.class)
    @JsonDeserialize(using = OffsetTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public OffsetTime getStartTime() {
        return startTime;
    }

    public void setStartTime(OffsetTime startTime) {
        this.startTime = startTime;
    }

    @JsonSerialize(using = OffsetTimeSerializer.class)
    @JsonDeserialize(using = OffsetTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public OffsetTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetTime endTime) {
        this.endTime = endTime;
    }

    public EmployeeAvailabilityState getState() {
        return state;
    }

    public void setState(EmployeeAvailabilityState state) {
        this.state = state;
    }

}
