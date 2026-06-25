"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.BuscarReservaPorIdUseCase = void 0;
// 2. Cambiamos el nombre de la clase para que coincida con tu ruteador
class BuscarReservaPorIdUseCase {
    reservaRepository;
    constructor(reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
    async execute(id) {
        const reserva = await this.reservaRepository.findById(id);
        if (!reserva) {
            throw new Error("La reserva solicitada no existe");
        }
        return {
            success: true,
            data: reserva,
        };
    }
}
exports.BuscarReservaPorIdUseCase = BuscarReservaPorIdUseCase;
