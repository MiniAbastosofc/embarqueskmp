import { apiClient } from "../../../core/api/apiClient";

export interface IngresoApiResponse {
  folioReserva: number;
  nombre: string;
  apellido: string;
  telefono: string | null;
  email: string | null;
  fechaInicio: string;
  fechaFin: string;
  estadoReservacion: string;
  conceptoDePagoId: number;
  conceptoPago: string;
  montoTotalReserva: number;
  montoBrutoPago: number;
  aplicaComisionPago: boolean;
  montoComisionPago: number;
  montoNetoPago: number;
}

export const IngresoRepository = {
  getByReservaId: async (id: number): Promise<IngresoApiResponse[]> => {
    try {
      const response = await apiClient.get<{
        success: boolean;
        data: IngresoApiResponse[];
      }>(`/ingresos/${id}`);
      return response.data.data;
    } catch (error: any) {
      throw new Error(
        error.response?.data?.message ||
          "Error al obtener los detalles de ingresos",
      );
    }
  },
};
