import {
  CrearReservaInput,
  IReservaRepository,
  Reserva,
} from "../domain/reservas.respository";

// Definimos la respuesta al crear
export interface CrearReservaResponse {
  success: boolean;
  data: { id: number } & CrearReservaInput;
}
export class CrearReservaUseCase {
  constructor(private reservaRepository: IReservaRepository) {}

  async execute(input: CrearReservaInput): Promise<CrearReservaResponse> {
    const reservaCreada = await this.reservaRepository.create(input);

    return {
      success: true,
      data: reservaCreada,
    };
  }
}
