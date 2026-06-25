// 1. Definimos la estructura de datos que necesita tu tarjeta para renderizar
interface UltimasCapturasProps {
  nombreCliente: string;
  noches: number;
  precio: number;
  plataforma: string;
}

// 2. Recibimos las propiedades desestructuradas
export const UltimasCapturas = ({
  nombreCliente,
  noches,
  precio,
  plataforma,
}: UltimasCapturasProps) => {
  // Formateador de dinero nativo para que se vea como $18,200.00
  const formatPrecio = (valor: number) => {
    return new Intl.NumberFormat("es-MX", {
      style: "currency",
      currency: "MXN",
    }).format(valor);
  };

  return (
    <>
      <div className=" rounded-xl p-1">
        {/* Tu cabecera con icono */}

        {/* 3. Renderizado de la tarjeta con datos dinámicos */}
        <div className="px-2 pb-2">
          <div className="bg-white p-3 rounded-lg flex items-center justify-between gap-4 border-primary border-l-4 shadow-md">
            {/* Información del Huésped */}
            <div className="flex flex-col gap-0.5">
              <span className="font-medium text-neutral-dark">
                {nombreCliente}
              </span>
              <span className="text-xs text-gray-500 font-medium">
                {noches} {noches === 1 ? "Noche" : "Noches"} •{" "}
                {formatPrecio(precio)} MXN
              </span>
            </div>

            {/* Etiqueta de la Plataforma / Origen */}
            <div className="w-1/4 min-w-[70px]">
              <div className="bg-secondary/40 rounded-lg w-full text-[#745c00] text-center font-bold text-xs py-1 px-1.5 tracking-wide truncate">
                {plataforma}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
