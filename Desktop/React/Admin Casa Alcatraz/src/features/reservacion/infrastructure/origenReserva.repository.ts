import { apiClient } from "../../../core/api/apiClient";

export interface OrigenReservaDTO {
  id: number;
  nombre: string;
  descripcion: string;
}

export const OrigenReservaRepository = {
  async getAll(): Promise<OrigenReservaDTO[]> {
    const response = await apiClient.get<{
      success: boolean;
      data: OrigenReservaDTO[];
    }>("/reservas/origenes");
    // Aprovechamos para limpiar los espacios en blanco (trim) que vimos en Postman
    return response.data.data.map((item) => ({
      ...item,
      nombre: item.nombre.trim(),
      descripcion: item.descripcion.trim(),
    }));
  },
};
