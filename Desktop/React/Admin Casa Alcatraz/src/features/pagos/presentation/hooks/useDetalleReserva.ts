import { useState, useEffect, useCallback } from "react";
import {
  IngresoRepository,
  type IngresoApiResponse,
} from "../../../pagos/infrastructure/ingreso.repository";

export const useDetalleReserva = (reservaId: number | null) => {
  const [pagos, setPagos] = useState<IngresoApiResponse[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  const cargarDetalles = useCallback(async () => {
    if (!reservaId) return;
    setIsLoading(true);
    try {
      const data = await IngresoRepository.getByReservaId(reservaId);
      setPagos(data);
    } catch (error) {
      console.error("Error al consultar detalles de ingresos:", error);
    } finally {
      setIsLoading(false);
    }
  }, [reservaId]);

  useEffect(() => {
    cargarDetalles();
  }, [cargarDetalles]);

  const primerRegistro = pagos[0];
  const costoTotal = primerRegistro?.montoTotalReserva || 0;

  const totalPagadoCliente = pagos.reduce(
    (sum, item) => sum + item.montoBrutoPago,
    0,
  );

  const balancePendiente = costoTotal - totalPagadoCliente;

  const totalComisiones = pagos.reduce(
    (sum, item) => sum + item.montoComisionPago,
    0,
  );

  return {
    pagos,
    isLoading,
    refrescarPagos: cargarDetalles,
    datosCliente: primerRegistro
      ? {
          nombreCompleto: `${primerRegistro.nombre} ${primerRegistro.apellido}`,
          folio: primerRegistro.folioReserva,
          fechaInicio: primerRegistro.fechaInicio,
          fechaFin: primerRegistro.fechaFin,
          estado: primerRegistro.estadoReservacion,
        }
      : null,
    costoTotal,
    totalPagadoCliente,
    balancePendiente,
    totalComisiones, // Exportamos la nueva sumatoria
  };
};
