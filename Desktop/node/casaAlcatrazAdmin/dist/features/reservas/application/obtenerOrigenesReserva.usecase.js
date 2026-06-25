"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ObtenerOrigenesReservaUseCase = void 0;
class ObtenerOrigenesReservaUseCase {
    origenesReservaRepository;
    constructor(origenesReservaRepository) {
        this.origenesReservaRepository = origenesReservaRepository;
    }
    async execute() {
        const origenesReservas = await this.origenesReservaRepository.findAll();
        return {
            success: true,
            data: origenesReservas,
        };
    }
}
exports.ObtenerOrigenesReservaUseCase = ObtenerOrigenesReservaUseCase;
