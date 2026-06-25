import { useState, useEffect, useCallback } from "react";
import {
  ReservaRepository,
  type ReservaApiResponse,
} from "../../infrastructure/reservas.repository";

export const useUltimasCapturas = () => {
  const [reservas, setReservas] = useState<ReservaApiResponse[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  const cargarCapturas = useCallback(async () => {
    setIsLoading(true);
    try {
      const data = await ReservaRepository.getAll();
      setReservas(data.slice(0, 5));
    } catch (error) {
      console.error("Error al cargar las últimas capturas de reservas:", error);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    cargarCapturas();
  }, [cargarCapturas]);

  return {
    reservas,
    isLoading,
    refrescarCapturas: cargarCapturas,
  };
};
