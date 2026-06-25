// src/features/reservacion/presentation/hooks/useCalendarioOcupacion.ts
import { useEffect, useState } from 'react';
import { type FechasOcupadas } from '../../domain/fechas-ocupadas.repository'; 
import { ApiFechasOcupadasRepository } from '../../infrastructure/api-fechas-ocupadas.repository';

export const useCalendarioOcupacion = () => {
  const [datosOcupacion, setDatosOcupacion] = useState<FechasOcupadas[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const repo = new ApiFechasOcupadasRepository();
    repo.obtenerOcupadas()
      .then(data => setDatosOcupacion(data))
      .catch(err => console.error("Error cargando calendario", err))
      .finally(() => setLoading(false));
  }, []);

  // Función para crear una fecha pura sin horas ni zonas horarias conflictivas
  const normalizarFecha = (d: Date) => {
    return new Date(d.getFullYear(), d.getMonth(), d.getDate()).getTime();
  };

  const obtenerDiasEnRango = (inicio: Date, fin: Date): Date[] => {
    const dias: Date[] = [];
    let fechaActual = new Date(inicio.getFullYear(), inicio.getMonth(), inicio.getDate());
    const fechaFin = new Date(fin.getFullYear(), fin.getMonth(), fin.getDate());

    while (fechaActual <= fechaFin) {
      dias.push(new Date(fechaActual));
      fechaActual.setDate(fechaActual.getDate() + 1);
    }
    return dias;
  };

  // Mapeamos a fechas individuales
  const diasOcupados = datosOcupacion.flatMap(reserva => 
    obtenerDiasEnRango(reserva.fechaDesde, reserva.fechaHasta)
  );

  // CORRECCIÓN AQUÍ: Comparación exacta usando milisegundos del día puro
  const buscarDetalleDia = (fecha: Date): FechasOcupadas | undefined => {
    const tiempoEvaluar = normalizarFecha(fecha);

    return datosOcupacion.find(reserva => {
      const inicio = normalizarFecha(reserva.fechaDesde);
      const fin = normalizarFecha(reserva.fechaHasta);
      return tiempoEvaluar >= inicio && tiempoEvaluar <= fin;
    });
  };

  return { diasOcupados, buscarDetalleDia, loading };
};
