    package com.greenbeansapps.myschooltransportation.implementation.usecases;

    import com.greenbeansapps.myschooltransportation.domain.dto.PaymentProjectionDto;
    import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionDto;
    import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionWithPaymentProjectionDto;
    import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
    import com.greenbeansapps.myschooltransportation.domain.entities.Student;
    import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidConductorException;
    import com.greenbeansapps.myschooltransportation.domain.usecases.GetAllStudentsByConductorIdUseCase;
    import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
    import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
    import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
    import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection;
    import org.springframework.stereotype.Service;

    import java.util.*;

    @Service
    public class GetAllStudentsByConductorIdUseCaseImpl implements GetAllStudentsByConductorIdUseCase {

        private final StudentRepository studentRepo;
        private final PaymentRepository paymentRepo;
        private final ConductorRepository conductorRepo;


        public GetAllStudentsByConductorIdUseCaseImpl(StudentRepository studentRepo, PaymentRepository paymentRepo, ConductorRepository conductorRepo) {
            this.studentRepo = studentRepo;
            this.paymentRepo = paymentRepo;
            this.conductorRepo = conductorRepo;
        }

        @Override
        public List<StudentProjectionWithPaymentProjectionDto> execute(UUID conductorId) {
            Optional<Conductor> conductor = this.conductorRepo.findById(conductorId);
            if (conductor.isEmpty()) {
                throw new InvalidConductorException();
            }
            List<StudentProjectionWithPaymentProjectionDto> studentProjectionWithPaymentProjectionDtoList = new ArrayList<>();

            // Copia a lista retornada pelo repositório para uma lista mutável - atualização para execução do teste unitário
            List<StudentProjectionDto> studentProjectionDtoList = new ArrayList<>(this.studentRepo.findAllByConductorId(conductorId));

            // Ordenando studentProjectionDtoList por ordem alfabética
            studentProjectionDtoList.sort(Comparator.comparing(StudentProjectionDto::getName));

            for (StudentProjectionDto studentProjectionDto : studentProjectionDtoList) {
                List<PaymentProjectionDto> paymentProjectionDtoList = this.paymentRepo.findAllPaymentByStudentId(studentProjectionDto.getId());
                if(paymentProjectionDtoList == null) {
                    paymentProjectionDtoList = new ArrayList<>();
                }
                studentProjectionWithPaymentProjectionDtoList.add(new StudentProjectionWithPaymentProjectionDto(studentProjectionDto.getId(), studentProjectionDto.getName(), studentProjectionDto.getSchool(),
                        studentProjectionDto.getGrade(), studentProjectionDto.getTransportationType(), studentProjectionDto.getShift(), studentProjectionDto.getMonthlyPayment(),
                        studentProjectionDto.getMonthlyPaymentExpiration(), studentProjectionDto.getResponsible(), studentProjectionDto.getAddress(), paymentProjectionDtoList));
            }

            return studentProjectionWithPaymentProjectionDtoList;
        }
    }
