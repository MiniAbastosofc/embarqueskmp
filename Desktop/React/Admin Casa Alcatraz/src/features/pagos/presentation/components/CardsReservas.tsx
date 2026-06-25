import { format, parseISO } from "date-fns";
import { es } from "date-fns/locale";

// 1. Interfaz actualizada reflejando la respuesta real de tu backend
interface CardReservasProps {
  id: number;
  nombreCliente: string;
  apellidoCliente: string;
  fechaInicio: string;
  fechaFin: string;
  origenNombre: string; // Mapeado a lo que antes llamabas plataforma
  montoTotal: number; // Mapeado a precio
  montoPagado: number;
  saldoPendiente: number;
  totalDiasReservacion: number; // Mapeado a noches
  personasHospedadas: number;
  estatusPago: string;
  isSelected: boolean;
  onSelect?: () => void;
}

const formatearRangoFechas = (inicio: string, fin: string) => {
  try {
    const dateInicio = parseISO(inicio);
    const dateFin = parseISO(fin);

    const txtInicio = format(dateInicio, "dd MMM", { locale: es });
    const txtFin = format(dateFin, "dd MMM", { locale: es });

    return `${txtInicio} - ${txtFin}`;
  } catch {
    return "Fechas no válidas";
  }
};

const PLATAFORMA_COLORS: Record<string, string> = {
  "OhSunny!": "bg-ohsunny",
  Web: "bg-primary",
  Booking: "bg-blue-600",
};

export const CardsReservas = ({
  id,
  nombreCliente,
  apellidoCliente,
  fechaInicio,
  fechaFin,
  origenNombre,
  montoTotal,
  // montoPagado,
  saldoPendiente,
  // totalDiasReservacion,
  // personasHospedadas,
  estatusPago,
  isSelected,
  onSelect,
}: CardReservasProps) => {
  const formatPrecio = (valor: number) => {
    return new Intl.NumberFormat("es-MX", {
      style: "currency",
      currency: "MXN",
    }).format(valor);
  };

  // Limpiamos los espacios en blanco que vienen del backend
  const plataformaLimpia = origenNombre.trim();
  const badgePlataformaBg =
    PLATAFORMA_COLORS[plataformaLimpia] || "bg-gray-500";

  // Estilos dinámicos para el estatus del pago usando tu paleta
  const esPagadoCompleto = estatusPago.toLowerCase() === "pagado";
  const badgePagoEstilo = esPagadoCompleto
    ? "bg-primary/10 text-primary border border-primary/30"
    : "bg-error/10 text-error border border-error/30";

  return (
    <div
      onClick={onSelect}
      className={`w-full rounded-lg p-4 cursor-pointer transition-all border-l-4 shadow-md flex flex-col gap-3
        ${
          isSelected
            ? "bg-primary/10 border-l-primary ring-1 ring-primary/20"
            : "bg-surfacecontainer border-l-surface hover:border-l-secondary"
        }`}
    >
      {/* Fila Superior: Cliente, Origen y Estatus de Pago */}
      <div className="w-full flex items-start justify-between gap-2">
        <div className="flex flex-col gap-1">
          <span className="font-headline font-bold text-lg text-neutral leading-tight">
            {nombreCliente} {apellidoCliente}
          </span>
          <span className="text-xs text-gray-500 font-medium">
            {formatearRangoFechas(fechaInicio, fechaFin)}
          </span>
          <span className="text-xs text-gray-500 font-medium">
            Folio Reserva: {id}
          </span>
        </div>

        <div className="flex flex-col items-end gap-1.5 shrink-0">
          {/* Badge de Origen (Plataforma) */}
          <span
            className={`px-2 py-0.5 rounded text-xxs font-bold text-white tracking-wide ${badgePlataformaBg}`}
          >
            {plataformaLimpia}
          </span>
          {/* Badge de Estatus de Pago */}
          <span
            className={`px-2 py-0.5 rounded text-xxs font-semibold uppercase tracking-wider ${badgePagoEstilo}`}
          >
            {estatusPago}
          </span>
        </div>
      </div>

      {/* Fila Central: Detalles de Hospedaje (Noches y Personas) */}
      {/* <div className="flex items-center gap-4 text-xs text-surface font-medium border-t border-b border-gray-200/60 py-2">
        <div className="flex items-center gap-1">
          <svg
            className="w-3.5 h-3.5 text-gray-400"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364-6.364l-.707.707M6.343 17.657l-.707.707m12.728 0l-.707-.707M6.343 6.343l-.707-.707M14 12a2 2 0 11-4 0 2 2 0 014 0z"
            />
          </svg>
          {totalDiasReservacion}{" "}
          {totalDiasReservacion === 1 ? "Noche" : "Noches"}
        </div>
        <div className="flex items-center gap-1">
          <svg
            className="w-3.5 h-3.5 text-gray-400"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a3 3 0 11-6 0 3 3 0 016 0z"
            />
          </svg>
          {personasHospedadas}{" "}
          {personasHospedadas === 1 ? "Huésped" : "Huéspedes"}
        </div>
      </div> */}

      {/* Fila Inferior: Desglose Financiero */}
      <div className="flex items-center justify-between pt-0.5 hidden">
        {saldoPendiente > 0 ? (
          <div className="flex flex-col">
            <span className="text-xxs text-gray-400 uppercase font-bold tracking-wider">
              Por Pagar
            </span>
            <span className="text-sm font-bold text-error">
              {formatPrecio(saldoPendiente)}
            </span>
          </div>
        ) : (
          <div className="text-xs font-semibold text-primary flex items-center gap-1">
            <svg
              className="w-4 h-4"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2.5}
                d="M5 13l4 4L19 7"
              />
            </svg>
            Liquidado
          </div>
        )}

        <div className="flex flex-col items-end">
          <span className="text-xxs text-gray-400 uppercase font-bold tracking-wider">
            Total
          </span>
          <span className="text-base font-black text-neutral">
            {formatPrecio(montoTotal)}
          </span>
        </div>
      </div>
    </div>
  );
};
