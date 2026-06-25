import { Info, Wallet } from "lucide-react";
import { format, parseISO } from "date-fns";
import { es } from "date-fns/locale";
import { type IngresoApiResponse } from "../../../infrastructure/ingreso.repository";

interface HistorialDePagosProps {
  listadoPagos: IngresoApiResponse[];
}

export const HistorialDePagos = ({ listadoPagos }: HistorialDePagosProps) => {
  const formatPrecio = (v: number) =>
    new Intl.NumberFormat("es-MX", {
      style: "currency",
      currency: "MXN",
    }).format(v);

  const formatearFecha = (fechaStr: string) => {
    try {
      return format(parseISO(fechaStr), "dd 'de' MMMM, yyyy", { locale: es });
    } catch {
      return fechaStr;
    }
  };

  // Suma total de los montos abonados
  const totalPagado = listadoPagos.reduce(
    (sum, item) => sum + item.montoNetoPago,
    0,
  );

  return (
    <div className="bg-surfacecontainer w-full rounded-2xl p-5 shadow-sm border border-black/5 flex flex-col gap-4 mt-2">
      <h3 className="font-headline text-lg font-bold text-primary">
        Historial de Pagos
      </h3>

      {/* Contenedor de Filas Dinámicas */}
      <div className="flex flex-col gap-2 max-h-[35vh] overflow-y-auto pr-1">
        {listadoPagos.map((pago, index) => (
          <div
            key={`${pago.conceptoDePagoId}-${index}`}
            className="w-full flex flex-row items-center justify-between bg-white p-3 md:p-4 rounded-xl shadow-xs border border-gray-100 gap-4"
          >
            <div className="flex items-start gap-2 md:gap-4 min-w-0">
              {/* Icono de billetera */}
              <div className="bg-tertiary p-2 md:p-3 rounded-xl text-primary flex items-center justify-center shrink-0 mt-0.5">
                <Wallet className="w-4 h-4 md:w-5 h-5" />
              </div>

              {/* Textos Informativos */}
              <div className="flex flex-col min-w-0">
                <span className="font-body font-semibold text-neutral text-xs md:text-base truncate">
                  {pago.conceptoPago}
                </span>

                <span className="font-label text-[10px] md:text-xs text-neutral/60 mt-0.5">
                  {formatearFecha(pago.fechaInicio)}
                </span>

                {/* DESGLOSE DE COMISIÓN: Solo aparece si aplicaComisionPago es true */}
                {pago.aplicaComisionPago && (
                  <div className="flex items-center gap-1 mt-1.5 bg-amber-50 border border-amber-200 text-[10px] md:text-xs text-amber-700 px-2 py-0.5 rounded-md w-fit font-medium">
                    <Info className="w-3 h-3 text-amber-600 shrink-0" />
                    <span>
                      Pagado: {formatPrecio(pago.montoBrutoPago)} • Comis: -
                      {formatPrecio(pago.montoComisionPago)}
                    </span>
                  </div>
                )}
              </div>
            </div>

            {/* Monto Neto Recibido */}
            <div className="text-right shrink-0">
              <div className="font-body font-bold text-primary text-sm md:text-lg whitespace-nowrap">
                +{formatPrecio(pago.montoNetoPago)}
              </div>
              {pago.aplicaComisionPago && (
                <div className="text-[10px] text-gray-400 font-medium">
                  Neto recibido
                </div>
              )}
            </div>
          </div>
        ))}
      </div>

      <hr className="border-t-2 border-dashed border-neutral/20 w-full" />

      {/* Fila del Total */}
      <div className="w-full flex items-center justify-between px-2">
        <span className="font-headline text-sm font-bold text-neutral/80 tracking-wide">
          TOTAL EN CUENTA
        </span>
        <span className="font-body font-black text-primary text-lg md:text-xl">
          {formatPrecio(totalPagado)}
        </span>
      </div>
    </div>
  );
};
