package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.BillingSummary;
import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.enums.ChargeType;
import com.greenbeansapps.myschooltransportation.domain.usecases.EnableSummaryGeneration;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.EnableSummaryGenerationRequest;
import com.greenbeansapps.myschooltransportation.infra.repositories.IBillingSummaryRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.IMonthlyFeeRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.IPaymentRepositoryJPA;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EnableSummaryGenerationUseCaseImpl implements EnableSummaryGeneration {

    @Autowired
    private IPaymentRepositoryJPA paymentRepo;

    @Autowired
    private IMonthlyFeeRepositoryJPA monthlyFeeRepository;

    @Autowired
    private IBillingSummaryRepositoryJPA billingSummaryRepo;

    @Override
    public void execute(EnableSummaryGenerationRequest enableSummaryGenerationRequest) {
        //Gerando resumo do faturamento
        List<MonthlyFee> monthlyFeeList = monthlyFeeRepository.findAllMonthlyFeesByReference(enableSummaryGenerationRequest.referenceMonth(), enableSummaryGenerationRequest.referenceYear());

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (MonthlyFee monthlyFee:monthlyFeeList) {
            executor.submit(() -> {
//                System.out.println("Executando tarefa " + monthlyFee.getId() + " na " + Thread.currentThread().getName());
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
    }
}
