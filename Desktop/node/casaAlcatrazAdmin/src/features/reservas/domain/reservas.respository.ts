export interface Reserva {
  id: number; // Mapea a FolioReserva
  nombreCliente: string; // Mapea a nombre
  apellidoCliente: string; // Mapea a apellido
  telefono?: string;
  email?: string;
  fechaInicio: Date; // Mapea a fecha_inicio
  fechaFin: Date; // Mapea a fecha_fin
  estadoDescripcion: string; // Mapea a la descripcion del estado
  origenNombre: string; // Mapea al nombre del origen
  montoTotal: number; // Mapea a monto_total
  totalDiasReservacion: number; // Mapea a total_dias_reservados
  personasHospedadas: number; // Mapea a personas_hospedadas
  montoPagado?: number;
  saldoPendiente?: number;
  estatusPago?: string;
  diasParaCheckIn?: number;
}

export interface ReservacionExtra {
  concepto: string;
  monto: number;
}

export interface CrearReservaInput {
  usuarioId: number; // @IdUsuario
  nombreCliente: string; // @NombreCliente
  apellidoCliente: string; // @ApellidoCliente
  telefono: string; // @Telefono
  email?: string; // @Email
  fechaInicio: Date; // @FechaInicio
  fechaFin: Date; // @FechaFin
  totalDiasReservacion: number; // @TotalDiasReservados
  montoTotal: number; // @MontoTotal
  nuevoOrigenNombre?: string;
  origenReservaId: number; // @OrigenReservaId
  personasHospedadas: number; // @PersonasHospedadas
  extras?: ReservacionExtra[];
}

export interface IReservaRepository {
  findById(id: number): Promise<Reserva | null>;
  findAll(): Promise<Reserva[]>;
  create(
    reserva: CrearReservaInput,
  ): Promise<{ id: number } & CrearReservaInput>;
  // update(
  //   id: number,
  //   reserva: Partial<Omit<Reserva, "id">>,
  // ): Promise<Reserva | null>;
  // delete(id: number): Promise<void>;
}

export interface ListarReservasResponse {
  success: boolean;
  data: Reserva[];
}
