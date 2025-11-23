package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.BillingSummary;
import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.ChargeType;
import com.greenbeansapps.myschooltransportation.domain.usecases.EnableSummaryGeneration;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.EnableSummaryGenerationRequest;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.MonthlyFeeRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IBillingSummaryRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EnableSummaryGenerationUseCaseImpl implements EnableSummaryGeneration {

    @Autowired
    private MonthlyFeeRepository monthlyFeeRepository;

    @Autowired
    private IBillingSummaryRepositoryJPA billingSummaryRepo;

    @Override
    public List<Student> execute(EnableSummaryGenerationRequest enableSummaryGenerationRequest) {

        deleteBillingSummaryPerReferenceByConductor(enableSummaryGenerationRequest);

        List<Student> affectedStudents = generateBillingSummaryPerReferenceByConductor(enableSummaryGenerationRequest);

        return affectedStudents;

    }

    private void deleteBillingSummaryPerReferenceByConductor(EnableSummaryGenerationRequest enableSummaryGenerationRequest) {

        List<BillingSummary> billingSummariesByConductor = billingSummaryRepo.findAllBillingSummaryByReference(enableSummaryGenerationRequest.referenceMonth().name(),
                enableSummaryGenerationRequest.referenceYear(), enableSummaryGenerationRequest.conductorId());

        billingSummaryRepo.deleteAll(billingSummariesByConductor);
    }

    private List<Student> generateBillingSummaryPerReferenceByConductor(EnableSummaryGenerationRequest enableSummaryGenerationRequest) {

        List<MonthlyFee> monthlyFeeList = monthlyFeeRepository.findAllMonthlyFeesByReference(enableSummaryGenerationRequest.referenceMonth(),
                enableSummaryGenerationRequest.referenceYear(), enableSummaryGenerationRequest.conductorId());

        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Student> affectedStudents = monthlyFeeList.stream()
                .map(MonthlyFee::getStudent)
                .distinct()
                .toList();

        for (MonthlyFee monthlyFee:monthlyFeeList) {
            executor.submit(() -> {
//              System.out.println("Executando tarefa " + monthlyFee.getId() + " na " + Thread.currentThread().getName());
                BillingSummary billingSummary = new BillingSummary();
                billingSummary.setStudent(monthlyFee.getStudent());
                billingSummary.setReferenceMonth(monthlyFee.getReferenceMonth().name());
                billingSummary.setReferenceYear(monthlyFee.getReferenceYear());
                billingSummary.setAmount(monthlyFee.getAmount());
                billingSummary.setChargeType(ChargeType.MENSALIDADE);
                billingSummaryRepo.save(billingSummary);
            });
        }

        executor.shutdown();

        return affectedStudents;
    }
}
