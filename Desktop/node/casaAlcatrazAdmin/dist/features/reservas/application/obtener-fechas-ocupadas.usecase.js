"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ObtenerFechasOcupadasUseCase = void 0;
class ObtenerFechasOcupadasUseCase {
    fechasRepository;
    constructor(fechasRepository) {
        this.fechasRepository = fechasRepository;
    }
    async execute() {
        return await this.fechasRepository.obtenerFechasOcupadas();
    }
}
exports.ObtenerFechasOcupadasUseCase = ObtenerFechasOcupadasUseCase;
