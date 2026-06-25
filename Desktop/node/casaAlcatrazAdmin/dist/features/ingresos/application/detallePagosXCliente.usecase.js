"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.BuscarPagosPorReservaUseCase = void 0;
class BuscarPagosPorReservaUseCase {
    ingresoRepository;
    constructor(ingresoRepository) {
        this.ingresoRepository = ingresoRepository;
    }
    async execute(reservacionId) {
        if (!reservacionId || isNaN(reservacionId)) {
            throw new Error("El ID de la reservación proporcionado no es válido");
        }
        const historialPagos = await this.ingresoRepository.obtenerPagosPorReservaId(reservacionId);
        return {
            success: true,
            data: historialPagos,
        };
    }
}
exports.BuscarPagosPorReservaUseCase = BuscarPagosPorReservaUseCase;
