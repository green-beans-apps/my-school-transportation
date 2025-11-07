package com.greenbeansapps.myschooltransportation.infra.opencl;

import org.jocl.*;
import static org.jocl.CL.*;

public class CalculateContractTermination {

    public float[] calculateContractTermination(float[] monthlyFees, int[] remainingMonths, float[] alreadyPaid, float percent) {

        CL.setExceptionsEnabled(true);

        try {
            // === 1) Obtém plataforma ===
            cl_platform_id[] platforms = new cl_platform_id[1];
            clGetPlatformIDs(platforms.length, platforms, null);
            cl_platform_id platform = platforms[0];

            // === 2) Tenta obter GPU ===
            cl_device_id device = tryGetDevice(platform, CL_DEVICE_TYPE_GPU);
            if (device != null) {
                System.out.println("Executando no **GPU**: " + getDeviceName(device));
                return runOpenCL(device, monthlyFees, remainingMonths, alreadyPaid, percent);
            }

            // === 3) Se não houver GPU, tenta CPU integrada ===
            device = tryGetDevice(platform, CL_DEVICE_TYPE_CPU);
            if (device != null) {
                System.out.println("Executando no **CPU (OpenCL)**: " + getDeviceName(device));
                return runOpenCL(device, monthlyFees, remainingMonths, alreadyPaid, percent);
            }

        } catch (Exception ignored) {}

        // === 4) CASO FINAL: processamento Java puro ===
        System.out.println("Executando no **CPU (Java puro)** sem OpenCL.");
        return calcularJava(monthlyFees, remainingMonths, alreadyPaid, percent);
    }

    private cl_device_id tryGetDevice(cl_platform_id platform, long type) {
        cl_device_id[] devices = new cl_device_id[1];
        int result = clGetDeviceIDs(platform, type, 1, devices, null);
        return result == CL_SUCCESS ? devices[0] : null;
    }

    private String getDeviceName(cl_device_id device) {
        long size[] = new long[1];
        clGetDeviceInfo(device, CL_DEVICE_NAME, 0, null, size);
        byte buffer[] = new byte[(int) size[0]];
        clGetDeviceInfo(device, CL_DEVICE_NAME, buffer.length, Pointer.to(buffer), null);
        return new String(buffer, 0, buffer.length - 1);
    }

    /**
     * Executa o cálculo usando OpenCL no dispositivo informado.
     * Caso o dispositivo seja GPU, iGPU ou CPU, a execução será a mesma,
     * apenas variando o hardware onde o kernel será processado.
     */
    private float[] runOpenCL(cl_device_id device, float[] monthlyFees, int[] remainingMonths, float[] alreadyPaid, float percent) throws Exception {

        // Cria um contexto OpenCL associado ao dispositivo selecionado
        cl_context context = clCreateContext(null, 1, new cl_device_id[]{device}, null, null, null);

        // Cria a fila de comandos que envia tarefas para o dispositivo
        cl_command_queue queue = clCreateCommandQueue(context, device, 0, null);

        // Lê o código do kernel.cl (arquivo com a função paralela)
        String kernelCode = new String(
                getClass().getClassLoader().getResourceAsStream("kernel.cl").readAllBytes()
        );

        // Compila o programa no dispositivo
        cl_program program = clCreateProgramWithSource(context, 1,
                new String[]{ kernelCode }, null, null);
        clBuildProgram(program, 0, null, null, null, null);

        // Cria a função (kernel) a partir do programa compilado
        cl_kernel kernel = clCreateKernel(program, "calculateContractTermination", null);

        // Cria um buffer na GPU/CPU para armazenar os valores de mensalidade
        cl_mem memValues = clCreateBuffer(context, CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR,
                monthlyFees.length * Sizeof.cl_float, Pointer.to(monthlyFees), null);

        // Cria um buffer para meses restantes (somente leitura, pois não será alterado no kernel)
        cl_mem memMonths = clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                remainingMonths.length * Sizeof.cl_int, Pointer.to(remainingMonths), null);

        // Cria um buffer para valores já pago (somente leitura, pois não será alterado no kernel)
        cl_mem memAlreadyPaid = clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                alreadyPaid.length * Sizeof.cl_float, Pointer.to(alreadyPaid), null);

        // Cria um buffer para retornar os valores de rescisao dos respectivos estudantes
        cl_mem memRescission = clCreateBuffer(context, CL_MEM_WRITE_ONLY,
                monthlyFees.length * Sizeof.cl_float, null, null);


        // Define os argumentos do kernel (na mesma ordem da função kernel.cl)
        clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memValues));                // Array de mensalidades
        clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(memMonths));                // Array de meses restantes
        clSetKernelArg(kernel, 2, Sizeof.cl_mem, Pointer.to(memAlreadyPaid));           // Array de valores já pagos
        clSetKernelArg(kernel, 3, Sizeof.cl_float, Pointer.to(new float[]{ percent })); // Percentual
        clSetKernelArg(kernel, 4, Sizeof.cl_mem, Pointer.to(memRescission));            // Valor de rescisao por estudante

        // Define o número de threads a serem executadas (uma por elemento no array)
        long[] globalSize = new long[]{ monthlyFees.length };

        // Envia a execução do kernel para o dispositivo
        clEnqueueNDRangeKernel(queue, kernel, 1, null, globalSize, null, 0, null, null);

        // Prepara um array para receber o resultado de volta
        float[] resultado = new float[monthlyFees.length];

        // Copia os dados processados da GPU/CPU para a memória da JVM
        clEnqueueReadBuffer(queue, memRescission, CL_TRUE, 0,
                resultado.length * Sizeof.cl_float, Pointer.to(resultado), 0, null, null);

        // Libera recursos da GPU/CPU
        clReleaseMemObject(memValues);
        clReleaseMemObject(memMonths);
        clReleaseMemObject(memAlreadyPaid);
        clReleaseMemObject(memRescission);
        clReleaseKernel(kernel);
        clReleaseProgram(program);
        clReleaseCommandQueue(queue);
        clReleaseContext(context);

        // Retorna o resultado já processado
        return resultado;
    }

    private float[] calcularJava(float[] monthlyFees, int[] remainingMonths, float[] alreadyPaid, float percent) {
        float[] result = new float[monthlyFees.length];
        for (int i = 0; i < monthlyFees.length; i++) {
            float totalExpectedAmount = monthlyFees[i] * 12.0f;
            float amountToBeDeducted = monthlyFees[i] * remainingMonths[i];
            float terminationAmount = (totalExpectedAmount - amountToBeDeducted - alreadyPaid[i]) * percent;
            result[i] = terminationAmount;
        }
        return result;
    }

}
