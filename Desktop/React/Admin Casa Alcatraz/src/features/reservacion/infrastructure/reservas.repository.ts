import { apiClient } from "../../../core/api/apiClient";
import { type ReservaFormData } from "../domain/reserva.schema";

export interface ReservaApiResponse {
  id: number; // Coincide con tu JSON real
  nombreCliente: string;
  apellidoCliente: string;
  telefono: string | null;
  email: string | null;
  fechaInicio: string;
  fechaFin: string;
  estadoDescripcion: string;
  origenNombre: string;
  montoTotal: number;
  totalDiasReservacion: number;
  personasHospedadas: number;
  montoPagado: number;
  saldoPendiente: number;
  estatusPago: string;
}

export const ReservaRepository = {
  create: async (formData: ReservaFormData): Promise<void> => {
    const esOtro = formData.origenReserva === "OTRO";
    const apiPayload = {
      usuarioId: 1,
      nombreCliente: formData.nombreHuesped,
      apellidoCliente: formData.apellidoHuesped,
      telefono: formData.telefono || null,
      email: null,
      fechaInicio: formData.fechaEntrada,
      fechaFin: formData.fechaSalida,
      totalDiasReservacion: formData.diasHospedaje,
      montoTotal: formData.montoTotal,
      // origenReservaId: Number(formData.origenReserva),
      personasHospedadas: formData.huespedes,
      origenReservaId: esOtro ? "OTRO" : Number(formData.origenReserva),
      nuevoOrigenNombre: esOtro ? (formData as any).nuevoOrigenNombre : null,
      extras: formData.extras || [],
    };

    try {
      console.log("🚀 Payload corregido listo para salir:", apiPayload);
      const response = await apiClient.post(
        "/reservas/crear-reserva",
        apiPayload,
      );
      return response.data;
    } catch (error: any) {
      if (error.response) {
        throw new Error(
          error.response.data?.message ||
            "Error al crear la reserva en el servidor",
        );
      } else if (error.request) {
        throw new Error(
          "No hay respuesta del servidor. Revisa tu conexión de red.",
        );
      } else {
        throw new Error(
          error.message || "Error inesperado al procesar el envío",
        );
      }
    }
  },

  getAll: async (): Promise<ReservaApiResponse[]> => {
    try {
      const response = await apiClient.get<{
        success: boolean;
        data: ReservaApiResponse[];
      }>("/reservas/todas");
      return response.data.data;
    } catch (error: any) {
      throw new Error(
        error.response?.data?.message ||
          "Error al obtener el listado de reservas",
      );
    }
  },
};
