package com.maven.grpc.example.proto.service.implementation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maven.grpc.example.CommunicateServiceGrpc;
import com.maven.grpc.example.GrpcService;
import com.maven.grpc.example.server.entity.Employee;
import com.maven.grpc.example.server.entity.HibernateUtils;
import io.grpc.stub.StreamObserver;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class EmpService extends CommunicateServiceGrpc.CommunicateServiceImplBase {
    private Gson gson= new Gson();
    private GrpcService.ClientResponse clientResponse;
   //fetching the data with required Id from db
    private Employee fetchEmployeeDataById(final Long id) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
       Query query = session.getNamedQuery("select_with_id").setParameter("id",id);
        Employee employee = (Employee) query.getSingleResult();
        session.close();
        return employee ;
    }
    //fetching the data with requried name from db
    private static List<Employee> fetchEmployeeDataByName(final String name) {
    SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.getNamedQuery("select_with_name").setParameter("employeeName",name);
        List<Employee> employeeList = query.getResultList();
        session.close();
        return employeeList ;
    }
//Accessing the request from server and returning it to the clint in String Format
    @Override
    public void fetchEmployee(GrpcService.ClientRequest request, StreamObserver<GrpcService.ClientResponse> responseObserver) {
        final Object ob = request.getEmployeeName().isEmpty()||request.getEmployeeName()==null? request.getId() : request.getEmployeeName();
        if (ob.getClass().getName().equals("java.lang.String")) {
            clientResponse  = GrpcService.ClientResponse.newBuilder().setEmployee( gson.toJson(fetchEmployeeDataByName(request.getEmployeeName()))).build();
            responseObserver.onNext(clientResponse);
            responseObserver.onCompleted();
        }else {
            clientResponse=GrpcService.ClientResponse.newBuilder().setEmployee( gson.toJson(fetchEmployeeDataById(request.getId()))).build();
            responseObserver.onNext(clientResponse);
            responseObserver.onCompleted();
        }
    }
//method for updating Employee details with client requested id
    @Override
    public void updateEmployeeWithId(GrpcService.ClientRequest request, StreamObserver<GrpcService.Sucess> responseObserver) {
       // super.updateEmployeeWithId(request, responseObserver);
    }

   //method to delete Employee Details with Client Requested Id
    @Override
    public void deleteEmployeeWithId(GrpcService.ClientRequest request, StreamObserver<GrpcService.Sucess> responseObserver) {
        //super.deleteEmployeeWithId(request, responseObserver);

    }
}