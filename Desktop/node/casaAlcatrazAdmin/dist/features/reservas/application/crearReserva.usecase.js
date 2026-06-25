"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CrearReservaUseCase = void 0;
class CrearReservaUseCase {
    reservaRepository;
    constructor(reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
    async execute(input) {
        const reservaCreada = await this.reservaRepository.create(input);
        return {
            success: true,
            data: reservaCreada,
        };
    }
}
exports.CrearReservaUseCase = CrearReservaUseCase;
