__kernel void calculateContractTermination(
    __global const float *monthlyValues,        // Mensalidades
    __global const int   *monthsDifference,     // Meses antes da matrícula
    __global const float *alreadyPaid,          // Valor já pago
    float percent,                          // Percentual aplicado
    __global float *rescissionValuePerStudent // <-- Vetor de saída
) {
    int i = get_global_id(0);

    float monthlyFee = monthlyValues[i];
    int unregisteredMonths = monthsDifference[i];
    float amountPaid = alreadyPaid[i];

    float totalExpectedValue = monthlyFee * 12.0f; // Valor total esperado
    float amountToBeDeducted = monthlyFee * (float)unregisteredMonths; // Valor total já pago

    float terminationAmount = (totalExpectedValue - amountToBeDeducted- amountPaid) * percent; // ValorTotalDaRecisao

    rescissionValuePerStudent[i] = terminationAmount;
}
