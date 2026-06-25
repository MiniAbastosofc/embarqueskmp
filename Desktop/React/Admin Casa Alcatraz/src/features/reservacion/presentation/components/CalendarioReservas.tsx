// src/features/reservacion/presentation/components/CalendarioReservas.tsx
import { DayPicker } from "react-day-picker";
import { useCalendarioOcupacion } from "../hooks/useCalendarioOcupacion";
import "react-day-picker/dist/style.css";
import { useState } from "react";
import type { FechasOcupadas } from "../../domain/fechas-ocupadas.repository";
import { es } from "date-fns/locale";

export const CalendarioReservas = () => {
  const { diasOcupados, buscarDetalleDia, loading } = useCalendarioOcupacion();
  // Estado local para guardar la reserva seleccionada al hacer clic
  const [reservaSeleccionada, setReservaSeleccionada] =
    useState<FechasOcupadas | null>(null);

  if (loading) {
    return (
      <div className="text-neutral font-body p-4 text-center">
        Cargando calendario de ocupación...
      </div>
    );
  }

  return (
    <div className="flex flex-col gap-6 max-w-md mx-auto">
      {/* Contenedor del Calendario */}
      <div className="flex flex-col items-center p-6 bg-surfacecontainer rounded-xl shadow-md border border-tertiary">
        <h2 className="text-2xl font-headline text-primary font-bold mb-4">
          Disponibilidad de la Casa
        </h2>

        <DayPicker
          mode="multiple"
          locale={es}
          modifiers={{ ocupado: diasOcupados }}
          modifiersClassNames={{
            ocupado: "bg-ohsunny text-neutral font-bold rounded-lg shadow-xs",
          }}
          // Capturamos el evento de clic en los días del calendario
          onDayClick={(date) => {
            const detalle = buscarDetalleDia(date);
            if (detalle) {
              setReservaSeleccionada(detalle);
            } else {
              setReservaSeleccionada(null); // Limpia la selección si es un día libre
            }
          }}
          components={{
            DayButton: ({ day, ...props }) => {
              const detalle = buscarDetalleDia(day.date);

              return (
                <button
                  {...props}
                  // Mantenemos una clase visual para indicar que es un día con información interactiva
                  className={`${props.className || ""} cursor-pointer transition-colors duration-200 font-label ${detalle ? "hover:scale-105" : ""}`}
                />
              );
            },
          }}
        />

        {/* Leyenda de colores */}
        <div className="flex gap-4 mt-4 text-sm font-body text-neutral">
          <div className="flex items-center gap-2">
            <span className="w-4 h-4 bg-ohsunny rounded-md shadow-xs" />
            <span>Ocupado</span>
          </div>
          <div className="flex items-center gap-2">
            <span className="w-4 h-4 bg-white border border-gray-200 rounded-md" />
            <span>Disponible</span>
          </div>
        </div>
      </div>

      {/* Tarjeta de Detalles Detallada (Se muestra solo si hacen clic en un día ocupado) */}
      {reservaSeleccionada ? (
        <div className="p-5 bg-surface text-tertiary rounded-xl shadow-lg border border-primary transition-all duration-300 animate-fade-in">
          <div className="flex justify-between items-center mb-3">
            <h3 className="text-lg font-headline font-bold text-secondary">
              Detalle de Ocupación
            </h3>
            <button
              onClick={() => setReservaSeleccionada(null)}
              className="text-tertiary/70 hover:text-secondary text-xs uppercase tracking-wider font-label"
            >
              Cerrar ×
            </button>
          </div>
          <div className="space-y-2 font-body text-sm">
            <p>
              <span className="font-bold text-secondary">Cliente:</span>{" "}
              {reservaSeleccionada.nombreCliente}
            </p>
            <p>
              <span className="font-bold text-secondary">Plataforma:</span>{" "}
              {reservaSeleccionada.plataformaOrigen}
            </p>
            <p>
              <span className="font-bold text-secondary">Desde:</span>{" "}
              {reservaSeleccionada.fechaDesde.toLocaleDateString()}
            </p>
            <p>
              <span className="font-bold text-secondary">Hasta:</span>{" "}
              {reservaSeleccionada.fechaHasta.toLocaleDateString()}
            </p>
          </div>
        </div>
      ) : (
        <div className="p-4 bg-surfacecontainer/50 border border-dashed border-neutral/20 text-neutral/60 text-center rounded-xl text-sm font-body">
          Haz clic en un día ocupado para ver los datos de la reserva.
        </div>
      )}
    </div>
  );
};
