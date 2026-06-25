import { useDetalleReserva } from "../hooks/useDetalleReserva";
import { CardAnticipos } from "./detalles/CardAnticipo";
import { CardBalancePendiente } from "./detalles/CardBalancePendiente";
import { CardComisiones } from "./detalles/CardComisiones";
import { CardCostoTotal } from "./detalles/CardCostoTotal";
import { HeaderDetalles } from "./detalles/HeaderDetalles";
import { HistorialDePagos } from "./detalles/HistorialDePagos";
// import { FormularioPagos } from "../components/FormularioPagos";

interface DetalleReservaProps {
  reservaId: number | null;
}

export const DetalleReserva = ({ reservaId }: DetalleReservaProps) => {
  const {
    pagos,
    isLoading,
    datosCliente,
    costoTotal,
    totalPagadoCliente,
    balancePendiente,
    totalComisiones,
    refrescarPagos,
  } = useDetalleReserva(reservaId);
  if (!reservaId) {
    return (
      <div className="p-6 text-gray-400 italic">
        Selecciona una reserva para ver su estado de cuenta.
      </div>
    );
  }

  if (isLoading || !datosCliente) {
    return (
      <div className="p-6 text-gray-400 font-medium">
        Calculando balance de caja...
      </div>
    );
  }

  return (
    <>
      <div className="w-full h-full rounded-lg relative">
        <HeaderDetalles
          cliente={datosCliente}
          onPagoRegistrado={refrescarPagos}
        />
        <div className="w-full grid grid-cols-1 lg:grid-cols-2 gap-4 md:gap-6 items-start justify-center my-5">
          <CardCostoTotal total={costoTotal} />
          <CardAnticipos recibido={totalPagadoCliente} />
          <CardComisiones comisiones={totalComisiones} />
          <CardBalancePendiente pendiente={balancePendiente} />
        </div>
        <HistorialDePagos listadoPagos={pagos} />
      </div>
    </>
  );
};
