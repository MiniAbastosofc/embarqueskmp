"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ListarReservasUseCase = void 0;
class ListarReservasUseCase {
    reservaRepository;
    constructor(reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
    async execute() {
        const reservas = await this.reservaRepository.findAll();
        return {
            success: true,
            data: reservas,
        };
    }
}
exports.ListarReservasUseCase = ListarReservasUseCase;
