import { apiClient } from "../../../core/api/apiClient";
import { type PagoFormValues } from "../presentation/hooks/useFormularioPagos";

export class ApiPagoRepository {
  async registrar(pago: PagoFormValues): Promise<void> {
    // Mapeamos el DTO de la aplicación a lo que requiere exactamente tu backend
    const payload = {
      origenDineroId: pago.origenDineroId,
      reservacionId: pago.reservacionId,
      conceptoPagoId: pago.conceptoPagoId,
      montoBruto: pago.montoBruto,
      aplicaComision: pago.aplicaComision,
      montoComision: pago.montoComision,
      userCreate: pago.userCreate,
    };

    await apiClient.post("/ingresos/crear-ingreso", payload);
  }
}
