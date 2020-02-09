package com.maven.grpc.example.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
//Employee Entity Class with named Query
@Entity
@Getter
@Setter
@NamedQueries
        (
                {
                        @NamedQuery(name="select_with_id", query="from Employee e where e.id = :id"),
                        @NamedQuery(name="select_with_name", query="from Employee e where e.employeeName = :employeeName")
                }
        )
@Table(name = "employee")
public class Employee  implements Serializable {

    private static final long serilizableId= 123434534L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String employeeName;
    @Column
    private String department;
    @Column
    private BigDecimal salary;
    @Column
    private Long number;


}
